package com.group.wallet.channel.authent.service; /**
 * 
 */

import java.util.Date;
import java.util.TimerTask;

import com.group.wallet.channel.authent.common.Toolkit;

/**
 * @author cai.hongzhi
 *
 */
public class SystemMonitor extends TimerTask{
	
	private String url = "";
	private String merchantNo = "";
	private String merchantPwd = "";
	private String terminalNo = "";

	public SystemMonitor(String url, String merchantNo, String merchantPwd, String terminalNo) {
		this.url = url;
		this.merchantNo = merchantNo;
		this.merchantPwd = merchantPwd;
		this.terminalNo = terminalNo;
	}

	@Override
	public void run() {
		try {
			TransactionClient tc = new TransactionClient(url);
			tc.setServerCert(Toolkit.getPropertyFromFile(Toolkit.GDYILIAN_CERT_PUB_64));
			tc.setMerchantNo(merchantNo);
			tc.setTerminalNo(terminalNo);
			tc.setMerchantPwd(merchantPwd);
			
			tc.connectionTest(Toolkit.HHmmss(new Date()), Toolkit.random(24));
			
		} catch (Exception e) {
			Toolkit.writeLog(SystemMonitor.class.getName(), "", e);
		}
	}

	public long getPeriod() {
		long period = 5*60*1000;
		try {
			int periodMin = Integer.parseInt(Toolkit.getPropertyFromFile("Monitor_Period"));
			if(periodMin > 0){
				return periodMin * 60 * 1000;
			}
		} catch (Exception e) {}
		return period;
	}

	public Date getFirstTime() {
		return new Date();
	}

}
