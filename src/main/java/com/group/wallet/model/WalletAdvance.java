package com.group.wallet.model;

import com.group.core.model.BaseEntity2;
import com.group.wallet.model.enums.AdvanceState;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

public class WalletAdvance extends BaseEntity2{

    private Long userId;

    private String channelOrderNum;

    private BigDecimal baseAmount;

    private BigDecimal noRefundAmount;

    private String signature;

    private Integer dayCount;

    private String state;

    @Transient
    private String stateName;

    private Date createTime;


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

    public BigDecimal getNoRefundAmount() {
        return noRefundAmount;
    }

    public void setNoRefundAmount(BigDecimal noRefundAmount) {
        this.noRefundAmount = noRefundAmount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public String getChannelOrderNum() {
        return channelOrderNum;
    }

    public void setChannelOrderNum(String channelOrderNum) {
        this.channelOrderNum = channelOrderNum;
    }

    public String getStateName() {
        return AdvanceState.getEnumName(state);
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}