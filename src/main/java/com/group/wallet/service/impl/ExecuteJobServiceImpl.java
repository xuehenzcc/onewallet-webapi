package com.group.wallet.service.impl;

import com.group.wallet.mapper.WalletBankCardMapper;
import com.group.wallet.mapper.WalletPlanDetailMapper;
import com.group.wallet.mapper.WalletPlanMapper;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletPlan;
import com.group.wallet.model.WalletPlanDetail;
import com.group.wallet.model.enums.SettleType;
import com.group.wallet.service.ExecuteJobService;
import com.group.wallet.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExecuteJobServiceImpl implements ExecuteJobService {

    @Autowired
    private PayService payService;

    @Autowired
    private WalletPlanDetailMapper walletPlanDetailMapper;
    @Autowired
    private WalletPlanMapper walletPlanMapper;
    @Autowired
    private WalletBankCardMapper walletBankCardMapper;

    @Override
    public void repayment(Long planDetailId) throws Exception{
        WalletPlanDetail planDetail = walletPlanDetailMapper.selectByPrimaryKey(planDetailId);
        WalletPlan plan = walletPlanMapper.selectByPrimaryKey(planDetail.getPlanId());
        Long channelId = plan.getChannelId();
        Long userId = plan.getUserId();
        Long bankCardId = plan.getCardId();
        BigDecimal amount = planDetail.getPayAmount();

        //注册子商户
        Map<String, Object> result = payService.registSubMerchant(channelId, userId, bankCardId, SettleType.T0.getValue());

        //快捷支付
        payService.quickPay(userId, channelId, bankCardId, amount, SettleType.T0.getValue(), 0.0, 0.0, "");
    }
}
