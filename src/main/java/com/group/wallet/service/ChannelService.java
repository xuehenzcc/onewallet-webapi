package com.group.wallet.service;

import com.group.wallet.model.WalletDeductRate;

import java.util.List;

/**
 * 通道Service
 */
public interface ChannelService {

    /**
     * 获取通道扣率
     * @param channelId
     * @return
     */
    WalletDeductRate getChannelRate(Long channelId);

    
}
