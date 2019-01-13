package com.group.wallet.mapper;

import com.group.utils.MyMapper;
import com.group.wallet.model.WalletDeductRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WalletDeductRateMapper extends MyMapper<WalletDeductRate>{

    //查询用户在通道的费率
    List<Map<String, Object>> selectUserChannelRate(@Param("userType") String userType, @Param("channelType") List<String> channelType);
}