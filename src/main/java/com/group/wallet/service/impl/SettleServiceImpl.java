package com.group.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.service.impl.PushService;
import com.group.wallet.mapper.*;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.*;
import com.group.wallet.service.SettleService;
import com.group.wallet.service.WalletService;
import com.group.wallet.util.StringReplaceUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class SettleServiceImpl implements SettleService {

    protected static Logger logger = LoggerFactory.getLogger(SettleServiceImpl.class);

    @Value("${project.name}")
    private String projectName;

    @Autowired
    private PushService pushService;
    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletDeductRateMapper walletDeductRateMapper;
    @Autowired
    private WalletIncomeRecordsMapper walletIncomeRecordsMapper;
    @Autowired
    private CommonMessagesMapper commonMessagesMapper;
    @Autowired
    private WalletChannelMapper walletChannelMapper;


    @Override
    public void calculateTradeProfit(WalletTradeRecords tradeRecords) {
        String orderNum = tradeRecords.getOrderNo();
        BigDecimal realTradeMoney = tradeRecords.getRealTradeMoney();//实际交易金额
        Long channelId = tradeRecords.getChannelId();//通道id
        String settleType = tradeRecords.getSettleType();//结算类型 t0，t1
        Long userId = tradeRecords.getUserId();//用户id
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null)
            return;
        String userType = userInfo.getUserType();//用户类型
        Long recommonId = userInfo.getRecommonId();//用户推荐人id

        Map<String, BigDecimal> map = getUserTypeRate(channelId, userType, settleType);//用户类型对应的汇率
        BigDecimal rate = map.get("rate");
        if(rate==null || rate.compareTo(BigDecimal.ZERO)<=0)
            return;

        WalletChannel channel = walletChannelMapper.selectByPrimaryKey(channelId);
        String channelType = channel.getChannelType();
        IncomeType incomeType = null;
        if(ChannelType.刷卡渠道.getValue().equals(channelType)){
            incomeType = IncomeType.刷卡收益;
            //刷卡渠道补分润
            calculateLKLProfit(userInfo, rate, realTradeMoney, orderNum);
        }else {
            incomeType = IncomeType.快捷分润;
        }

        String realNmae = StringReplaceUtil.userNameReplaceWithStar2(userInfo.getRealName());
        String phone = StringReplaceUtil.phoneReplaceWithStar(userInfo.getPhone());
        String userType1 = UserType.getEnumName(userInfo.getUserType());
        String desc = realNmae + " " + phone + " " + userType1;
        //计算交易分润
        calculateChannelProfit(desc,recommonId, rate, rate, realTradeMoney, incomeType, orderNum, userInfo, channelId, settleType, new ArrayList<Long>());

        //计算直推返点
        calculateRecommenProfit(recommonId, realTradeMoney, 0, orderNum, userInfo);
    }

    @Override
    public void calculateUpgrdeProfit(WalletUpgradeOrder upgradeOrder) {
        String orderNum = upgradeOrder.getOrderNum();
        Long userId = upgradeOrder.getUserId();
        String originalType = upgradeOrder.getOriginalType();
        String type = upgradeOrder.getType();
        BigDecimal orderPrice = upgradeOrder.getOrderPrice();
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(userInfo!=null && userInfo.getRecommonId()!=null){
            Long recommonId = userInfo.getRecommonId();
            String desc = UserType.getEnumName(originalType)+"升级"+UserType.getEnumName(type)+"¥"+orderPrice;
            calculateUpgrdeProfit(recommonId, orderPrice, 0, 0, orderNum, userInfo, desc);
        }
    }

    /**
     * 计算交易分润
     * @param userId 上级推荐人id
     * @param baseRate 交易用户的扣率
     * @param rate 扣率
     * @param realTradeMoney 实际交易金额
     * @param incomeType
     */
    private void calculateChannelProfit(String desc,Long userId, BigDecimal baseRate, BigDecimal rate, BigDecimal realTradeMoney, IncomeType incomeType, String orderNum, WalletUserInfo sourceUser, Long channelId, String settleType, List<Long> userIds){
        //如果计算过就退出
        if(userIds.contains(userId))
            return;

        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null)
            return;
        
        String userType = userInfo.getUserType();
        Map<String, BigDecimal> map = getUserTypeRate(channelId, userType, settleType);
        BigDecimal rate2 = map.get("rate");//上级推荐人费率
        if(rate2==null || rate2.compareTo(BigDecimal.ZERO)<=0)
            return;

        if(baseRate.compareTo(rate2)>0){
            BigDecimal balanceRate = baseRate.subtract(rate2);
            if(balanceRate.compareTo(BigDecimal.ZERO)>0){
                BigDecimal balanceMoney = balanceRate.multiply(realTradeMoney).multiply(new BigDecimal("0.01"));
                updateProfit(userInfo, balanceMoney, incomeType, orderNum, sourceUser, desc);
                userIds.add(userId);
            }

            baseRate = rate2;
        }

        Long recommonId = userInfo.getRecommonId();
        calculateChannelProfit(desc,recommonId, baseRate, rate2, realTradeMoney, incomeType, orderNum, sourceUser, channelId, settleType, userIds);
    }

    /**
     * 计算直推分润
     * @param userId
     * @param realTradeMoney
     */
    private void calculateRecommenProfit(Long userId, BigDecimal realTradeMoney, int level, String orderNum, WalletUserInfo sourceUser){
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null)
            return;

        String userType = userInfo.getUserType();
        if(UserType.超级合伙人.getValue().equals(userType)){
            level++;
            if(level==2){
                String realNmae = StringReplaceUtil.userNameReplaceWithStar2(sourceUser.getRealName());
                String phone = StringReplaceUtil.phoneReplaceWithStar(sourceUser.getPhone());
                String userType1 = UserType.getEnumName(sourceUser.getUserType());

                String desc = realNmae + " " + phone + " " + userType1;
                BigDecimal money = realTradeMoney.multiply(new BigDecimal("0.0001"));
                updateProfit(userInfo, money, IncomeType.直推返点, orderNum, sourceUser, desc);
                return;
            }
        }

        Long recommenId = userInfo.getRecommonId();
        calculateRecommenProfit(recommenId, realTradeMoney, level, orderNum, sourceUser);
    }

    /**
     * 计算升级分润
     * @param userId
     * @param orderMoney
     * @param level
     * @param level1
     */
    private void calculateUpgrdeProfit(Long userId, BigDecimal orderMoney, int level, int level1, String orderNum, WalletUserInfo sourceUser, String desc){
        logger.info("===计算升级分润===");
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null)
            return;
        level++;//推荐人级别

        String userType = userInfo.getUserType();
        if(level==1){
            if(UserType.超级合伙人.getValue().equals(userType)){
                level1++;
                if(level1==1){
                    BigDecimal money = orderMoney.multiply(new BigDecimal("0.3"));
                    updateProfit(userInfo, money, IncomeType.升级收益, orderNum, sourceUser, desc);
                }
            }else {
                BigDecimal money = orderMoney.multiply(new BigDecimal("0.2"));
                updateProfit(userInfo, money, IncomeType.升级收益, orderNum, sourceUser, desc);
            }
        }
        else if(level==2){
            if(UserType.超级合伙人.getValue().equals(userType)){
                level1++;
                if(level1==1){
                    BigDecimal money = orderMoney.multiply(new BigDecimal("0.3"));
                    updateProfit(userInfo, money, IncomeType.升级收益, orderNum, sourceUser, desc);
                }
            }else {
                BigDecimal money = orderMoney.multiply(new BigDecimal("0.1"));
                updateProfit(userInfo, money, IncomeType.升级收益, orderNum, sourceUser, desc);
            }
        }
        else {
            if(UserType.超级合伙人.getValue().equals(userType)){
                level1++;
                if(level1==1){
                    BigDecimal money = orderMoney.multiply(new BigDecimal("0.3"));
                    updateProfit(userInfo, money, IncomeType.升级收益, orderNum, sourceUser, desc);
                }
            }
        }

        Long recommonId = userInfo.getRecommonId();
        calculateUpgrdeProfit(recommonId, orderMoney, level, level1, orderNum, sourceUser, desc);
    }

    /**
     * 拉卡拉补刷卡分润
     * @param userInfo
     * @param rate
     * @param realTradeMoney
     * @param orderNum
     * update by zcc 2019-01-04
     */
    private void calculateLKLProfit(WalletUserInfo userInfo, BigDecimal rate, BigDecimal realTradeMoney, String orderNum){
    	String realNmae = StringReplaceUtil.userNameReplaceWithStar2(userInfo.getRealName());
        String phone = StringReplaceUtil.phoneReplaceWithStar(userInfo.getPhone());
        String userType1 = UserType.getEnumName(userInfo.getUserType());
        String desc = realNmae + " " + phone + " " + userType1;
        
        BigDecimal rate2 = new BigDecimal("0.68");
        BigDecimal balanceRate = rate2.subtract(rate);
        if(balanceRate.compareTo(BigDecimal.ZERO)>0){
            BigDecimal balanceMoney = balanceRate.multiply(realTradeMoney).multiply(new BigDecimal("0.01"));
            updateProfit(userInfo, balanceMoney, IncomeType.刷卡收益, orderNum, userInfo, desc);
        }
    }

    /**
     * 更新分润
     * @param userInfo
     * @param profitMoney
     * @param incomeType
     * @param orderNum
     * @param sourceUser
     */
    @Override
    public void updateProfit(WalletUserInfo userInfo, BigDecimal profitMoney, IncomeType incomeType, String orderNum, WalletUserInfo sourceUser, String desc){
        logger.info("===更新分润："+profitMoney+"===");

        String[] states = {UserState.已认证储蓄卡.getValue(), UserState.待审核.getValue(), UserState.已开通.getValue() };
        if(!Arrays.asList(states).contains(userInfo.getState()))
            return;

        profitMoney = profitMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
        if(profitMoney.compareTo(BigDecimal.ZERO)<=0)
            return;

        //获取最近一笔未还清的预支
        WalletAdvance advance = walletService.getNewestAdvance(userInfo.getId());

        BigDecimal profitBalance = userInfo.getProfitBalance()==null?BigDecimal.ZERO:userInfo.getProfitBalance();//用户剩余分润金额
        BigDecimal profitBalance2 = null;
        if(advance != null){
            //有未还的预支金额，分润余额不更新
            profitBalance2 = profitBalance;
        }else {
            profitBalance2 = profitBalance.add(profitMoney);

            //更新分润余额
            WalletUserInfo userInfo1 = new WalletUserInfo();
            userInfo1.setId(userInfo.getId());
            userInfo1.setProfitBalance(profitBalance2);
            walletUserInfoMapper.updateByPrimaryKeySelective(userInfo1);
        }

        //添加分润记录
        WalletIncomeRecords incomeRecords = new WalletIncomeRecords();
        incomeRecords.setUserId(userInfo.getId());
        incomeRecords.setFromUserId(sourceUser.getId());
        incomeRecords.setOrderNum(orderNum);
        incomeRecords.setType(incomeType.getValue());
        incomeRecords.setAmount(profitMoney);//分润/提现金额
        incomeRecords.setPristineAmount(profitBalance);//原始金额
        incomeRecords.setSurplusAmount(profitBalance2);//剩余金额
        incomeRecords.setState(IncomeRecordsState.已到账.getValue());
        incomeRecords.setDescp(desc);
        incomeRecords.setCreateTime(new Date());
        walletIncomeRecordsMapper.insertSelective(incomeRecords);

        //推送
        if(sourceUser!=null){
            String realName = sourceUser.getRealName();
            String content = "";
            DecimalFormat df = new DecimalFormat("#.##");
            if(IncomeType.升级收益.getValue().equals(incomeType.getValue())){
                content = StringReplaceUtil.userNameReplaceWithStar2(realName)+"为你产生了一笔"+df.format(profitMoney)+"的升级收益已到您的账户中，请在"+projectName+"APP内我的收益查看或提现。";
            }else {
                content = StringReplaceUtil.userNameReplaceWithStar2(realName)+"为你产生了一笔"+df.format(profitMoney)+"的分润净收入已到您的账户中，请在"+projectName+"APP内我的收益查看或提现。";
            }

            CommonMessages messages = new CommonMessages();
            messages.setApp(projectName);
            messages.setUserId(userInfo.getId());
            messages.setType(MessageType.分润消息.getValue());
            messages.setTitle(StringUtils.substring(content, 0, 20));
            messages.setContent(content);
            messages.setCreateTime(new Date());
            commonMessagesMapper.insertSelective(messages);

            String authority = userInfo.getAuthority();
            if(StringUtils.isNotEmpty(authority)){
                JSONObject jsonObject = JSON.parseObject(authority);
                if(jsonObject!=null){
                    String profit_msg = jsonObject.getString("profit_msg");
                    String profit_voice = jsonObject.getString("profit_voice");
                    if("1".equals(profit_msg)){
                        if("1".equals(profit_voice)){
                            Map<String, String> extras = new HashMap<>();
                            extras.put("voice", "1");
                            extras.put("message", "您好，您有一笔分润到帐");
                            pushService.push(userInfo.getId()+"", content, "", extras);
                        }else {
                            pushService.push(userInfo.getId()+"", content, "", null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Map<String, BigDecimal> getUserTypeRate(Long channelId, String userType, String settleType){
        Map<String, BigDecimal> map = new HashMap<>();
        if(channelId==null || StringUtils.isEmpty(userType) || StringUtils.isEmpty(settleType))
            return map;

        WalletDeductRate deductRate = new WalletDeductRate();
        deductRate.setDeductType(DeductType.商户.getValue());
        deductRate.setChannelId(channelId);
        deductRate.setUserType(userType);
        deductRate.setSettleType(settleType);

        List<WalletDeductRate> deductRates = walletDeductRateMapper.select(deductRate);
        if(deductRates!=null && deductRates.size()>0){
            deductRate = deductRates.get(0);
            BigDecimal rate = deductRate.getDeductRate()==null?BigDecimal.ZERO:deductRate.getDeductRate();
            BigDecimal poundage = deductRate.getPoundage()==null?BigDecimal.ZERO:deductRate.getPoundage();
            map.put("rate", rate);
            map.put("poundage", poundage);
        }

        return map;
    }

}
