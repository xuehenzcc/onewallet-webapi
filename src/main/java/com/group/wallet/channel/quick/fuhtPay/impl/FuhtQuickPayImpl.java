package com.group.wallet.channel.quick.fuhtPay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.config.MyWebAppConfig;
import com.group.core.exception.ServiceException;
import com.group.utils.HttpClientUtils;
import com.group.utils.MD5;
import com.group.utils.SignUtils;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.mapper.WalletBranchBankMapper;
import com.group.wallet.model.*;
import com.group.wallet.service.CommonService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 福汇通
 */
@Service
public class FuhtQuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(FuhtQuickPayImpl.class);

    private final String userid = "100193";
    private final String userpwd = MD5.getMessageDigest("123456".getBytes()).toLowerCase();
    private final String secret = "31960F2115DAFC305BFF9F89B9652D16";

    @Autowired
    private CommonService commonService;
    @Autowired
    private WalletBranchBankMapper walletBranchBankMapper;

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
        Map<String, Object> result = new HashMap<>();

        Map<String, String> params = new HashMap<>();
        params.put("orderNo", tradeRecords.getOrderNo());
        String appSecert = MyWebAppConfig.environment.getProperty("ape.apikey");
        String sign = SignUtils.getSign(params, appSecert, logger);

        String projectUrl = commonService.getProjectUrl();
        result.put("openUrl", projectUrl+"pay/index?orderNo="+tradeRecords.getOrderNo()+"&sign="+sign);
        return result;
    }

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception {
        Map<String, String> signParams = new HashMap<>();
        signParams.put("pay_type", (String) params.get("pay_type"));
        signParams.put("service_id", (String) params.get("service_id"));
        signParams.put("userid", userid);
        signParams.put("userpwd", userpwd);
        signParams.put("terminal_time", (String) params.get("terminal_time"));
        signParams.put("total_fee", (String) params.get("total_fee"));
        String sign = getSign(signParams);

        String key_sign = (String) params.get("key_sign");
        if(sign.equals(key_sign)){
            return true;
        }

        return false;
    }

    @Override
    public Map<String, Object> sendSMSCode(WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        String pay_type = "76";
        String service_id = "01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String terminal_time = sdf.format(new Date());
        int total_fee = tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue();
        int realFee = tradeRecords.getRealTradeMoney().multiply(tradeRecords.getRate()).setScale(0, BigDecimal.ROUND_UP).intValue() +
                tradeRecords.getPlatformPoundage().multiply(new BigDecimal(100)).intValue();
        int money_withdraw = total_fee - realFee;


        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("branchBankName", userInfo.getSettleBank());
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExample(example);
        String bankcode = "";
        if(list!=null && list.size()>0){
            bankcode = list.get(0).getBarnchBankCode();
        }

        Map<String, Object> down_data = new HashMap<>();
        down_data.put("feerate", tradeRecords.getRate().divide(new BigDecimal(100)));
        down_data.put("withdraw_fee", tradeRecords.getPlatformPoundage());

        Map<String, String> signParams = new HashMap<>();
        signParams.put("pay_type", pay_type);
        signParams.put("service_id", service_id);
        signParams.put("userid", userid);
        signParams.put("userpwd", userpwd);
        signParams.put("terminal_time", terminal_time);
        signParams.put("total_fee", total_fee+"");
        String sign = getSign(signParams);

        Map<String, String> payParams = new HashMap<>();
        payParams.put("pay_type", pay_type);
        payParams.put("service_id", service_id);
        payParams.put("userid", userid);
        payParams.put("userpwd", userpwd);
        payParams.put("terminal_time", terminal_time);
        payParams.put("total_fee", total_fee+"");
        payParams.put("province", userInfo.getSettleProvince());
        payParams.put("city", userInfo.getSettleCity());
        payParams.put("address", tradeRecords.getTradeAddress());
        payParams.put("body", "快捷支付");
        payParams.put("phoneNo", bankCard.getPhone());
        payParams.put("customerName", bankCard.getName());
        payParams.put("cerdId", bankCard.getIdCard());
        payParams.put("acctNo", bankCard.getCardNo());
        payParams.put("cvn2", bankCard.getCvv());
        payParams.put("expDate", bankCard.getValidThru());
        payParams.put("money_withdraw", money_withdraw+"");
        payParams.put("acctNo_withdraw", userInfo.getSettleCardNo());
        payParams.put("bankname_withdraw", userInfo.getSettleBank());
        payParams.put("bankcode", bankcode);
        payParams.put("mobile_withdraw", userInfo.getSettlePhone());
        payParams.put("down_data", JSON.toJSONString(down_data));
        payParams.put("attach", tradeRecords.getOrderNo());
        payParams.put("notify_url", channel.getNoticeUrl());
        payParams.put("key_sign", sign);

        String result = HttpClientUtils.sendPost(channel.getChannelUrl(), JSON.toJSONString(payParams));
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject resultObject = jsonObject.getJSONObject("result");
        JSONObject dataObject = jsonObject.getJSONObject("data");

        String code = resultObject.getString("code");
        if(!"10000".equals(code)){
            String msg = resultObject.getString("msg");
            throw new ServiceException("2000", msg);
        }else {
            String result_code = dataObject.getString("result_code");
            if(!"01".equals(result_code)){
                String result_msg = dataObject.getString("result_msg");
                throw new ServiceException("2000", result_msg);
            }
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

    private String getSign(Map<String, String> paramMap) throws Exception{
        SortedMap<String, String> smap = new TreeMap<String, String>(paramMap);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> m : smap.entrySet()) {
            String value = m.getValue();
            if(value==null)
                value = "";

            stringBuffer.append(m.getKey()).append("=").append(value).append("&");
        }

        String argPreSign = StringUtils.substring(stringBuffer.toString(), 0, stringBuffer.length()-1) + secret;
        logger.info("签名字符串：" + argPreSign);
        String signStr = MD5.getMessageDigest(argPreSign.getBytes("utf-8")).toLowerCase();
        logger.info("签名结果：" + signStr);
        return signStr;
    }

}
