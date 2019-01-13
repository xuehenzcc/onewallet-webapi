package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import java.math.BigDecimal;
import java.util.Date;

public class WalletRefundOrder extends BaseEntity2{

    private String orderNum;

    private String title;

    private Long advanceId;

    private Long userId;

    //提前还款金额
    private BigDecimal amount;

    //账户管理费
    private BigDecimal manageAmount;

    //违约金
    private BigDecimal penalamount;

    //未还预支收益
    private BigDecimal noRefundAmount;

    private String state;

    private String payWay;

    private Date payTime;

    private Date createTime;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getManageAmount() {
        return manageAmount;
    }

    public void setManageAmount(BigDecimal manageAmount) {
        this.manageAmount = manageAmount;
    }

    public BigDecimal getPenalamount() {
        return penalamount;
    }

    public void setPenalamount(BigDecimal penalamount) {
        this.penalamount = penalamount;
    }

    public BigDecimal getNoRefundAmount() {
        return noRefundAmount;
    }

    public void setNoRefundAmount(BigDecimal noRefundAmount) {
        this.noRefundAmount = noRefundAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}