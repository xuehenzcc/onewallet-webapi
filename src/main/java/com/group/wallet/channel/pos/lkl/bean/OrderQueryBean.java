package com.group.wallet.channel.pos.lkl.bean;

public class OrderQueryBean extends BaseBean {

	/**
	 * 商户订单号
	 */
	private String app_order_no;
	/**
	 * 当前应用商户唯一编号
	 */
	protected String app_merch_no;

	public String getApp_order_no() {
		return app_order_no;
	}

	public void setApp_order_no(String app_order_no) {
		this.app_order_no = app_order_no;
	}

	public String getApp_merch_no() {
		return app_merch_no;
	}

	public void setApp_merch_no(String app_merch_no) {
		this.app_merch_no = app_merch_no;
	}
	
	
}
