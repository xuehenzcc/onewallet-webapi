package com.group.wallet.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单
 * 
 * @author zcc
 * 20181222	
 */
public class ShopOrder {

//	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
//	  `order_num` VARCHAR(100) DEFAULT NULL COMMENT '订单号',
//	  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
//	  `unit_price` DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
//	  `count` INT(11) DEFAULT NULL COMMENT '数量',
//	  `total_price` DECIMAL(10,2) DEFAULT NULL COMMENT '总金额',
//	  `state` VARCHAR(10) DEFAULT NULL COMMENT '状态',
//	  `pay_way` VARCHAR(10) DEFAULT NULL COMMENT '付款方式',
//	  `pay_time` DATETIME DEFAULT NULL COMMENT '付款时间',
//	  `express_company` VARCHAR(20) DEFAULT NULL COMMENT '快递公司',
//	  `express_number` VARCHAR(20) DEFAULT NULL COMMENT '快递单号',
//	  `receive_man` VARCHAR(20) DEFAULT NULL COMMENT '收货人',
//	  `link_tel` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
//	  `receive_address` VARCHAR(50) DEFAULT NULL COMMENT '收货地址',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	
	
	private Long id;
	private String orderNum;
	private Long userId;
	private Long shopId;
	private String shopImage;
	private String shopName;
	private BigDecimal unitPrice;
	private Integer count;
	private BigDecimal totalPrice;
	private String state;
	private String shopType;//商品类型（0展业1大pos2智能pos3小pos）
	private String payWay;
	private Date payTime;
	private String expressCompany;//快递公司
	private String expressNumber;//快递单号
	private String receiveMan;//收件人
	private String linkTel;//联系电话
	private String receiveAddress;//收货地址
	private Date createTime;
	
	
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getShopImage() {
		return shopImage;
	}
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getReceiveMan() {
		return receiveMan;
	}
	public void setReceiveMan(String receiveMan) {
		this.receiveMan = receiveMan;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
