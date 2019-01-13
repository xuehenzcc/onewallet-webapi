package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import java.math.BigDecimal;
import java.util.Date;

public class WalletChannelMer extends BaseEntity2{

    private Long channelId;

    private Long userId;

    //银行卡号
    private String cardNo;

    //大商户("1"),
    //一对一商户("2")
    private String type;

    //T0("T0"),
    //T1("T1")
    private String settleType;

    //通道子商户号
    private String channelMerNo;

    private String channelMerName;

    private String walletMerNo;

    private BigDecimal deductRate;

    private BigDecimal poundage;

    private String md5Key;

    private Date createTime;


    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getChannelMerNo() {
        return channelMerNo;
    }

    public void setChannelMerNo(String channelMerNo) {
        this.channelMerNo = channelMerNo == null ? null : channelMerNo.trim();
    }

    public String getChannelMerName() {
        return channelMerName;
    }

    public void setChannelMerName(String channelMerName) {
        this.channelMerName = channelMerName == null ? null : channelMerName.trim();
    }

    public String getWalletMerNo() {
        return walletMerNo;
    }

    public void setWalletMerNo(String walletMerNo) {
        this.walletMerNo = walletMerNo == null ? null : walletMerNo.trim();
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key == null ? null : md5Key.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public BigDecimal getDeductRate() {
        return deductRate;
    }

    public void setDeductRate(BigDecimal deductRate) {
        this.deductRate = deductRate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }
}