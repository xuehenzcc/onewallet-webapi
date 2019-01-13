package com.group.wallet.channel.pos.lkl.bean;


public class PayOrderPo {
	 //用户IP
	private String ip;
	//订单金额
	private String amount;
	//回调URL
	private String notifyUrl;
	//用户ID
	private int code;//如果升级就传公司特定的code
	//收款帐号  
	private int payemCode;//个人交易时 不需要传，升级的时候传升级人的code
    //支付终端
	private int terminal;//微信2    支付宝 1    银联4   京东16
	//支付方式
    private int payType;//T+1 1   D0 0
    
    private int termCode;//支付应用编号  金贝猫安卓 传1  IOS 传2
    
    private int upgrade;// 传 1  为升级交易
    
    private int upgradeLevel;//升级等级
    
    private int channelId;
    
    
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getUpgradeLevel() {
		return upgradeLevel;
	}
	public void setUpgradeLevel(int upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}
	public int getUpgrade() {
		return upgrade;
	}
	public void setUpgrade(int upgrade) {
		this.upgrade = upgrade;
	}
	public int getPayemCode() {
		return payemCode;
	}
	public void setPayemCode(int payemCode) {
		this.payemCode = payemCode;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
	public int getTermCode() {
		return termCode;
	}
	public void setTermCode(int termCode) {
		this.termCode = termCode;
	}
	public int getPayType(){
        return payType;
    }
    public void setPayType(int payType){
        this.payType = payType;
    }
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public int getTerminal() {
		return terminal;
	}
	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}

}
