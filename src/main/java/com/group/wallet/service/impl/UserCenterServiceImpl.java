package com.group.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.config.MyWebAppConfig;
import com.group.core.enums.FileType;
import com.group.core.exception.ServiceException;
import com.group.core.service.QiniuyunService;
import com.group.core.service.RongCloudService;
import com.group.core.service.impl.PushService;
import com.group.utils.CommonUtils;
import com.group.utils.ImageUtils;
import com.group.wallet.channel.authent.common.Toolkit;
import com.group.wallet.channel.authent.service.AuthentXmlImpl;
import com.group.wallet.channel.authent.service.TransactionClient;
import com.group.wallet.channel.pos.lkl.impl.LklPosPayImpl;
import com.group.wallet.mapper.*;
import com.group.wallet.model.*;
import com.group.wallet.model.enums.*;
import com.group.wallet.service.CommonService;
import com.group.wallet.service.EmaySmsService;
import com.group.wallet.service.NoticeService;
import com.group.wallet.service.UserCenterService;
import com.group.wallet.util.StringReplaceUtil;
//import com.sun.jdi.request.StepRequest;
import com.sun.rowset.internal.Row;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Value("${project.name}")
    private String projectName;

    @Autowired
    private QiniuyunService qiniuyunService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private EmaySmsService emaySmsService;
    @Autowired
    private PushService pushService;
    @Autowired
    private RongCloudService rongCloudService;
    @Autowired
    private LklPosPayImpl lklPosPay;

    @Autowired
    private WalletUserInfoMapper walletUserInfoMapper;
    @Autowired
    private WalletBankCardMapper walletBankCardMapper;
    @Autowired
    private WalletUpdatephoneApplyMapper walletUpdatephoneApplyMapper;
    @Autowired
    private WalletAccountMapper walletAccountMapper;
    @Autowired
    private WalletDeductRateMapper walletDeductRateMapper;
    @Autowired
    private CommonMessagesMapper commonMessagesMapper;
    @Autowired
    private WalletCopywritingMapper walletCopywritingMapper;
    @Autowired
    private WalletAuthentRecordsMapper walletAuthentRecordsMapper;
    @Autowired
    private WalletBranchBankMapper walletBranchBankMapper;
    @Autowired
    private WalletCustomerServiceMapper walletCustomerServiceMapper;
    @Autowired
    private WalletChannelMapper walletChannelMapper;

    @Override
    @Transactional
    public void regist(String phone, String password, String inviter) throws Exception{
        WalletUserInfo userInfo2 = new WalletUserInfo();
        userInfo2.setPhone(phone);
        int count = walletUserInfoMapper.selectCount(userInfo2);
        if(count>0){
            throw new ServiceException("2000","该手机号已经被注册");
        }
        if(phone.equals(inviter)){
            throw new ServiceException("2000","推荐人不能是本人");
        }

        Long recommonId = null;
        String recommenNum = "";

        SysConfig sysConfig = commonService.getConfigValue("highest_persion");
        String configValue = sysConfig.getConfigvalue();
        String[] phones = StringUtils.split(configValue,",");
        List<String> phones2 = Arrays.asList(phones);

        if(!phones2.contains(phone)) {
            if (StringUtils.isEmpty(inviter)) {
                throw new ServiceException("2000", "推荐的用户不能为空");
            }

            WalletUserInfo userInfo1 = new WalletUserInfo();
            userInfo1.setPhone(inviter);
            userInfo1 = walletUserInfoMapper.selectOne(userInfo1);
            if(userInfo1==null){
                throw new ServiceException("2000","邀请人账号不存在");
            }
            recommonId = userInfo1.getId();
            recommenNum = userInfo1.getNumber();

            //给推荐人发短信
            String realName = userInfo1.getRealName();
            String content = "【"+projectName+"】您好！您推荐的"+phone+"成功注册成为"+projectName+"的商户。赶紧让他完善资料通过审核吧，他刷卡你赚钱哟！";
            emaySmsService.sendSms(userInfo1.getPhone(), content);

            //给推荐人推送
            pushService.push(userInfo1.getId()+"", content, "", null);
            CommonMessages messages = new CommonMessages();
            messages.setApp(projectName);
            messages.setUserId(userInfo1.getId());
            messages.setType(MessageType.系统消息.getValue());
            messages.setTitle(MessageType.系统消息.name());
            messages.setContent(content);
            messages.setCreateTime(new Date());
            commonMessagesMapper.insertSelective(messages);
        }

        JSONObject authority = new JSONObject();
        authority.put("profit_msg", "1");
        authority.put("profit_voice", "1");
        authority.put("receipt_msg", "1");
        authority.put("receipt_voice", "1");

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setPhone(phone);
        userInfo.setLoginPwd(password);
        userInfo.setRecommonId(recommonId);
        userInfo.setUserType(UserType.个体商户.getValue());
        userInfo.setState(UserState.注册.getValue());
        userInfo.setPrivacy("1");
        userInfo.setAuthority(JSON.toJSONString(authority));
        userInfo.setCreateTime(new Date());
        walletUserInfoMapper.insertSelective(userInfo);

        Long id = userInfo.getId();
        String number = recommenNum + "@" + userInfo.getId();
        WalletUserInfo userInfo1 = new WalletUserInfo();
        userInfo1.setId(id);
        userInfo1.setNumber(number);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo1);

        //给注册人发短信
        emaySmsService.sendSms(phone, "【"+projectName+"】欢迎"+phone+"注册成为"+projectName+"的商户，您可以使用"+projectName+"的所有功能，现在请登录"+projectName+"APP点击右下角-我的-实名认证，进行个人信息认证！");
    }

    @Override
    public WalletUserInfo getUserInfo(Long userId) {
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null){
            throw new ServiceException("2000","获取不到用户信息");
        }

        userInfo.setRealName(StringReplaceUtil.userNameReplaceWithStar2(userInfo.getRealName()));
        userInfo.setPhone(StringReplaceUtil.phoneReplaceWithStar(userInfo.getPhone()));

        return userInfo;
    }

    @Override
    public JSONObject getLklUserInfo(Long userId) throws Exception{
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);

        WalletChannel channel = new WalletChannel();
        channel.setNumber("LKL");
        channel = walletChannelMapper.selectOne(channel);

        JSONObject jsonObject = lklPosPay.getLklUserInfo(userInfo.getPhone(), channel);
        return jsonObject;
    }

    @Override
    public void h5Regist(String phone, String password, Long recommenId) throws Exception {
        WalletUserInfo userInfo2 = new WalletUserInfo();
        userInfo2.setPhone(phone);
        int count = walletUserInfoMapper.selectCount(userInfo2);
        if(count>0){
            throw new ServiceException("2000","该手机号已经被注册");
        }

        WalletUserInfo recommener = walletUserInfoMapper.selectByPrimaryKey(recommenId);
        if(recommener==null){
            throw new ServiceException("2000","邀请人不存在");
        }
        if(phone.equals(recommener.getPhone())){
            throw new ServiceException("2000","推荐人不能是本人");
        }
        String recommenNum = recommener.getNumber()==null?"":recommener.getNumber();

        //给推荐人发短信
        String content = "【"+projectName+"】您好！您推荐的"+phone+"成功注册成为"+projectName+"的商户。赶紧让他完善资料通过审核吧，他刷卡你赚钱哟！";
        emaySmsService.sendSms(recommener.getPhone(), content);

        //给推荐人推送
        CommonMessages messages = new CommonMessages();
        messages.setApp(projectName);
        messages.setUserId(recommener.getId());
        messages.setType(MessageType.系统消息.getValue());
        messages.setTitle(MessageType.系统消息.name());
        messages.setContent(content);
        messages.setCreateTime(new Date());
        commonMessagesMapper.insertSelective(messages);

        pushService.push(recommener.getId()+"", content, "", null);

        JSONObject authority = new JSONObject();
        authority.put("profit_msg", "1");
        authority.put("profit_voice", "1");
        authority.put("receipt_msg", "1");
        authority.put("receipt_voice", "1");

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setPhone(phone);
        userInfo.setLoginPwd(password);
        userInfo.setRecommonId(recommenId);
        userInfo.setUserType(UserType.个体商户.getValue());
        userInfo.setState(UserState.注册.getValue());
        userInfo.setPrivacy("1");
        userInfo.setAuthority(JSON.toJSONString(authority));
        userInfo.setCreateTime(new Date());
        walletUserInfoMapper.insertSelective(userInfo);

        Long id = userInfo.getId();
        String number = recommenNum + "@" + id;
        WalletUserInfo userInfo3 = new WalletUserInfo();
        userInfo3.setId(id);
        userInfo3.setNumber(number);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo3);

        //给注册人发短信
        emaySmsService.sendSms(phone, "【"+projectName+"】欢迎"+phone+"注册成为"+projectName+"的商户，您可以使用"+projectName+"的所有功能，现在请登录"+projectName+"APP点击右下角-我的-实名认证，进行个人信息认证！");
    }

    @Override
    public void restPassword(String phone, String password) {
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setPhone(phone);
        userInfo = walletUserInfoMapper.selectOne(userInfo);
        if(userInfo==null){
            throw new ServiceException("2000","该手机号不存在");
        }

        WalletUserInfo walletUserInfo = new WalletUserInfo();
        walletUserInfo.setId(userInfo.getId());
        walletUserInfo.setLoginPwd(password);

        walletUserInfoMapper.updateByPrimaryKeySelective(walletUserInfo);
    }

    @Override
    public Map<String, Object> login(String phone, String password) throws Exception{
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setPhone(phone);
        userInfo = walletUserInfoMapper.selectOne(userInfo);
        if(userInfo==null){
            throw new ServiceException("2000","该手机号不存在");
        }

        if(!password.equals(userInfo.getLoginPwd())){
            throw new ServiceException("2000","密码不正确");
        }

        Map<String, Object> map = new HashMap<>();
        String token = "";
        try {
            token = rongCloudService.getToken(userInfo.getId()+"", userInfo.getRealName(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("token",token);
        map.put("userId",userInfo.getId());

        return map;
    }

    @Override
    public Map<String, Object> getUserCenterData(Long userId) {
        Map<String,Object> map = new HashMap<>();
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        Long recommonId = userInfo.getRecommonId();
        WalletUserInfo recommoner = walletUserInfoMapper.selectByPrimaryKey(recommonId);

        map.put("userInfo",userInfo);
        map.put("recommoner",recommoner);
        return map;
    }

    @Override
    public String updateHeadImage(Long userId, MultipartFile headImage) throws Exception{
        String imageUrl =  qiniuyunService.uploadMultipartFile(headImage, FileType.图像.getValue());

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setHeadImage(imageUrl);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);

        return imageUrl;
    }

    @Override
    public void updateNickName(Long userId, String nickName) {
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setNickName(nickName);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public void updateWxNo(Long userId, String wxNo) {
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setWxNo(wxNo);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public String updateWxQrCode(Long userId, MultipartFile wxQrCode) throws IOException{
        String imageUrl = qiniuyunService.uploadMultipartFile(wxQrCode,FileType.图片.getValue());

        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setWxQrcode(imageUrl);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);

        return imageUrl;
    }

    @Override
    public void privacywitch(Long userId, String privacywitch) {
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setPrivacy(privacywitch);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public List<WalletBranchBank> getBranchBank(String bankName, String search, Integer pageNo) {
        Example example = new Example(WalletBranchBank.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("branchBankName", "%"+bankName+"%");
        if(StringUtils.isNotEmpty(search)){
            criteria.andLike("branchBankName", "%"+search+"%");
        }

        RowBounds rowBounds = new RowBounds((pageNo-1)*20, 20);
        List<WalletBranchBank> list = walletBranchBankMapper.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    @Override
    public void authIdCard(Long userId, String realName, String idCard, String indate) {
        Example example = new Example(WalletUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("idCard", idCard);
        criteria.andNotEqualTo("id", userId);

        int count = walletUserInfoMapper.selectCountByExample(example);
        if(count>0){
            throw new ServiceException("2000", "该身份证已存在");
        }
    }

    /*@Override
    public void authActivityImage(Long userId, String url) {
        WalletAuthentRecords authentRecords = new WalletAuthentRecords();
        authentRecords.setType(AuthType.人脸认证.getValue());
        authentRecords.setUserId(userId);
        int count = walletAuthentRecordsMapper.selectCount(authentRecords);
        if(count>=3) throw new ServiceException("2000", "认证次数超出限制，请联系客服");

        authentRecords.setCreateTime(new Date());
        walletAuthentRecordsMapper.insertSelective(authentRecords);
    }*/

    @Override
    public void realNameAuthentication(WalletUserInfo userInfo) throws Exception{
        //判断身份证是否重复
        Example example = new Example(WalletUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("idCard", userInfo.getIdCard());
        criteria.andNotEqualTo("id", userInfo.getId());
        int count = walletUserInfoMapper.selectCountByExample(example);
        if(count>0){
            throw new ServiceException("2000", "该身份证已存在");
        }

        //判断结算卡是否重复
        Example example2 = new Example(WalletUserInfo.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("settleCardNo", userInfo.getSettleCardNo());
        criteria2.andNotEqualTo("id", userInfo.getId());
        int count2 = walletUserInfoMapper.selectCountByExample(example2);
        if(count2>0){
            throw new ServiceException("2000", "该结算卡已存在");
        }

        //四要素认证
        boolean check = authentCheck(userInfo.getId(), userInfo.getSettleCardNo(), userInfo.getSettlePhone(), userInfo.getIdCard(), userInfo.getRealName());
        if(!check){
            throw new ServiceException("2000", "结算卡认证不通过");
        }

        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);

        //推荐人升级
        WalletUserInfo userInfo3 = walletUserInfoMapper.selectByPrimaryKey(userInfo.getId());
        Long recommenId = userInfo3.getRecommonId();
        upgrade(recommenId);
    }

    private void upgrade(Long recommenId){
        WalletUserInfo recommener = walletUserInfoMapper.selectByPrimaryKey(recommenId);
        if(recommener==null)
            return;

        String recommenType = recommener.getUserType();
        Integer count = walletUserInfoMapper.selectRecommenCount(UserState.已认证储蓄卡.getValue(), recommenId);

        if(UserType.个体商户.getValue().equals(recommenType) && count>=1){
            WalletUserInfo userInfo = new WalletUserInfo();
            userInfo.setId(recommenId);
            userInfo.setUserType(UserType.市级代理商.getValue());
            walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
        }

        if(UserType.市级代理商.getValue().equals(recommenType) && count>=9){
            WalletUserInfo userInfo = new WalletUserInfo();
            userInfo.setId(recommenId);
            userInfo.setUserType(UserType.省级代理商.getValue());
            walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
    }

    @Override
    public void creditCardAuthentication(WalletUserInfo userInfo) throws Exception {
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public boolean authentCheck(Long userId, String accountNo, String mobileNo, String idCardNo, String idCardName) throws Exception{

        //已经认证通过的不需要再提交认证
        WalletAuthentRecords authentRecords3 = new WalletAuthentRecords();
        authentRecords3.setType(AuthType.身份证认证.getValue());
        authentRecords3.setCardNo(accountNo);
        authentRecords3.setName(idCardName);
        authentRecords3.setIdCard(idCardNo);
        authentRecords3.setPhone(mobileNo);
        authentRecords3.setIsPass("1");
        int count2 = walletAuthentRecordsMapper.selectCount(authentRecords3);
        if(count2>0){
            return true;
        }

        //认证未通过4次的卡不允许再提交
        WalletAuthentRecords authentRecords2 = new WalletAuthentRecords();
        authentRecords2.setType(AuthType.身份证认证.getValue());
        authentRecords2.setCardNo(accountNo);
        authentRecords2.setIsPass("0");
        int count = walletAuthentRecordsMapper.selectCount(authentRecords2);
        if(count>4){
            throw new ServiceException("2000", "认证次数超出限制，请联系客服");
        }

        //如果信息重复提交不需要调用认证接口
        WalletAuthentRecords authentRecords = new WalletAuthentRecords();
        authentRecords.setType(AuthType.身份证认证.getValue());
        authentRecords.setCardNo(accountNo);
        authentRecords.setName(idCardName);
        authentRecords.setIdCard(idCardNo);
        authentRecords.setPhone(mobileNo);
        int count1 = walletAuthentRecordsMapper.selectCount(authentRecords);
        if(count1 > 0){
            throw new ServiceException("2000", "银行卡信息重复提交");
        }

        WalletAuthentRecords authentRecords1 = new WalletAuthentRecords();
        authentRecords1.setType(AuthType.身份证认证.getValue());
        authentRecords1.setUserId(userId);
        authentRecords1.setCardNo(accountNo);
        authentRecords1.setName(idCardName);
        authentRecords1.setIdCard(idCardNo);
        authentRecords1.setPhone(mobileNo);
        authentRecords1.setCreateTime(new Date());

        boolean result = authentCheck2(accountNo, mobileNo, idCardNo, idCardName);
        if(result){
            authentRecords1.setIsPass("1");
            walletAuthentRecordsMapper.insertSelective(authentRecords1);
        }else {
            authentRecords1.setIsPass("0");
            walletAuthentRecordsMapper.insertSelective(authentRecords1);
            return false;
        }

        return true;
    }

    private boolean authentCheck2(String accountNo, String mobileNo, String idCardNo, String idCardName) throws Exception{
        //四要素验证
        String url = "https://authent.payeco.com/services/ApiV2ServerRSA"; // 生产环境地址
        TransactionClient transClient = new TransactionClient();
        transClient.setUrl(url);
        transClient.setServerCert("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJ1fKGMV/yOUnY1ysFCk0yPP4bfOolC/nTAyHmoser+1yzeLtyYsfitYonFIsXBKoAYwSAhNE+ZSdXZs4A5zt4EKoU+T3IoByCoKgvpCuOx8rgIAqC3O/95pGb9n6rKHR2sz5EPT0aBUUDAB2FJYjA9Sy+kURxa52EOtRKolSmEwIDAQAB");
        transClient.setMerchantNo("502058700003");
        transClient.setTerminalNo("02028828");
        transClient.setMerchantPwd("8DCD7385DB2A48C4");

        String tripleDesKey = Toolkit.random(24); // 交易报文密钥, 随机生成, 用于加密解密报文
        AuthentXmlImpl reqXml = new AuthentXmlImpl();
        String transactType = "32"; // 认证类型 11 12 13 14 21 31 41 51 ...
        reqXml.setTransactType(transactType);
        reqXml.setAccountNo(accountNo);
        reqXml.setMobileNo(mobileNo);
        reqXml.setIDCardNo(idCardNo);
        reqXml.setIDCardType("01");
        reqXml.setIDCardName(idCardName);

        AuthentXmlImpl respXml = transClient.authent(reqXml, tripleDesKey);
        String RespCode = respXml.getRespCode();
        String AccountNoResult = respXml.getAccountNoResult();//银行卡号对比结果
        if(!"0000".equals(RespCode) || !"0000".equals(AccountNoResult)){
            return false;
        }

        return true;
    }

    @Override
    public List<WalletBankCard> getMyCreditCards(Long userId) {
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String idCard = userInfo.getIdCard();

        Example example = new Example(WalletBankCard.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        if(StringUtils.isEmpty(idCard)){
            criteria.andEqualTo("idCard", idCard);
        }
        example.setOrderByClause("create_time desc");

        List<WalletBankCard> cards = walletBankCardMapper.selectByExample(example);
        return cards;
    }

    @Override
    public List<WalletBankCard> getOtherCreditCards(Long userId) {
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String idCard = userInfo.getIdCard();

        WalletBankCardExample example = new WalletBankCardExample();
        example.or().andUserIdEqualTo(userId).andIdCardNotEqualTo(idCard);
        example.setOrderByClause("create_time desc");

        List<WalletBankCard> cards = walletBankCardMapper.selectByExample(example);
        return cards;
    }

    @Override
    public void addCreditCards(WalletBankCard bankCard) throws Exception {
        String cardNo = bankCard.getCardNo();
        WalletBankCard bankCard1 = new WalletBankCard();
        bankCard1.setCardNo(cardNo);
        int count = walletBankCardMapper.selectCount(bankCard1);
        if(count>0){
            throw new ServiceException("2000","该卡号已经存在");
        }

        //四要素认证
        boolean result = authentCheck(bankCard.getUserId(), bankCard.getCardNo(), bankCard.getPhone(), bankCard.getIdCard(), bankCard.getName());
        if(result){
            walletBankCardMapper.insertSelective(bankCard);
        }else {
            throw new ServiceException("2000", "信用卡认证失败");
        }
    }

    @Override
    public void updateCreditCards(WalletBankCard bankCard) {
        walletBankCardMapper.updateByPrimaryKeySelective(bankCard);
    }

    @Override
    public void delCreditCards(Long cardId) {
        walletBankCardMapper.deleteByPrimaryKey(cardId);
    }

    @Override
    public void updateSettleCard(WalletUserInfo userInfo) throws Exception{
        WalletUserInfo userInfo1 = walletUserInfoMapper.selectByPrimaryKey(userInfo.getId());

        //验证四要素
        boolean check = authentCheck(userInfo.getId(), userInfo.getSettleCardNo(), userInfo.getSettlePhone(), userInfo1.getIdCard(), userInfo1.getRealName());
        if(!check){
            throw new ServiceException("2000", "银行卡四要素认证失败");
        }

        //更新结算卡信息
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public void updatePhoneApply(WalletUpdatephoneApply updatephoneApply) {
        String phone = updatephoneApply.getPhone();
        WalletUpdatephoneApply updatephoneApply1 = new WalletUpdatephoneApply();
        updatephoneApply1.setPhone(phone);
        updatephoneApply1.setState("0");
        int count = walletUpdatephoneApplyMapper.selectCount(updatephoneApply1);
        if(count>0){
            throw new ServiceException("2000","你有申请还未审核通过");
        }

        walletUpdatephoneApplyMapper.insertSelective(updatephoneApply);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setId(userId);
        userInfo.setLoginPwd(oldPassword);
        int count = walletUserInfoMapper.selectCount(userInfo);
        if(count==0){
            throw new ServiceException("2000","密码不正确");
        }

        WalletUserInfo userInfo1 = new WalletUserInfo();
        userInfo1.setId(userId);
        userInfo1.setLoginPwd(newPassword);
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo1);
    }

    @Override
    public void updatePrivacy(Long userId, String type, String value) {
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String authority = userInfo.getAuthority();
        JSONObject jsonObject = JSON.parseObject(authority);
        if(jsonObject==null){
            jsonObject = new JSONObject();
        }
        jsonObject.put(type, value);

        WalletUserInfo userInfo1 = new WalletUserInfo();
        userInfo1.setId(userInfo.getId());
        userInfo1.setAuthority(JSON.toJSONString(jsonObject));
        walletUserInfoMapper.updateByPrimaryKeySelective(userInfo1);
    }

    @Override
    public Map<String, Object> getMyRecommonMerchant(Long userId) {
        Map<String,Object> result = new HashMap<>();

        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        String number = userInfo.getNumber();

        //合伙人
        int D1 = walletUserInfoMapper.selectDirectRecommonCount(userId, "DEF");
        int D2 = walletUserInfoMapper.selectIndirectRecommonCount(number+"@", userId, "DEF");
        int D3 = D1+D2;

        //代理商
        int B1 = walletUserInfoMapper.selectDirectRecommonCount(userId, "BC");
        int B2 = walletUserInfoMapper.selectIndirectRecommonCount(number+"@", userId, "BC");
        int B3 = B1+B2;

        //个体商户
        int A1 = walletUserInfoMapper.selectDirectRecommonCount(userId, "A");
        int A2 = walletUserInfoMapper.selectIndirectRecommonCount(number+"@", userId, "A");
        int A3 = A1+A2;

        int T = D3+B3+A3;

        result.put("T",T);
        result.put("D1",D1);
        result.put("D2",D2);
        result.put("D3",D3);
        result.put("B1",B1);
        result.put("B2",B2);
        result.put("B3",B3);
        result.put("A1",A1);
        result.put("A2",A2);
        result.put("A3",A3);

        return result;
    }

    @Override
    public Map<String, Object> getMyRecommonMerchant2(Long userId) {
        WalletUserInfo userInfo1 = walletUserInfoMapper.selectByPrimaryKey(userId);

        //获取直接推荐商家数量
        WalletUserInfo userInfo = new WalletUserInfo();
        userInfo.setRecommonId(userId);
        int count = walletUserInfoMapper.selectCount(userInfo);

        //获取间接推荐商家数量
        Example example = new Example(WalletUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("recommonId", userId);
        criteria.andLike("number", "%"+userInfo1.getNumber()+"%");
        int count1 = walletUserInfoMapper.selectCountByExample(example);

        Map<String, Object> map = new HashMap<>();
        map.put("directCount", count);
        map.put("indirectCount", count1);
        map.put("totalCount", count+count1);
        return map;
    }

    @Override
    public List<WalletUserInfo> getRecommonMerchant(Long userId,String userType, String userState) {
        List<String> userTypes = new ArrayList<>();
        if("partner".equals(userType)){
            userTypes.add(UserType.初级合伙人.getValue());
            userTypes.add(UserType.高级合伙人.getValue());
            userTypes.add(UserType.超级合伙人.getValue());
        }
        if("agent".equals(userType)){
            userTypes.add(UserType.市级代理商.getValue());
            userTypes.add(UserType.省级代理商.getValue());
        }
        if("merchant".equals(userType)){
            userTypes.add(UserType.个体商户.getValue());
        }

        Example example = new Example(WalletUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("recommonId", userId);
        criteria.andNotEqualTo("id", userId);
        criteria.andIn("userType", userTypes);
        if(StringUtils.isNotEmpty(userState)){
            if(UserState.信息不全.getValue().equals(userState)){
                List<String> states = new ArrayList<>();
                states.add(UserState.注册.getValue());
                states.add(UserState.信息不全.getValue());
                criteria.andIn("state", states);
            }else {
                criteria.andEqualTo("state", userState);
            }
        }
        example.setOrderByClause("user_type");

        List<WalletUserInfo> list = walletUserInfoMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<WalletUserInfo> getRecommonMerchant1(Long userId, String userState, int pageNo) {
        int start = (pageNo-1)*20;
        int limit = 20;
        RowBounds rowBounds = new RowBounds(start, limit);

        List<String> states = new ArrayList<>();
        if("0".equals(userState)){

        }
        if("1".equals(userState)){
            states.add(UserState.已开通.getValue());
        }
        if("2".equals(userState)){
            states.add(UserState.待审核.getValue());
            states.add(UserState.待复审.getValue());
        }
        if("3".equals(userState)){
            states.add(UserState.注册.getValue());
            states.add(UserState.信息不全.getValue());
            states.add(UserState.已认证储蓄卡.getValue());
        }
        if("4".equals(userState)){
            states.add(UserState.审核不通过.getValue());
        }

        Example example = new Example(WalletUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("recommonId", userId);
        if(states.size()>0){
            criteria.andIn("state", states);
        }
        example.setOrderByClause("create_time desc");
        List<WalletUserInfo> list = walletUserInfoMapper.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    @Override
    public BigDecimal getMyAccount(Long userId) {
        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);
        BigDecimal accountBalance = userInfo.getAccountBalance();
        return accountBalance;
    }

    @Override
    public List<WalletAccount> getMyAccountDetail(Long userId) {
        WalletAccount account = new WalletAccount();
        account.setUserId(userId);
        List<WalletAccount> list = walletAccountMapper.select(account);
        return list;
    }

    @Override
    public void takeOutMoney(Long accountId, BigDecimal money) {
        WalletAccount account = walletAccountMapper.selectByPrimaryKey(accountId);
        BigDecimal amount = account.getAmount();
        if(money.compareTo(amount)>0){
            throw new ServiceException("2000", "提现金额超额");
        }

        Long userId = account.getUserId();
        Long channelId = account.getChannelId();

        WalletUserInfo userInfo = walletUserInfoMapper.selectByPrimaryKey(userId);

        WalletDeductRate deductRate = new WalletDeductRate();
        deductRate.setDeductType(DeductType.通道.getValue());
        deductRate.setChannelId(channelId);
        deductRate.setUserType(userInfo.getUserType());
        List<WalletDeductRate> list = walletDeductRateMapper.select(deductRate);

        //扣除手续费和提现费，按照剩余金额代付

    }

    @Override
    public List<Map<String, Object>> getMyRate(String userType, String payWay) {
        List<String> channelType = new ArrayList<>();
        channelType.add(ChannelType.快捷渠道.getValue());
        channelType.add(ChannelType.H5快捷渠道.getValue());
        channelType.add(ChannelType.刷卡渠道.getValue());
        List<Map<String, Object>> list = walletDeductRateMapper.selectUserChannelRate(userType, channelType);
        return list;
    }

    @Override
    public List<CommonMessages> getMessageList(Long userId, Integer pageNo, String type) {
        Example example = new Example(CommonMessages.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("app", "%"+projectName+"%");
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("type", type);
        example.setOrderByClause("id desc");

        RowBounds rowBounds = new RowBounds((pageNo-1)*20, 20);
        List<CommonMessages> list = commonMessagesMapper.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    @Override
    public List<WalletCopywritingWithBLOBs> getCopywriting(String type, Integer pageNo) {
        Example example = new Example(WalletCopywritingWithBLOBs.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", "1");
        criteria.andEqualTo("type", type);
        example.setOrderByClause("create_time desc");

        RowBounds rowBounds = new RowBounds((pageNo-1)*20, 20);
        List<WalletCopywritingWithBLOBs> list = walletCopywritingMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (int i = 0; i < list.size(); i++) {
            WalletCopywritingWithBLOBs copywriting = list.get(i);
            List<String> images = ImageUtils.convertImageUrlHtml2(copywriting.getImages());
            String images2 = "";
            if(images!=null && images.size()>0){
                for (int j = 0; j < images.size(); j++) {
                    images2 += images.get(j)+";";
                }
            }
            copywriting.setImages(images2);
        }
        return list;
    }

    @Override
    public List<WalletCustomerServiceWithBLOBs> getCustomerService(String type) {
        WalletCustomerServiceWithBLOBs customerService = new WalletCustomerServiceWithBLOBs();
        customerService.setType(type);
        List<WalletCustomerServiceWithBLOBs> list = walletCustomerServiceMapper.select(customerService);
        return list;
    }

}
