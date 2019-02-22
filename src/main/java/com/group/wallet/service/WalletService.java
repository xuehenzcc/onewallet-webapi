package com.group.wallet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.group.wallet.model.CommonAdvertising;
import com.group.wallet.model.WalletModule;
import com.group.wallet.model.WalletReceiveOrder;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.zzlm.ZzlmAdvance;
import com.group.wallet.model.zzlm.ZzlmAdvanceBill;
import com.group.wallet.model.zzlm.ZzlmIncomeRecords;

/**
 * 钱包service
 */
public interface WalletService {

    /**
     * 创建领取刷卡机订单
     * @param receiveOrder
     * @return
     */
    Map<String, Object> createPOSMachineOrder(WalletReceiveOrder receiveOrder);

    /**
     * 绑定刷卡器
     * @param userId
     * @param sn
     */
    void bindPOSMachine(Long userId, String sn);

    /**
     * 获取我的收益主界面数据
     * @param userId
     * @return
     */
    Map<String, Object> getMyIncome(Long userId);

    /**
     * 获取收益明细
     * @param userId
     * @return
     */
    Map<String,Object> incomeRecords(Long userId, String startDate, String endDate);

    /**
     * 获取全部明细
     * @param userId
     * @return
     */
    List<ZzlmIncomeRecords> allIncomeRecords(Long userId, String type, Integer pageNo);

    /**
     * 获取收款明细
     * @param userId
     * @param pageNo
     * @return
     */
    List<WalletTradeRecords> proceedsRecords(Long userId, Integer pageNo);

    /**
     * 判断是否可提现
     * @param userId
     */
    void checkTakeOut(Long userId);

    /**
     * 分润提现
     * @param userId
     * @param amount
     */
    Map<String, Object> takeOutIncom(HttpServletRequest request, Long userId, BigDecimal amount) throws Exception;

    /**
     * 获取预支收益
     * @param userId
     * @return
     */
    Map<String, Object> getMyAdvanceIncome(Long userId);

    /**
     * 获取最近一笔未还清的预支
     * @param userId
     * @return
     */
    ZzlmAdvance getNewestAdvance(Long userId);

    /**
     * 确认预支
     * @param userId
     * @param amount
     */
    Long advanceIncome(Long userId, BigDecimal amount);

    /**
     * 更新预支签名
     * @param advance
     */
    void updateSign(ZzlmAdvance advance);

    /**
     * 获取预支明细
     * @param userId
     * @return
     */
    List<ZzlmAdvance> advancedIncomeRecords(Long userId, int pageNo);

    /**
     * 获取账单明细
     * @param userId
     * @param pageNo
     * @return
     */
    List<ZzlmAdvanceBill> advancedIncomeBills(Long userId, int pageNo);

    /**
     * 获取最近一笔未还清的预支的账户管理费和违约金
     * @param userId
     * @return
     */
    Map<String, BigDecimal> getManageAmount(Long userId);

    /**
     * 提前还款
     * @param userId
     * @param amount
     */
    Map<String, String> prepayment(Long userId, BigDecimal amount);

    /**
     * 创建升级订单
     * @param userId
     * @param userType
     * @return
     */
    Map<String, Object> createUpgradeOrder(Long userId, String userType, BigDecimal amount);

    /**
     * 获取广告
     * @param type
     * @return
     */
    List<CommonAdvertising> getAdverts(String type);

    /**
     * 获取第三方链接
     * @param type
     * @return
     */
    List<WalletModule> getThirdLinks(String type);

    /**
     * 获取拉卡拉订单详情
     * @param orderId
     * @return
     */
    JSONObject getLklOrderDetail(Long orderId) throws Exception;
}
