package com.group.wallet.mapper;

import com.group.utils.MyMapper;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletTradeRecordsExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WalletTradeRecordsMapper extends MyMapper<WalletTradeRecords>{

    /**
     * 查询交易记录
     * @param orderNo
     * @return
     */
    WalletTradeRecords selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询当日交易总金额
     * @return
     */
    BigDecimal selectTodayTotalAmount(@Param("userId")Long userId, @Param("channelId")Long channelId, @Param("tradeState")String tradeState);


    /**
     * 查询临时表数据
     * @return
     */
    List<Map<String, Object>> selectTemporaryData();

    /**
     * 删除临时表数据
     * @param customerNumber
     */
    void deleteTemporaryData(@Param("customerNumber")String customerNumber);

}