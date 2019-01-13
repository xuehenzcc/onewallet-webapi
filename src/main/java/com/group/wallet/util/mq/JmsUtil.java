package com.group.wallet.util.mq;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.group.wallet.model.enums.IncomeType;
import com.group.wallet.service.wal.HomeService;

@Component
public class JmsUtil {

	@Autowired
	private HomeService homeService;
	
	
	@JmsListener(destination="wallet_profit_amt")
	public void receiveUploadQueue(String message){
		System.out.println("队列-wallet_profit_amt 收到上传交易数据批次号消息："+message);
		//执行分润
		if(null != message && !"".equals(message)){
			homeService.bachFenRun(message);
		}
	}
	
	@JmsListener(destination="test")
	public void receiveQueue(String message){
		System.out.println("收到消息："+message);
		String str[]=message.split(",");
		String type=str[0];
		String userId=str[1];
		String amt=str[2];
		String sn=str[3];
		if("A8".equals(type)){
			//获取用户层级关系--分别进行激活返现
			try {
				homeService.getUserRelation(Long.valueOf(userId),new BigDecimal(amt), sn);
			} catch (NumberFormatException e) {
				System.out.println("消费MQ异常：····"+e);
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("消费MQ异常：····"+e);
				e.printStackTrace();
			}
		}else if("A10".equals(type)){//退还保证金
			try {
				homeService.updateCalculateResult(IncomeType.激活返现, "退还保证金",Long.valueOf(userId),new BigDecimal(amt),sn);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("B2".equals(type)){//小POS未支付
			BigDecimal amount=new BigDecimal(amt).multiply(BigDecimal.valueOf(-1));
			try {
				homeService.updateCalculateResult(IncomeType.激活返现, "购买POS机",Long.valueOf(userId),amount,sn);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
