package com.group.wallet.service.impl;

import com.group.wallet.mapper.WalletChannelMapper;
import com.group.wallet.mapper.WalletDeductRateMapper;
import com.group.wallet.model.WalletDeductRate;
import com.group.wallet.model.enums.DeductType;
import com.group.wallet.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private WalletChannelMapper walletChannelMapper;
    @Autowired
    private WalletDeductRateMapper walletDeductRateMapper;

    @Override
    public WalletDeductRate getChannelRate(Long channelId) {
        WalletDeductRate deductRate = new WalletDeductRate();
        deductRate.setDeductType(DeductType.通道.getValue());
        deductRate.setChannelId(channelId);
        List<WalletDeductRate> list = walletDeductRateMapper.select(deductRate);
        if(list.size()>0){
            return list.get(0);
        }

        return null;
    }
}
