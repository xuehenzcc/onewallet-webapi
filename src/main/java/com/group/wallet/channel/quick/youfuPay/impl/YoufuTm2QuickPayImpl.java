package com.group.wallet.channel.quick.youfuPay.impl;

import com.group.core.config.MyWebAppConfig;
import com.group.core.exception.ServiceException;
import com.group.utils.SignUtils;
import com.group.wallet.channel.quick.youfuPay.utils.*;
import com.group.wallet.mapper.WalletBranchBankMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.service.CommonService;
import net.sf.json.JSONObject;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YoufuTm2QuickPayImpl implements QuickPay {

    private final Logger log = LoggerFactory.getLogger(YoufuTm2QuickPayImpl.class);

    private final String PUBLICKKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEvwG2998AV4STOEiqko5Y6d2ktHhlKeqW2EUjx2G2gJcBgOkP6daHtAldrQxzS5pucMf3kCeJaQNXnx5FcnkwLCJZJbfigBrd6DO4RojVksiWu11rmBVBM0FT6iBMOCyH73Lf3JJaVVqjQ6SgFZ13zYkVm5l4xUIHKZjGRIvwnQIDAQAB";
    private final String PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIS/Abb33wBXhJM4SKqSjljp3aS0eGUp6pbYRSPHYbaAlwGA6Q/p1oe0CV2tDHNLmm5wx/eQJ4lpA1efHkVyeTAsIlklt+KAGt3oM7hGiNWSyJa7XWuYFUEzQVPqIEw4LIfvct/cklpVWqNDpKAVnXfNiRWbmXjFQgcpmMZEi/CdAgMBAAECgYBQlL5OumMFixrwX19FJBgDA1xHikH3bx13mitGT9cR+Tcxc3aG8XUIBZMV6WFAb+l8FzxcHIbTlAqyWuX+VEIZyAgBcYpZd9vUO9GNyNvpkVjkedltiO56ZubOw9e3bHOg2YMhncKYVjwPwzIAGBw6+XzhVyj/kw/1unrRcBoLwQJBANoMLLA5YgtLGemgBQ8/h/3UcZ+rKLqKBWRNSWjF55a5wDGdqWjSi4VT3Osz437yq5oCKLLxcB9tdArWld+LyrECQQCb2fJAbudMNJSq/srZRjYoOFY6PlPQCXPFN0Zt7e/6P/FIzsbnWDfTMkFnr6sQyWu8w+qmX+gWNMjMHjCBIyetAkBSKZpBQS4voTn/bZvadg8RV5cFGmK7f7yuYe0SfNhaVF4WlBk89XE7aTFqs1+6+0gsPToGy7F7Niwu1VMeLDfRAkBAPICA1jU3T6THJeVt0GaaBT5lGzNoV70D2FiLDrsOpjRJEhEhYVcBEYGrvtf/P0XMs+OPV5BNNsxcdUUAtQCxAkAHafXXcY5JVdlbxcd5Ul1mgMjWC+6CRJ9vXr/WSmJBKhbcnMqmLV19LLKtBS57jnUDWPr7lLZZAsrP+BCryebm";
    private final String CHARSET = "UTF-8";
    private final String PARAM = "reqParam";
    private final String ACCOUNT = "85058108520003";

    @Autowired
    private CommonService commonService;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
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

        if(StringUtils.contains(bankCard.getBankName(), "交通银行")){
            throw new ServiceException("2000", "该通道暂不支持交通银行");
        }
        if(StringUtils.contains(bankCard.getBankName(), "招商银行")){
            throw new ServiceException("2000", "该通道暂不支持招商银行");
        }

        Map<String, String> params = new HashMap<>();
        params.put("orderNo", tradeRecords.getOrderNo());
        String appSecert = MyWebAppConfig.environment.getProperty("ape.apikey");
        String sign = SignUtils.getSign(params, appSecert, log);

        String projectUrl = commonService.getProjectUrl();
        result.put("openUrl", projectUrl+"pay/index?orderNo="+tradeRecords.getOrderNo()+"&sign="+sign);
        return result;
    }

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception {
        return false;
    }

    @Override
    public Map<String, Object> sendSMSCode(WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        Long userId = tradeRecords.getUserId();//交易用户id
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);

        // 机构编号
        String agentnum = "151";

        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put("agentnum", agentnum);// 机构编号
        dataMap.put("channelNum", "100006");// 渠道编号
        dataMap.put("payCode", "100702");// 业务类型

        dataMap.put("amount", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue());// 交易金额，单位（分）
        dataMap.put("appOrderId", tradeRecords.getOrderNo());//交易订单号
        dataMap.put("settlementName", bankCard.getName());// 姓名
        dataMap.put("crpIdNo", bankCard.getIdCard());// 身份证号

        dataMap.put("inCard", userInfo.getSettleCardNo());// 收款卡号
        dataMap.put("inPhone", userInfo.getSettlePhone());// 收款卡预留手机号
        dataMap.put("inBankName", userInfo.getSettleBank());// 卡开户行

        dataMap.put("inBranchBankName", StringUtils.isEmpty(userInfo.getSettleBranchBank())?userInfo.getSettleBank():userInfo.getSettleBranchBank());// 支行名称

        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("branchBankName", "%"+userInfo.getSettleBank()+"%");
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
        if(list!=null && list.size()>0){
            dataMap.put("inBankCode", list.get(0).getBarnchBankCode());
        }else {
            dataMap.put("inBankCode", "301451000308");//联行号301451000332
        }

        dataMap.put("outCard", bankCard.getCardNo());// 交易卡卡号
        dataMap.put("outPhone", bankCard.getPhone());// 交易卡预留手机号
        dataMap.put("outCardCvv2", StringUtils.isEmpty(bankCard.getCvv())?"111":bankCard.getCvv());// 交易卡CVV2
        dataMap.put("outCardExpire", StringUtils.isEmpty(bankCard.getValidThru())?"1019":bankCard.getValidThru());// 有效期

        int realFee = tradeRecords.getRealTradeMoney().multiply(tradeRecords.getRate()).intValue()+
                tradeRecords.getPlatformPoundage().multiply(new BigDecimal(100)).intValue();
        dataMap.put("realFee", realFee);// 手续费

        dataMap.put("callBackUrl", channel.getNoticeUrl());// 异步通知地址(也就是回调地址)
        dataMap.put("returnUrl", "http://api.wtlyhqb.com/onewallet-webapi/pay/success");// 同步跳转地址

        log.debug("dataMap|"+dataMap+"|");
        String jsonMap = JSONUtil.mapToJson(dataMap);

        log.info("jsonMap|"+jsonMap+"|");

        jsonMap = Base64.encodeBase64String(jsonMap.getBytes());

        log.debug("dataMap base64|"+jsonMap+"|");

        // 通过私钥进行签名，得到签名
        byte[] mySign = LocalUtil.sign(Base64.decodeBase64(PRIVATEKEY.getBytes()), jsonMap);
        String reqSign = new String(mySign);

        log.debug("reqSign|"+reqSign+"|");

        // 通过公钥进行数据加密
        byte[] encodedData = RSAUtils2.encryptByPublicKey(jsonMap.getBytes(),PUBLICKKEY);

        String reqData = Base64.encodeBase64String(encodedData);

        log.debug("reqData|"+reqData+"|");

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("sign", reqSign);
        request.put("data", reqData);
        request.put("account", ACCOUNT);//商户号

        log.debug("request|"+request+"|");

        JSONObject realParam = new JSONObject();
        realParam.put("serviceCode", "N05e0");
        realParam.put("request", request);

        log.debug("request|"+realParam.toString()+"|");

        String rspJson = HttpClientUtils.postJson(channel.getChannelUrl(), PARAM, realParam.toString());

        log.debug("rspJson|"+rspJson+"|");

        MessageResponse msg = (MessageResponse) JSONObject.toBean(JSONObject.fromObject(rspJson), MessageResponse.class);

        Map<String, Object> rspMap = msg.getResponse();

        log.debug("工信系统响应的数据rspMap|"+rspMap+"|");

        String rspCode = rspMap.get("respCode").toString();
        String respInfo = rspMap.get("respInfo").toString();
        if (!rspCode.equals("0000")) {
            throw new ServiceException("2000", respInfo);
        } else {
            System.out.println("交易成功，开始验签。");
            String rspSign = rspMap.get("sign").toString();

            Map<String, Object> signMap = new HashMap<String, Object>();
            signMap.put("respCode", rspCode);
            signMap.put("respInfo", respInfo);
            String signData = com.group.wallet.channel.quick.youfuPay.utils.Base64.encodeToString(getParString(signMap));
            boolean vfy = LocalUtil.verifySignature(
                    com.group.wallet.channel.quick.youfuPay.utils.Base64.decode(PUBLICKKEY.getBytes()), rspSign,
                    signData.getBytes(CHARSET));
            if(!vfy){
                throw new ServiceException("2000", "签名错误");
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderNoSure", respInfo);
        return result;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, WalletChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        // 机构编号
        String agentnum = "101";

        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put("agentnum", agentnum);// 机构编号
        dataMap.put("channelNum", "100006");// 渠道编号
        dataMap.put("payCode", "100702");// 业务类型

        dataMap.put("appOrderId", tradeRecords.getOrderNo());
        dataMap.put("verifycode", params.get("smsCode"));// 验证码
        dataMap.put("orderNoSure", params.get("orderNoSure"));//确认订单号

        log.debug("dataMap|"+dataMap+"|");
        String jsonMap = JSONUtil.mapToJson(dataMap);

        log.debug("jsonMap|"+jsonMap+"|");

        jsonMap = Base64.encodeBase64String(jsonMap.getBytes());

        log.debug("dataMap base64|"+jsonMap+"|");

        // 通过私钥进行签名，得到签名
        byte[] mySign = LocalUtil.sign(Base64.decodeBase64(PRIVATEKEY.getBytes()), jsonMap);
        String reqSign = new String(mySign);

        log.debug("reqSign|"+reqSign+"|");

        // 通过公钥进行数据加密
        byte[] encodedData = RSAUtils2.encryptByPublicKey(jsonMap.getBytes(),PUBLICKKEY);

        String reqData = Base64.encodeBase64String(encodedData);

        log.debug("reqData|"+reqData+"|");

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("sign", reqSign);
        request.put("data", reqData);
        request.put("account", ACCOUNT);//商户号

        log.debug("request|"+request+"|");

        JSONObject realParam = new JSONObject();
        realParam.put("serviceCode", "N05d0");
        realParam.put("request", request);

        log.debug("request|"+realParam.toString()+"|");

        String rspJson = HttpClientUtils.postJson(channel.getChannelUrl(), PARAM,realParam.toString());

        log.debug("rspJson|"+rspJson+"|");

        MessageResponse msg = (MessageResponse) JSONObject.toBean(JSONObject.fromObject(rspJson), MessageResponse.class);

        Map<String, Object> rspMap = msg.getResponse();

        log.debug("工信系统响应的数："+rspMap);

        String rspCode = rspMap.get("respCode").toString();
        String respInfo = rspMap.get("respInfo").toString();
        if (!rspCode.equals("0000")) {
            throw new ServiceException("2000", respInfo);
        } else {
            System.out.println("交易成功，开始验签。");
            String rspSign = rspMap.get("sign").toString();

            Map<String, Object> signMap = new HashMap<String, Object>();
            signMap.put("respCode", rspCode);
            signMap.put("respInfo", respInfo);
            String signData = com.group.wallet.channel.quick.youfuPay.utils.Base64.encodeToString(getParString(signMap));
            boolean vfy = LocalUtil.verifySignature(
                    com.group.wallet.channel.quick.youfuPay.utils.Base64.decode(PUBLICKKEY.getBytes()), rspSign,
                    signData.getBytes(CHARSET));
            if(!vfy){
                throw new ServiceException("2000", "签名错误");
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

    public static String getParString(Map<String, Object> signMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("respCode=" + signMap.get("respCode") + ", ");
        sb.append("respInfo=" + signMap.get("respInfo"));
        sb.append("}");
        return sb.toString();
    }

}
