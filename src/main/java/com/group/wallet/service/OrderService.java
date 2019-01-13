package com.group.wallet.service;

import com.group.wallet.model.WalletReceiveOrder;
import com.group.wallet.model.WalletRefundOrder;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUpgradeOrder;

public interface OrderService {

    /**
     * 获取交易订单
     * @param orderNo
     * @return
     */
    WalletTradeRecords getTradeOrder(String orderNo);

    /**
     * 获取升级订单
     * @param orderNum
     * @return
     */
    WalletUpgradeOrder getUpgradeOrder(String orderNum);

    /**
     * 获取提前还款订单
     * @param orderNum
     * @return
     */
    WalletRefundOrder getRefundOrder(String orderNum);

    /**
     * 获取领取刷卡机订单
     * @param orderNum
     * @return
     */
    WalletReceiveOrder getReceiveOrder(String orderNum);
}
