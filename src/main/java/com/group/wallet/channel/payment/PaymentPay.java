package com.group.wallet.channel.payment;

import com.group.wallet.model.WalletChannel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public void t0pay(HttpServletRequest request, WalletChannel channel, Map<String, Object> params) throws Exception;
}
