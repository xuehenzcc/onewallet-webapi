package com.group.wallet.model.zzlm;

import com.group.core.model.BaseEntity2;

import java.math.BigDecimal;
import java.util.Date;

public class ZzlmAdvanceBill extends BaseEntity2{

    private Long advanceId;

    private Long userId;

    private BigDecimal baseAmount;

    private BigDecimal yesProfit;

    private BigDecimal manageAmount;

    private BigDecimal penalAmount;

    private BigDecimal noRefundAmount;

    private BigDecimal refundAmount;

    private Integer dayCount;

    private Date createTime;


    public Long getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(Long advanceId) {
        this.advanceId = advanceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getYesProfit() {
        return yesProfit;
    }

    public void setYesProfit(BigDecimal yesProfit) {
        this.yesProfit = yesProfit;
    }

    public BigDecimal getManageAmount() {
        return manageAmount;
    }

    public void setManageAmount(BigDecimal manageAmount) {
        this.manageAmount = manageAmount;
    }

    public BigDecimal getNoRefundAmount() {
        return noRefundAmount;
    }

    public void setNoRefundAmount(BigDecimal noRefundAmount) {
        this.noRefundAmount = noRefundAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPenalAmount() {
        return penalAmount;
    }

    public void setPenalAmount(BigDecimal penalAmount) {
        this.penalAmount = penalAmount;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }
}