package com.group.wallet.channel.quick.ysbPay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.utils.HttpClientUtils;
import com.group.utils.MD5;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class YsbQuickPayImpl implements QuickPay {

    private final String accountId = "2120180527101423001";

    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        //新增报件
        JSONObject jsonObject = registerCustomer(userInfo, channel, channelMer);
        String result_code = jsonObject.getString("result_code");
        String merchantNo = jsonObject.getString("merchantNo");
        if("1015".equals(result_code)){
            //修改报件
            JSONObject jsonObject1 = updateCustomer(userInfo, channel, channelMer);
            String result_code1 = jsonObject1.getString("result_code");
            merchantNo = jsonObject1.getString("merchantNo");
            if(!"0000".equals(result_code1)){
                String result_msg1 = jsonObject1.getString("result_msg");
                throw new ServiceException("2000", result_msg1);
            }
        }
        else if(!"0000".equals(result_code)){
            String result_msg = jsonObject.getString("result_msg");
            throw new ServiceException("2000", result_msg);
        }

        //绑卡
        h5bindInfo(userInfo, channel, merchantNo);
        checkCardInfo(userInfo, channel, merchantNo);

        return merchantNo;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        h5bind(userInfo, channel, channelMer);
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
        return null;
    }

    @Override
    public void settlement(WalletChannel channel, Map<String, Object> params) throws Exception {

    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }



    private JSONObject registerCustomer(WalletUserInfo userInfo, WalletChannel channel, WalletChannelMer channelMer) throws Exception{
        String memberId = userInfo.getPhone();
        String name = userInfo.getRealName();
        String certType = "1";
        String certNo = userInfo.getIdCard();
        String D0FeeRate = channelMer.getDeductRate()+"";
        String D0FixedFee = channelMer.getPoundage()+"";
        String T1FeeRate = channelMer.getDeductRate()+"";
        String T1FixedFee = channelMer.getPoundage()+"";

        String macStr = "accountId="+accountId+"&memberId="+memberId+"&name="+name+"&certType="+certType+"&certNo="+certNo+"&D0FeeRate="+D0FeeRate+"&D0FixedFee="+D0FixedFee+"&T1FeeRate="+T1FeeRate+"&T1FixedFee="+T1FixedFee+"&key="+channel.getMd5Key();
        String mac = MD5.getMessageDigest(macStr.getBytes()).toUpperCase();

        Map<String, Object> params = new HashMap();
        params.put("accountId", accountId);
        params.put("memberId", memberId);
        params.put("name", name);
        params.put("certType", certType);
        params.put("certNo", certNo);
        params.put("D0FeeRate", D0FeeRate);
        params.put("D0FixedFee", D0FixedFee);
        params.put("T1FeeRate", T1FeeRate);
        params.put("T1FixedFee", T1FixedFee);
        params.put("mac", mac);

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"report/register", JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }


    private JSONObject updateCustomer(WalletUserInfo userInfo, WalletChannel channel, WalletChannelMer channelMer) throws Exception{
        String memberId = userInfo.getPhone();
        String merchantNo = "2110000000000000019164";
        String D0FeeRate = channelMer.getDeductRate()+"";
        String D0FixedFee = channelMer.getPoundage()+"";
        String T1FeeRate = channelMer.getDeductRate()+"";
        String T1FixedFee = channelMer.getPoundage()+"";

        String macStr = "accountId="+accountId+"&memberId="+memberId+"&merchantNo="+merchantNo+"&D0FeeRate="+D0FeeRate+"&D0FixedFee="+D0FixedFee+"&T1FeeRate="+T1FeeRate+"&T1FixedFee="+T1FixedFee+"&key="+channel.getMd5Key();
        String mac = MD5.getMessageDigest(macStr.getBytes()).toUpperCase();

        Map<String, Object> params = new HashMap();
        params.put("accountId", accountId);
        params.put("memberId", memberId);
        params.put("merchantNo", merchantNo);
        params.put("D0FeeRate", D0FeeRate);
        params.put("D0FixedFee", D0FixedFee);
        params.put("T1FeeRate", T1FeeRate);
        params.put("T1FixedFee", T1FixedFee);
        params.put("mac", mac);

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"report/update", JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    private JSONObject h5bind(WalletUserInfo userInfo, WalletChannel channel, WalletChannelMer channelMer) throws Exception{
        String memberId = userInfo.getPhone();
        String merchantNo = channelMer.getChannelMerNo();
        String responseUrl = "http://www.baidu.com";

        String macStr = "accountId="+accountId+"&memberId="+memberId+"&merchantNo="+merchantNo+"&responseUrl="+responseUrl+"&key="+channel.getMd5Key();
        String mac = MD5.getMessageDigest(macStr.getBytes()).toUpperCase();

        Map<String, String> params = new HashMap();
        params.put("accountId", accountId);
        params.put("memberId", memberId);
        params.put("merchantNo", merchantNo);
        params.put("responseUrl", responseUrl);
        params.put("mac", mac);

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"bind/h5bind", params, null, null);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    private void h5bindInfo(WalletUserInfo userInfo, WalletChannel channel, String merchantNo) throws Exception{
        String cardNo = userInfo.getSettleCardNo();
        String name = userInfo.getRealName();
        String responseUrl = "http://www.baidu.com";

        Map<String, String> params = new HashMap();
        params.put("cardNo", cardNo);
        params.put("merchantNo", merchantNo);
        params.put("name", name);
        params.put("responseUrl", responseUrl);

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"bind/h5bindInfo", params, null, null);
        System.out.println(result);
    }

    private void checkCardInfo(WalletUserInfo userInfo, WalletChannel channel, String merchantNo) throws Exception{
        String name = userInfo.getRealName();
        String bankName = userInfo.getSettleBank();
        String cardNo = userInfo.getSettleCardNo();
        String cardType = "0";
        String responseUrl = "http://www.baidu.com";
        String idCardNo = userInfo.getIdCard();
        String phone = userInfo.getSettlePhone();

        Map<String, String> params = new HashMap();
        params.put("name", name);
        params.put("bankName", bankName);
        params.put("cardNo", cardNo);
        params.put("merchantNo", merchantNo);
        params.put("cardType", cardType);
        params.put("responseUrl", responseUrl);
        params.put("idCardNo", idCardNo);
        params.put("phone", phone);

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"bind/checkCardInfo", params, null, null);
        System.out.println(result);
    }


    private Map<String, Object> queryInfo(String memberId, String merchantNo){


        return null;
    }

}
