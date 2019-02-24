package com.group.wallet.service;

import com.group.wallet.model.zzlm.ZzlmDeductRate;

/**
 * 通道Service
 */
public interface ChannelService {

    /**
     * 获取通道扣率
     * @param channelId
     * @return
     */
    ZzlmDeductRate getChannelRate(Long channelId);

    
}
