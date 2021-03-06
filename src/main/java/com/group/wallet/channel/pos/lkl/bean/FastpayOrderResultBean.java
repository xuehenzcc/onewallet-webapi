package com.group.wallet.channel.pos.lkl.bean;

/**
 * 
*    
* 项目名称：rsrpaydemo   
* 类名称：FastpayOrderResultBean   
* 类描述：   快捷支付订单结果
* 创建时间：2017年2月8日 下午2:12:03   
* @version 1.0   
*
 */
public class FastpayOrderResultBean extends FastpayOrderBaseBean {
	/**
	 * 支付平台交易流水号
	 */
	private String order_no;
	/**
	 * 支付结果代码
	 */
	private String payed_code;
	/**
	 * 支付完成时间
	 */
	private String paycomtime;
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getPayed_code() {
		return payed_code;
	}
	public void setPayed_code(String payed_code) {
		this.payed_code = payed_code;
	}
	public String getPaycomtime() {
		return paycomtime;
	}
	public void setPaycomtime(String paycomtime) {
		this.paycomtime = paycomtime;
	}
}
