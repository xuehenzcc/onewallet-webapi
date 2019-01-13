package com.group.wallet.channel.quick.shenzhouPay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.utils.CommonUtils;
import com.group.utils.HttpClientUtils;
import com.group.wallet.mapper.WalletSubCardMapper;
import com.group.wallet.mapper.WalletTradeRecordsMapper;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.TradeState;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.channel.quick.shenzhouPay.utils.MerchantApiUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.crypto.MacSpi;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShenzhouQuickPayImpl implements QuickPay {

    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;
    @Autowired
    private WalletSubCardMapper walletSubCardMapper;

    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        Map<String, String> paramMap = new HashMap<>();

        String onDeaName = userInfo.getRealName();//注册商户名
        String onDeaRegAddress = "-";//商户注册地址
        String onDeaArtifId = userInfo.getIdCard();//法人身份证号
        String onDeaPhone = userInfo.getSettlePhone();//手机号码
        String onDeaSettleCard = userInfo.getSettleCardNo();//商户结算卡号
        String onDeaBankName = userInfo.getSettleBank();//开户行名称
        String onDeaSettleName = userInfo.getSettleName();//开户人姓名

        paramMap.put("onDeaName",onDeaName);
        paramMap.put("onDeaRegAddress",onDeaRegAddress);
        paramMap.put("onDeaArtifId",onDeaArtifId);
        paramMap.put("onDeaPhone",onDeaPhone);
        paramMap.put("onDeaSettleCard",onDeaSettleCard);
        paramMap.put("onDeaBankName",onDeaBankName);
        paramMap.put("onDeaSettleName",onDeaSettleName);
        paramMap.put("rate", channelMer.getDeductRate().multiply(new BigDecimal(1000)).setScale(0).toString());

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"/quickGateWayPay/hcqMerchantAdd",paramMap,null,"utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        String subMerNo = "";
        if("000000".equals(code)){
            JSONObject result1 = jsonObject.getJSONObject("result");
            String respCode = result1.getString("resultCode");
            if("1200".equals(respCode)){
                String xsDm = result1.getString("xsDm");
                String serial = result1.getString("serial");
                subMerNo = xsDm;
            }else {
                String respMsg = result1.getString("respMsg");
                String resultMsg = result1.getString("resultMsg");
                throw new ServiceException("2000",StringUtils.isEmpty(respMsg)?resultMsg:respMsg);
            }
        }else {
            String message = jsonObject.getString("message");
            throw new ServiceException("2000", message);
        }

        return subMerNo;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        return null;
    }

    /*@Override
    public void dredgeBusiness(WalletChannel channel, String subMerNo, WalletBankCard bankCard) throws Exception{
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("trano", subMerNo);
        paramMap.put("pan", bankCard.getCardNo());
        paramMap.put("name", bankCard.getName());
        paramMap.put("phone", bankCard.getPhone());
        paramMap.put("expireDate", bankCard.getValidThru());
        paramMap.put("cvn", bankCard.getCvv());

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"/quickGateWayPay/hcqBankUppay",paramMap,null,"utf-8");
        JSONObject result1 = JSON.parseObject(result);
        String status = result1.getString("status");
        if("OK".equals(status)){
            JSONObject result2 = result1.getJSONObject("result");
            String respCode = result2.getString("respCode");
            if(!"00".equals(respCode) && !"A001".equals(respCode)){
                String respMsg = result1.getString("respMsg");
                throw new ServiceException("2000","开通业务失败-"+respMsg);
            }

            *//*WalletSubCard subCard = new WalletSubCard();
            subCard.setSubMerNo(subMerNo);
            subCard.setCardNo(bankCard.getCardNo());
            subCard.setCreateTime(new Date());
            walletSubCardMapper.insertSelective(subCard);*//*
        }else {
            String message = result1.getString("message");
            throw new ServiceException("2000","开通业务失败-"+message);
        }
    }*/

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("productType", "40000302");
        paramMap.put("payKey", channel.getPayKey());// 商户支付Key
        paramMap.put("orderPrice", tradeRecords.getRealTradeMoney()+"");

        paramMap.put("outTradeNo", tradeRecords.getOrderNo());
        paramMap.put("productName", tradeRecords.getGoodsName());// 商品名称
        paramMap.put("orderIp", "");// 下单IP
        paramMap.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));// 订单时间
        paramMap.put("returnUrl", channel.getReturnUrl());// 页面通知返回url
        paramMap.put("notifyUrl", channel.getNoticeUrl()); // 后台消息通知Url
        paramMap.put("payBankAccountNo", channelMer.getChannelMerNo());//子商户号

        paramMap.put("sign", MerchantApiUtil.getSign(paramMap, channel.getMd5Key()));
        String html = HttpClientUtils.sendPost(channel.getChannelUrl()+"/quickGateWayPay/initPay", paramMap,null,"utf-8");
        String URL = StringUtils.substringBetween(html, "<script>window.location.href='", "'</script>");
        Map<String, String> map = CommonUtils.urlSplit(URL);

        String name = bankCard.getName();
        String cardNo = bankCard.getCardNo();
        String phone = bankCard.getPhone();

        map.put("pan", cardNo);
        map.put("name", name);
        map.put("phone", phone);

        Map<String, Object> resultMap = new HashMap<>();
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"quickGateWayPay/hcqPaySMS", map, null, "utf-8");
        JSONObject result1 = JSON.parseObject(result);
        String status = result1.getString("status");
        if("OK".equals(status)){
            JSONObject result2 = result1.getJSONObject("result");
            String respCode = result2.getString("respCode");
            if("00".equals(respCode)){

                String trano = result1.getString("trano");
                String stan = result1.getString("stan");
                String amount = result1.getString("amount");
                String name1 = result1.getString("name");
                String pan = result2.getString("pan");
                String preSerial = result1.getString("preSerial");
                String phone1 = result1.getString("phone");

                resultMap.put("trano", trano);
                resultMap.put("stan", stan);
                resultMap.put("amount", amount);
                resultMap.put("name", name);
                resultMap.put("pan", pan);
                resultMap.put("preSerial", preSerial);
                resultMap.put("phone", phone1);
                resultMap.put("outTradeNo", tradeRecords.getOrderNo());
            }else {
                String respMsg = result2.getString("respMsg");
                throw new ServiceException("2000",respMsg);
            }
        }else {
            String message = result1.getString("message");
            throw new ServiceException("2000",message);
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> sendSMSCode(WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        /*Map<String, String> map = JSON.parseObject(json, Map.class);
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"quickGateWayPay/hcqPay", map, null, "utf-8");
        JSONObject result1 = JSON.parseObject(result);
        String status = result1.getString("status");
        if("OK".equals(status)){
            JSONObject result2 = result1.getJSONObject("result");
            String respCode = result2.getString("respCode");
            if(!"00".equals(respCode)){
                String respMsg = result2.getString("respMsg");
                throw new ServiceException("2000","支付失败-"+respMsg);
            }
        }else {
            String message = result1.getString("message");
            throw new ServiceException("2000","支付失败-"+message);
        }*/
        return null;
    }

    @Override
    public void settlement(WalletChannel channel, Map<String, Object> params) throws Exception {

    }

    /*@Override
    public String h5QuickPay(WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("productType", "40000302");
        paramMap.put("payKey", channel.getPayKey());// 商户支付Key
        paramMap.put("orderPrice", tradeRecords.getRealTradeMoney()+"");

        paramMap.put("outTradeNo", tradeRecords.getOrderNo());
        paramMap.put("productName", tradeRecords.getGoodsName());// 商品名称
        paramMap.put("orderIp", "");// 下单IP
        paramMap.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));// 订单时间
        paramMap.put("returnUrl", channel.getReturnUrl());// 页面通知返回url
        paramMap.put("notifyUrl", channel.getNoticeUrl()); // 后台消息通知Url
        paramMap.put("payBankAccountNo", channelMer.getChannelMerNo());//子商户号

        paramMap.put("sign", MerchantApiUtil.getSign(paramMap, channel.getMd5Key()));
        String html = HttpClientUtils.sendPost(channel.getChannelUrl()+"/quickGateWayPay/initPay", paramMap,null,"utf-8");
        return html;
    }*/

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception{


        return true;
    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception{
        String payKey = (String) params.get("payKey");
        String orderPrice = (String) params.get("orderPrice");
        String outTradeNo = (String) params.get("outTradeNo");
        String productType = (String) params.get("productType");
        String orderTime = (String) params.get("orderTime");
        String productName = (String) params.get("productName");
        String tradeStatus = (String) params.get("tradeStatus");
        String successTime = (String) params.get("successTime");
        String trxNo = (String) params.get("trxNo");
        String field2 = (String) params.get("field2");
        String sign = (String) params.get("sign");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("payKey", payKey);
        paramMap.put("orderPrice", orderPrice);
        paramMap.put("outTradeNo", outTradeNo);
        paramMap.put("productType", productType);
        paramMap.put("orderTime", orderTime);
        paramMap.put("productName", productName);
        paramMap.put("tradeStatus", tradeStatus);
        paramMap.put("successTime", successTime);
        paramMap.put("trxNo", trxNo);
        paramMap.put("field2", field2);

        String sign1 = MerchantApiUtil.getSign(paramMap, "eb067bc7f8b040178fce2a43cd5336f3");
        if(!sign.equals(sign1)){
            throw new ServiceException("2000","签名错误");
        }

        Example example = new Example(WalletTradeRecords.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo", outTradeNo);

        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setTradeState(TradeState.支付成功.getValue());
        walletTradeRecordsMapper.updateByExampleSelective(tradeRecords,example);
        return "SUCCESS";
    }

}
