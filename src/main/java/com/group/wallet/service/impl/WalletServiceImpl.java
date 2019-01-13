package com.group.wallet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.core.service.ImageService;
import com.group.utils.ImageUtils;
import com.group.utils.OrderUtils;
import com.group.wallet.channel.payment.PaymentFactory;
import com.group.wallet.channel.payment.PaymentPay;
import com.group.wallet.channel.pos.lkl.impl.LklPosPayImpl;
import com.group.wallet.mapper.*;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.*;
import com.group.wallet.service.PayService;
import com.group.wallet.service.SettleService;
import com.group.wallet.service.WalletService;
import com.group.wallet.util.StringReplaceUtil;
import com.sun.rowset.internal.Row;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class WalletServiceImpl implements WalletService {

    private final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Value("${project.name}")
    private String projectName;

    @Autowired
    private PayService payService;
    @Autowired
    private LklPosPayImpl lklPosPay;

    @Autowired
    private WalletReceiveOrderMapper WalletReceiveOrderMapper;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletIncomeRecordsMapper walletIncomeRecordsMapper;
    @Autowired
    private WalletAdvanceMapper walletAdvanceMapper;
    @Autowired
    private WalletAdvanceBillMapper walletAdvanceBillMapper;
    @Autowired
    private WalletUpgradeOrderMapper walletUpgradeOrderMapper;
    @Autowired
    private CommonAdvertisingMapper commonAdvertisingMapper;
    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;
    @Autowired
    private WalletRefundOrderMapper walletRefundOrderMapper;
    @Autowired
    private WalletModuleMapper walletModuleMapper;
    @Autowired
    private WalletChannelMapper walletChannelMapper;


    @Override
    public Map<String, Object> createPOSMachineOrder(WalletReceiveOrder receiveOrder) {
        String orderNum = OrderType.购买刷卡机订单.getValue()+OrderUtils.creatOrderNum();
        receiveOrder.setOrderNum(orderNum);
        receiveOrder.setState("未支付");
        receiveOrder.setCreateTime(new Date());
        WalletReceiveOrderMapper.insertSelective(receiveOrder);

        Map<String,Object> result = new HashMap<>();
        result.put("orderNum",orderNum);
        return result;
    }

    @Override
    public void bindPOSMachine(Long userId, String sn) {
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setSn(sn);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public Map<String, Object> getMyIncome(Long userId) {
        Map<String, Object> result = new HashMap<>();

        //当前可提现余额
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        BigDecimal profitBalance = userInfo.getProfitBalance();//分润余额

        //共累计收益
        BigDecimal totalIncome = walletIncomeRecordsMapper.selectTotalIncome(userId);

        //今入累计收益
        BigDecimal todayIncome = walletIncomeRecordsMapper.selectTodayIncome(userId);

        //昨日累计收益
        BigDecimal yestodayIncome = walletIncomeRecordsMapper.selectYestodayIncome(userId);

        result.put("profitBalance",profitBalance);
        result.put("totalIncome",totalIncome);
        result.put("todayIncome",todayIncome);
        result.put("yestodayIncome",yestodayIncome);
        return result;
    }

    @Override
    public Map<String, Object> incomeRecords(Long userId, String startDate, String endDate) {
        Map<String,Object> result = new HashMap<>();
        BigDecimal totalMoney = BigDecimal.ZERO;

        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
            list = walletIncomeRecordsMapper.getSevenDayIncome(userId);
        }else {
            list = walletIncomeRecordsMapper.getTimeIncome(userId,startDate,endDate);
        }

        for (int i = 0; i < list.size(); i++) {
            BigDecimal money = (BigDecimal) list.get(i).get("amount");
            money = money==null?BigDecimal.ZERO:money;
            totalMoney = totalMoney.add(money);
        }

        result.put("totalMoney",totalMoney);
        result.put("list",list);

        return result;
    }

    @Override
    public List<WalletIncomeRecords> allIncomeRecords(Long userId, String type, Integer pageNo) {
        int start = (pageNo-1)*20;
        int limit = 20;
        RowBounds rowBounds = new RowBounds(start,limit);

        Example example = new Example(WalletIncomeRecords.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        if("A0".equals(type)){
            List<String> types = new ArrayList<>();
            types.add("A1");
            types.add("A2");
            types.add("A3");
            criteria.andIn("type", types);
        }else if ("A67".equals(type)){
            List<String> types = new ArrayList<>();
            types.add("A6");
            types.add("A7");
            criteria.andIn("type", types);
        }else {
            criteria.andEqualTo("type", type);
        }
        example.setOrderByClause("id desc");

        List<WalletIncomeRecords> list = walletIncomeRecordsMapper.selectByExampleAndRowBounds(example,rowBounds);
        return list;
    }

    @Override
    public List<WalletTradeRecords> proceedsRecords(Long userId, Integer pageNo) {
        List<String> states = new ArrayList<>();
        //states.add(TradeState.支付中.getValue());
        states.add(TradeState.支付成功.getValue());

        Example example = new Example(WalletTradeRecords.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andIn("tradeState", states);
        example.setOrderByClause("id desc");

        RowBounds rowBounds = new RowBounds((pageNo-1)*20, 20);

        List<WalletTradeRecords> list = walletTradeRecordsMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTradeState(TradeState.getEnumName(list.get(i).getTradeState()));
        }
        return list;
    }

    @Override
    public void checkTakeOut(Long userId) {
        WalletAdvance advance1 = getNewestAdvance(userId);
        if(advance1!=null){
            throw new ServiceException("2000","预支收益还清才可提现");
        }
    }

    @Override
    @Transactional
    public Map<String, Object> takeOutIncom(HttpServletRequest request, Long userId, BigDecimal amount) throws Exception{
        Map<String, Object> result = new HashMap<>();
        WalletUserInfo userInfo = walletUserInfoMapper.selectUserInfoForUpdate(userId);

        //判断提现条件
        WalletAdvance advance1 = getNewestAdvance(userId);
        if(advance1!=null){
            throw new ServiceException("2000","预支收益还清才可提现");
        }

        String state = userInfo.getState();
        if(!UserState.已认证储蓄卡.getValue().equals(state) && !UserState.已开通.getValue().equals(state) && !UserState.待审核.getValue().equals(state)){
            throw new ServiceException("2000", "您还未认证储蓄卡");
        }
        BigDecimal profitBalance = userInfo.getProfitBalance()==null?BigDecimal.ZERO:userInfo.getProfitBalance();//分润余额

        if(amount.compareTo(profitBalance)>0){
            throw new ServiceException("2000","提现金额大于余额");
        }

        //修改用户分润余额
        WalletUserInfo userInfo1 = new WalletUserInfo();
        userInfo1.setId(userId);
        userInfo1.setProfitBalance(profitBalance.subtract(amount));
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo1);

        //添加提现记录
        WalletIncomeRecords incomeRecords = new WalletIncomeRecords();
        incomeRecords.setUserId(userId);
        incomeRecords.setOrderNum(OrderUtils.creatOrderNum()+ PaymentType.分润代付.getValue());
        incomeRecords.setType(IncomeType.提现.getValue());
        incomeRecords.setAmount(amount);
        incomeRecords.setPristineAmount(profitBalance);
        incomeRecords.setSurplusAmount(profitBalance.subtract(amount));
        incomeRecords.setState(IncomeRecordsState.已提交.getValue());
        incomeRecords.setCreateTime(new Date());
        walletIncomeRecordsMapper.insertSelective(incomeRecords);

        BigDecimal takeAmount = amount;//可提现金额
        BigDecimal manageAmount = null;//管理费


        //计算管理费
        List<String> userTypes = new ArrayList<>();
        userTypes.add(UserType.高级合伙人.getValue());
        userTypes.add(UserType.初级合伙人.getValue());
        userTypes.add(UserType.省级代理商.getValue());
        userTypes.add(UserType.市级代理商.getValue());

        /*String userType = userInfo.getUserType();
        if(userTypes.contains(userType)){
            Long recommenId = userInfo.getRecommonId();
            WalletUserInfo recommener = walletUserInfoMapper.selectByPrimaryKey(recommenId);
            if(recommener!=null){
                String userType1 = recommener.getUserType();
                if(userType.equals(userType1)){
                    takeAmount = amount.multiply(new BigDecimal("0.92"));
                    manageAmount = amount.multiply(new BigDecimal("0.08"));
                    settleService.updateProfit(recommener, manageAmount, IncomeType.管理费, null, userInfo, null);
                }
            }
        }*/

        //代付（扣除2元提现费）
        payService.payment(request, userId, "YINSHENG", incomeRecords.getOrderNum(), takeAmount.subtract(new BigDecimal("2")));

        result.put("name", userInfo.getRealName());
        result.put("phone", userInfo.getPhone());
        result.put("amount", amount);
        result.put("poundage", new BigDecimal("2"));
        result.put("realityAmount", takeAmount.subtract(new BigDecimal("2")));
        result.put("manageAmount", manageAmount);
        result.put("cardNo", userInfo.getSettleCardNo());
        result.put("takeTime", new Date());
        return result;
    }

    @Override
    public Map<String, Object> getMyAdvanceIncome(Long userId) {
        Map<String, Object> map = new HashMap<>();
        //可预支金额
        BigDecimal advanceProfit = walletIncomeRecordsMapper.getAdvanceProfit(userId);
        if(advanceProfit==null) advanceProfit = BigDecimal.ZERO;

        //已预支金额
        BigDecimal noRefundAmount = BigDecimal.ZERO;

        Example example = new Example(WalletAdvance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(0,1);
        List<WalletAdvance> list = walletAdvanceMapper.selectByExampleAndRowBounds(example, rowBounds);
        if(list.size()>0){
            WalletAdvance advance = list.get(0);
            String state = advance.getState();
            if(AdvanceState.已提交.getValue().equals(state)){
                advanceProfit = BigDecimal.ZERO;
                noRefundAmount = BigDecimal.ZERO;
            }
            else if(AdvanceState.审核不通过.getValue().equals(state)){
                noRefundAmount = BigDecimal.ZERO;
            }
            else {
                noRefundAmount = advance.getNoRefundAmount();
                if(noRefundAmount.compareTo(BigDecimal.ZERO)>0){
                    advanceProfit = BigDecimal.ZERO;
                }
            }
        }

        map.put("advanceProfit",advanceProfit.compareTo(new BigDecimal(3000))>0?new BigDecimal(3000):advanceProfit);
        map.put("noRefundAmount",noRefundAmount);

        return map;
    }

    /**
     * 获取最新一笔未还预支
     * @param userId
     * @return
     */
    @Override
    public WalletAdvance getNewestAdvance(Long userId){
        List<String> states = new ArrayList<>();
        states.add(AdvanceState.已提交.getValue());
        states.add(AdvanceState.审核不通过.getValue());
        states.add(AdvanceState.已还清.getValue());

        Example example = new Example(WalletAdvance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andNotIn("state", states);
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(0,1);
        List<WalletAdvance> list = walletAdvanceMapper.selectByExampleAndRowBounds(example, rowBounds);
        if(list.size()>0){
            WalletAdvance advance = list.get(0);
            BigDecimal noRefundAmount = advance.getNoRefundAmount();
            if(noRefundAmount.compareTo(BigDecimal.ZERO)>0){
                return  advance;
            }
        }

        return null;
    }


    @Override
    public Long advanceIncome(Long userId, BigDecimal amount) {
        if(amount.compareTo(new BigDecimal(100))<0){
            //throw new ServiceException("2000","满100元才能预支");
        }

        //判断预支条件
        WalletAdvance advance1 = getNewestAdvance(userId);
        if(advance1!=null){
            throw new ServiceException("2000","您还有未还款的预支！");
        }

        BigDecimal advanceProfit = walletIncomeRecordsMapper.getAdvanceProfit(userId);
        if(advanceProfit!=null && advanceProfit.compareTo(amount)<0){
            throw new ServiceException("2000","预支金额大于可预支金额");
        }

        //添加预支记录
        WalletAdvance advance = new WalletAdvance();
        advance.setUserId(userId);
        advance.setChannelOrderNum(OrderUtils.creatOrderNum()+PaymentType.预支收益代付.getValue());
        advance.setBaseAmount(amount);
        advance.setNoRefundAmount(amount);
        advance.setDayCount(0);
        advance.setState(AdvanceState.已提交.getValue());
        advance.setCreateTime(new Date());
        walletAdvanceMapper.insertSelective(advance);
        return advance.getId();
    }

    @Override
    public void updateSign(WalletAdvance advance) {
        walletAdvanceMapper.updateByPrimaryKeySelective(advance);
    }

    @Override
    public List<WalletAdvance> advancedIncomeRecords(Long userId, int pageNo) {
        Example example = new Example(WalletAdvance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds((pageNo-1)*20, 20);

        List<WalletAdvance> list = walletAdvanceMapper.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    @Override
    public List<WalletAdvanceBill> advancedIncomeBills(Long userId, int pageNo) {
        Example example = new Example(WalletAdvanceBill.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds((pageNo-1)*20, 20);

        List<WalletAdvanceBill> list = walletAdvanceBillMapper.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    @Override
    public Map<String, BigDecimal> getManageAmount(Long userId) {
        WalletAdvance advance = getNewestAdvance(userId);
        Map<String, BigDecimal> map = getManageAmount(advance);
        return map;
    }

    @Override
    public Map<String, String> prepayment(Long userId, BigDecimal amount) {
        Map<String, String> result = new HashMap<>();
        WalletAdvance advance = getNewestAdvance(userId);
        if(advance==null){
            throw new ServiceException("2000","当前没有未还的预支！");
        }

        Map<String, BigDecimal> map = getManageAmount(advance);
        BigDecimal manageAmount = map.get("manageAmount")==null?BigDecimal.ZERO:map.get("manageAmount");//账户管理费
        BigDecimal penalAmount = map.get("penalAmount")==null?BigDecimal.ZERO:map.get("penalAmount");//违约金
        BigDecimal noRefundAmount = advance.getNoRefundAmount()==null?BigDecimal.ZERO:advance.getNoRefundAmount();//未还金额

        logger.info("还款金额:"+amount);
        logger.info("账户管理费:"+manageAmount);
        logger.info("违约金:"+penalAmount);
        logger.info("未还金额:"+noRefundAmount);

        if(amount.compareTo(manageAmount.add(penalAmount).add(noRefundAmount))!=0){
            throw new ServiceException("2000","还款金额不正确");
        }

        //创建提前还款订单
        String orderNum = OrderType.提前还款订单.getValue()+OrderUtils.creatOrderNum();
        WalletRefundOrder refundOrder = new WalletRefundOrder();
        refundOrder.setOrderNum(orderNum);
        refundOrder.setTitle("提前还款");
        refundOrder.setAdvanceId(advance.getId());
        refundOrder.setUserId(advance.getUserId());
        refundOrder.setAmount(amount);
        refundOrder.setManageAmount(manageAmount);
        refundOrder.setPenalamount(penalAmount);
        refundOrder.setNoRefundAmount(noRefundAmount);
        refundOrder.setState(RefundOrderState.未支付.getValue());
        refundOrder.setCreateTime(new Date());
        walletRefundOrderMapper.insertSelective(refundOrder);

        result.put("orderNum", orderNum);
        return result;
    }

    /**
     * 获取账户管理费
     * @param advance
     * @return
     */
    private Map<String, BigDecimal> getManageAmount(WalletAdvance advance){
        Map<String, BigDecimal> map = new HashMap<>();
        if(advance==null){
            return map;
        }

        BigDecimal baseAmount = advance.getBaseAmount();//本金
        BigDecimal noRefundAmount = advance.getNoRefundAmount();//未还金额
        int dayCount = advance.getDayCount()==null?0:advance.getDayCount();//还款天数
        if(noRefundAmount.compareTo(BigDecimal.ZERO)>0){
            BigDecimal manageAmount = baseAmount.multiply(new BigDecimal("0.0005")).setScale(2, BigDecimal.ROUND_HALF_UP);//管理费
            map.put("manageAmount",manageAmount);

            if(dayCount>7){
                BigDecimal penalAmount = baseAmount.multiply(new BigDecimal("0.0003").setScale(2, BigDecimal.ROUND_HALF_UP));//违约金
                map.put("penalAmount",penalAmount);
            }
        }

        return map;
    }

    @Override
    public Map<String, Object> createUpgradeOrder(Long userId, String userType, BigDecimal amount) {
        Map<String, Object> map = new HashMap<>();
        String orderNum = OrderType.升级订单.getValue()+OrderUtils.creatOrderNum();

        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String userType1 = userInfo.getUserType();

        if(userType1.compareTo(userType)>=0){
            throw new ServiceException("2000", "您不能升级为该级别");
        }

        WalletUpgradeOrder upgradeOrder = new WalletUpgradeOrder();
        upgradeOrder.setOrderNum(orderNum);
        upgradeOrder.setUpType(UpgradeType.用户等级升级.getValue());
        upgradeOrder.setUserId(userId);
        upgradeOrder.setTitle("购买升级");
        upgradeOrder.setOrderPrice(amount);
        upgradeOrder.setOriginalType(userInfo.getUserType());
        upgradeOrder.setType(userType);
        upgradeOrder.setState("待支付");
        upgradeOrder.setCreateTime(new Date());
        walletUpgradeOrderMapper.insertSelective(upgradeOrder);

        map.put("orderNum",orderNum);
        return map;
    }

    @Override
    public List<CommonAdvertising> getAdverts(String type) {
        Example example = new Example(CommonAdvertising.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("app", "%"+projectName+"%");
        criteria.andEqualTo("type", type);

        List<CommonAdvertising> list = commonAdvertisingMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<WalletModule> getThirdLinks(String type) {
        Example example = new Example(WalletModule.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("app", "%"+projectName+"%");
        criteria.andEqualTo("type", type);

        List<WalletModule> list = walletModuleMapper.selectByExample(example);
        return list;
    }

    @Override
    public JSONObject getLklOrderDetail(Long orderId) throws Exception{
        WalletTradeRecords tradeRecords = walletTradeRecordsMapper.selectByPrimaryKey(orderId);
        String orderNo = tradeRecords.getOrderNo();
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(tradeRecords.getUserId());
        WalletChannel channel = walletChannelMapper.selectByPrimaryKey(tradeRecords.getChannelId());

        if(!"LKL".equals(channel.getNumber())){
            throw new ServiceException("2000", "当前通道不是拉卡拉");
        }

        JSONObject jsonObject = lklPosPay.getOrderDetail(orderNo, userInfo.getPhone(), channel);
        return jsonObject;
    }
}
