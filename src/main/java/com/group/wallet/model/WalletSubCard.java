package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import java.util.Date;

public class WalletSubCard extends BaseEntity2 {

    private String subMerNo;

    private String cardNo;

    private Date createTime;


    public String getSubMerNo() {
        return subMerNo;
    }

    public void setSubMerNo(String subMerNo) {
        this.subMerNo = subMerNo == null ? null : subMerNo.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}