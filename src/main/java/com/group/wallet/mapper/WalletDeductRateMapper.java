package com.group.wallet.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.group.utils.MyMapper;
import com.group.wallet.model.zzlm.ZzlmDeductRate;

public interface WalletDeductRateMapper extends MyMapper<ZzlmDeductRate>{

    //查询用户在通道的费率
    List<Map<String, Object>> selectUserChannelRate(@Param("userType") String userType, @Param("channelType") List<String> channelType);
}