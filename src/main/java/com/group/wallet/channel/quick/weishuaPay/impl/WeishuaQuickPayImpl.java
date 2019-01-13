package com.group.wallet.channel.quick.weishuaPay.impl;

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
import java.text.DecimalFormat;
import java.util.*;

@Service
public class WeishuaQuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(WeishuaQuickPayImpl.class);

    private final String merchno = "201804111630111";
    private final String secret = "b323aac6";

    @Autowired
    private CommonService commonService;
    @Autowired
    private WalletBranchBankMapper walletBranchBankMapper;

    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        String channelMerchantNo = "";
        Map<String, Object> map = registInfo(userInfo, channel, channelMer);
        String returncode = (String) map.get("returncode");
        if("0000".equals(returncode)){
            channelMerchantNo = (String) map.get("subMerchantNo");
        }
        else if ("03008".equals(returncode)){
            //修改报件
            channelMerchantNo = (String) map.get("subMerchantNo");
            if(StringUtils.isNotEmpty(channelMerchantNo)) channelMer.setChannelMerNo(channelMerchantNo);
            map = updateInfo(userInfo, channel, channelMer);
            returncode = (String) map.get("returncode");
            if(!"0000".equals(returncode)){
                String errtext = (String) map.get("errtext");
                throw new ServiceException("2000", errtext);
            }
        }
        else {
            String errtext = (String) map.get("errtext");
            throw new ServiceException("2000", errtext);
        }

        return channelMerchantNo;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        //修改报件
        Map<String, Object> map = updateInfo(userInfo, channel, channelMer);
        String returncode = (String) map.get("returncode");
        if(!"0000".equals(returncode)){
            String errtext = (String) map.get("errtext");
            throw new ServiceException("2000", errtext);
        }

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
        logger.info("===openUrl===："+result.get("openUrl"));
        return result;
    }

    @Override
    public Map<String, Object> sendSMSCode(WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("transcode", "026");//交易码
        paramMap.put("version", "0100");
        paramMap.put("ordersn", UUID.randomUUID().toString().replaceAll("-", ""));//流水号
        paramMap.put("dsorderid", tradeRecords.getOrderNo());//商户订单号
        paramMap.put("merchno", merchno);//商户号

        paramMap.put("amount", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue()+"");//订单金额，单位分
        paramMap.put("bankcard", bankCard.getCardNo());//消费银行卡号
        paramMap.put("accountName", bankCard.getName());
        paramMap.put("mobile", bankCard.getPhone());
        paramMap.put("cvn2", bankCard.getCvv());
        paramMap.put("expireDate", bankCard.getValidThru());
        paramMap.put("subMerchantNo", tradeRecords.getRouteMerchantNo());
        paramMap.put("returnUrl", commonService.getProjectUrl()+"pay/success?orderNo="+tradeRecords.getOrderNo());
        paramMap.put("notifyUrl", commonService.getProjectUrl()+"notice/weishuapay");
        paramMap.put("sign", getSign(paramMap));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"api/quick/pay.do", JSON.toJSONString(paramMap));
        Map<String, Object> resultMap = JSON.parseObject(result, HashMap.class);
        String returncode = (String) resultMap.get("returncode");
        if(!"0000".equals(returncode)){
            String errtext = (String) resultMap.get("errtext");
            throw new ServiceException("2000", errtext);
        }

        String sign = (String) resultMap.get("sign");
        resultMap.remove("sign");
        String sign2 = getSign(resultMap);
        if(!sign.equals(sign2)){
            throw new ServiceException("2000", "签名错误");
        }

        String pay_info = (String) resultMap.get("pay_info");
        String pay_code = (String) resultMap.get("pay_code");
        if(StringUtils.isNotEmpty(pay_code)){
            String pay_params1 = "";
            Map<String, Object> pay_params = JSON.parseObject(pay_code, HashMap.class);
            for (Map.Entry<String, Object> entry : pay_params.entrySet()){
                pay_params1 += entry.getKey()+"="+entry.getValue()+"&";
            }
            pay_info = pay_info + "?" + pay_params1;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("pay_info", pay_info);
        return map;
    }

    @Override
    public void settlement(WalletChannel channel, Map<String, Object> params) throws Exception {

    }

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception {
        String sign = (String) params.get("sign");
        params.remove("sign");
        String sign2 = getSign(params);
        if(sign.equals(sign2)){
            return true;
        }
        return false;
    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }


    /**
     * 快捷进件
     * @param userInfo
     * @param channel
     * @param channelMer
     * @return
     * @throws Exception
     */
    private Map<String, Object> registInfo(WalletUserInfo userInfo, WalletChannel channel, WalletChannelMer channelMer) throws Exception{
        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("branchBankName", userInfo.getSettleBank());
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExample(example);
        String swiftCode = "";
        if(list!=null && list.size()>0){
            swiftCode = list.get(0).getSwiftCode();
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("transcode", "025");//交易码
        paramMap.put("version", "0100");
        paramMap.put("ordersn", UUID.randomUUID().toString().replaceAll("-", ""));//流水号
        paramMap.put("merchno", merchno);//商户号
        paramMap.put("dsorderid", System.currentTimeMillis() + "");//商户订单号

        paramMap.put("futureRateValue", new DecimalFormat("#.####").format(channelMer.getDeductRate()));
        paramMap.put("fixAmount", new DecimalFormat("#.####").format(channelMer.getPoundage().multiply(new BigDecimal(100))));
        paramMap.put("accountName", userInfo.getRealName());
        paramMap.put("idcard", userInfo.getIdCard());
        paramMap.put("settleBankCard", userInfo.getSettleCardNo());
        paramMap.put("settleBankName", userInfo.getSettleBank());
        paramMap.put("settleBankCode", swiftCode);
        paramMap.put("mobile", userInfo.getSettlePhone());
        paramMap.put("sign", getSign(paramMap));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"api/merchant/register.do", JSON.toJSONString(paramMap));
        Map<String, Object> map = JSON.parseObject(result, HashMap.class);
        return map;
    }

    /**
     * 修改进件
     * @param userInfo
     * @param channel
     * @param channelMer
     * @return
     * @throws Exception
     */
    private Map<String, Object> updateInfo(WalletUserInfo userInfo, WalletChannel channel, WalletChannelMer channelMer) throws Exception{
        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("branchBankName", userInfo.getSettleBank());
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExample(example);
        String swiftCode = "";
        if(list!=null && list.size()>0){
            swiftCode = list.get(0).getSwiftCode();
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("transcode", "027");//交易码
        paramMap.put("version", "0100");
        paramMap.put("ordersn", UUID.randomUUID().toString().replaceAll("-", ""));//流水号
        paramMap.put("dsorderid", System.currentTimeMillis() + "");//商户订单号
        paramMap.put("merchno", merchno);//商户号

        paramMap.put("futureRateValue", new DecimalFormat("#.####").format(channelMer.getDeductRate()));
        paramMap.put("fixAmount", new DecimalFormat("#.####").format(channelMer.getPoundage().multiply(new BigDecimal(100))));
        paramMap.put("settleBankCard", userInfo.getSettleCardNo());
        paramMap.put("settleBankCode", swiftCode);
        paramMap.put("settleBankName", userInfo.getSettleBank());
        paramMap.put("accountName", userInfo.getRealName());
        paramMap.put("mobile", userInfo.getSettlePhone());
        paramMap.put("subMerchantNo", channelMer.getChannelMerNo());

        paramMap.put("sign", getSign(paramMap));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"api/update/fee.do", JSON.toJSONString(paramMap));
        Map<String, Object> map = JSON.parseObject(result, HashMap.class);
        return map;
    }

    private String getSign(Map<String, Object> paramMap) throws Exception{
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if(value==null)
                value = "";

            stringBuffer.append(m.getKey()).append("=").append(value);
        }

        String argPreSign = stringBuffer.toString() + secret;
        logger.info("签名字符串：" + argPreSign);
        String signStr = MD5.getMessageDigest(argPreSign.getBytes("utf-8"));
        logger.info("签名结果：" + signStr);
        return signStr;
    }

}
