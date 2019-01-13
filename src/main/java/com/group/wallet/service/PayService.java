package com.group.wallet.service;

import com.group.wallet.model.WalletChannel;
import com.group.wallet.model.WalletDeductRate;
import com.group.wallet.model.WalletTradeRecords;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 支付
 */
public interface PayService {

    /**
     * 获取通道
     * @param channelType
     * @return
     */
    List<Map<String, Object>> getChannels(Long userId, String channelType);

    /**
     * 支付校验
     * @param channelId
     * @param settleType
     * @param amount
     */
    void payCheck(Long channelId, String settleType, BigDecimal amount, Long userId);

    /**
     * 注册子商户 2，快捷支付通道根据用户结算卡信息进件  2，还卡根据用户信用卡信息进件
     * @param channelId
     * @param userId
     * @return
     */
    Map<String, Object> registSubMerchant(Long channelId, Long userId, Long bankCardId, String settleType) throws Exception;

    /**
     * 快捷支付
     * @param userId
     * @param channelId
     * @param orderPrice
     * @return
     * @throws Exception
     */
    Map<String, Object> quickPay(Long userId,Long channelId, Long bankCardId, BigDecimal orderPrice, String settleType, Double lon, Double lat, String tradeAddress) throws Exception;

    /**
     * 刷卡支付
     * @param userId
     * @param channelId
     * @param bankCardId
     * @param orderPrice
     * @param settleType
     * @param lon
     * @param lat
     * @param tradeAddress
     * @return
     * @throws Exception
     */
    Map<String, Object> posPay(Long userId,Long channelId, Long bankCardId, BigDecimal orderPrice, String settleType, Double lon, Double lat, String tradeAddress) throws Exception;

    /**
     * 发送短信验证码
     * @param outTradeNo
     * @param jsonParams
     * @return
     * @throws Exception
     */
    Map<String, Object> quickPaySendSms(String outTradeNo, String jsonParams) throws Exception;

    /**
     * 快捷支付确认
     * @param outTradeNo
     * @param jsonParams
     */
    Map<String, Object> quickPayConfirm(String outTradeNo, String jsonParams) throws Exception;

    /**
     * 结算
     * @param channelNo
     * @param params
     * @throws Exception
     */
    void settlement(String channelNo, Map<String, Object> params) throws Exception;

    /**
     * 代付
     * @param userId
     * @param channelNum
     * @param amount
     */
    void payment(HttpServletRequest request, Long userId, String channelNum, String outTradeNo, BigDecimal amount) throws Exception;

    /**
     * 易宝手动出款
     * @param orderId
     */
    void yibaosettle(Long orderId, String balance) throws Exception;

    /**
     * h5支付
     * @param orderNo
     * @return
     */
    Map<String, Object> h5Pay(String orderNo);
}
