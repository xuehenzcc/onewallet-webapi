package com.group.wallet.channel.quick.zheyangPay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.group.core.exception.ServiceException;
import com.group.utils.HttpClientUtils;
import com.group.utils.MD5;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.mapper.WalletTradeRecordsMapper;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.TradeState;
import com.group.wallet.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZheyangHZXE1QuickPayImpl implements QuickPay {

    @Autowired
    private CommonService commonService;

    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;


    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        String projectUrl = commonService.getProjectUrl();

        JSONObject content = new JSONObject();
        JSONObject obj = new JSONObject();
        String method = "fastpayPrecreate2";
        obj.put("encryptId", "000600000000077");
        obj.put("apiVersion", 1);
        obj.put("txnDate", Calendar.getInstance().getTimeInMillis());

        obj.put("method", method);
        obj.put("mid", "000600000000077");//商户号
        obj.put("srcAmt", tradeRecords.getRealTradeMoney());//金额
        obj.put("accountNumber", bankCard.getCardNo());//信用卡号
        obj.put("tel", bankCard.getPhone());//预留手机号
        obj.put("notifyUrl", projectUrl+"/notice/zheyangfpay");
        obj.put("frontUrl", projectUrl+"/pay/success?orderNo="+tradeRecords.getOrderNo());
        obj.put("fastpayFee", channelMer.getDeductRate());//交易手续费 0.5%传0.5
        obj.put("agencyType", "hzxe1");

        obj.put("holderName", userInfo.getSettleName());
        obj.put("idcard", userInfo.getIdCard());
        obj.put("settAccountNumber", userInfo.getSettleCardNo());
        obj.put("settAccountTel", userInfo.getSettlePhone());
        obj.put("extraFee", channelMer.getPoundage());//额外手续费
        obj.put("expired", "2020");
        obj.put("cvv2", "111");

        obj.put("bizOrderNumber", tradeRecords.getOrderNo());

        content.put("content", JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue));
        content.put("key", channel.getPayKey());
        String signStr = JSON.toJSONString(content,SerializerFeature.WriteMapNullValue);
        System.out.println("signStr=="+signStr);
        String sign = MD5.getMessageDigest(signStr.getBytes("utf-8"));
        content.remove("key");
        content.put("sign", sign);
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+method, JSON.toJSONString(content));
        JSONObject resultObj = JSONObject.parseObject(result);
        JSONObject resultSignObj = new JSONObject();
        resultSignObj.put("result", resultObj.getString("result"));
        resultSignObj.put("key", channel.getPayKey());
        signStr = JSON.toJSONString(resultSignObj,SerializerFeature.WriteMapNullValue);
        System.out.println("signStr=="+signStr);
        sign = MD5.getMessageDigest(signStr.getBytes("utf-8"));
        if(!sign.equals(resultObj.getString("sign"))){
            //throw new ServiceException("2000", "签名错误");
        }

        String resultStr = resultObj.getString("result");
        JSONObject resultObj1 = JSONObject.parseObject(resultStr);
        String code = resultObj1.getString("code");
        String message = resultObj1.getString("message");
        if(!"000000".equals(code)){
            throw new ServiceException("2000", message);
        }

        JSONObject data = resultObj1.getJSONObject("data");
        String openUrl = data.getString("openUrl");

        Map<String, Object> map = new HashMap<>();
        map.put("openUrl", openUrl);

        //修改订单状态未支付中
        WalletTradeRecords tradeRecords1 = new WalletTradeRecords();
        tradeRecords1.setTradeState(TradeState.支付中.getValue());

        Example example = new Example(WalletTradeRecords.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo", tradeRecords.getOrderNo());
        walletTradeRecordsMapper.updateByExampleSelective(tradeRecords1, example);

        return map;
    }

    @Override
    public Map<String, Object> sendSMSCode(WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public void settlement(WalletChannel channel, Map<String, Object> params) throws Exception {

    }

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception{
        String bizOrderNumber = (String) params.get("bizOrderNumber");
        String completedTime = (String) params.get("completedTime");
        String mid = (String) params.get("mid");
        String srcAmt = (String) params.get("srcAmt");
        String sign = (String) params.get("sign");

        String key = channel.getPayKey();
        String signStr = "bizOrderNumber="+bizOrderNumber+"&completedTime="+completedTime+"&mid="+mid+"&srcAmt="+srcAmt+"&key="+key;
        String sign1 = MD5.getMessageDigest(signStr.getBytes("utf-8"));

        if(sign1.equals(sign1)){
            return true;
        }
        return false;
    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }
}
