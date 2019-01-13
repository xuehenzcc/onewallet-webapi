package com.group.wallet.channel.pos.lkl.bean;

public class FastpayOrderBaseBean extends BaseBean {

	/**
	 * 收款商户编号
	 */
	protected String app_merch_no;
	/**
	 * 应用内唯一订单编号
	 */
	protected String app_order_no;
	
	/**
	 * 订单创建时间，可不传
	 */
	protected String order_create_time;
	/**
	 * 应用自定义字段
	 */
	protected String ext;
	
	protected double rate;
	/**
	 * 商品描述
	 */
	protected String subject;
	/**
	 * 银行卡卡号
	 */
	protected String card_no;
	/**
	 * 银行卡类型
	 * 00信用卡
		01借记卡

	 */
	protected String cardtype;
	protected String cardholder;
	protected String expiredate;
	protected String cvv2;
	protected String id_no;
	protected String bank_branch_nbk_code;
	protected String mobile_no;
	public String getApp_merch_no() {
		return app_merch_no;
	}
	public void setApp_merch_no(String app_merch_no) {
		this.app_merch_no = app_merch_no;
	}
	public String getApp_order_no() {
		return app_order_no;
	}
	public void setApp_order_no(String app_order_no) {
		this.app_order_no = app_order_no;
	}
	public String getOrder_create_time() {
		return order_create_time;
	}
	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getBank_branch_nbk_code() {
		return bank_branch_nbk_code;
	}
	public void setBank_branch_nbk_code(String bank_branch_nbk_code) {
		this.bank_branch_nbk_code = bank_branch_nbk_code;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	
	
	
}
