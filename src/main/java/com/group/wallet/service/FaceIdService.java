package com.group.wallet.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/11/6 下午3:00
 * 描述：
 */
public interface FaceIdService {


    public static final String api_key = "EkD42e4mRF_8QrsQtU5yTl1U9_25iB4E";

    public static final String api_secret = "Da5QyhjfkeohMLJa0d4_d3NWkxGm8AD1";


    /**
     * 获取BizToken
     * @param idcardName
     * @param idcardNumber
     * @return
     * @throws Exception
     */
    String getBizToken(String idcardName, String idcardNumber) throws Exception;

    /**
     * 获取人脸检测结果
     * @param userId
     * @param meglive_data
     * @param bizToken
     * @return
     * @throws Exception
     */
    Map<String, Object> verify(Long userId, MultipartFile meglive_data, String bizToken) throws Exception;
}
