package com.group.wallet.channel.quick.yibaoPay.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.group.core.config.MyWebAppConfig;
import com.group.core.exception.ServiceException;
import com.group.utils.GetImage;
import com.group.utils.HttpClientUtils;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.channel.quick.yibaoPay.utils.AESUtil;
import com.group.wallet.channel.quick.yibaoPay.utils.Digest;
import com.group.wallet.mapper.WalletTradeRecordsMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.TradeState;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.model.zzlm.ZzlmChannelMer;

import tk.mybatis.mapper.entity.Example;

/**
 * 易宝支付
 */
@Service
public class YibaoQuickPayImpl implements QuickPay {

    private final Logger logger = LoggerFactory.getLogger(YibaoQuickPayImpl.class);

    @Value("${spring.profiles.active}")
    private String profiles;

    private String MainCustomerNumber = "10021204560";// 代理商编码
    private String MailStr = "1328338666@qq.com";// 商户邮箱

    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;


    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, ZzlmChannel channel, ZzlmChannelMer channelMer) throws Exception {
        //1，查询子商户
        Map<String, String> customerInfo = customerInforQuery(userInfo, channel);
        String customerNumber = customerInfo.get("ledgeCustomerNumber");//商户编号
        String bankAccountNumber = customerInfo.get("bankAccountNumber");//结算卡号
        String bankName = customerInfo.get("bankName");//开户行

        if(StringUtils.isEmpty(customerNumber)){
            //2，注册子商户
            customerNumber = register(userInfo, channel);
        }
        else {
            //3，修改子商户银行卡信息
            bankAccountNumber = StringUtils.substring(bankAccountNumber, bankAccountNumber.length()-4, bankAccountNumber.length());
            String settleCardNo = StringUtils.substring(userInfo.getSettleCardNo(), userInfo.getSettleCardNo().length()-4, userInfo.getSettleCardNo().length());

            if(!userInfo.getSettleBank().equals(bankName) || !bankAccountNumber.equals(settleCardNo)){
                customerInforUpdate(userInfo, channel, customerNumber);
            }
        }

        DecimalFormat df = new DecimalFormat("#.####");
        BigDecimal rate = channelMer.getDeductRate().divide(new BigDecimal(100));
        BigDecimal poundage = channelMer.getPoundage();

        //3，设置费率&提现费 1：交易费率 2：T1自助结算费率 3：T0自助结算费率 4：T0自助结算工作日额外费率 5：T0自助结算非工作日额外费率
        feeSetApi(userInfo, channel, customerNumber, "1", df.format(rate));//交易费率
        feeSetApi(userInfo, channel, customerNumber, "3", df.format(poundage));//提现费
        feeSetApi(userInfo, channel, customerNumber, "4", "0");
        feeSetApi(userInfo, channel, customerNumber, "5", "0");

        return customerNumber;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, ZzlmChannel channel, ZzlmChannelMer channelMer) throws Exception {
        return null;
    }

    /**
     * 查询子商户号
     * @param userInfo
     * @return
     */
    private Map<String, String> customerInforQuery(WalletUserInfo userInfo, ZzlmChannel channel) throws Exception{
        Map<String, String> map = new HashMap<>();
        String key = channel.getMd5Key(); // 商户秘钥
        String customertype = "CUSTOMER";
        String bindmobile = userInfo.getPhone();
        String idCard = userInfo.getIdCard();//身份证号
        idCard = StringUtils.substring(idCard, idCard.length()-4, idCard.length());

        StringBuffer signature = new StringBuffer();
        signature
                .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                .append(bindmobile == null ? "" : bindmobile)
                .append(customertype == null ? "" : customertype);

        System.out.println("source===" + signature.toString());
        String hmac = Digest.hmacSign(signature.toString(), key);
        System.out.println("hmac====" + hmac);

        Map<String, Object> mEntityBuilder = new HashMap<>();
        mEntityBuilder.put("hmac", hmac);
        mEntityBuilder.put("mainCustomerNumber", MainCustomerNumber);
        mEntityBuilder.put("mobilePhone", bindmobile);
        mEntityBuilder.put("customerType", customertype);

        String result = HttpClientUtils.sendPostCommon(channel.getChannelUrl()+"/customerInforQuery.action", mEntityBuilder, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if("0000".equals(code)){
            JSONArray retList = jsonObject.getJSONArray("retList");
            if(retList!=null && retList.size()>0){
                for (int i = 0; i < retList.size(); i++) {
                    JSONObject jsonObject1 = (JSONObject) retList.get(i);
                    String idCard1 = jsonObject1.getString("idCard");
                    idCard1 = StringUtils.substring(idCard1, idCard1.length()-4, idCard1.length());
                    String ledgeCustomerNumber = jsonObject1.getString("ledgeCustomerNumber");
                    String bankAccountNumber = jsonObject1.getString("bankAccountNumber");
                    String bankName = jsonObject1.getString("bankName");
                    if (idCard.equals(idCard1)){
                        map.put("ledgeCustomerNumber", ledgeCustomerNumber);
                        map.put("bankAccountNumber", bankAccountNumber);
                        map.put("bankName", bankName);
                    }
                }
            }
        }

        return map;
    }

    /**
     * 注册子商户
     * @param userInfo
     * @return
     */
    private String register(WalletUserInfo userInfo, ZzlmChannel channel) throws Exception{
        String key = channel.getMd5Key(); // 商户秘钥
        String requestid = UUID.randomUUID().toString().substring(0, 15);  //注册请求号，每次请求唯一
        String customertype = "PERSON";// 企业-ENTERPRISE,个体工商户-INDIVIDUAL,个人-PERSON
        String businesslicence = "";
        String bindmobile = userInfo.getPhone();
        String signedname = userInfo.getSettleName();//签约名
        String linkman = userInfo.getSettleName();//推荐人
        String idcard = userInfo.getIdCard();//身份证号
        String legalperson = userInfo.getRealName();// 法人
        String minsettleamount = "1";
        String riskreserveday = "0";
        String bankaccounttype = "PrivateCash";// 非必填，不参与签名
        String bankaccountnumber = userInfo.getSettleCardNo();//银行卡号
        String bankname = userInfo.getSettleBank();//银行卡开户行
        String accountname = userInfo.getSettleName();
        String manualSettle = "N";
        String auditStatus = "SUCCESS"; //FAILED


        StringBuffer signature = new StringBuffer();
        signature
                .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                .append(requestid == null ? "" : requestid)
                .append(customertype == null ? "" : customertype)
                .append(businesslicence == null ? "" : businesslicence)
                .append(bindmobile == null ? "" : bindmobile)
                .append(signedname == null ? "" : signedname)
                .append(linkman == null ? "" : linkman)
                .append(idcard == null ? "" : idcard)
                .append(legalperson == null ? "" : legalperson)
                .append(minsettleamount == null ? "" : minsettleamount)
                .append(riskreserveday == null ? "" : riskreserveday)
                .append(bankaccountnumber == null ? "" : bankaccountnumber)
                .append(bankname == null ? "" : bankname)
                .append(accountname == null ? "" : accountname)
                .append(manualSettle == null ? "" : manualSettle);

        System.out.println("source===" + signature.toString());
        String hmac = Digest.hmacSign(signature.toString(), key);
        System.out.println("hmac====" + hmac);

        Map<String, Object> mEntityBuilder = new HashMap<>();
        mEntityBuilder.put("hmac", hmac);
        mEntityBuilder.put("mainCustomerNumber", MainCustomerNumber);
        mEntityBuilder.put("mailstr", MailStr);
        mEntityBuilder.put("requestId", requestid);
        mEntityBuilder.put("customerType", customertype);
        mEntityBuilder.put("businessLicence", businesslicence);
        mEntityBuilder.put("bindMobile", bindmobile);
        mEntityBuilder.put("signedName", signedname);
        mEntityBuilder.put("linkMan", linkman);
        mEntityBuilder.put("idCard", idcard);
        mEntityBuilder.put("legalPerson", legalperson);
        mEntityBuilder.put("minSettleAmount", minsettleamount);
        mEntityBuilder.put("riskReserveDay", riskreserveday);
        mEntityBuilder.put("bankAccountNumber", bankaccountnumber);
        mEntityBuilder.put("bankName", bankname);
        mEntityBuilder.put("accountName", accountname);

        String cdn = MyWebAppConfig.environment.getProperty("qiniu.cdn");
        String bankCardPhotoUrl = userInfo.getSettleCardZScan();
        String businessLicensePhotoUrl = userInfo.getIdCardZImage();
        String idCardPhotoUrl = userInfo.getIdCardZImage();
        String idCardBackPhotoUrl = userInfo.getIdCardFImage();
        String personPhotoUrl = userInfo.getIdCardZImage();

        String tempurl = "";
        if("onl".equals(profiles)){
            tempurl = "/alidata/server/apache-tomcat-8.5.20/webapps/onewallet-webapi/temp/";
        }else {
            tempurl = "/Users/dailing/Downloads/temp/";
        }

        GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + bankCardPhotoUrl), tempurl + bankCardPhotoUrl);
        GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + businessLicensePhotoUrl), tempurl + businessLicensePhotoUrl);
        GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + idCardPhotoUrl), tempurl + idCardPhotoUrl);
        GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + idCardBackPhotoUrl), tempurl + idCardBackPhotoUrl);
        GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + personPhotoUrl), tempurl + personPhotoUrl);

        mEntityBuilder.put("bankCardPhoto", new File(tempurl + bankCardPhotoUrl));
        mEntityBuilder.put("businessLicensePhoto", new File(tempurl + businessLicensePhotoUrl));
        mEntityBuilder.put("idCardPhoto", new File(tempurl + idCardPhotoUrl));
        mEntityBuilder.put("idCardBackPhoto", new File(tempurl + idCardBackPhotoUrl));
        mEntityBuilder.put("personPhoto", new File(tempurl + personPhotoUrl));

        mEntityBuilder.put("manualSettle", manualSettle);
        mEntityBuilder.put("auditStatus", auditStatus);

        String result = HttpClientUtils.sendPostCommon(channel.getChannelUrl()+"/register.action", mEntityBuilder, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String message = jsonObject.getString("message");
            throw new ServiceException("2000", message);
        }

        String customerNumber = jsonObject.getString("customerNumber");
        return customerNumber;
    }

    /**
     * 子商户费率设置
     * @param userInfo
     * @param channel
     * @param customerNumber
     * @param productType
     * @param rate
     * @throws Exception
     */
    private void feeSetApi(WalletUserInfo userInfo, ZzlmChannel channel, String customerNumber, String productType, String rate) throws Exception{
        String key = channel.getMd5Key(); // 商户秘钥

        StringBuffer signature = new StringBuffer();
        signature
                .append(customerNumber == null ? "" : customerNumber)
                .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                .append(productType == null ? "" : productType)
                .append(rate == null ? "" : rate);

        System.out.println("source===" + signature.toString());
        String hmac = Digest.hmacSign(signature.toString(), key);
        System.out.println("hmac====" + hmac);

        Map<String, Object> mEntityBuilder = new HashMap<>();
        mEntityBuilder.put("hmac", hmac);
        mEntityBuilder.put("mainCustomerNumber", MainCustomerNumber);
        mEntityBuilder.put("customerNumber", customerNumber);
        mEntityBuilder.put("productType", productType);
        mEntityBuilder.put("rate", rate);

        String result = HttpClientUtils.sendPostCommon(channel.getChannelUrl()+"/feeSetApi.action", mEntityBuilder, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String message = jsonObject.getString("message");
            throw new ServiceException("2000", message);
        }
    }

    /**
     * 修改子商户银行卡信息
     * @param userInfo
     * @param channel
     * @throws Exception
     */
    private void customerInforUpdate(WalletUserInfo userInfo, ZzlmChannel channel, String customerNumber) throws Exception{
        String key = channel.getMd5Key(); // 商户秘钥
        String bankCardNumber = userInfo.getSettleCardNo();
        String bankName = userInfo.getSettleBank();

        StringBuffer signature = new StringBuffer();
        signature
                .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                .append(customerNumber == null ? "" : customerNumber)
                .append(bankCardNumber == null ? "" : bankCardNumber)
                .append(bankName == null ? "" : bankName);

        System.out.println("source===" + signature.toString());
        String hmac = Digest.hmacSign(signature.toString(), key);
        System.out.println("hmac====" + hmac);

        Map<String, Object> mEntityBuilder = new HashMap<>();
        mEntityBuilder.put("hmac", hmac);
        mEntityBuilder.put("mainCustomerNumber", MainCustomerNumber);
        mEntityBuilder.put("customerNumber", customerNumber);
        mEntityBuilder.put("modifyType", "2");//2：银行卡信息 3：结算信息 4：分润信息 6：子商户基本信息
        mEntityBuilder.put("bankCardNumber", bankCardNumber);
        mEntityBuilder.put("bankName", bankName);

        String result = HttpClientUtils.sendPostCommon(channel.getChannelUrl()+"/customerInforUpdate.action", mEntityBuilder, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String message = jsonObject.getString("message");
            throw new ServiceException("2000", message);
        }
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, ZzlmChannel channel, ZzlmChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        DecimalFormat df = new DecimalFormat("#.##");
        Map<String, Object> map = new HashMap<>();

        String key = channel.getMd5Key(); // 商户秘钥
        String customerNumber = channelMer.getChannelMerNo();//子商户编号
        String requestid = tradeRecords.getOrderNo();  //注册请求号，每次请求唯一
        String mobileNumber = userInfo.getSettlePhone();
        String source = "B";//支付方式
        String amount = df.format(tradeRecords.getRealTradeMoney());//订单金额
        String mcc = "5311";
        String callBackUrl = channel.getNoticeUrl();
        String webCallBackUrl = channel.getReturnUrl();
        String payerBankAccountNo = bankCard.getCardNo();

        StringBuffer signature = new StringBuffer();
        signature
                .append(source == null ? "" : source)
                .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                .append(customerNumber == null ? "" : customerNumber)
                .append(amount == null ? "" : amount)
                .append(mcc == null ? "" : mcc)
                .append(requestid == null ? "" : requestid)
                .append(mobileNumber == null ? "" : mobileNumber)
                .append(callBackUrl == null ? "" : callBackUrl)
                .append(webCallBackUrl == null ? "" : webCallBackUrl)
                .append(payerBankAccountNo == null ? "" : payerBankAccountNo);

        System.out.println("source===" + signature.toString());
        String hmac = Digest.hmacSign(signature.toString(), key);
        System.out.println("hmac====" + hmac);

        Map<String, Object> mEntityBuilder = new HashMap<>();
        mEntityBuilder.put("hmac", hmac);
        mEntityBuilder.put("mainCustomerNumber", MainCustomerNumber);
        mEntityBuilder.put("customerNumber", customerNumber);
        mEntityBuilder.put("requestId", requestid);
        mEntityBuilder.put("mobileNumber", mobileNumber);
        mEntityBuilder.put("source", source);
        mEntityBuilder.put("amount", amount);
        mEntityBuilder.put("mcc", mcc);
        mEntityBuilder.put("callBackUrl", callBackUrl);
        mEntityBuilder.put("webCallBackUrl", webCallBackUrl);
        mEntityBuilder.put("payerBankAccountNo", payerBankAccountNo);

        String result = HttpClientUtils.sendPostCommon(channel.getChannelUrl()+"/receiveApi.action", mEntityBuilder, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String message = jsonObject.getString("message");
            throw new ServiceException("2000", message);
        }

        String url = jsonObject.getString("url");
        String key1 = StringUtils.substring(key, 0 , 16);
        url = AESUtil.decrypt(url, key1);
        map.put("openUrl", url);

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
    public Map<String, Object> sendSMSCode(ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPayConfirm(WalletUserInfo userInfo, ZzlmChannel channel, WalletTradeRecords tradeRecords, WalletBankCard bankCard, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public void settlement(ZzlmChannel channel, Map<String, Object> params) throws Exception {
        logger.info("===易宝结算===");
        String key = channel.getMd5Key(); // 商户秘钥

        String amount = (String) params.get("amount");
        String customerNumber = (String) params.get("customerNumber");
        String externalNo = UUID.randomUUID().toString().substring(0, 15);;
        String transferWay = (String) params.get("transferWay");
        String callBackUrl = "";

        params.put("mainCustomerNumber", MainCustomerNumber);
        params.put("externalNo", externalNo);

        StringBuffer signature = new StringBuffer();
        signature
                .append(amount == null ? "" : amount)
                .append(customerNumber == null ? "" : customerNumber)
                .append(externalNo == null ? "" : externalNo)
                .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                .append(transferWay == null ? "" : transferWay)
                .append(callBackUrl == null ? "" : callBackUrl);

        System.out.println("source===" + signature.toString());
        String hmac = Digest.hmacSign(signature.toString(), key);
        System.out.println("hmac====" + hmac);

        params.put("hmac", hmac);

        String result = HttpClientUtils.sendPostCommon(channel.getChannelUrl()+"/withDrawApi.action", params, null, "utf-8");
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String message = jsonObject.getString("message");
            throw new ServiceException("2000", message);
        }
    }

    @Override
    public boolean checkSign(ZzlmChannel channel, Map<String, Object> params) throws Exception {
        String source = (String) params.get("source");
        String hmac = (String) params.get("hmac");

        System.out.println("source===" + source);
        String hmac1 = Digest.hmacSign(source, channel.getMd5Key());
        System.out.println("hmac1====" + hmac);

        if(hmac.equals(hmac1)){
            return true;
        }
        return false;
    }

    @Override
    public String payNotice(Map<String, String> params) throws Exception {
        return null;
    }


    public void updateImage() throws Exception{
        List<Map<String, Object>> list = walletTradeRecordsMapper.selectTemporaryData();
        for (int i = 0; i < list.size(); i++) {

            try {
                Map<String, Object> map = list.get(i);
                String customerNumber = (String) map.get("customer_number");
                Long userId = (Long) map.get("user_id");
                if (userId==null) continue;
                WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
                if(userInfo==null) continue;

                StringBuffer signature = new StringBuffer();
                signature
                        .append(MainCustomerNumber == null ? "" : MainCustomerNumber)
                        .append(customerNumber == null ? "" : customerNumber);

                System.out.println("source===" + signature.toString());
                String hmac = Digest.hmacSign(signature.toString(), "n1634g5ZK2SXI79M3m90PyK7KCp3M9AB83859Q3Y38Dv7lLx950z3s6B46q8");
                System.out.println("hmac====" + hmac);

                Map<String, Object> mEntityBuilder = new HashMap<>();
                mEntityBuilder.put("hmac", hmac);
                mEntityBuilder.put("mainCustomerNumber", MainCustomerNumber);
                mEntityBuilder.put("customerNumber", customerNumber);

                String cdn = MyWebAppConfig.environment.getProperty("qiniu.cdn");
                String bankCardPhotoUrl = userInfo.getSettleCardZScan();
                String businessLicensePhotoUrl = userInfo.getIdCardZImage();
                String idCardPhotoUrl = userInfo.getIdCardZImage();
                String idCardBackPhotoUrl = userInfo.getIdCardFImage();
                String personPhotoUrl = userInfo.getIdCardZImage();

                String tempurl = "";
                if("onl".equals(profiles)){
                    tempurl = "/alidata/server/apache-tomcat-8.5.20/webapps/onewallet-webapi/temp/";
                }else {
                    tempurl = "/Users/dailing/Downloads/temp/";
                }

                GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + bankCardPhotoUrl), tempurl + bankCardPhotoUrl);
                GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + businessLicensePhotoUrl), tempurl + businessLicensePhotoUrl);
                GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + idCardPhotoUrl), tempurl + idCardPhotoUrl);
                GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + idCardBackPhotoUrl), tempurl + idCardBackPhotoUrl);
                GetImage.writeImageToDisk(GetImage.getImageFromNetByUrl(cdn + personPhotoUrl), tempurl + personPhotoUrl);

                mEntityBuilder.put("idCardPhoto", new File(tempurl + idCardPhotoUrl));
                mEntityBuilder.put("idCardBackPhoto", new File(tempurl + idCardBackPhotoUrl));
                mEntityBuilder.put("bankCardPhoto", new File(tempurl + bankCardPhotoUrl));
                //mEntityBuilder.put("businessLicensePhoto", new File(tempurl + businessLicensePhotoUrl));
                //mEntityBuilder.put("personPhoto", new File(tempurl + personPhotoUrl));

                String result = HttpClientUtils.sendPostCommon("https://skb.yeepay.com/skb-app/customerPictureUpdate.action", mEntityBuilder, null, "utf-8");
                JSONObject jsonObject = JSON.parseObject(result);
                String code = jsonObject.getString("code");
                if("0000".equals(code)){
                    walletTradeRecordsMapper.deleteTemporaryData(customerNumber);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
