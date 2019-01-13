package com.group.wallet.service;

import com.group.wallet.model.WalletPlan;
import com.group.wallet.model.WalletPlanDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 还信用卡
 */
public interface RepaymentCreditCardService {


    /**
     * 创建还款计划
     * @param userId
     * @param cardId
     * @param planType
     * @param amount
     * @param count
     */
    Map<String, Object> addPlan(Long userId, Long cardId, String planType, BigDecimal amount, int count);

    /**
     * 确认还款计划
     * @param planId
     */
    void confirmPlan(Long planId) throws Exception;

    /**
     * 查询还款计划
     * @param userId
     * @param planType
     * @return
     */
    List<WalletPlan> planList(Long userId, String planType);

    /**
     * 还款详情
     * @param planId
     * @return
     */
    List<WalletPlanDetail> planDetail(Long planId);

    /**
     * 中止计划
     * @param planId
     */
    void abortPlan(Long planId);

    /**
     * 获取还款等级，费率
     * @param userId
     * @return
     */
    Map<String, Object> getCreditRate(Long userId);

    /**
     * 创建信用卡升级订单
     * @param userId
     * @param userCreditType
     * @param amount
     */
    Map<String, Object> createUpgradeOrder(Long userId, String userCreditType, BigDecimal amount);
}
