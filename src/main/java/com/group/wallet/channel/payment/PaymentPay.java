package com.group.wallet.channel.payment;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.group.wallet.model.zzlm.ZzlmChannel;

/**
 * 代付
 */
public interface PaymentPay {

    /**
     * t0代付
     * @param params
     * @param channel
     * @throws Exception
     */
    public void t0pay(HttpServletRequest request, ZzlmChannel channel, Map<String, Object> params) throws Exception;
}
