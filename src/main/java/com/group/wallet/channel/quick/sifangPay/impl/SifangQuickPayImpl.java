package com.group.wallet.channel.quick.sifangPay.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.core.config.MyWebAppConfig;
import com.group.core.exception.ServiceException;
import com.group.utils.HttpClientUtils;
import com.group.utils.RSA;
import com.group.utils.SignUtils;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.mapper.WalletBranchBankMapper;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletBranchBank;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.model.zzlm.ZzlmChannelMer;
import com.group.wallet.service.CommonService;

import tk.mybatis.mapper.entity.Example;

/**
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/7/25 下午2:37
 * 描述：四方快捷支付
 */
@Service
public class SifangQuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(SifangQuickPayImpl.class);

    //代理商机构号
    private final String agentId = "20095810";
    //一级商户号&费率
    private final Map<String, String> merchIds = new HashMap<String, String>(){{
        put("0.46", "830581055330008");
        put("0.47", "830581055330009");
        put("0.48", "830581055330010");
        put("0.49", "830581055330011");
        put("0.50", "830581055330012");
        put("0.51", "830581055330013");
        put("0.52", "830581055330014");
        put("0.53", "830581055330015");
        put("0.54", "830581055330016");
        put("0.55", "830581055330017");
        put("0.56", "830581055330018");
        put("0.57", "830581055330019");
        put("0.58", "830581055330020");
        put("0.59", "830581055330021");
    }};

    private final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4mLlZNZA4nafMrtcc+iC\n" +
            "+Hzxlq8sdlDlBuCe4SYfnSqXxRV/idPiOrKCOuroEs+rLXHG1XEX5a+6KR5K2L9+\n" +
            "RP8qdvjsab7cg2Zr7yOrSOe11l8iMNl859A4VR3RcbFuEwsAtvyAfEKk/IAp5dNQ\n" +
            "h83WuhMoZjK2terCz9pf+Vea3wTKSiJJutOsM+Ufr8FYUOOwiN+//u7k1oCsuxCn\n" +
            "rhV07lbTGLiZv7cI8kZepd+9Yw36PTm1OGx/l2gupAIhn0pgmw9GcRo+YM94y7hL\n" +
            "djIK6Wjs+ibBsh+LO9GO33ViqFPtI1c/Rrg+2sq+cF3pEOXT9HowbiFktq/FaIPr\n" +
            "dwIDAQAB";

    private final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC5VYsh+c3yCI8v\n" +
            "wZqqCcxwGrXGt9hMP6cePHhw7xjO4welAAhW/AjEn9tzIcXfhYUI+DxBADHdJWPR\n" +
            "N0fc3xGV3j/EEy3cnLX2bRzJMSMikBHvYKA+WUJ/6Y97UF4cIDxVx8NeZvVnh41o\n" +
            "scs3x82Uvo52ymJUqGYC0LoOvmpkXxa/aLEL9pIrBLz+U310JtlhF5IhGEh5mfWK\n" +
            "AwJXQHcqoZ3Pgdi0WKySTzxKuFVh/4mmYwSYTVag2V3nF0qdQhH5UBjlrf2qJsK8\n" +
            "HpRSN6DvznVljtyS/872ddjxby74IpSaHh1hws3OAK7GtwjajnIOaEvLCIwl9h78\n" +
            "psrtXELFAgMBAAECggEAOKHVJAqrTbwj1Nn6It9foxnmVdSzuYjxziF4RDorgHyL\n" +
            "R3YdFkdpfnvCmQnG5yhroHQKJWqj0gms2K9lvC5Ft1/0aDvcFb+lMqng0/G55MPV\n" +
            "VypVjPfFhESgXXQPbdTD2wpxvpUDhCE9qrI5gNADF+Kqsc3iAjB7C30wfXTeGdEX\n" +
            "Rzrxds+JW02KBumiTmcjaUi/xPzZxXzp2GBSg9Q20wamZL9yy00xKQZHvVeK3KPR\n" +
            "7bVUjDCjdJjU7v2xv0nQPnx4sqkX8tjApNbsgopWF+WWvR2BBT5yX4txLg8/OdXT\n" +
            "iA6gWlSF0j8IXr/m3FEDgLRYFhVQ0WnRK79kjaJugQKBgQDn3PvEjIFlRPo4u4LH\n" +
            "F8copFK5NdFjHVW2zaj3Qcdu5u2JtfuAukjQWoNAYtQ2zrUBhunWK3RNiE+ZGBdk\n" +
            "g4SBVl0BR1XyWFhy19X6MD8qiz0lwqfdUqpCylCRUndseFU+HpCIsQvRp67G+zlx\n" +
            "hhUiHmISwv2+cG3hjsQTW8PAUQKBgQDMoJaWuXtCFSy7yUZ1EBb0j4OCwy4pxdKv\n" +
            "n0EuVqihFjOJR6Y0qNYmb30GKbWIwHV6BCBb8K4wrtdryZJI4MH+d650t9y6WbJz\n" +
            "aMAikFTaR3oJhfUoyaj6DvnvUdpd+4GXcwMA9Avj5w6OAdeQyDdaMYc/hRjNeD2T\n" +
            "2bgRXyPSNQKBgQC3QKPIC1i1hlfRgPnyiuuARqeO9GciU+wLx+3URivujhuNbMRz\n" +
            "UEf+TJtRxwp8qtDKfSvRdpS8NpDaqfzLF1shsfVd7liSapq1YbsfuwQhhtz0PC3a\n" +
            "MdyZXzdgM/JpS43rw9JuBqgVstbjM2mKPPP0/Fh9BZnw6wEddW2o8yz6kQKBgQCR\n" +
            "++NTJoKT841Vb1PPwinOUfCNwndVn+ceGeyjhXmKas9bmf0uRmF5TEKSugDjzgJB\n" +
            "iV4YmY1799CB+m1dhtN/mpygcmeLnuBby5h/UB7pFuMulh3/+laOB08x+GBkekDK\n" +
            "7mdvL6yn67iLgS7sAJrUws7tdyPpumWb7FCQPCLPHQKBgQC6+QWRgS9G/TzDWqR9\n" +
            "sKh7AXzTqymfSxfMLJpNub0jk9UMoqWs+tib9DvetI/woonNARYHg/2der6kn3TH\n" +
            "m/jIXZ2i1WsOCR4YvhcdJK+MnVylh8vYtCidZFu8L0oCPvsMu6AnsBcJ5QNp9Lh5\n" +
            "Q3D5FwqP5cewW8cGOqrom3R+Ow==";


    @Autowired
    private CommonService commonService;

    @Autowired
    private WalletBranchBankMapper walletBranchBankMapper;


    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, ZzlmChannel channel, ZzlmChannelMer channelMer) throws Exception {
        String merchId = merchIds.get(new DecimalFormat("0.00").format(channelMer.getDeductRate()));//一级商户号

        Map<String, String> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("merTrace", System.currentTimeMillis() + "");
        map.put("frontMid", merchId);
        map.put("merName", userInfo.getRealName());
        map.put("realName", userInfo.getRealName());
        map.put("connectMobile", userInfo.getPhone());
        map.put("merState", userInfo.getSettleProvince());
        map.put("merCity", userInfo.getSettleCity());
        map.put("merAddress", "1111");
        map.put("certType", "01");
        map.put("certId", userInfo.getIdCard());
        map.put("mobile", userInfo.getSettlePhone());
        map.put("accountId", userInfo.getSettleCardNo());
        map.put("accountName", userInfo.getSettleName());
        map.put("bankName", userInfo.getSettleBank());
        map.put("openBName", StringUtils.isEmpty(userInfo.getSettleBranchBank())?userInfo.getSettleBank():userInfo.getSettleBranchBank());
        map.put("openBCode", getBank(userInfo));//开户行连行号
        map.put("openBState", userInfo.getSettleProvince());
        map.put("openBCity", userInfo.getSettleCity());
        map.put("accType", "01");
        map.put("merType", "00");
        map.put("settleType", "0");
        map.put("operFlag", "A");
        map.put("rateType", "0");
        map.put("rateFee", new DecimalFormat("#.##").format(channelMer.getDeductRate()));
        map.put("drawFee", channelMer.getPoundage().multiply(new BigDecimal(100)).intValue()+"");
        map.put("brandFee", new DecimalFormat("#.##").format(channelMer.getDeductRate()));
        map.put("conFmax", "99999900");
        map.put("conFmin", "0");
        map.put("signature", getSign(map));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pg/mchtApply", map, null, "utf-8");
        Map<String, Object> resultMap = resolvResult(result);
        String respCode = (String) resultMap.get("respCode");
        if(!"00".equals(respCode)){
            String respMsg = (String) resultMap.get("respMsg");
            throw new ServiceException("2000", respMsg);
        }

        String userId = (String) resultMap.get("userId");//二级商户号
        if(StringUtils.isEmpty(userId)){
            throw new ServiceException("2000", "获取不到子商户号");
        }
        return merchId+"&"+userId;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, ZzlmChannel channel, ZzlmChannelMer channelMer) throws Exception {
        String[] arr = StringUtils.split(channelMer.getChannelMerNo(), "&");

        Map<String, String> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("merTrace", System.currentTimeMillis() + "");
        map.put("frontMid", arr[0]);
        map.put("userId", arr[1]);
        map.put("merName", userInfo.getRealName());
        map.put("realName", userInfo.getRealName());
        map.put("certType", "01");
        map.put("certId", userInfo.getIdCard());
        map.put("mobile", userInfo.getSettlePhone());
        map.put("accountId", userInfo.getSettleCardNo());
        map.put("accountName", userInfo.getSettleName());
        map.put("bankName", userInfo.getSettleBank());
        map.put("openBName", StringUtils.isEmpty(userInfo.getSettleBranchBank())?userInfo.getSettleBank():userInfo.getSettleBranchBank());
        map.put("openBCode", getBank(userInfo));
        map.put("openBState", userInfo.getSettleProvince());
        map.put("openBCity", userInfo.getSettleCity());
        map.put("accType", "01");
        map.put("operFlag", "M02");
        map.put("signature", getSign(map));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pg/mchtUpdate", map, null, "utf-8");
        Map<String, Object> resultMap = resolvResult(result);
        String respCode = (String) resultMap.get("respCode");
        if(!"00".equals(respCode)){
            String respMsg = (String) resultMap.get("respMsg");
            throw new ServiceException("2000", respMsg);
        }

        String userId = (String) resultMap.get("userId");
        return userId;
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, ZzlmChannel channel, ZzlmChannelMer channelMer, WalletBankCard bankCard) throws Exception {
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
    public boolean checkSign(ZzlmChannel channel, Map<String, Object> params) throws Exception {
        String signature = (String) params.get("signature");
        params.remove(signature);
        try {
            SortedMap<String, Object> smap = new TreeMap<String, Object>(params);

            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry<String, Object> m : smap.entrySet()) {
                Object value = m.getValue();
                if(value!=null && !"".equals(value.toString())){
                    stringBuffer.append(m.getKey()).append("=").append(value).append("&");
                }
            }

            String argPreSign = stringBuffer.toString();
            argPreSign = StringUtils.substring(argPreSign, 0, argPreSign.length()-1);
            logger.info("签名字符串：" + argPreSign);

            return RSA.verify(argPreSign, signature, PUBLIC_KEY, "GBK");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> sendSMSCode(ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        String[] arr = StringUtils.split(tradeRecords.getRouteMerchantNo(), "&");

        Map<String, String> map = new HashMap<>();
        map.put("tranCode", "T001");
        map.put("agentId", agentId);
        map.put("merchId", arr[0]);
        map.put("userId", arr[1]);
        map.put("tranDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("tranId", tradeRecords.getOrderNo());
        map.put("tranTime", new SimpleDateFormat("HHmmss").format(new Date()));
        map.put("payCardNo", bankCard.getCardNo());
        map.put("expireDate", bankCard.getValidThru());
        map.put("cvn", bankCard.getCvv());
        map.put("name", bankCard.getName());
        map.put("idCard", bankCard.getIdCard());
        map.put("payMobile", bankCard.getPhone());
        map.put("tranAmt", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue()+"");
        map.put("signature", getSign(map));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"wtz/paySMS", map, null, "utf-8");
        Map<String, Object> resultMap = resolvResult(result);
        String respCode = (String) resultMap.get("respCode");
        if(!"00".equals(respCode)){
            String respMsg = (String) resultMap.get("respMsg");
            throw new ServiceException("2000", respMsg);
        }

        String serial = (String) resultMap.get("serial");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("serial", serial);
        return map1;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        String[] arr = StringUtils.split(tradeRecords.getRouteMerchantNo(), "&");

        Map<String, String> map = new HashMap<>();
        map.put("tranCode", "T002");
        map.put("agentId", agentId);
        map.put("merchId", arr[0]);
        map.put("userId", arr[1]);
        map.put("tranDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("tranId", tradeRecords.getOrderNo());
        map.put("tranTime", new SimpleDateFormat("HHmmss").format(new Date()));
        map.put("tranAmt", tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).intValue()+"");
        map.put("payCardNo", bankCard.getCardNo());
        map.put("expireDate", bankCard.getValidThru());
        map.put("cvn", bankCard.getCvv());
        map.put("payMobile", bankCard.getPhone());
        map.put("name", bankCard.getName());
        map.put("idCard", bankCard.getIdCard());
        map.put("serial", (String) params.get("serial"));
        map.put("smsCode", (String) params.get("smsCode"));
        map.put("trustBackUrl", commonService.getProjectUrl()+"notice/sifangpay");
        map.put("signature", getSign(map));

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"wtz/pay", map, null, "utf-8");
        Map<String, Object> resultMap = resolvResult(result);
        String respCode = (String) resultMap.get("respCode");
        if(!"00".equals(respCode)){
            String respMsg = (String) resultMap.get("respMsg");
            throw new ServiceException("2000", respMsg);
        }

        return null;
    }

    @Override
    public void settlement(ZzlmChannel channel, Map<String, Object> params) throws Exception {

    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }

    private String getSign(Map<String, String> paramMap) throws Exception{
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if(value!=null && !"".equals(value.toString())){
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }

        String argPreSign = stringBuffer.toString();
        argPreSign = StringUtils.substring(argPreSign, 0, argPreSign.length()-1);
        logger.info("签名字符串：" + argPreSign);
        String signStr = RSA.sign(argPreSign, PRIVATE_KEY, "GBK");
        System.out.println(RSA.verify(argPreSign, signStr, PUBLIC_KEY, "GBK"));
        logger.info("签名结果：" + signStr);
        return signStr;
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

    private Map<String, Object> resolvResult(String result){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isEmpty(result))
            return map;

        String[] results = StringUtils.split(result, "&");
        if(results!=null && results.length>0){
            for (int i = 0; i < results.length; i++) {
                String resultObj = results[i];
                String[] resultObjs = StringUtils.split(resultObj, "=");
                if(resultObjs!=null && resultObjs.length==2){
                    map.put(resultObjs[0], resultObjs[1]);
                }
            }
        }

        return map;
    }

}
