package com.group.wallet.service;

import com.alibaba.fastjson.JSONObject;
import com.group.wallet.channel.payment.yspay.utils.StringUtils;
import com.group.wallet.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户中心
 */
public interface UserCenterService {

    /**
     * 注册
     * @param phone
     * @param password
     * @param inviter
     */
    void regist(String phone,String password,String inviter) throws Exception;

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    WalletUserInfo getUserInfo(Long userId);

    /**
     * 查询拉卡拉商户信息
     * @param userId
     * @return
     */
    JSONObject getLklUserInfo(Long userId) throws Exception;

    /**
     * H5注册
     * @param phone
     * @param password
     * @param recommenId
     * @throws Exception
     */
    void h5Regist(String phone, String password, Long recommenId) throws Exception;

    /**
     * 重置密码
     * @param phone
     * @param password
     */
    void restPassword(String phone,String password);

    /**
     * 登陆
     * @param phone
     * @param password
     * @return
     */
    Map<String, Object> login(String phone,String password) throws Exception;

    /**
     * 获取用户中心数据
     * @param userId
     * @return
     */
    Map<String,Object> getUserCenterData(Long userId);

    /**
     * 更新用户图像
     * @param userId
     * @param headImage
     */
    String updateHeadImage(Long userId,MultipartFile headImage) throws Exception;

    /**
     * 修改昵称
     * @param userId
     * @param nickName
     */
    void updateNickName(Long userId,String nickName);

    /**
     * 修改微信号
     * @param userId
     * @param wxNo
     */
    void updateWxNo(Long userId,String wxNo);

    /**
     * 修改微信二维码
     * @param userId
     * @param wxQrCode
     */
    String updateWxQrCode(Long userId,MultipartFile wxQrCode) throws IOException;

    /**
     * 隐私开关
     * @param userId
     * @param privacywitch
     */
    void privacywitch(Long userId,String privacywitch);

    /**
     * 获取支行
     * @param bankName
     * @param search
     * @return
     */
    List<WalletBranchBank> getBranchBank(String bankName, String search, Integer pageNo);

    /**
     * 身份证认证
     * @param userId
     * @param realName
     * @param idCard
     * @param indate
     */
    void authIdCard(Long userId, String realName, String idCard, String indate);

    /**
     * 活体认证
     * @param userId
     * @param url
     */
    //void authActivityImage(Long userId, String url);

    /**
     * 认证储蓄卡
     * @param userInfo
     */
    void realNameAuthentication(WalletUserInfo userInfo) throws Exception;

    /**
     * 认证信用卡
     * @param userInfo
     * @throws Exception
     */
    void creditCardAuthentication(WalletUserInfo userInfo) throws Exception;

    /**
     * 四要素验证
     * @return
     */
    boolean authentCheck(Long userId, String accountNo, String mobileNo, String idCardNo, String idCardName) throws Exception;

    /**
     * 获取信用卡
     * @param userId
     * @return
     */
    List<WalletBankCard> getMyCreditCards(Long userId);

    /**
     * 获取他人的信用卡
     * @param userId
     * @return
     */
    List<WalletBankCard> getOtherCreditCards(Long userId);

    /**
     * 添加信用卡
     * @param bankCard
     */
    void addCreditCards(WalletBankCard bankCard) throws Exception;

    /**
     * 修改信用卡
     * @param bankCard
     */
    void updateCreditCards(WalletBankCard bankCard);

    /**
     * 删除信用卡
     * @param cardId
     */
    void delCreditCards(Long cardId);

    /**
     * 修改结算卡
     * @param userInfo
     */
    void updateSettleCard(WalletUserInfo userInfo) throws Exception;

    /**
     * 修改手机申请
     * @param updatephoneApply
     */
    void updatePhoneApply(WalletUpdatephoneApply updatephoneApply);

    /**
     * 更新密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(Long userId,String oldPassword,String newPassword);

    /**
     * 修改隐私开关
     * @param userId
     * @param type
     * @param value
     */
    void updatePrivacy(Long userId, String type, String value);

    /**
     * 获取我的商户
     * @param userId
     * @return
     */
    Map<String,Object> getMyRecommonMerchant(Long userId);

    /**
     * 获取商户数量
     * @param userId
     * @return
     */
    Map<String,Object> getMyRecommonMerchant2(Long userId);

    /**
     * 获取推荐的商户
     * @param userId
     * @param userType
     * @return
     */
    List<WalletUserInfo> getRecommonMerchant(Long userId,String userType, String userState);

    /**
     * 获取推荐商户分页数据
     * @param userId
     * @param userState
     * @return
     */
    List<WalletUserInfo> getRecommonMerchant1(Long userId, String userState, int pageNo);

    /**
     * 获取我的账户
     * @param userId
     * @return
     */
    BigDecimal getMyAccount(Long userId);

    /**
     * 获取账号明细
     * @param userId
     * @return
     */
    List<WalletAccount> getMyAccountDetail(Long userId);

    /**
     * 我要提现
     * @param accountId
     * @param money
     */
    void takeOutMoney(Long accountId, BigDecimal money);

    /**
     * 获取我的汇率
     * @param userType
     * @param payWay
     * @return
     */
    List<Map<String, Object>> getMyRate(String userType, String payWay);

    /**
     * 获取消息记录
     * @param userId
     * @param pageNo
     * @return
     */
    List<CommonMessages> getMessageList(Long userId, Integer pageNo, String type);

    /**
     * 获取中央文案库
     * @param pageNo
     * @return
     */
    List<WalletCopywritingWithBLOBs> getCopywriting(String type, Integer pageNo);

    /**
     * 获取客服
     * @param type
     * @return
     */
    List<WalletCustomerServiceWithBLOBs> getCustomerService(String type);

}
