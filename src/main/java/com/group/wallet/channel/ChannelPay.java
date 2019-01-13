package com.group.wallet.channel;

import com.group.wallet.model.*;

import java.util.Map;

/**
 * 通道支付
 */
public interface ChannelPay  {

    /**
     * 注册子商户&商户进件
     * @param userInfo
     * @param bankCard
     * @param channel
     * @param channelMer
     * @return
     * @throws Exception
     */
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception;

    /**
     * 更新子商户
     * @param userInfo
     * @param bankCard
     * @param channel
     * @param channelMer
     * @return
     * @throws Exception
     */
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception;

    /**
     * 下单&返回H5
     * @param userInfo
     * @param tradeRecords
     * @param channel
     * @param channelMer
     * @param bankCard
     * @return
     * @throws Exception
     */
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer, WalletBankCard bankCard) throws Exception;

    /**
     * 校验签名
     * @param channel
     * @param params
     * @return
     * @throws Exception
     */
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception;
}
