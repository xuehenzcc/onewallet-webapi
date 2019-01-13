package com.group.wallet.channel.pos.lkl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.exception.ServiceException;
import com.group.core.service.QiniuyunService;
import com.group.utils.HttpClientUtils;
import com.group.wallet.channel.pos.PosPay;
import com.group.wallet.channel.pos.lkl.bean.*;
import com.group.wallet.channel.pos.lkl.config.LklPayConfig;
import com.group.wallet.channel.pos.lkl.rsa.RSAEncryptByPrivateKey;
import com.group.wallet.mapper.WalletBranchBankMapper;
import com.group.wallet.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LklPosPayImpl implements PosPay{

    @Autowired
    private QiniuyunService qiniuyunService;

    @Autowired
    private WalletBranchBankMapper walletBranchBankMapper;

    @Override
    public String regisSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        //商户进件
        regisInfo(userInfo, channel);
        //绑定结算卡
        bindCard(userInfo, channel);

        return null;
    }

    @Override
    public String updateSubMerchant(WalletUserInfo userInfo, WalletBankCard bankCard, WalletChannel channel, WalletChannelMer channelMer) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> quickPay(WalletUserInfo userInfo, WalletTradeRecords tradeRecords, WalletChannel channel, WalletChannelMer channelMer, WalletBankCard bankCard) throws Exception {
        OrderBean bean = new OrderBean();
        bean.setApp_merch_no(userInfo.getPhone());
        bean.setApp_order_no(tradeRecords.getOrderNo());
        bean.setAmount(tradeRecords.getRealTradeMoney().multiply(new BigDecimal(100)).longValue());
        bean.setArriveDelay(0);
        bean.setNotifyUrl(channel.getNoticeUrl());
        bean.setOrder_create_time(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        bean.setPayType(128);
        bean.setScantype("1");//1 用户扫商户 2 商户扫用户 4微信公众号 5 H5支付
        bean.setRate(0.006);
		bean.setExt("{\"$fee\":200,\"$top\":0}");
        String param = bean.requestParam();

        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pay/order", param);

        JSONObject json = JSON.parseObject(result);
        String code = json.getString("code");
        if(!"0000".equals(code)){
            String msg = json.getString("msg");
            throw new ServiceException("2000", msg);
        }
        String result2 = json.getString("result");
        Map<String, Object> result3 = JSON.parseObject(result2, HashMap.class);
        String pay_info = (String) result3.get("pay_info");
        Map<String, Object> reslult4 = JSON.parseObject(pay_info, HashMap.class);
        return reslult4;
    }

    @Override
    public boolean checkSign(WalletChannel channel, Map<String, Object> params) throws Exception {
        return true;
    }

    /**
     * 商户进件
     * @param userInfo
     * @param channel
     * @return
     * @throws Exception
     */
    private void regisInfo(WalletUserInfo userInfo, WalletChannel channel) throws Exception{
        MerchantBean merch = new MerchantBean();
        merch.setApp_merch_no(userInfo.getPhone());
        merch.setMerch_name(userInfo.getRealName());
        merch.setProvince_id("170000");
        merch.setCity_id("170400");
        merch.setIndustry_category_code("393");

        MerchantDetailBean merchDetail = new MerchantDetailBean();
        merchDetail.setMerch_short_name(userInfo.getRealName());
        merchDetail.setPrincipal_name(userInfo.getRealName());
        merchDetail.setPrincipal_mobile(userInfo.getPhone());
        merchDetail.setId_card(userInfo.getIdCard());
        merchDetail.setIndentity_photo(qiniuyunService.convertImageUrl(userInfo.getIdCardZImage()));
        merchDetail.setIndentity_reverse_photo(qiniuyunService.convertImageUrl(userInfo.getIdCardFImage()));
        merchDetail.setLegal_person_idphoto(qiniuyunService.convertImageUrl(userInfo.getHandIdcardImage()));
        merchDetail.setLegal_person_name(userInfo.getRealName());
        merch.setMerch_detail(merchDetail);

        String param = merch.requestParam();
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pay/merch/save", param);
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String msg = jsonObject.getString("msg");
            throw new ServiceException("2000", msg);
        }
    }

    /**
     * 绑定银行卡
     * @param userInfo
     * @param channel
     * @throws Exception
     */
    private void bindCard(WalletUserInfo userInfo, WalletChannel channel) throws Exception{
        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("branchBankName", userInfo.getSettleBank());
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExample(example);
        String bankCode = "102526008009";
        if(list!=null && list.size()>0){
            bankCode = list.get(0).getBarnchBankCode();
        }

        BanckCardBean card = new BanckCardBean();
        card.setApp_merch_no(userInfo.getPhone());
        card.setCardholder(userInfo.getRealName());
        card.setId_no(userInfo.getIdCard());
        card.setCard_no(userInfo.getSettleCardNo());
        card.setMobile_no(userInfo.getSettlePhone());
        card.setBank_branch_nbk_code(bankCode);

        card.setOpen_city_id("170400");
        card.setOpen_province_id("170000");
        card.setAccount_type(2);
        card.setHold_bank_photo(qiniuyunService.convertImageUrl(userInfo.getHandCreditImage()));
        card.setBank_photo(qiniuyunService.convertImageUrl(userInfo.getSettleCardZScan()));
        card.setBank_back_photo(qiniuyunService.convertImageUrl(userInfo.getSettleCardFScan()));

        String param = card.requestParam();
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pay/merch/cardbind", param);
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if(!"0000".equals(code)){
            String msg = jsonObject.getString("msg");
            throw new ServiceException("2000", msg);
        }
    }

    /**
     * 获取拉卡拉商户信息
     * @param merchNo
     * @return
     */
    public JSONObject getLklUserInfo(String merchNo, WalletChannel channel) throws Exception{
        MerchantQueryBean bean = new MerchantQueryBean();
        bean.setApp_merch_no(merchNo);
        String param = bean.requestParam();
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pay/merch/query", param);
        //String json = RSAEncryptByPrivateKey.getInstance(LklPayConfig.APP_Private_Key).decryptPrivateRSA(JSON.parseObject(result).getString("sign"));

        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if("0000".equals(code)){
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            return jsonObject1;
        }else {
            return new JSONObject();
        }
    }

    /**
     * 获取订单信息
     * @param orderNo
     * @param merchNo
     * @param channel
     * @throws Exception
     */
    public JSONObject getOrderDetail(String orderNo, String merchNo, WalletChannel channel) throws Exception{
        OrderQueryBean bean = new OrderQueryBean();
        bean.setApp_order_no(orderNo);
        bean.setApp_merch_no(merchNo);
        String param = bean.requestParam();
        String result = HttpClientUtils.sendPost(channel.getChannelUrl()+"pay/order/detail/lkl", param);

        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if("0000".equals(code)){
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            return jsonObject1;
        }else {
            return new JSONObject();
        }
    }
}
