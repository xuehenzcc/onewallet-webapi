package com.group.wallet.channel.pos.lkl.bean;
/**
 * 
*    
* 项目名称：rsrpaydemo   
* 类名称：FastpayOrderBean   
* 类描述： 创建预付单  
* 创建时间：2017年2月8日 下午2:10:26   
* @version 1.0   
*
 */
public class FastpayOrderBean extends FastpayOrderBaseBean {

	/**
	 * 支付结果回调地址
	 */
	private String notifyUrl;
	private Long amount;

	/**
	 * 1短信验证码支付
	 * 2链接方式
	 */
	private String payType;
	
	private int arriveDelay;
	
	private String channelid;
	
	
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

    public void setAmount(long l)
    {
        
        this.amount = l;
    }
    public Long getAmount()
    {
        
        return amount;
    }

  

    public String getPayType()
    {
        
        return payType;
    }

    public void setPayType(String payType)
    {
        
        this.payType = payType;
    }

    public int getArriveDelay()
    {
        
        return arriveDelay;
    }

    public void setArriveDelay(int arriveDelay)
    {
        
        this.arriveDelay = arriveDelay;
    }

    public void setAmount(Long amount)
    {
        
        this.amount = amount;
    }

    public String getChannelid()
    {
        
        return channelid;
    }

    public void setChannelid(String channelid)
    {
        
        this.channelid = channelid;
    }
	
	
}
