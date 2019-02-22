package com.group.wallet.channel.quick.lingchuangPay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.utils.HttpClientUtils;
import com.group.utils.MD5;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.mapper.WalletBranchBankMapper;
import com.group.wallet.model.*;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.model.zzlm.ZzlmChannelMer;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class LingchuangQuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(LingchuangQuickPayImpl.class);

    private final String App_ID = "0000000007";
    private final String App_Key = "886cc87581e5ed759292301e72e2a4d3";

    @Autowired
    private WalletBranchBankMapper walletBranchBankMapper;

    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, ZzlmChannel channel, ZzlmChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, ZzlmChannel channel, ZzlmChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, ZzlmChannel channel, ZzlmChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("branchBankName", "%"+userInfo.getSettleBank()+"%");
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
        String bankNum = "";
        if(list!=null && list.size()>0){
            bankNum = "0"+list.get(0).getBarnchBankCode().substring(0,3);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("service", "service.api.cash.apply");
        params.put("appid", App_ID);
        params.put("mchntOrderNo", tradeRecords.getOrderNo());
        params.put("powerId", "9000000001");
        params.put("subject", "华果方-凌创快捷支付");
        params.put("amount", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue()+"");
        params.put("cardId", bankCard.getCardNo());
        params.put("mobileNo", bankCard.getPhone());
        params.put("acctName", userInfo.getSettleName());
        params.put("acctIdcard", userInfo.getIdCard());
        params.put("bankNum", bankNum);
        params.put("acctCardno", userInfo.getSettleCardNo());
        params.put("cvn2", StringUtils.isEmpty(bankCard.getCvv())?"111":bankCard.getCvv());
        params.put("expDt", StringUtils.isEmpty(bankCard.getValidThru())?"1019":bankCard.getValidThru());
        params.put("busType", "WKPAY");
        params.put("tradeRate", new DecimalFormat("#.##").format(channelMer.getDeductRate()));
        params.put("drawFee", new DecimalFormat("#.##").format(channelMer.getPoundage()));
        params.put("notifyUrl", channel.getNoticeUrl());
        params.put("returnUrl", channel.getReturnUrl());
        params.put("version", "01");
        params.put("signature", getSign(params));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"cash/apply", JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        Map<String, Object> map = new HashMap<>();
        if(!"10000".equals(code)){
            String msg = jsonObject.getString("msg");
            throw new ServiceException("2000", msg);
        }else {
            map.put("openUrl", jsonObject.getString("payInfo"));
        }

        return map;
    }

    @Override
    public boolean checkSign(ZzlmChannel channel, Map<String, Object> params) throws Exception {
        String signature = (String) params.get("signature");
        params.remove("signature");
        params.put("subject", URLEncoder.encode((String) params.get("subject")));
        String signature1 = getSign(params);
        if(signature.equals(signature1)){
            return true;
        }

        return false;
    }

    @Override
    public Map<String, Object> sendSMSCode(ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public void settlement(ZzlmChannel channel, Map<String, Object> params) throws Exception {

    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }

    private String getSign(Map<String, Object> paramMap) throws Exception{
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if(value!=null && !"".equals(value.toString())){
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }

        String argPreSign = stringBuffer.toString() + "key=" + App_Key;
        logger.info("签名字符串：" + argPreSign);
        String signStr = MD5.getMessageDigest(argPreSign.getBytes("utf-8"));
        logger.info("签名结果：" + signStr);
        return signStr;
    }
}
