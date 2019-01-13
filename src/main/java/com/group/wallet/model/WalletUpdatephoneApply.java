package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import java.util.Date;

public class WalletUpdatephoneApply extends BaseEntity2{

    private Long userId;

    private String phone;

    private String invoice;

    private String handInvoice;

    private String state;

    private Date createTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice == null ? null : invoice.trim();
    }

    public String getHandInvoice() {
        return handInvoice;
    }

    public void setHandInvoice(String handInvoice) {
        this.handInvoice = handInvoice == null ? null : handInvoice.trim();
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
}