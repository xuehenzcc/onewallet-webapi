package com.group.wallet.mapper;

import com.group.utils.MyMapper;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.WalletUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletUserInfoMapper extends MyMapper<WalletUserInfo>{

    /***
     * 获取用户信息（加悲观锁）
     * @param userId
     * @return
     */
    WalletUserInfo selectUserInfoForUpdate(@Param("userId") Long userId);

    /**
     * 查询推荐的已认证的用户的数量
     * @param userState
     * @param recommonId
     * @return
     */
    int selectRecommenCount(@Param("userState") String userState, @Param("recommonId") Long recommonId);

    /**
     * 查询直接推荐的数量
     * @param userId
     * @param userType
     * @return
     */
    int selectDirectRecommonCount(@Param("userId") Long userId, @Param("userType") String userType);

    /**
     * 查询间接推荐数量
     * @param userNumber
     * @param userId
     * @param userType
     * @return
     */
    int selectIndirectRecommonCount(@Param("userNumber") String userNumber, @Param("userId") Long userId, @Param("userType") String userType);
}