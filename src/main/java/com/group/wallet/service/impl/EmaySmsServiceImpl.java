package com.group.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.group.utils.HttpClientUtils;
import com.group.wallet.service.EmaySmsService;
import com.group.wallet.util.AES;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmaySmsServiceImpl implements EmaySmsService {

    private String host = "bjmtn.b2m.cn";

    private String appId = "EUCP-EMY-SMS1-7935E";

    private String key = "206502443145FE64";

    private String algorithm = "AES/ECB/PKCS5Padding";


    @Override
    public void sendSms(String phone, String content) throws Exception{
        Map<String, String> headers = new HashMap<>();
        headers.put("appId",appId);
        headers.put("encode","utf-8");

        Map<String, Object> param = new HashMap<>();
        param.put("mobile", phone);
        param.put("content", content);
        param.put("requestTime", System.currentTimeMillis());
        param.put("requestValidPeriod", 10);

        byte[] bytes = JSON.toJSONString(param).getBytes("utf-8");
        byte[] parambytes = AES.encrypt(bytes, key.getBytes("utf-8"), algorithm);

        String result = HttpClientUtils.sendPost("http://"+host+"/inter/sendSingleSMS", parambytes, headers, "utf-8");
        System.out.print(result);
    }
}
