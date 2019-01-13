package com.group.wallet.channel.pos.lkl.bean;

public class FastpayOrderTransBean extends BaseBean {

	/**
	 * 短信验证码
	 */
	private String smscode;
	/**
	 * 收款商户编号
	 */
	protected String app_merch_no;
	/**
	 * 应用内唯一订单编号
	 */
	protected String app_order_no;
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
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
	
	
}
