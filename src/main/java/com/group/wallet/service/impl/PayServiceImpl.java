package com.group.wallet.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.group.core.exception.ServiceException;
import com.group.utils.OrderUtils;
import com.group.wallet.channel.ChannelPay;
import com.group.wallet.channel.FactoryBuilder;
import com.group.wallet.channel.payment.PaymentFactory;
import com.group.wallet.channel.payment.PaymentPay;
import com.group.wallet.channel.quick.QuickPay;
import com.group.wallet.mapper.WalletBankCardMapper;
import com.group.wallet.mapper.WalletChannelMapper;
import com.group.wallet.mapper.WalletChannelMerMapper;
import com.group.wallet.mapper.WalletDeductRateMapper;
import com.group.wallet.mapper.WalletSubCardMapper;
import com.group.wallet.mapper.WalletTradeRecordsMapper;
import com.group.wallet.mapper.WalletUserInfoMapper;
import com.group.wallet.model.WalletBankCard;
import com.group.wallet.model.WalletTradeRecords;
import com.group.wallet.model.WalletUserInfo;
import com.group.wallet.model.enums.ChannelMerType;
import com.group.wallet.model.enums.ChannelType;
import com.group.wallet.model.enums.DeductType;
import com.group.wallet.model.enums.SettleType;
import com.group.wallet.model.enums.TradeState;
import com.group.wallet.model.enums.UserState;
import com.group.wallet.model.zzlm.ZzlmChannel;
import com.group.wallet.model.zzlm.ZzlmChannelMer;
import com.group.wallet.model.zzlm.ZzlmDeductRate;
import com.group.wallet.service.PayService;
import com.group.wallet.service.SettleService;

@Service
public class PayServiceImpl implements PayService {

    @Value("${project.name}")
    private String projectName;

    @Autowired
    private SettleService settleService;

    @Autowired
    private WalletChannelMapper walletChannelMapper;
    @Autowired
    private WalletTradeRecordsMapper walletTradeRecordsMapper;
    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletChannelMerMapper walletChannelMerMapper;
    @Autowired
    private WalletDeductRateMapper walletDeductRateMapper;
    @Autowired
    private WalletBankCardMapper walletBankCardMapper;
    @Autowired
    private WalletSubCardMapper walletSubCardMapper;

    @Override
    public List<Map<String, Object>> getChannels(Long userId, String channelType) {
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String userType = userInfo.getUserType();

        List<String> channelTypes = new ArrayList<>();
        if("Q".equals(channelType)){
            channelTypes.add(ChannelType.快捷渠道.getValue());
            channelTypes.add(ChannelType.H5快捷渠道.getValue());
        }else if ("S".equals(channelType)){
            channelTypes.add(ChannelType.刷卡渠道.getValue());
        }

        List<Map<String, Object>> list = walletDeductRateMapper.selectUserChannelRate(userType, channelTypes);
        return list;
    }

    @Override
    public void payCheck(Long channelId, String settleType, BigDecimal amount, Long userId) {
        ZzlmDeductRate deductRate = new ZzlmDeductRate();
        deductRate.setDeductType(DeductType.通道.getValue());
        deductRate.setChannelId(channelId);
        deductRate.setSettleType(settleType);
        List<ZzlmDeductRate> deductRates = walletDeductRateMapper.select(deductRate);
        if(deductRates==null || deductRates.size()==0){
            throw new ServiceException("2000", "获取不到通道汇率");
        }

        deductRate = deductRates.get(0);
        BigDecimal limitMin = deductRate.getLimitMin()==null?BigDecimal.ZERO:deductRate.getLimitMin();
        BigDecimal limitMax = deductRate.getLimitMax()==null?BigDecimal.ZERO:deductRate.getLimitMax();
        BigDecimal dayMax = deductRate.getDayMax()==null?BigDecimal.ZERO:deductRate.getDayMax();

        if(limitMin!=null && limitMin.compareTo(amount)>0){
            throw new ServiceException("2000", "付款金额必须大于"+limitMin+"元");
        }
        if(limitMax!=null && limitMax.compareTo(amount)<0){
            throw new ServiceException("2000", "付款金额必须小于"+limitMax+"元");
        }

        BigDecimal todayTotalAmount = walletTradeRecordsMapper.selectTodayTotalAmount(userId, channelId, TradeState.支付成功.getValue());
        if(dayMax!=null && todayTotalAmount!=null){
            if(dayMax.compareTo(todayTotalAmount)<0){
                throw new ServiceException("2000", "已经超出该通道当日最大限额");
            }
        }
    }

    @Override
    @Transactional
    public Map<String, Object> registSubMerchant(Long channelId, Long userId, Long bankCardId, String settleType) throws Exception{
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        ZzlmChannel channel = walletChannelMapper.selectByPrimaryKey(channelId);
        WalletBankCard bankCard = walletBankCardMapper.selectByPrimaryKey(bankCardId);
        ChannelPay channelPay = FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);

        String cardNo = userInfo.getSettleCardNo();//结算卡号
        String subMerchantNo = "";
        Map<String, Object> map = new HashMap<>();

        //获取商户在当前通道的t0，t1汇率
        Map<String, BigDecimal> map1 = settleService.getUserTypeRate(channel.getId(), userInfo.getUserType(), settleType);
        BigDecimal rate = map1.get("rate")==null?BigDecimal.ZERO:map1.get("rate");
        BigDecimal poundage = map1.get("poundage")==null?BigDecimal.ZERO:map1.get("poundage");

        if(rate!=null && rate.compareTo(BigDecimal.ZERO)>0){

            ZzlmChannelMer channelMer2 = new ZzlmChannelMer();
            channelMer2.setChannelId(channelId);//1，通道id
            channelMer2.setUserId(userId); //2，用户id
            //channelMer2.setCardNo(cardNo);//3，结算银行卡
            channelMer2.setType(ChannelMerType.一对一商户.getValue());//4，子商户类型
            channelMer2.setSettleType(settleType);//5，结算类型
            List<ZzlmChannelMer> channelMers = walletChannelMerMapper.select(channelMer2);

            if(channelMers==null || channelMers.size()==0){
                ZzlmChannelMer channelMer1 = new ZzlmChannelMer();
                channelMer1.setChannelId(channelId);
                channelMer1.setUserId(userId);
                channelMer1.setCardNo(cardNo);
                channelMer1.setType(ChannelMerType.一对一商户.getValue());
                channelMer1.setSettleType(settleType);
                channelMer1.setDeductRate(rate);
                channelMer1.setPoundage(poundage);
                channelMer1.setCreateTime(new Date());

                subMerchantNo = channelPay.regisSubMerchant(userInfo, bankCard, channel, channelMer1);
                channelMer1.setChannelMerNo(subMerchantNo);
                walletChannelMerMapper.insertSelective(channelMer1);
            }
            else {
                List<String> channelNos = Arrays.asList("SZZF","ZYZF","ZYZF-LD","ZYZF-HF","ZYZF-XE","YIBAO","YOUFU","YOUFUTM","YOUFUTM2","YINSB","FUHT","LINGCHUANG","LKL");
                for (int i = 0; i < channelMers.size(); i++) {
                    channelMer2 = channelMers.get(i);
                    BigDecimal deductRate = channelMer2.getDeductRate();
                    BigDecimal poundage1 = channelMer2.getPoundage();
                    String cardNo1 = channelMer2.getCardNo();
                    String channelNo = channel.getNumber();

                    if("SIFANG".equals(channelNo)){
                        //四方通道特殊处理
                        //没有修改费率
                        if(deductRate.compareTo(rate)==0 && poundage1.compareTo(poundage)==0){
                            ZzlmChannelMer channelMer1 = new ZzlmChannelMer();
                            channelMer1.setId(channelMer2.getId());
                            channelMer1.setCardNo(cardNo);
                            channelMer1.setDeductRate(rate);
                            channelMer1.setPoundage(poundage);
                            channelMer1.setChannelMerNo(channelMer2.getChannelMerNo());
                            channelMer1.setCreateTime(new Date());

                            channelPay.updateSubMerchant(userInfo,bankCard, channel, channelMer1);
                            walletChannelMerMapper.updateByPrimaryKeySelective(channelMer1);
                        }
                        //修改费率或者结算卡
                        else {
                            ZzlmChannelMer channelMer1 = new ZzlmChannelMer();
                            channelMer1.setId(channelMer2.getId());
                            channelMer1.setCardNo(cardNo);
                            channelMer1.setDeductRate(rate);
                            channelMer1.setPoundage(poundage);
                            channelMer1.setCreateTime(new Date());

                            String channelMerNo = channelPay.regisSubMerchant(userInfo,bankCard, channel, channelMer1);
                            channelMer1.setChannelMerNo(channelMerNo);
                            walletChannelMerMapper.updateByPrimaryKeySelective(channelMer1);
                        }
                    }
                    else {
                        if(deductRate.compareTo(rate)!=0 || poundage1.compareTo(poundage)!=0 || !cardNo.equals(cardNo1)){
                            ZzlmChannelMer channelMer1 = new ZzlmChannelMer();
                            channelMer1.setId(channelMer2.getId());
                            channelMer1.setCardNo(cardNo);
                            channelMer1.setDeductRate(rate);
                            channelMer1.setPoundage(poundage);
                            channelMer1.setChannelMerNo(channelMer2.getChannelMerNo());
                            channelMer1.setCreateTime(new Date());

                            if(channelNos.contains(channelNo)){
                                //以前老的通道，修改子商户跟注册子商户同一个方法
                                channelPay.regisSubMerchant(userInfo,bankCard, channel, channelMer1);
                            }else {
                                channelPay.updateSubMerchant(userInfo,bankCard, channel, channelMer1);
                            }
                            walletChannelMerMapper.updateByPrimaryKeySelective(channelMer1);
                        }else {
                            subMerchantNo = channelMer2.getChannelMerNo();
                        }
                    }
                }
            }
        }
        else {
            throw new ServiceException("2000", "该通道未配置费率");
        }

        map.put("subMerchantNo",subMerchantNo);
        return map;
    }

    @Override
    public Map<String, Object> quickPay(Long userId, Long channelId, Long bankCardId, BigDecimal orderPrice, String settleType, Double lon, Double lat, String tradeAddress) throws Exception{
        ZzlmChannel channel = walletChannelMapper.selectByPrimaryKey(channelId);
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(!UserState.已开通.getValue().equals(userInfo.getState())){
            throw new ServiceException("2000", "您还未审核通过！");
        }

        //校验付款金额
        payCheck(channelId, settleType, orderPrice, userId);

        //查询付款银行卡信息(信用卡)
        WalletBankCard bankCard = walletBankCardMapper.selectByPrimaryKey(bankCardId);

        //查询用户在通道注册的自商户
        ZzlmChannelMer channelMer = new ZzlmChannelMer();
        channelMer.setChannelId(channelId);
        channelMer.setUserId(userId);
        channelMer.setCardNo(userInfo.getSettleCardNo());
        channelMer.setType(ChannelMerType.一对一商户.getValue());
        channelMer.setSettleType(settleType);
        List<ZzlmChannelMer> channelMers = walletChannelMerMapper.select(channelMer);
        if(CollectionUtils.isEmpty(channelMers)){
            throw new ServiceException("2000", "商户未在通道注册");
        }
        channelMer = channelMers.get(0);

        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setApp(projectName);
        tradeRecords.setOrderNo(OrderUtils.creatOrderNum());
        tradeRecords.setUserId(userId);
        tradeRecords.setBankCardId(bankCardId);
        tradeRecords.setTradeTime(new Date());
        tradeRecords.setTradeState(TradeState.未支付.getValue());
        tradeRecords.setTradeMoney(orderPrice);//交易金额
        tradeRecords.setRealTradeMoney(orderPrice);//实际交易金额
        tradeRecords.setRate(channelMer.getDeductRate());//费率
        tradeRecords.setPlatformPoundage(channelMer.getPoundage());//平台手续费
        tradeRecords.setGoodsName("快捷支付");
        tradeRecords.setSettleType(settleType);
        tradeRecords.setChannelId(channelId);//渠道id
        tradeRecords.setRouteNo(channel.getNumber());//渠道编码
        tradeRecords.setRouteMerchantNo(channelMer.getChannelMerNo());//渠道商户编号
        tradeRecords.setLat(lat);
        tradeRecords.setLon(lon);
        tradeRecords.setTradeAddress(tradeAddress);
        walletTradeRecordsMapper.insertSelective(tradeRecords);

        ChannelPay channelPay = FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);
        Map<String, Object> result = channelPay.quickPay(userInfo, tradeRecords, channel, channelMer, bankCard);
        return result;
    }

    @Override
    public Map<String, Object> posPay(Long userId,Long channelId, Long bankCardId, BigDecimal orderPrice, String settleType, Double lon, Double lat, String tradeAddress) throws Exception {
        return quickPay(userId, channelId, bankCardId, orderPrice, settleType, lon, lat, tradeAddress);
    }

    @Override
    public Map<String, Object> quickPaySendSms(String outTradeNo, String jsonParam) throws Exception {
        Map<String, Object> params = JSON.parseObject(jsonParam, HashMap.class);

        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setOrderNo(outTradeNo);
        tradeRecords = walletTradeRecordsMapper.selectOne(tradeRecords);
        if(tradeRecords==null){
            throw new ServiceException("2000", "订单不存在");
        }

        WalletBankCard bankCard = walletBankCardMapper.selectByPrimaryKey(tradeRecords.getBankCardId());
        bankCard.setValidThru((String) params.get("validThru"));
        bankCard.setCvv((String) params.get("cvv2"));

        ZzlmChannel channel = walletChannelMapper.selectByPrimaryKey(tradeRecords.getChannelId());
        QuickPay quickPay = (QuickPay) FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);
        Map<String, Object> result = quickPay.sendSMSCode(channel, tradeRecords, bankCard);
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> quickPayConfirm(String outTradeNo, String jsonParam) throws Exception{
        Map<String, Object> params = JSON.parseObject(jsonParam, HashMap.class);

        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setOrderNo(outTradeNo);
        tradeRecords = walletTradeRecordsMapper.selectOne(tradeRecords);
        if(tradeRecords==null){
            throw new ServiceException("2000", "订单不存在");
        }

        WalletBankCard bankCard = walletBankCardMapper.selectByPrimaryKey(tradeRecords.getBankCardId());
        bankCard.setValidThru((String) params.get("validThru"));
        bankCard.setCvv((String) params.get("cvv2"));

        ZzlmChannel channel = walletChannelMapper.selectByPrimaryKey(tradeRecords.getChannelId());
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(tradeRecords.getUserId());
        QuickPay quickPay = (QuickPay) FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);
        Map<String, Object> result = quickPay.quickPayConfirm(userInfo, channel, tradeRecords, bankCard, params);

        //修改订单状态未支付中
        WalletTradeRecords tradeRecords1 = new WalletTradeRecords();
        tradeRecords1.setId(tradeRecords.getId());
        tradeRecords1.setTradeState(TradeState.支付中.getValue());
        walletTradeRecordsMapper.updateByPrimaryKeySelective(tradeRecords1);

        return result;
    }

    @Override
    public void settlement(String channelNo, Map<String, Object> params) throws Exception {
        ZzlmChannel channel = new ZzlmChannel();
        channel.setNumber(channelNo);
        channel = walletChannelMapper.selectOne(channel);
        QuickPay quickPay = (QuickPay) FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);

        String orderNo = (String) params.get("requestId");
        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setOrderNo(orderNo);
        tradeRecords = walletTradeRecordsMapper.selectOne(tradeRecords);

        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal realTradeMoney1 = tradeRecords.getRealTradeMoney();
        BigDecimal rate = tradeRecords.getRate()==null?zero:tradeRecords.getRate();
        BigDecimal rateMoney = realTradeMoney1.multiply(rate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformPoundage = tradeRecords.getPlatformPoundage()==null?zero:tradeRecords.getPlatformPoundage();
        //到账金额
        BigDecimal arrivalMoney = realTradeMoney1.subtract(rateMoney);

        DecimalFormat df = new DecimalFormat("#.##");
        params.put("amount", df.format(arrivalMoney));

        quickPay.settlement(channel, params);
    }

    @Override
    public void payment(HttpServletRequest request, Long userId, String channelNum, String outTradeNo, BigDecimal amount) throws Exception{
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);

        Map<String, Object> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", outTradeNo);
        bizContent.put("business_code", "2010022");
        bizContent.put("currency", "CNY");
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "银盛代付");
        bizContent.put("bank_name", userInfo.getSettleBank());
        bizContent.put("bank_city", userInfo.getSettleCity());
        bizContent.put("bank_account_no", userInfo.getSettleCardNo());
        bizContent.put("bank_account_name", userInfo.getSettleName());
        bizContent.put("bank_account_type", "personal");
        bizContent.put("bank_card_type", "debit");

        ZzlmChannel channel = new ZzlmChannel();
        channel.setNumber(channelNum);
        channel = walletChannelMapper.selectOne(channel);

        PaymentPay paymentPay = PaymentFactory.newPaymentPay(channel);
        paymentPay.t0pay(request, channel, bizContent);
    }

    /**
     * 临时方法(后面删除)
     * @param orderId
     * @throws Exception
     */
    @Override
    public void yibaosettle(Long orderId, String balance) throws Exception {
        WalletTradeRecords walletTradeRecords = walletTradeRecordsMapper.selectByPrimaryKey(orderId);
        String orderNo = walletTradeRecords.getOrderNo();
        Long channelId = walletTradeRecords.getChannelId();
        Long userId = walletTradeRecords.getUserId();

        ZzlmChannel channel = walletChannelMapper.selectByPrimaryKey(channelId);

        String channelNo = channel.getNumber();
        if(!"YIBAO".equals(channelNo)){
            throw new ServiceException("2000", "该方法只适用于易宝");
        }
        if(new BigDecimal(balance).compareTo(BigDecimal.ZERO)<=0){
            throw new ServiceException("2000", "出款金额必须大于0");
        }

        ZzlmChannelMer channelMer2 = new ZzlmChannelMer();
        channelMer2.setChannelId(channelId);
        channelMer2.setUserId(userId);
        //channelMer2.setCardNo(userInfo.getSettleCardNo());
        channelMer2.setType(ChannelMerType.一对一商户.getValue());
        channelMer2.setSettleType(SettleType.T0.getValue());
        channelMer2 = walletChannelMerMapper.selectOne(channelMer2);
        String channelMerNo = channelMer2.getChannelMerNo();

        Map<String, Object> params = new HashMap<>();
        params.put("requestId", orderNo);
        params.put("customerNumber", channelMerNo);
        params.put("transferWay", "1");

        QuickPay quickPay = (QuickPay) FactoryBuilder.build(channel.getChannelType()).getChannelPay(channel);

        /*WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setOrderNo(orderNo);
        tradeRecords = walletTradeRecordsMapper.selectOne(tradeRecords);

        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal realTradeMoney1 = tradeRecords.getRealTradeMoney();
        BigDecimal rate = tradeRecords.getRate()==null?zero:tradeRecords.getRate();
        BigDecimal rateMoney = realTradeMoney1.multiply(rate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformPoundage = tradeRecords.getPlatformPoundage()==null?zero:tradeRecords.getPlatformPoundage();
        //到账金额
        BigDecimal arrivalMoney = realTradeMoney1.subtract(rateMoney).subtract(new BigDecimal("0.01"));*/

        params.put("amount", balance);
        quickPay.settlement(channel, params);
    }

    @Override
    public Map<String, Object> h5Pay(String orderNo) {
        WalletTradeRecords tradeRecords = new WalletTradeRecords();
        tradeRecords.setOrderNo(orderNo);
        tradeRecords = walletTradeRecordsMapper.selectOne(tradeRecords);

        Long bankCardId = tradeRecords.getBankCardId();//信用卡id

        WalletBankCard bankCard = walletBankCardMapper.selectByPrimaryKey(bankCardId);
        ZzlmChannel channel = walletChannelMapper.selectByPrimaryKey(tradeRecords.getChannelId());

        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("channelNo", channel.getNumber());
        map.put("channelName", channel.getName());
        map.put("realTradeMoney", tradeRecords.getRealTradeMoney());
        map.put("bankCardName", bankCard.getBankName());
        map.put("validThru", bankCard.getValidThru());
        map.put("bankCardNo", StringUtils.substring(bankCard.getCardNo(), bankCard.getCardNo().length()-4, bankCard.getCardNo().length()));

        return map;
    }

}
