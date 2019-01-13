package com.group.wallet.model;

import com.group.core.config.MyWebAppConfig;
import com.group.core.model.BaseEntity2;
import com.group.wallet.model.enums.UserCreditType;
import com.group.wallet.model.enums.UserType;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

public class WalletUserInfo extends BaseEntity2{

    private String phone;

    private String loginPwd;

    private Long recommonId;

    private String number;

    private String userType;

    @Transient
    private String userTypeName;

    private String creditType;

    @Transient
    private String creditTypeName;

    private String merchantType;

    private String nickName;

    private String realName;

    private String idCard;

    private String indate;

    private String authenticated;

    private String wxNo;

    private String wxQrcode;

    private BigDecimal accountBalance;

    private BigDecimal profitBalance;

    private String settleCardNo;

    private String settleName;

    private String settleBank;

    private String settleProvince;

    private String settleCity;

    private String settleBranchBank;

    private String settlePhone;

    private String headImage;

    //隐私开关
    private String privacy;

    private String settleCardZScan;

    private String settleCardFScan;

    private String idCardZImage;

    private String idCardFImage;

    private String handIdcardImage;

    private String creditCardZImage;

    private String handCreditImage;

    private String rechargeReceipt;

    private Integer isAuth;

    private String activityImage;

    private String sn;

    private String state;

    private String rejectReason;

    private String authority;

    private Date createTime;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public Long getRecommonId() {
        return recommonId;
    }

    public void setRecommonId(Long recommonId) {
        this.recommonId = recommonId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate == null ? null : indate.trim();
    }

    public String getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(String authenticated) {
        this.authenticated = authenticated == null ? null : authenticated.trim();
    }

    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo == null ? null : wxNo.trim();
    }

    public String getWxQrcode() {
        return wxQrcode;
    }

    public void setWxQrcode(String wxQrcode) {
        this.wxQrcode = wxQrcode == null ? null : wxQrcode.trim();
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getProfitBalance() {
        return profitBalance;
    }

    public void setProfitBalance(BigDecimal profitBalance) {
        this.profitBalance = profitBalance;
    }

    public String getSettleCardNo() {
        return settleCardNo;
    }

    public void setSettleCardNo(String settleCardNo) {
        this.settleCardNo = settleCardNo == null ? null : settleCardNo.trim();
    }

    public String getSettleName() {
        return settleName;
    }

    public void setSettleName(String settleName) {
        this.settleName = settleName == null ? null : settleName.trim();
    }

    public String getSettleBank() {
        return settleBank;
    }

    public void setSettleBank(String settleBank) {
        this.settleBank = settleBank == null ? null : settleBank.trim();
    }

    public String getSettleProvince() {
        return settleProvince;
    }

    public void setSettleProvince(String settleProvince) {
        this.settleProvince = settleProvince == null ? null : settleProvince.trim();
    }

    public String getSettleCity() {
        return settleCity;
    }

    public void setSettleCity(String settleCity) {
        this.settleCity = settleCity == null ? null : settleCity.trim();
    }

    public String getSettleBranchBank() {
        return settleBranchBank;
    }

    public void setSettleBranchBank(String settleBranchBank) {
        this.settleBranchBank = settleBranchBank == null ? null : settleBranchBank.trim();
    }

    public String getSettlePhone() {
        return settlePhone;
    }

    public void setSettlePhone(String settlePhone) {
        this.settlePhone = settlePhone == null ? null : settlePhone.trim();
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy == null ? null : privacy.trim();
    }

    public String getSettleCardZScan() {
        return settleCardZScan;
    }

    public void setSettleCardZScan(String settleCardZScan) {
        this.settleCardZScan = settleCardZScan == null ? null : settleCardZScan.trim();
    }

    public String getSettleCardFScan() {
        return settleCardFScan;
    }

    public void setSettleCardFScan(String settleCardFScan) {
        this.settleCardFScan = settleCardFScan == null ? null : settleCardFScan.trim();
    }

    public String getIdCardZImage() {
        return idCardZImage;
    }

    public void setIdCardZImage(String idCardZImage) {
        this.idCardZImage = idCardZImage == null ? null : idCardZImage.trim();
    }

    public String getIdCardFImage() {
        return idCardFImage;
    }

    public void setIdCardFImage(String idCardFImage) {
        this.idCardFImage = idCardFImage == null ? null : idCardFImage.trim();
    }

    public String getHandIdcardImage() {
        return handIdcardImage;
    }

    public void setHandIdcardImage(String handIdcardImage) {
        this.handIdcardImage = handIdcardImage == null ? null : handIdcardImage.trim();
    }

    public String getCreditCardZImage() {
        return creditCardZImage;
    }

    public void setCreditCardZImage(String creditCardZImage) {
        this.creditCardZImage = creditCardZImage == null ? null : creditCardZImage.trim();
    }

    public String getHandCreditImage() {
        return handCreditImage;
    }

    public void setHandCreditImage(String handCreditImage) {
        this.handCreditImage = handCreditImage == null ? null : handCreditImage.trim();
    }

    public String getRechargeReceipt() {
        return rechargeReceipt;
    }

    public void setRechargeReceipt(String rechargeReceipt) {
        this.rechargeReceipt = rechargeReceipt == null ? null : rechargeReceipt.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getUserTypeName() {
        return UserType.getEnumName(userType);
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getCreditTypeName() {
        if(StringUtils.isNotEmpty(creditType)){
            return UserCreditType.getEnumName(creditType);
        }
        return creditTypeName;
    }

    public void setCreditTypeName(String creditTypeName) {
        this.creditTypeName = creditTypeName;
    }

    public String getActivityImage() {
        return activityImage;
    }

    public void setActivityImage(String activityImage) {
        this.activityImage = activityImage;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }
}