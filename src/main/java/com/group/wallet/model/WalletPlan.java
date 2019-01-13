package com.group.wallet.model;

import com.group.core.model.BaseEntity2;
import com.group.wallet.channel.payment.yspay.utils.StringUtils;
import com.group.wallet.model.enums.PlanState;
import org.junit.Ignore;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;


public class WalletPlan extends BaseEntity2{

    private Long userId;

    private Long cardId;

    @Column(name="(select card_no from wallet_bank_card where card_id = wallet_bank_card.id) cardNo", insertable=false)
    private String cardNo;

    @Column(name="(select bank_name from wallet_bank_card where card_id = wallet_bank_card.id) cardName", insertable=false)
    private String cardName;

    private String planType;

    private Long channelId;

    private BigDecimal amount;

    private Integer count;

    private BigDecimal deductRate;

    private BigDecimal poundage;

    private String payWay;

    private String state;

    @Transient
    private String stateName;

    private Date createTime;

    private Date startTime;

    private Date endTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getStateName() {
        if(!StringUtils.isEmpty(state)){
            return PlanState.getEnumName(state);
        }
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}