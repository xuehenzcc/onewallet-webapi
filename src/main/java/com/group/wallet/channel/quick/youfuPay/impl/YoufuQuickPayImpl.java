package com.group.wallet.channel.quick.youfuPay.impl;

import com.group.core.exception.ServiceException;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.channel.quick.youfuPay.utils.*;
import com.group.wallet.model.*;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 优付金融
 */
@Service
public class YoufuQuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(YoufuQuickPayImpl.class);

    private final String PUBLICKKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEvwG2998AV4STOEiqko5Y6d2ktHhlKeqW2EUjx2G2gJcBgOkP6daHtAldrQxzS5pucMf3kCeJaQNXnx5FcnkwLCJZJbfigBrd6DO4RojVksiWu11rmBVBM0FT6iBMOCyH73Lf3JJaVVqjQ6SgFZ13zYkVm5l4xUIHKZjGRIvwnQIDAQAB";
    private final String PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIS/Abb33wBXhJM4SKqSjljp3aS0eGUp6pbYRSPHYbaAlwGA6Q/p1oe0CV2tDHNLmm5wx/eQJ4lpA1efHkVyeTAsIlklt+KAGt3oM7hGiNWSyJa7XWuYFUEzQVPqIEw4LIfvct/cklpVWqNDpKAVnXfNiRWbmXjFQgcpmMZEi/CdAgMBAAECgYBQlL5OumMFixrwX19FJBgDA1xHikH3bx13mitGT9cR+Tcxc3aG8XUIBZMV6WFAb+l8FzxcHIbTlAqyWuX+VEIZyAgBcYpZd9vUO9GNyNvpkVjkedltiO56ZubOw9e3bHOg2YMhncKYVjwPwzIAGBw6+XzhVyj/kw/1unrRcBoLwQJBANoMLLA5YgtLGemgBQ8/h/3UcZ+rKLqKBWRNSWjF55a5wDGdqWjSi4VT3Osz437yq5oCKLLxcB9tdArWld+LyrECQQCb2fJAbudMNJSq/srZRjYoOFY6PlPQCXPFN0Zt7e/6P/FIzsbnWDfTMkFnr6sQyWu8w+qmX+gWNMjMHjCBIyetAkBSKZpBQS4voTn/bZvadg8RV5cFGmK7f7yuYe0SfNhaVF4WlBk89XE7aTFqs1+6+0gsPToGy7F7Niwu1VMeLDfRAkBAPICA1jU3T6THJeVt0GaaBT5lGzNoV70D2FiLDrsOpjRJEhEhYVcBEYGrvtf/P0XMs+OPV5BNNsxcdUUAtQCxAkAHafXXcY5JVdlbxcd5Ul1mgMjWC+6CRJ9vXr/WSmJBKhbcnMqmLV19LLKtBS57jnUDWPr7lLZZAsrP+BCryebm";
    private final String CHARSET = "UTF-8";
    private final String PARAM = "reqParam";
    private final String ACCOUNT = "18988941302";

    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("cardId", bankCard.getCardNo());
        dataMap.put("crpIdNo", bankCard.getIdCard());
        dataMap.put("crpName", bankCard.getName());
        dataMap.put("crpPhone", bankCard.getPhone());
        dataMap.put("headBank", bankCard.getBankName());
        dataMap.put("channelNum", "100001");
        dataMap.put("payCode", "100301");
        dataMap.put("expiry", bankCard.getValidThru());
        dataMap.put("backNum", bankCard.getCvv());
        dataMap.put("orderNo", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));

        String jsonMap = JSONUtil.mapToJson(dataMap);
        System.out.println("进行签名加密的原数据：" + jsonMap);
        jsonMap = Base64.encodeToString(jsonMap);
        // 通过私钥进行签名，得到签名
        byte[] mySign = LocalUtil.sign(Base64.decode(PRIVATEKEY.getBytes()), jsonMap);
        String reqSign = new String(mySign);

        System.out.println("向工信系统发送的签名：" + reqSign);

        // 通过公钥进行数据加密
        byte[] encodedData = RSAUtils2.encryptByPublicKey(jsonMap.getBytes(), PUBLICKKEY);
        String reqData = Base64Utils.encode(encodedData);

        System.out.println("向工信系统发送的加密数据：" + reqData);

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("sign", reqSign);
        request.put("data", reqData);
        request.put("account", ACCOUNT);

        JSONObject realParam = new JSONObject();
        realParam.put("serviceCode", "C1212");
        realParam.put("request", request);

        String rspJson = HttpClientUtils.postJson(channel.getChannelUrl(), PARAM,
                realParam.toString());

        MessageResponse msg = (MessageResponse) JSONObject.toBean(JSONObject.fromObject(rspJson), MessageResponse.class);

        Map<String, Object> rspMap = msg.getResponse();

        System.out.println("工信系统响应的数据：" + rspMap);

        String rspCode = rspMap.get("respCode").toString();
        String respInfo = rspMap.get("respInfo").toString();
        if (!rspCode.equals("0000")) {
            throw new ServiceException("2000", "卡签约失败-"+respInfo);
        } else {
            System.out.println("交易成功，开始验签。");
            String rspSign = rspMap.get("sign").toString();
            String openCardOrderNo = rspMap.get("openCardOrderNo").toString();

            Map<String, Object> signMap = new HashMap<String, Object>();
            signMap.put("respCode", rspCode);
            signMap.put("respInfo", respInfo);
			/*signMap.put("openCardOrderNo", openCardOrderNo);*/
            signMap.put("html", rspMap.get("html").toString());
            String html = new String(Base64Utils.decode(rspMap.get("html").toString()));
            System.out.println(html);
            String signData = Base64.encodeToString(signMap.toString());
            boolean vfy = LocalUtil.verifySignature(
                    Base64.decode(PUBLICKKEY.getBytes()), rspSign,
                    signData.getBytes(CHARSET));
            System.out.println("验证签名 " + vfy);

            if(vfy){
                return openCardOrderNo;
            }else {
                throw new ServiceException("2000", "验签不通过");
            }
        }
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("amount", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue());//单位分
        //dataMap.put("settleAmount", "49575");
        dataMap.put("settleType", "D0");
        dataMap.put("cardId", bankCard.getCardNo());
        dataMap.put("crpIdNo", bankCard.getIdCard());
        dataMap.put("crpName", bankCard.getName());
        dataMap.put("crpPhone", bankCard.getPhone());
        dataMap.put("notifyUrl", channel.getNoticeUrl());
        dataMap.put("channelNum", "100001");
        dataMap.put("payCode", "100301");
        dataMap.put("appOrderId", tradeRecords.getOrderNo());

        String jsonMap = JSONUtil.mapToJson(dataMap);
        System.out.println("进行签名加密的原数据："+jsonMap);
        jsonMap = Base64.encodeToString(jsonMap);
        //通过私钥进行签名，得到签名
        byte[] mySign = LocalUtil.sign(Base64.decode(PRIVATEKEY.getBytes()), jsonMap);
        String reqSign = new String(mySign);

        System.out.println("向工信系统发送的签名：" + reqSign);

        //通过公钥进行数据加密
        byte[] encodedData = RSAUtils2.encryptByPublicKey(jsonMap.getBytes(), PUBLICKKEY);
        String reqData=Base64Utils.encode(encodedData);

        System.out.println("向工信系统发送的加密数据：" + reqData);

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("sign", reqSign);
        request.put("data", reqData);
        request.put("account", bankCard.getPhone());

        JSONObject realParam = new JSONObject();
        realParam.put("serviceCode", "A0210");
        realParam.put("request", request);

        String rspJson = HttpClientUtils.postJson(channel.getChannelUrl(), PARAM, realParam.toString());

        MessageResponse msg = (MessageResponse) JSONObject.toBean(JSONObject.fromObject(rspJson), MessageResponse.class);

        Map<String, Object> rspMap = msg.getResponse();

        System.out.println("工信系统响应的数据："+rspMap);

        String rspCode = rspMap.get("respCode").toString();
        if (!rspCode.equals("0000")) {
            System.out.println("交易失败："+rspCode);
        } else {
            System.out.println("交易成功，开始验签。");
            String rspSign = rspMap.get("sign").toString();

            Map<String, Object> signMap = new HashMap<String, Object>();
            signMap.put("respCode", rspMap.get("respCode").toString());
            signMap.put("respInfo", rspMap.get("respInfo").toString());
            signMap.put("sysOrderId", rspMap.get("sysOrderId").toString());
            String signData = Base64.encodeToString(signMap.toString());

            boolean vfy = LocalUtil.verifySignature(Base64.decode(PUBLICKKEY.getBytes()), rspSign, signData.getBytes(CHARSET));
            System.out.println("验证签名 " + vfy);
            System.out.println("订单号 ：" + rspMap.get("sysOrderId").toString());
        }

        return null;
    }

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception {
        return false;
    }

    @Override
    public Map<String, Object> sendSMSCode(WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("orderNo", "180425144451487");
        dataMap.put("checkCode", "111111");
        dataMap.put("amount", "10000");
        dataMap.put("merUrl", "http://www.baidu.com");

        String jsonMap = JSONUtil.mapToJson(dataMap);
        System.out.println("进行签名加密的原数据："+jsonMap);
        jsonMap = Base64.encodeToString(jsonMap);
        //通过私钥进行签名，得到签名
        byte[] mySign = LocalUtil.sign(Base64.decode(PRIVATEKEY.getBytes()), jsonMap);
        String reqSign = new String(mySign);

        System.out.println("向工信系统发送的签名：" + reqSign);

        //通过公钥进行数据加密
        byte[] encodedData = RSAUtils2.encryptByPublicKey(jsonMap.getBytes(), PUBLICKKEY);
        String reqData=Base64Utils.encode(encodedData);

        System.out.println("向工信系统发送的加密数据：" + reqData);

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("sign", reqSign);
        request.put("data", reqData);
        request.put("account", "13345100990");

        JSONObject realParam = new JSONObject();
        realParam.put("serviceCode", "B0210");
        realParam.put("request", request);

        String rspJson = HttpClientUtils.postJson(channel.getChannelUrl(), PARAM, realParam.toString());

        MessageResponse msg = (MessageResponse) JSONObject.toBean(JSONObject.fromObject(rspJson), MessageResponse.class);

        Map<String, Object> rspMap = msg.getResponse();

        System.out.println("工信系统响应的数据："+rspMap);

        String rspCode = rspMap.get("respCode").toString();
        if (!rspCode.equals("0000")) {
            System.out.println("交易失败："+rspCode);
        } else {
            System.out.println("交易成功，开始验签。");
            String rspSign = rspMap.get("sign").toString();

            Map<String, Object> signMap = new HashMap<String, Object>();
            signMap.put("respCode", rspMap.get("respCode").toString());
            signMap.put("respInfo", rspMap.get("respInfo").toString());
            signMap.put("html", rspMap.get("html").toString());

            String signData = Base64.encodeToString(signMap.toString());

            boolean vfy = LocalUtil.verifySignature(Base64.decode(PUBLICKKEY.getBytes()), rspSign, signData.getBytes(CHARSET));
            System.out.println("验证签名 " + vfy);
            System.out.println(rspMap.get("html").toString());
            String html = new String(Base64.decode(rspMap.get("html").toString()));
            System.out.println(html);
        }

        return null;
    }

    @Override
    public void settlement(WalletChannel channel, Map<String, Object> params) throws Exception {

    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }

}
