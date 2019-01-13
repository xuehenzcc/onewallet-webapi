package com.group.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationInitializeRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.group.utils.OrderUtils;
import com.group.wallet.service.ZhimaService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZhimaServiceImpl implements ZhimaService {

    //芝麻开放平台地址
    private String gatewayUrl     = "https://zmopenapi.zmxy.com.cn/openapi.do";
    //商户应用 Id
    private String appId          = "***";
    //商户 RSA 私钥
    private String privateKey     = "***";
    //芝麻 RSA 公钥
    private String zhimaPublicKey = "***";

    @Override
    public Map<String, Object> certificationInitialize(String certName, String certNo) {
        Map<String, String> identityParam = new HashMap<>();
        identityParam.put("identity_type", "CERT_INFO");
        identityParam.put("cert_type", "IDENTITY_CARD");
        identityParam.put("cert_name", certName);
        identityParam.put("cert_no", certNo);

        ZhimaCustomerCertificationInitializeRequest req = new ZhimaCustomerCertificationInitializeRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setTransactionId("YHQB"+ OrderUtils.creatOrderNum());// 必要参数，商户请求唯一标志
        req.setProductCode("w1010100000000002978");// 必要参数，芝麻认证产品码
        req.setBizCode("FACE");// 必要参数
        req.setIdentityParam(JSON.toJSONString(identityParam));// 必要参数
        req.setMerchantConfig("{\"need_user_authorization\":\"false\"}");//
        req.setExtBizParam("{}");// 必要参数
        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
        try {
            ZhimaCustomerCertificationInitializeResponse response =(ZhimaCustomerCertificationInitializeResponse)client.execute(req);
            System.out.println(response.isSuccess());
            System.out.println(response.getErrorCode());
            System.out.println(response.getErrorMessage());
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }

        return null;
    }
}
