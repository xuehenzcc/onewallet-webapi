package com.group.wallet.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.core.exception.ServiceException;
import com.group.core.quartz.QuartzJobModel;
import com.group.core.service.QuartzService;
import com.group.utils.DateUtil;
import com.group.utils.OrderUtils;
import com.group.wallet.mapper.WalletBankCardMapper;
import com.group.wallet.mapper.WalletChannelMapper;
import com.group.wallet.mapper.WalletPlanDetailMapper;
import com.group.wallet.mapper.WalletPlanMapper;
import com.group.wallet.mapper.WalletUpgradeOrderMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletPlan;
import com.group.wallet.model.WalletPlanDetail;
import com.group.wallet.model.WalletUpgradeOrder;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.OrderType;
import com.group.wallet.model.enums.PlanDetailState;
import com.group.wallet.model.enums.PlanState;
import com.group.wallet.model.enums.SettleType;
import com.group.wallet.model.enums.UpgradeType;
import com.group.wallet.model.enums.UserCreditType;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.service.ExecuteJobService;
import com.group.wallet.service.RepaymentCreditCardService;
import com.group.wallet.service.SettleService;

import tk.mybatis.mapper.entity.Example;

@Service
public class RepaymentCreditCardServiceImpl implements RepaymentCreditCardService {

    @Autowired
    private SettleService settleService;
    @Autowired
    QuartzService quartzServicel;

    @Autowired
    private WalletBankCardMapper walletBankCardMapper;
    @Autowired
    private WalletPlanDetailMapper walletPlanDetailMapper;
    @Autowired
    private WalletPlanMapper walletPlanMapper;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletChannelMapper walletChannelMapper;
    @Autowired
    private WalletUpgradeOrderMapper walletUpgradeOrderMapper;

    @Override
    public Map<String, Object> addPlan(Long userId, Long cardId, String planType, BigDecimal amount, int count) {
        Map<String, Object> map = new HashMap<>();

        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        WalletBankCard bankCard = walletBankCardMapper.selectByPrimaryKey(cardId);
        String dueDateStr = bankCard.getDueDate();
        String billDateStr = bankCard.getBillDate();
        if(StringUtils.isEmpty(dueDateStr)){
            throw new ServiceException("2000", "信用卡的最后还款日不能为空");
        }
        if(StringUtils.isEmpty(billDateStr)){
            throw new ServiceException("2000", "信用卡的账单日不能为空");
        }

        Map<String, Date> dates = calculateDays(new Integer(dueDateStr), new Integer(billDateStr));
        Date billDate = dates.get("billDate");//信用卡账单日
        Date dueDate = dates.get("dueDate");//信用卡还款日

        //与最后还款日相差天数
        int daycount = DateUtil.getBetweenDays(dueDate, new Date())-1;
        if(daycount<0){
            throw new ServiceException("2000", "已经过了最后还款日，不能添加还款计划");
        }

        int count1 = 0;//最多能还款的次数
        Date now = new Date();
        int year = DateUtil.getYear();
        int month = DateUtil.getMonth();
        int day = DateUtil.getDay();
        int hour = now.getHours();

        Date firstExcuteDate = null;//第一次执行时间
        Calendar calendar = Calendar.getInstance();
        Random ran = new Random();
        if(hour<=10){
            count1 = 3;
            calendar.set(year, month-1, day, 10, ran.nextInt(30));
            firstExcuteDate = calendar.getTime();
        }
        if(hour>10 && hour<=14){
            count1 = 2;
            calendar.set(year, month-1, day, 14, ran.nextInt(30));
            firstExcuteDate = calendar.getTime();
        }
        if(hour>14 && hour<=16){
            count1 = 1;
            calendar.set(year, month-1, day, 16, ran.nextInt(30));
            firstExcuteDate = calendar.getTime();
        }
        if(hour>16){
            count1 = 0;
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, ran.nextInt(30));
            firstExcuteDate = calendar.getTime();
        }
        count1 += daycount*3;
        if(count1<count){
            throw new ServiceException("2000", "还款次数已经超出最大还款次数的限制");
        }

        //计算还款金额
        List<BigDecimal> list = sealOff(amount, count);

        //获取商户在还卡通道的t0，t1汇率
        ZzlmChannel channel = new ZzlmChannel();
        channel.setNumber("WS");
        channel = walletChannelMapper.selectOne(channel);

        Map<String, BigDecimal> map1 = settleService.getUserTypeRate(channel.getId(), userInfo.getUserType(), SettleType.T0.getValue());
        BigDecimal rate = map1.get("rate")==null?BigDecimal.ZERO:map1.get("rate");
        BigDecimal poundage = map1.get("poundage")==null?BigDecimal.ZERO:map1.get("poundage");

        //获取商户在当前通道的t0，t1汇率
        WalletPlan plan = new WalletPlan();
        plan.setUserId(userId);
        plan.setCardId(cardId);
        plan.setPlanType(planType);
        plan.setAmount(amount);
        plan.setCount(count);
        plan.setDeductRate(rate);
        plan.setPoundage(poundage);
        plan.setPayWay(null);
        plan.setState(PlanState.创建.getValue());
        plan.setCreateTime(new Date());
        walletPlanMapper.insertSelective(plan);

        //添加计划
        List<WalletPlanDetail> planDetails = new ArrayList<>();
        createPlanDetail(plan, userId, firstExcuteDate, count, 0, list, planDetails);

        BigDecimal totalPayAmount = BigDecimal.ZERO;
        BigDecimal totalServiceCharge = BigDecimal.ZERO;

        for (int i = 0; i < planDetails.size(); i++) {
            BigDecimal payAmount = planDetails.get(i).getPayAmount();
            BigDecimal serviceCharge = planDetails.get(i).getServiceCharge();

            totalPayAmount = totalPayAmount.add(payAmount);
            totalServiceCharge = totalServiceCharge.add(serviceCharge);
        }

        WalletPlan plan1 = new WalletPlan();
        plan1.setId(plan.getId());
        plan1.setStartTime(planDetails.get(0).getExecuteTime());
        plan1.setEndTime(planDetails.get(planDetails.size()-1).getExecuteTime());
        walletPlanMapper.updateByPrimaryKeySelective(plan1);

        map.put("plan", plan);
        map.put("planDetails", planDetails);
        map.put("totalPayAmount", totalPayAmount);
        map.put("totalServiceCharge", totalServiceCharge);

        return map;
    }

    /**
     * 计算拆分后每笔还款的金额
     * @param total
     * @param num
     * @return
     */
    public List<BigDecimal> sealOff(BigDecimal total, int num){
        List<BigDecimal> list = new ArrayList<>();
        int max = total.divide(new BigDecimal(10), 0, BigDecimal.ROUND_HALF_UP).intValue();
        for (int i = 0; i < num; i++) {
            if(i<num-1){
                BigDecimal middle = total.divide(new BigDecimal(num-i), 0, BigDecimal.ROUND_HALF_UP);
                Random ran=new Random();
                int ranint = ran.nextInt(max);

                BigDecimal money = middle.subtract(new BigDecimal(ranint));

                total = total.subtract(money);

                System.out.println("第"+(i+1)+"次还款金额为:"+money+"，剩余还款金额为"+total);
                list.add(money);
            }
            else {
                System.out.println("第"+(i+1)+"次还款金额为:"+total+"，剩余还款金额为"+total);
                list.add(total);
            }
        }

        return list;
    }

    /**
     * 计算账单日跟最近还款日
     * @param dueDate
     * @param billDate
     * @return
     */
    public Map<String, Date> calculateDays(int dueDate, int billDate){
        Map<String, Date> map = new HashMap<>();
        int day = DateUtil.getDay();//当前日
        int month = DateUtil.getMonth();//当前月
        int year = DateUtil.getYear();//当前年

        Calendar calendar = Calendar.getInstance();
        Date billDate2 = null;
        Date dueDate2 = null;

        //账单日跟还款日在同月
        if(dueDate>billDate){
            calendar.set(year, month-1, dueDate);
            dueDate2 = calendar.getTime();

            calendar.set(year, month-1, billDate);
            billDate2 = calendar.getTime();
        }

        //账单日跟还款日夸月
        if(dueDate<=billDate){
            if(day>billDate){
                calendar.set(year, month-1, billDate);
                billDate2 = calendar.getTime();

                calendar.set(year, month, dueDate);
                dueDate2 = calendar.getTime();
            }
        }

        map.put("billDate", billDate2);
        map.put("dueDate", dueDate2);
        return map;
    }

    /**
     * 创建还款明细
     * @param plan 计划
     * @param userId 用户id
     * @param date 执行时间
     * @param totalCount 总共次数
     * @param count 执行次数（从0开始）
     * @param list
     */
    public void createPlanDetail(WalletPlan plan, Long userId, Date date, int totalCount, int count, List<BigDecimal> list, List<WalletPlanDetail> planDetails){
        if(count >= totalCount)
            return;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = date.getHours();

        Date nextDate = new Date();
        Random ran = new Random();
        if(hour==10){
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, ran.nextInt(30));
            nextDate = calendar.getTime();
        }
        if(hour==14){
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, ran.nextInt(30));
            nextDate = calendar.getTime();
        }
        if(hour==16){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, ran.nextInt(30));
            nextDate = calendar.getTime();
        }

        BigDecimal deductRate = plan.getDeductRate().divide(new BigDecimal(100));//费率
        BigDecimal poundage = plan.getPoundage();//手续费

        BigDecimal arrivalAmount = list.get(count);//到账金额
        BigDecimal payAmount = arrivalAmount.add(poundage).divide(new BigDecimal(1).subtract(deductRate), 2, BigDecimal.ROUND_HALF_UP);//刷卡金额
        BigDecimal serviceCharge = payAmount.subtract(arrivalAmount);//服务费

        //添加计划明细
        WalletPlanDetail planDetail = new WalletPlanDetail();
        planDetail.setPlanId(plan.getId());
        planDetail.setUserId(userId);
        planDetail.setExecuteTime(date);
        planDetail.setPayAmount(payAmount);
        planDetail.setArrivalAmount(arrivalAmount);
        planDetail.setDeductRate(deductRate);
        planDetail.setPoundage(poundage);
        planDetail.setServiceCharge(serviceCharge);
        planDetail.setCount(count);
        planDetail.setState(PlanDetailState.创建.getValue());
        walletPlanDetailMapper.insertSelective(planDetail);
        planDetails.add(planDetail);

        count++;
        createPlanDetail(plan, userId, nextDate, totalCount, count, list, planDetails);
    }

    @Override
    @Transactional
    public void confirmPlan(Long planId) throws Exception{
        WalletPlan plan1 = walletPlanMapper.selectByPrimaryKey(planId);
        if(PlanState.执行中.getValue().equals(plan1.getState())){
            throw new ServiceException("2000", "该计划已经在执行中");
        }

        WalletPlan plan = new WalletPlan();
        plan.setId(planId);
        plan.setState(PlanState.执行中.getValue());
        walletPlanMapper.updateByPrimaryKeySelective(plan);

        WalletPlanDetail planDetail = new WalletPlanDetail();
        planDetail.setPlanId(planId);
        List<WalletPlanDetail> list = walletPlanDetailMapper.select(planDetail);
        for (int i = 0; i < list.size(); i++) {
            WalletPlanDetail planDetail1 = list.get(i);

            //添加定时任务
            QuartzJobModel quartzJobModel = new QuartzJobModel();
            quartzJobModel.setJobName("excute-"+planDetail1.getId());
            quartzJobModel.setDescription("开始执行智能还款");
            quartzJobModel.setTargetObject(ExecuteJobService.class);
            quartzJobModel.setTargetMethod("repayment");
            quartzJobModel.setClasses(new Class[] {Long.class});
            quartzJobModel.setObjects(new Object[] {planDetail.getId()});
            quartzJobModel.setCronExpression(DateUtil.getCron(planDetail1.getExecuteTime()));
            quartzServicel.addQuartzJob(quartzJobModel);

            WalletPlanDetail planDetail2 = new WalletPlanDetail();
            planDetail2.setId(planDetail1.getId());
            planDetail2.setState(PlanDetailState.未执行.getValue());
            walletPlanDetailMapper.updateByPrimaryKeySelective(planDetail2);
        }
    }

    @Override
    public List<WalletPlan> planList(Long userId, String planType) {
        Example example = new Example(WalletPlan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("planType", planType);
        criteria.andNotEqualTo("state", PlanState.创建.getValue());

        List<WalletPlan> plans = walletPlanMapper.selectByExample(example);
        return plans;
    }

    @Override
    public List<WalletPlanDetail> planDetail(Long planId) {
        WalletPlanDetail planDetail = new WalletPlanDetail();
        planDetail.setPlanId(planId);
        List<WalletPlanDetail> list = walletPlanDetailMapper.select(planDetail);
        return list;
    }

    @Override
    public void abortPlan(Long planId) {
        WalletPlan plan = new WalletPlan();
        plan.setId(planId);
        plan.setState(PlanState.终止.getValue());
        walletPlanMapper.updateByPrimaryKeySelective(plan);

        Example example = new Example(WalletPlanDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("planId", planId);

        WalletPlanDetail planDetail = new WalletPlanDetail();
        planDetail.setState(PlanDetailState.终止.getValue());
        walletPlanDetailMapper.updateByExampleSelective(planDetail, example);
    }

    @Override
    public Map<String, Object> getCreditRate(Long userId) {
        Map<String, Object> map = new HashMap<>();
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String creditType = userInfo.getCreditType();
        String creditTypeName = userInfo.getCreditTypeName();

        //获取商户在还卡通道的t0，t1汇率
        ZzlmChannel channel = new ZzlmChannel();
        channel.setNumber("WS");
        channel = walletChannelMapper.selectOne(channel);

        Map<String, BigDecimal> map1 = settleService.getUserTypeRate(channel.getId(), userInfo.getUserType(), SettleType.T0.getValue());
        BigDecimal rate = map1.get("rate")==null?BigDecimal.ZERO:map1.get("rate");
        BigDecimal poundage = map1.get("poundage")==null?BigDecimal.ZERO:map1.get("poundage");

        map.put("creditType", creditType);
        map.put("creditTypeName", creditTypeName);
        map.put("rate", rate);
        map.put("poundage", poundage);

        return map;
    }

    @Override
    public Map<String, Object> createUpgradeOrder(Long userId, String userCreditType, BigDecimal amount) {
        Map<String, Object> map = new HashMap<>();
        String orderNum = OrderType.升级订单.getValue()+ OrderUtils.creatOrderNum();

        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String userInfoCreditType1 = StringUtils.isEmpty(userInfo.getCreditType())?UserCreditType.个体商户.getValue():userInfo.getCreditType();

        if(userInfoCreditType1.compareTo(userCreditType)>=0){
            throw new ServiceException("2000", "您不能升级为该级别");
        }

        WalletUpgradeOrder upgradeOrder = new WalletUpgradeOrder();
        upgradeOrder.setOrderNum(orderNum);
        upgradeOrder.setUpType(UpgradeType.用户等级升级.getValue());
        upgradeOrder.setUserId(userId);
        upgradeOrder.setTitle("购买升级");
        upgradeOrder.setOrderPrice(amount);
        upgradeOrder.setOriginalType(userInfo.getCreditType());
        upgradeOrder.setType(userCreditType);
        upgradeOrder.setState("待支付");
        upgradeOrder.setCreateTime(new Date());
        walletUpgradeOrderMapper.insertSelective(upgradeOrder);

        map.put("orderNum",orderNum);
        return map;
    }

}
