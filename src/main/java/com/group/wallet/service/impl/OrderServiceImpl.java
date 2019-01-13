package com.group.wallet.service.impl;

import com.group.wallet.mapper.WalletReceiveOrderMapper;
import com.group.wallet.mapper.WalletRefundOrderMapper;
import com.group.wallet.mapper.WalletTradeRecordsMapper;
import com.group.wallet.mapper.WalletUpgradeOrderMapper;
import com.group.wallet.model.WalletReceiveOrder;
import com.group.wallet.model.WalletRefundOrder;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUpgradeOrder;
import com.group.wallet.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;
    @Autowired
    private WalletUpgradeOrderMapper walletUpgradeOrderMapper;
    @Autowired
    private WalletRefundOrderMapper walletRefundOrderMapper;
    @Autowired
    private WalletReceiveOrderMapper walletReceiveOrderMapper;

    @Override
    public WalletTradeRecords getTradeOrder(String orderNo) {
        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setOrderNo(orderNo);
        tradeRecords = walletTradeRecordsMapper.selectOne(tradeRecords);
        return tradeRecords;
    }

    @Override
    public WalletUpgradeOrder getUpgradeOrder(String orderNum) {
        WalletUpgradeOrder upgradeOrder = new WalletUpgradeOrder();
        upgradeOrder.setOrderNum(orderNum);
        upgradeOrder = walletUpgradeOrderMapper.selectOne(upgradeOrder);
        return upgradeOrder;
    }

    @Override
    public WalletRefundOrder getRefundOrder(String orderNum) {
        WalletRefundOrder refundOrder = new WalletRefundOrder();
        refundOrder.setOrderNum(orderNum);
        refundOrder = walletRefundOrderMapper.selectOne(refundOrder);
        return refundOrder;
    }

    @Override
    public WalletReceiveOrder getReceiveOrder(String orderNum) {
        WalletReceiveOrder receiveOrder = new WalletReceiveOrder();
        receiveOrder.setOrderNum(orderNum);
        receiveOrder = walletReceiveOrderMapper.selectOne(receiveOrder);
        return receiveOrder;
    }

}
