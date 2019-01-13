package com.group.wallet.service;

import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUpgradeOrder;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.ChannelType;
import com.group.wallet.model.enums.IncomeType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 结算
 */
public interface SettleService {

    /**
     * 计算交易分润(刷卡分润，快捷分润，扫码支付)
     * @param tradeRecords
     */
    void calculateTradeProfit(WalletTradeRecords tradeRecords);

    /**
     * 更新用户的分润金额
     * @param userInfo
     * @param profitMoney
     * @param incomeType
     * @param orderNum
     * @param sourceUser
     */
    void updateProfit(WalletUserInfo userInfo, BigDecimal profitMoney, IncomeType incomeType, String orderNum, WalletUserInfo sourceUser, String desc);

    /**
     * 升级分润
     * @param upgradeOrder
     */
    void calculateUpgrdeProfit(WalletUpgradeOrder upgradeOrder);

    /**
     * 获取商户的汇率和结算手续费
     * @param userType
     * @param settleType
     * @return
     */
    Map<String, BigDecimal> getUserTypeRate(Long channelId, String userType, String settleType);
}
