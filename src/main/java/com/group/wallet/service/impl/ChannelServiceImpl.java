package com.group.wallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.wallet.mapper.WalletChannelMapper;
import com.group.wallet.mapper.WalletDeductRateMapper;
import com.group.wallet.model.enums.DeductType;
import com.group.wallet.model.zzlm.ZzlmDeductRate;
import com.group.wallet.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private WalletChannelMapper walletChannelMapper;
    @Autowired
    private WalletDeductRateMapper walletDeductRateMapper;

    @Override
    public ZzlmDeductRate getChannelRate(Long channelId) {
        ZzlmDeductRate deductRate = new ZzlmDeductRate();
        deductRate.setDeductType(DeductType.通道.getValue());
        deductRate.setChannelId(channelId);
        List<ZzlmDeductRate> list = walletDeductRateMapper.select(deductRate);
        if(list.size()>0){
            return list.get(0);
        }

        return null;
    }
}
