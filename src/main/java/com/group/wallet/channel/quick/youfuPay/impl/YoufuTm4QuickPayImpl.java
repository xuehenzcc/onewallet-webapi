package com.group.wallet.channel.quick.youfuPay.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.core.exception.ServiceException;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.channel.quick.youfuPay.utils.Base64;
import com.group.wallet.channel.quick.youfuPay.utils.Base64Utils;
import com.group.wallet.channel.quick.youfuPay.utils.HttpClientUtils;
import com.group.wallet.channel.quick.youfuPay.utils.JSONUtil;
import com.group.wallet.channel.quick.youfuPay.utils.LocalUtil;
import com.group.wallet.channel.quick.youfuPay.utils.MessageResponse;
import com.group.wallet.channel.quick.youfuPay.utils.RSAUtils2;
import com.group.wallet.mapper.WalletBranchBankMapper;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletBranchBank;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.model.zzlm.ZzlmChannelMer;
import com.group.wallet.service.CommonService;

import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;

@Service
public class YoufuTm4QuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(YoufuTm4QuickPayImpl.class);

    private final String PUBLICKKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEvwG2998AV4STOEiqko5Y6d2ktHhlKeqW2EUjx2G2gJcBgOkP6daHtAldrQxzS5pucMf3kCeJaQNXnx5FcnkwLCJZJbfigBrd6DO4RojVksiWu11rmBVBM0FT6iBMOCyH73Lf3JJaVVqjQ6SgFZ13zYkVm5l4xUIHKZjGRIvwnQIDAQAB";
    private final String PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIS/Abb33wBXhJM4SKqSjljp3aS0eGUp6pbYRSPHYbaAlwGA6Q/p1oe0CV2tDHNLmm5wx/eQJ4lpA1efHkVyeTAsIlklt+KAGt3oM7hGiNWSyJa7XWuYFUEzQVPqIEw4LIfvct/cklpVWqNDpKAVnXfNiRWbmXjFQgcpmMZEi/CdAgMBAAECgYBQlL5OumMFixrwX19FJBgDA1xHikH3bx13mitGT9cR+Tcxc3aG8XUIBZMV6WFAb+l8FzxcHIbTlAqyWuX+VEIZyAgBcYpZd9vUO9GNyNvpkVjkedltiO56ZubOw9e3bHOg2YMhncKYVjwPwzIAGBw6+XzhVyj/kw/1unrRcBoLwQJBANoMLLA5YgtLGemgBQ8/h/3UcZ+rKLqKBWRNSWjF55a5wDGdqWjSi4VT3Osz437yq5oCKLLxcB9tdArWld+LyrECQQCb2fJAbudMNJSq/srZRjYoOFY6PlPQCXPFN0Zt7e/6P/FIzsbnWDfTMkFnr6sQyWu8w+qmX+gWNMjMHjCBIyetAkBSKZpBQS4voTn/bZvadg8RV5cFGmK7f7yuYe0SfNhaVF4WlBk89XE7aTFqs1+6+0gsPToGy7F7Niwu1VMeLDfRAkBAPICA1jU3T6THJeVt0GaaBT5lGzNoV70D2FiLDrsOpjRJEhEhYVcBEYGrvtf/P0XMs+OPV5BNNsxcdUUAtQCxAkAHafXXcY5JVdlbxcd5Ul1mgMjWC+6CRJ9vXr/WSmJBKhbcnMqmLV19LLKtBS57jnUDWPr7lLZZAsrP+BCryebm";
    private final String CHARSET = "UTF-8";
    private final String PARAM = "reqParam";
    private final String ACCOUNT = "850581096800003";

    @Autowired
    private CommonService commonService;

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
        // 机构编号
        String agentnum = "151";

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("agentnum", agentnum);// 机构编号
        dataMap.put("channelNum", "100011");// 渠道编号
        dataMap.put("payCode", "100705");// 业务类型
        dataMap.put("appOrderId", tradeRecords.getOrderNo());//交易订单号
        dataMap.put("settlementName", bankCard.getName());// 姓名
        dataMap.put("inCard", userInfo.getSettleCardNo());// 收款卡号
        dataMap.put("crpIdNo", bankCard.getIdCard());// 身份证号
        dataMap.put("phone", bankCard.getPhone());//交易卡预留手机号
        dataMap.put("inphone", userInfo.getSettlePhone());//收款卡预留手机号
        dataMap.put("bankCode", getBank(userInfo));//开户连行号
        dataMap.put("rateFee", new DecimalFormat("#.####").format(tradeRecords.getRate().divide(new BigDecimal(100))));
        dataMap.put("drawFee", tradeRecords.getPlatformPoundage().multiply(new BigDecimal(100)).intValue());
        dataMap.put("outCardNo", bankCard.getCardNo());// 交易卡卡号
        dataMap.put("amount", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue());// 交易金额，单位（分）

        dataMap.put("callBackUrl", commonService.getProjectUrl()+"notice/youfupay");
        dataMap.put("returnUrl", channel.getReturnUrl());

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
        realParam.put("serviceCode", "N1131");
        realParam.put("request", request);

        System.out.println(realParam.toString());
        String rspJson = HttpClientUtils.postJson(channel.getChannelUrl(), PARAM,
                realParam.toString());

        MessageResponse msg = (MessageResponse) JSONObject.toBean(
                JSONObject.fromObject(rspJson), MessageResponse.class);

        Map<String, Object> rspMap = msg.getResponse();

        System.out.println("工信系统响应的数据：" + rspMap);

        Map<String, Object> result = new HashMap<>();
        String rspCode = rspMap.get("respCode").toString();
        if (!rspCode.equals("0000")) {
            String respInfo = rspMap.get("respInfo").toString();
            throw new ServiceException("2000", respInfo);
        } else {
            System.out.println("交易成功，开始验签。");
            String rspSign = rspMap.get("sign").toString();

            Map<String, Object> signMap = new HashMap<String, Object>();
            signMap.put("respCode", rspMap.get("respCode").toString());
            signMap.put("respInfo", rspMap.get("respInfo").toString());
            String signData = Base64.encodeToString(getParString(signMap));
            System.out.println();
            boolean vfy = LocalUtil.verifySignature(
                    Base64.decode(PUBLICKKEY.getBytes()), rspSign,
                    signData.getBytes(CHARSET));
            if(!vfy){
                throw new ServiceException("2000", "签名错误");
            }

            String respInfo = rspMap.get("respInfo").toString();
            String openUrl = StringUtils.substringAfter(respInfo, "|");
            result.put("openUrl", openUrl);
        }

        return result;
    }

    @Override
    public boolean checkSign(ZzlmChannel channel, Map<String, Object> params) throws Exception {
        String rspSign = (String) params.get("sign");
        params.remove("sign");

        boolean vfy = false;
        try {
            String signData = Base64.encodeToString(getParString1(params));
            vfy = LocalUtil.verifySignature(
                    Base64.decode(PUBLICKKEY.getBytes()), rspSign,
                    signData.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            logger.error("===校验签名失败===", e);
        }
        return vfy;
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

    public static String getParString(Map<String, Object> signMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("respCode=" + signMap.get("respCode") + ", ");
        sb.append("respInfo=" + signMap.get("respInfo"));
        sb.append("}");
        return sb.toString();
    }

    /**
     * 解决JDK版本导致的验签不过问题
     *
     * @param signMap
     * @return
     */
    public static String getParString1(Map<String, Object> signMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("amount=" + signMap.get("amount") + ", ");
        sb.append("respCode=" + signMap.get("respCode") + ", ");
        sb.append("channelNum=" + signMap.get("channelNum") + ", ");
        sb.append("appOrderId=" + signMap.get("appOrderId") + ", ");
        sb.append("orderId=" + signMap.get("orderId") + ", ");
        sb.append("respInfo=" + signMap.get("respInfo") + "");
        sb.append("}");
        System.out.println(sb.toString());
        return sb.toString();
    }

    private String getBank(WalletUserInfo userInfo){
        String bankCode = "";
        RowBounds rowBounds = new RowBounds(0,1);
        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(userInfo.getSettleBranchBank())){
            criteria.andEqualTo("branchBankName", userInfo.getSettleBranchBank());
            List<WalletBranchBank> list = walletBranchBankMapper.selectByExampleAndRowBounds(example, rowBounds);
            if(list!=null && list.size()>0){
                bankCode = list.get(0).getBarnchBankCode();
            }else if (StringUtils.isNotEmpty(userInfo.getSettleBank())){
                example.clear();
                criteria = example.createCriteria();
                criteria.andEqualTo("branchBankName", userInfo.getSettleBank());
                List<WalletBranchBank> list1 = walletBranchBankMapper.selectByExampleAndRowBounds(example, rowBounds);
                if(list1!=null && list1.size()>0){
                    bankCode = list1.get(0).getBarnchBankCode();
                }
            }
        }else if(StringUtils.isNotEmpty(userInfo.getSettleBank())) {
            criteria.andEqualTo("branchBankName", userInfo.getSettleBank());
            List<WalletBranchBank> list1 = walletBranchBankMapper.selectByExampleAndRowBounds(example, rowBounds);
            if(list1!=null && list1.size()>0){
                bankCode = list1.get(0).getBarnchBankCode();
            }
        }

        return bankCode;
    }

}
