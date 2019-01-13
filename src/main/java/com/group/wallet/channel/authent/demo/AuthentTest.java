package com.group.wallet.channel.authent.demo;


import com.group.wallet.channel.authent.common.Toolkit;
import com.group.wallet.channel.authent.service.AuthentXmlImpl;
import com.group.wallet.channel.authent.service.TransactionClient;

/**
 * 认证服务测试客户端
 * @author pangxingyi
 *
 */
public class AuthentTest {

	public static void main(String[] args) throws Exception {
		String url = "http://test.payeco.com:9080/services/ApiV2ServerRSA"; // 测试环境地址

		TransactionClient transClient = new TransactionClient();
		transClient.setUrl(url);
		transClient.setServerCert("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJ1fKGMV/yOUnY1ysFCk0yPP4bfOolC/nTAyHmoser+1yzeLtyYsfitYonFIsXBKoAYwSAhNE+ZSdXZs4A5zt4EKoU+T3IoByCoKgvpCuOx8rgIAqC3O/95pGb9n6rKHR2sz5EPT0aBUUDAB2FJYjA9Sy+kURxa52EOtRKolSmEwIDAQAB");
		transClient.setMerchantNo("1477361246764");
		transClient.setTerminalNo("02028828");
		transClient.setMerchantPwd("123456");

		String tripleDesKey = Toolkit.random(24); // 交易报文密钥, 随机生成, 用于加密解密报文
		AuthentXmlImpl reqXml = new AuthentXmlImpl();
		String transactType = "31"; // 认证类型 11 12 13 14 21 31 41 51 ...

		reqXml.setTransactType(transactType);

		if ("11".equals(transactType) || "12".equals(transactType) || "14".equals(transactType) || "71".equals(transactType)) {
			reqXml.setIDCardNo("440102196304264425");
			reqXml.setIDCardType("01");
			reqXml.setIDCardName("张伟");
		} else if ("13".equals(transactType)) {
			reqXml.setIDCardNo("440106196911211822");
			reqXml.setIDCardType("01");
			reqXml.setIDCardName("李毅涛");
			reqXml.setCustAddress("广东省广州市林和西路");
		} else if ("21".equals(transactType)) {
			reqXml.setMobileNo("13560084890");
			reqXml.setIDCardNo("440105198812072727");
			reqXml.setIDCardType("01");
		} else if ("22".equals(transactType)) {
			reqXml.setMobileNo("13560084890");
			reqXml.setIDCardName("李伟");
			reqXml.setDeviceData("asdfgh");
		} else if ("23".equals(transactType)) {
			reqXml.setMobileNo("13560084890");
			reqXml.setIDCardNo("411221199312062149");
			reqXml.setIDCardType("01");
			reqXml.setIDCardName("李伟");
			reqXml.setDeviceData("");
		} else if ("31".equals(transactType) || "32".equals(transactType)) {
			reqXml.setAccountNo("62284829381037299");
			reqXml.setMobileNo("13560084824");
			reqXml.setIDCardNo("411221199312062149");
			reqXml.setIDCardType("01");
			reqXml.setIDCardName("李伟");
		} else if ("41".equals(transactType)) {
			reqXml.setMobileNo("18664913883");
			reqXml.setCustAddress("20160815_深圳市|20160718_广州市");
		} else if ("51".equals(transactType)) {
			reqXml.setAccountNo("6226621203662737");
		} else if ("61".equals(transactType)) {
			reqXml.setAccountNo("440102196304264425");
			reqXml.setMobileNo("13560084824");
			reqXml.setIDCardNo("411221199312062149");
			reqXml.setIDCardType("01");
			reqXml.setIDCardName("李伟");
			reqXml.setCustAddress("广东省广州市林和西路");
		} else if ("62".equals(transactType)) {
			reqXml.setIDCardNo("411221199312062149");
			reqXml.setIDCardType("01");
		} else if ("63".equals(transactType)) {
			reqXml.setAccountNo("440102196304264425");
			reqXml.setIDCardNo("411221199312062149");
			reqXml.setIDCardType("01");
		} else {

		}

		AuthentXmlImpl respXml = transClient.authent(reqXml, tripleDesKey);

		System.out.println(respXml);
	}

}
