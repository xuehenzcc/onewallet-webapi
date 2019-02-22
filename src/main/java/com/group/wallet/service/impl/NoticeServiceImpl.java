package com.group.wallet.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.core.service.impl.PushService;
import com.group.wallet.channel.ChannelPay;
import com.group.wallet.channel.FactoryBuilder;
import com.group.wallet.mapper.CommonMessagesMapper;
import com.group.wallet.mapper.WalletAdvanceBillMapper;
import com.group.wallet.mapper.WalletAdvanceMapper;
import com.group.wallet.mapper.WalletChannelMapper;
import com.group.wallet.mapper.WalletIncomeRecordsMapper;
import com.group.wallet.mapper.WalletReceiveOrderMapper;
import com.group.wallet.mapper.WalletRefundOrderMapper;
import com.group.wallet.mapper.WalletTradeRecordsMapper;
import com.group.wallet.mapper.WalletUpgradeOrderMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.CommonMessages;
import com.group.wallet.model.WalletReceiveOrder;
import com.group.wallet.model.WalletRefundOrder;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUpgradeOrder;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.AdvanceState;
import com.group.wallet.model.enums.IncomeRecordsState;
import com.group.wallet.model.enums.MessageType;
import com.group.wallet.model.enums.PaymentType;
import com.group.wallet.model.enums.RefundOrderState;
import com.group.wallet.model.enums.TradeState;
import com.group.wallet.model.zzlm.ZzlmAdvance;
import com.group.wallet.model.zzlm.ZzlmAdvanceBill;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.model.zzlm.ZzlmIncomeRecords;
import com.group.wallet.service.NoticeService;
import com.group.wallet.service.SettleService;
import com.group.wallet.util.StringReplaceUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class NoticeServiceImpl implements NoticeService {

    protected static Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Value("${project.name}")
    private String projectName;

    @Autowired
    private SettleService settleService;
    @Autowired
    private PushService pushService;

    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;
    @Autowired
    private WalletChannelMapper walletChannelMapper;
    @Autowired
    private WalletUpgradeOrderMapper walletUpgradeOrderMapper;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletIncomeRecordsMapper walletIncomeRecordsMapper;
    @Autowired
    private WalletAdvanceMapper walletAdvanceMapper;
    @Autowired
    private WalletRefundOrderMapper walletRefundOrderMapper;
    @Autowired
    private WalletAdvanceBillMapper walletAdvanceBillMapper;
    @Autowired
    private CommonMessagesMapper commonMessagesMapper;
    @Autowired
    private WalletReceiveOrderMapper walletReceiveOrderMapper;


    @Override
    public boolean checkSign(String channelNo ,Map<String, Object> params) throws Exception{
        ZzlmChannel channel = new ZzlmChannel();
        channel.setNumber(channelNo);
        channel = walletChannelMapper.selectOne(channel);

        ChannelPay quickPay = FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);
        boolean result = quickPay.checkSign(channel, params);
        return result;
    }

    @Override
    @Transactional
    public void paySuccessNotice(String orderNumner) {
        logger.info("===订单号："+orderNumner+"===");
        //更新订单状态
        WalletTradeRecords tradeRecords = walletTradeRecordsMapper.selectByOrderNo(orderNumner);
        String tradeState = tradeRecords.getTradeState();
        if(TradeState.支付成功.getValue().equals(tradeState))
            return;

        tradeRecords = walletTradeRecordsMapper.selectByPrimaryKey(tradeRecords.getId());
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal realTradeMoney1 = tradeRecords.getRealTradeMoney();
        BigDecimal rate = tradeRecords.getRate()==null?zero:tradeRecords.getRate();
        BigDecimal rateMoney = realTradeMoney1.multiply(rate).multiply(new BigDecimal("0.01"));
        BigDecimal platformPoundage = tradeRecords.getPlatformPoundage()==null?zero:tradeRecords.getPlatformPoundage();
        //到账金额
        BigDecimal arrivalMoney = realTradeMoney1.subtract(rateMoney).subtract(platformPoundage);

        WalletTradeRecords tradeRecords1 = new WalletTradeRecords();
        tradeRecords1.setId(tradeRecords.getId());
        tradeRecords1.setTradeState(TradeState.支付成功.getValue());
        tradeRecords1.setArrivalAmount(arrivalMoney);
        tradeRecords1.setPayTime(new Date());
        walletTradeRecordsMapper.updateByPrimaryKeySelective(tradeRecords1);

        //计算分润
        try {
            settleService.calculateTradeProfit(tradeRecords);
        } catch (Exception e) {
            logger.error("计算分润失败：", e);
        }

        //交易成功通知
        Long userId = tradeRecords.getUserId();
        BigDecimal realTradeMoney = tradeRecords.getRealTradeMoney();
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String realName = StringReplaceUtil.userNameReplaceWithStar2(userInfo.getRealName());
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH时mm分");
        String content = "商户"+realName+"于"+sdf.format(new Date())+"在"+projectName+"成功收款"+realTradeMoney+"元，请在我的账单中查询或在我的账户里提现。";
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
                String receipt_msg = jsonObject.getString("receipt_msg");
                String receipt_voice = jsonObject.getString("receipt_voice");
                if("1".equals(receipt_msg)){
                    if("1".equals(receipt_voice)){
                        Map<String, String> extras = new HashMap<>();
                        extras.put("voice", "1");
                        extras.put("message", "您好，您有一笔交易成功");
                        pushService.push(userInfo.getId()+"", content, "", extras);
                    }else {
                        pushService.push(userInfo.getId()+"", content, "", null);
                    }
                }
            }
        }
    }

    @Override
    public void paymentNotice(String outTradeNo) {
        if(StringUtils.contains(outTradeNo, PaymentType.分润代付.getValue())){
            Example example = new Example(ZzlmIncomeRecords.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("orderNum", outTradeNo);

            ZzlmIncomeRecords incomeRecords = new ZzlmIncomeRecords();
            incomeRecords.setState(IncomeRecordsState.已到账.getValue());
            walletIncomeRecordsMapper.updateByExampleSelective(incomeRecords, example);
        }
        if(StringUtils.contains(outTradeNo, PaymentType.预支收益代付.getValue())){
            Example example = new Example(ZzlmAdvance.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("channelOrderNum", outTradeNo);

            ZzlmAdvance advance = new ZzlmAdvance();
            advance.setState(AdvanceState.已到账.getValue());
            walletAdvanceMapper.updateByExampleSelective(advance, example);
        }
    }

    @Override
    @Transactional
    public void upgradePayNotice(String orderNum) {
        logger.info("升级付款成功通知："+orderNum);
        WalletUpgradeOrder upgradeOrder = new WalletUpgradeOrder();
        upgradeOrder.setOrderNum(orderNum);
        upgradeOrder = walletUpgradeOrderMapper.selectOne(upgradeOrder);
        if(upgradeOrder==null){
            throw new ServiceException("2000","订单号异常");
        }

        String state = upgradeOrder.getState();
        if("已支付".equals(state))
            return;

        //更新订单状态为"已支付"
        WalletUpgradeOrder upgradeOrder1 = new WalletUpgradeOrder();
        upgradeOrder1.setId(upgradeOrder.getId());
        upgradeOrder1.setState("已支付");
        walletUpgradeOrderMapper.updateByPrimaryKeySelective(upgradeOrder1);

        //修改用户级别
        Long userId = upgradeOrder.getUserId();
        String type = upgradeOrder.getType();
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setUserType(type);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);

        //更新升级分润
        settleService.calculateUpgrdeProfit(upgradeOrder);

        //注册子商户
        /*try {
            List<WalletChannel> channels = walletChannelMapper.selectAll();
            for (int i = 0; i < channels.size(); i++) {
                Long channelId = channels.get(i).getId();
                payService.registSubMerchant(channelId, userId, SettleType.T0.getValue());
                payService.registSubMerchant(channelId, userId, SettleType.T1.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void buyPOSMachineOrderNotice(String orderNum) {
        WalletReceiveOrder receiveOrder = new WalletReceiveOrder();
        receiveOrder.setOrderNum(orderNum);
        receiveOrder = walletReceiveOrderMapper.selectOne(receiveOrder);

        if("未支付".equals(receiveOrder.getState())){
            WalletReceiveOrder receiveOrder1 = new WalletReceiveOrder();
            receiveOrder1.setId(receiveOrder.getId());
            receiveOrder1.setState("支付成功");
            receiveOrder1.setPayTime(new Date());
            walletReceiveOrderMapper.updateByPrimaryKeySelective(receiveOrder1);
        }
    }

    @Override
    public void refundOrderNotice(String orderNum) {
        WalletRefundOrder refundOrder = new WalletRefundOrder();
        refundOrder.setOrderNum(orderNum);
        refundOrder = walletRefundOrderMapper.selectOne(refundOrder);
        if(refundOrder==null)
            return;

        if(RefundOrderState.支付成功.getValue().equals(refundOrder.getState()))
            return;

        Long advanceId = refundOrder.getAdvanceId();
        Long userId = refundOrder.getUserId();
        BigDecimal amount = refundOrder.getAmount();//提前还款金额
        BigDecimal manageAmount = refundOrder.getManageAmount()==null?BigDecimal.ZERO:refundOrder.getManageAmount();//账户管理费
        BigDecimal penalAmount = refundOrder.getPenalamount()==null?BigDecimal.ZERO:refundOrder.getPenalamount();//违约金
        BigDecimal noRefundAmount = refundOrder.getNoRefundAmount()==null?BigDecimal.ZERO:refundOrder.getNoRefundAmount();//未还预支金额
        BigDecimal noRefundAmount1 = BigDecimal.ZERO;

        ZzlmAdvance advance = walletAdvanceMapper.selectByPrimaryKey(advanceId);
        BigDecimal baseAmount = advance.getBaseAmount();
        int dayCount = advance.getDayCount();

        //更新预支收益
        ZzlmAdvance advance1 = new ZzlmAdvance();
        advance1.setId(advanceId);
        if(amount.compareTo(manageAmount.add(penalAmount).add(noRefundAmount))<0){
            noRefundAmount1 = noRefundAmount.add(manageAmount).add(penalAmount).subtract(amount);
            advance1.setNoRefundAmount(noRefundAmount1);
        }
        if(amount.compareTo(manageAmount.add(penalAmount).add(noRefundAmount))==0){
            advance1.setNoRefundAmount(noRefundAmount1);
            advance1.setState(AdvanceState.已还清.getValue());
        }
        walletAdvanceMapper.updateByPrimaryKeySelective(advance1);

        //添加还款明细记录
        ZzlmAdvanceBill advanceBill = new ZzlmAdvanceBill();
        advanceBill.setAdvanceId(advanceId);
        advanceBill.setUserId(userId);
        advanceBill.setBaseAmount(baseAmount);
        advanceBill.setYesProfit(BigDecimal.ZERO);
        advanceBill.setManageAmount(manageAmount);
        advanceBill.setPenalAmount(penalAmount);
        advanceBill.setNoRefundAmount(noRefundAmount1);
        advanceBill.setRefundAmount(amount);
        advanceBill.setDayCount(dayCount);
        advanceBill.setCreateTime(new Date());
        walletAdvanceBillMapper.insertSelective(advanceBill);
    }

}
