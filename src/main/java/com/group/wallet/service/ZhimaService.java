package com.group.wallet.service;

import java.util.Map;

/**
 * 芝麻认证
 */
public interface ZhimaService {

    /**
     * 芝麻认证初始化
     * @param certName
     * @param certNo
     * @return
     */
    Map<String, Object> certificationInitialize(String certName, String certNo);
}
