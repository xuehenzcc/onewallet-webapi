package com.group.wallet.service;

import java.math.BigDecimal;
import java.util.Map;

public interface NoticeService {

    /**
     * 校验签名
     * @param channelNo
     * @param params
     * @return
     */
    boolean checkSign(String channelNo, Map<String, Object> params) throws Exception;

    /**
     * 交易成功通知
     * @param orderNumner
     */
    void paySuccessNotice(String orderNumner);

    /**
     * 代付通知
     * @param outTradeNo
     */
    void paymentNotice(String outTradeNo);

    /**
     * 升级付款通知
     * @param orderNum
     */
    void upgradePayNotice(String orderNum);

    /**
     * 购买刷卡机付款成功通知
     * @param orderNum
     */
    void buyPOSMachineOrderNotice(String orderNum);

    /**
     * 提前还款成功通知
     * @param orderNum
     */
    void refundOrderNotice(String orderNum);

}
