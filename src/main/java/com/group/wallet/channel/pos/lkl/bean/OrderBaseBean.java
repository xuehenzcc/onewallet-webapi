package com.group.wallet.channel.pos.lkl.bean;
/**
 * 
*    
* 项目名称：rsrpaydemo   
* 类名称：OrderBaseBean   
* 类描述：订单中的一些公用属性   
* 创建人：
* 创建时间：2017年1月8日 下午2:17:24   
* @version 1.0   
*
 */
public class OrderBaseBean  extends BaseBean{
	/**
	 * 商户编号
	 */
	protected String app_merch_no;

	/**
	 * 应用内唯一订单编号
	 */
	protected String app_order_no;

	/**
	 * 本次交易金额，单位分
	 */
	protected long amount;
	/**
	 * 到账延时
	 * 0立即到账
	 * 1延时一天
	 */
	protected int arriveDelay;

	/**
	 * 支付方式代码，参见文档
	 * 
	 */
	protected int payType;
	/**
	 * 订单创建时间，可不传
	 */
	protected String order_create_time;
	/**
	 * 应用自定义字段
	 */
	protected String ext;
	
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
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getArriveDelay() {
		return arriveDelay;
	}
	public void setArriveDelay(int arriveDelay) {
		this.arriveDelay = arriveDelay;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
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
	
	
}
