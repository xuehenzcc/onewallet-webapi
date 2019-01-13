package com.group.wallet.service;

public interface ExecuteJobService {

    /**
     * 定时还款
     * @param planDetailId
     */
    void repayment(Long planDetailId) throws Exception;
}
