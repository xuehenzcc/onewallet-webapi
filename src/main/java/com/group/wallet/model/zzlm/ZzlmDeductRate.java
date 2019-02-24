package com.group.wallet.model.zzlm;

import com.group.core.model.BaseEntity2;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ZzlmDeductRate extends BaseEntity2{

    private String deductType;

    private Long channelId;

    /*@Column(name="(select name from wallet_channel where id = channel_id) channelName", insertable=false)
    private String channelName;*/

    //快捷渠道("Q1"),
    //H5快捷渠道("Q2"),
    //二维码渠道("C"),
    //代付渠道("P")
    /*@Column(name="(select channel_type from wallet_channel where id = channel_id)", insertable=false)
    private String channelType;*/

    //通道提示
    /*@Column(name="(select remarks from wallet_channel where id = channel_id)", insertable=false)
    private String remarks;*/

    private String userType;

    private String payWay;

    private String scanType;

    private String settleType;

    //扣率
    private BigDecimal deductRate;

    //手续费
    private BigDecimal poundage;

    //单笔限额最小值
    private BigDecimal limitMin;

    //单笔限额最大值
    private BigDecimal limitMax;

    //每日最高限额
    private BigDecimal dayMax;


    public String getDeductType() {
        return deductType;
    }

    public void setDeductType(String deductType) {
        this.deductType = deductType == null ? null : deductType.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType == null ? null : scanType.trim();
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType == null ? null : settleType.trim();
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

    public BigDecimal getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(BigDecimal limitMin) {
        this.limitMin = limitMin;
    }

    public BigDecimal getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(BigDecimal limitMax) {
        this.limitMax = limitMax;
    }

    public BigDecimal getDayMax() {
        return dayMax;
    }

    public void setDayMax(BigDecimal dayMax) {
        this.dayMax = dayMax;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    /*public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }*/
}