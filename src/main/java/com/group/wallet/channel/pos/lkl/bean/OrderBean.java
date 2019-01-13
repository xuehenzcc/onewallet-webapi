package com.group.wallet.channel.pos.lkl.bean;


/**
 * 
*    
* 项目名称：rsrpaydemo   
* 类名称：OrderBean   
* 类描述：创建订单   
* 创建人：  
* 创建时间：2017年1月8日 下午2:13:24   
* @version 1.0   
*
 */
public class OrderBean extends OrderBaseBean {

	

	/**
	 * 支付结果回调地址
	 */
	private String notifyUrl;
	
	
	/**
	 * 反扫授权码
	 */
	private String authcode;
	/**
	 * 扫码方式
	 * 1正扫（用户扫商户二维码），2反扫
	 */
	private String scantype;
	
	private double rate;
	
	private String subject;
	
	
	private String channelid;
	
	
	
	
	
	public String getChannelid()
    {
        
        return channelid;
    }
    public void setChannelid(String channelid)
    {
        
        this.channelid = channelid;
    }
    public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public double getRate()
    {
        
        return rate;
    }
	public void setRate(double rate)
    {
        
        this.rate = rate;
    }
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getScantype() {
		return scantype;
	}
	public void setScantype(String scantype) {
		this.scantype = scantype;
	}
	
	
	
	
	
	
}
