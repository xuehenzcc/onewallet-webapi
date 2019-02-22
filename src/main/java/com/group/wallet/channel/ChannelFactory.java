package com.group.wallet.channel;

import com.group.wallet.model.zzlm.ZzlmChannel;

/**
 * 通道工厂接口
 */
public interface ChannelFactory {

    /**
     * 获取通道
     * @param channel
     * @return
     */
    public ChannelPay getChannelPay(ZzlmChannel channel);

}
