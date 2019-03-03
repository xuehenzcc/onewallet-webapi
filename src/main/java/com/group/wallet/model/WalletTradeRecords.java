package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class WalletTradeRecords extends BaseEntity2{

    private String app;

    private String orderNo;

    private String serialNo;

    //用户id
    private Long userId;

    //商家id
    private String merchantId;

    //店铺id
    private Long storeId;

    //收银员id
    private Long cashierId;

    //台卡id
    private Long qrcodeId;

    //会员id
    private Long associatorId;

    //信用卡id（非结算卡）
    private Long bankCardId;

    //交易时间
    private Date tradeTime;

    //交易金额
    private BigDecimal tradeMoney;

    //实际支付金额
    private BigDecimal realTradeMoney;

    //折扣金额
    private BigDecimal discountMoney;

    //费率
    private BigDecimal rate;

    //商户手续费
    private BigDecimal merchantPoundage;

    //平台手续费
    private BigDecimal platformPoundage;

    //到账金额
    private BigDecimal arrivalAmount;

    //退款金额
    private BigDecimal refundMoney;

    private String goodsName;

    private String scanType;

    private String payWay;

    //付款时间
    private Date payTime;

    //交易状态
    private String tradeState;

    private Integer isSettled;

    private String settleType;

    //渠道id
    private Long channelId;

    //渠道名称
    @Column(name="(select name from wallet_channel where id = channel_id) channelName", insertable=false)
    private String channelName;

    //渠道编码
    private String routeNo;

    //渠道商户编号
    private String routeMerchantNo;

    //渠道订单号
    private String routeOrderNo;

    //渠道流水号
    private String routeSerialNo;

    //渠道手续费
    private BigDecimal routePoundage;

    private String failreason;

    //经度
    private Double lon;

    //纬度
    private Double lat;

    //交易详细地址
    private String tradeAddress;

    private String remarks;
    
    private String batch;//批次号
    private Date businessTime;//交易时间

    
    
    public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public Long getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(Long qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    public Long getAssociatorId() {
        return associatorId;
    }

    public void setAssociatorId(Long associatorId) {
        this.associatorId = associatorId;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public BigDecimal getRealTradeMoney() {
        return realTradeMoney;
    }

    public void setRealTradeMoney(BigDecimal realTradeMoney) {
        this.realTradeMoney = realTradeMoney;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getMerchantPoundage() {
        return merchantPoundage;
    }

    public void setMerchantPoundage(BigDecimal merchantPoundage) {
        this.merchantPoundage = merchantPoundage;
    }

    public BigDecimal getPlatformPoundage() {
        return platformPoundage;
    }

    public void setPlatformPoundage(BigDecimal platformPoundage) {
        this.platformPoundage = platformPoundage;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType == null ? null : scanType.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState == null ? null : tradeState.trim();
    }

    public Integer getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Integer isSettled) {
        this.isSettled = isSettled;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType == null ? null : settleType.trim();
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo == null ? null : routeNo.trim();
    }

    public String getRouteMerchantNo() {
        return routeMerchantNo;
    }

    public void setRouteMerchantNo(String routeMerchantNo) {
        this.routeMerchantNo = routeMerchantNo == null ? null : routeMerchantNo.trim();
    }

    public String getRouteOrderNo() {
        return routeOrderNo;
    }

    public void setRouteOrderNo(String routeOrderNo) {
        this.routeOrderNo = routeOrderNo == null ? null : routeOrderNo.trim();
    }

    public String getRouteSerialNo() {
        return routeSerialNo;
    }

    public void setRouteSerialNo(String routeSerialNo) {
        this.routeSerialNo = routeSerialNo == null ? null : routeSerialNo.trim();
    }

    public BigDecimal getRoutePoundage() {
        return routePoundage;
    }

    public void setRoutePoundage(BigDecimal routePoundage) {
        this.routePoundage = routePoundage;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason == null ? null : failreason.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getTradeAddress() {
        return tradeAddress;
    }

    public void setTradeAddress(String tradeAddress) {
        this.tradeAddress = tradeAddress;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(BigDecimal arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}