package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import javax.persistence.Column;
import java.math.BigDecimal;

public class WalletAccount extends BaseEntity2{

    private Long userId;

    private Long channelId;

    @Column(name = "(select name from wallet_channel where id = channel_id) channelName", insertable = false)
    private String channelName;

    private BigDecimal amount;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}