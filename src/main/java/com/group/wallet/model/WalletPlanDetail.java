package com.group.wallet.model;

import com.group.core.model.BaseEntity2;
import com.group.wallet.model.enums.PlanDetailState;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

public class WalletPlanDetail extends BaseEntity2{

    private Long planId;

    private Long userId;

    private Date executeTime;

    private BigDecimal payAmount;

    private BigDecimal arrivalAmount;

    private BigDecimal deductRate;

    private BigDecimal poundage;

    private BigDecimal serviceCharge;

    private Integer count;

    private String state;

    @Transient
    private String stateName;


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(BigDecimal arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
    }

    public BigDecimal getDeductRate() {
        return deductRate;
    }

    public void setDeductRate(BigDecimal deductRate) {
        this.deductRate = deductRate;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getStateName() {
        if(StringUtils.isNotEmpty(state)){
            return PlanDetailState.getEnumName(state);
        }
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}