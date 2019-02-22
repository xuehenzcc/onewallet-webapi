package com.group.wallet.channel.quick;

import java.util.Map;

import com.group.wallet.channel.ChannelPay;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUserInfo;
//import com.sun.tools.corba.se.idl.StringGen;
import com.group.wallet.model.zzlm.ZzlmChannel;

/**
 * 快捷支付
 */
public interface QuickPay extends ChannelPay{

    /**
     * 发送短信确认
     * @param channel
     * @param tradeRecords
     * @param bankCard
     */
    public Map<String, Object> sendSMSCode(ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception;

    /**
     * 快捷支付确认
     * @param userInfo
     * @param channel
     * @param tradeRecords
     * @param bankCard
     * @param params
     * @throws Exception
     */
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception;

    /**
     * 结算
     * @param channel
     * @param params
     * @throws Exception
     */
    public void settlement(ZzlmChannel channel, Map<String, Object> params) throws Exception;

    /**
     * 付款成功通知
     * @param params
     * @return
     */
    public String payNotice(Map<String, String> params) throws Exception;

}
