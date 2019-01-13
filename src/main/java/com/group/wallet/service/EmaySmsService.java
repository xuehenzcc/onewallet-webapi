package com.group.wallet.service;

public interface EmaySmsService {

    /**
     * 发送短信
     * @param phone
     * @param content
     */
    void sendSms(String phone, String content) throws Exception;
}
