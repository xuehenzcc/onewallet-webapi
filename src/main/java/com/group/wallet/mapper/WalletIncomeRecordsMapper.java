package com.group.wallet.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.group.utils.MyMapper;
import com.group.wallet.model.zzlm.ZzlmIncomeRecords;

public interface WalletIncomeRecordsMapper extends MyMapper<ZzlmIncomeRecords>{

    /**
     * 查询总共累计收益
     * @param userId
     * @return
     */
    BigDecimal selectTotalIncome(@Param("userId")Long userId);

    /**
     * 查询今日累计收入
     * @param userId
     * @return
     */
    BigDecimal selectTodayIncome(@Param("userId")Long userId);

    /**
     * 查询昨日累计收入
     * @param userId
     * @return
     */
    BigDecimal selectYestodayIncome(@Param("userId")Long userId);

    /**
     * 获取最近7天收益明细
     * @param userId
     * @return
     */
    List<Map<String,Object>> getSevenDayIncome(@Param("userId")Long userId);

    /**
     * 获取时间区间内的收益明细
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String,Object>> getTimeIncome(@Param("userId")Long userId, @Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 获取预支收益
     * @param userId
     * @return
     */
    BigDecimal getAdvanceProfit(@Param("userId")Long userId);
}