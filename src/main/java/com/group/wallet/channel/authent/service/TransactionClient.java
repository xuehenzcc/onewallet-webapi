package com.group.wallet.channel.authent.service;

import com.group.wallet.channel.authent.common.LogLevel;
import com.group.wallet.channel.authent.common.SslConnection;
import com.group.wallet.channel.authent.common.Toolkit;
import com.group.wallet.channel.authent.encrypt.MD5;
import com.group.wallet.channel.authent.encrypt.RSAProvider;
import com.group.wallet.channel.authent.util.ApiV2Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Timer;


public class TransactionClient {

	private String url = "";
	private String merchantNo = "";
	private String terminalNo = "";
	private String merchantPwd = "";
	private String serverCert = "";
	private int timeout = 120000;
	private int contentLength = 6;

	private static Timer timer = null;
	private static boolean started = false;

	/**
	 * 测试商户系统到易联服务器是否连通
	 * @param acqSsn 系统跟踪号
	 * @param encryptKey 随机加密密钥
	 * @return
	 * @throws Exception
	 */
	public SimpleXml connectionTest(String acqSsn, String encryptKey) throws Exception{
		SimpleXmlImpl request = new SimpleXmlImpl();
		request.setVersion("2.1.0");
		request.setProcCode("0800");
		request.setAcqSsn(acqSsn);
		request.setTransDatetime(Toolkit.yyyyMMddHHmmss(new Date()));
		request.setMerchantNo(merchantNo);
		request.setTerminalNo(terminalNo);
		return sender(request, encryptKey);
	}

	/**
	 * 认证服务
	 * @param xml
	 * @param tripleDesKey
	 * @return
	 * @throws Exception
	 */
	public AuthentXmlImpl authent(AuthentXmlImpl xml, String tripleDesKey) throws Exception {
		xml.setProcCode("0100");
		xml.setVersion("2.1.5");
		xml.setProcessCode("300016");

		xml.setMerchantNo(merchantNo);
		xml.setTerminalNo(terminalNo);
		xml.setAcqSsn(Toolkit.HHmmss(new Date()));
		xml.setTransDatetime(Toolkit.yyyyMMddHHmmss(new Date()));

		return (AuthentXmlImpl)transact(xml, tripleDesKey);
	}


	/**
	 * 认证服务
	 * @param acqSsn 系统跟踪号
	 * @param encryptKey 3DES密钥
	 * @param transactType 认证类型
	 * @param address 户籍地址
	 * @param accountno 银行卡号
	 * @param idcardno 身份证号
	 * @param name 姓名
	 * @param mobile 手机号
	 * @return
	 * @throws Exception
	 */
	public AuthentXmlImpl authent(String acqSsn, String encryptKey,
								  String transactType, String address, String accountno,
								  String idcardno, String name, String mobile) throws Exception {
		AuthentXmlImpl xml = new AuthentXmlImpl();
		xml.setVersion("2.1.5");
		xml.setProcCode("0100");
		xml.setProcessCode("300016");
		xml.setAcqSsn(acqSsn);
		xml.setMerchantNo(merchantNo);
		xml.setTransactType(transactType);
		xml.setCustAddress(address);
		xml.setAccountNo(accountno);
		xml.setIDCardNo(idcardno);
		xml.setIDCardName(name);
		xml.setMobileNo(mobile);
		xml.setTransDatetime(Toolkit.yyyyMMddHHmmss(new Date()));
		xml.setTerminalNo(terminalNo);
		xml.setIDCardType("01");

		AuthentXmlImpl result = (AuthentXmlImpl)transact(xml, encryptKey);

		return result;
	}

	public AuthentXmlImpl authent(String acqSsn, String encryptKey,
								  String transactType, String address, String accountNo,
								  String idcardNo, String name, String mobile, String authorCode) throws Exception {
		AuthentXmlImpl xml = new AuthentXmlImpl();
		xml.setVersion("2.1.5");
		xml.setProcCode("0100");
		xml.setProcessCode("300016");
		xml.setAcqSsn(acqSsn);
		xml.setMerchantNo(merchantNo);
		xml.setTransactType(transactType);
		xml.setCustAddress(address);
		xml.setAccountNo(accountNo);
		xml.setIDCardNo(idcardNo);
		xml.setIDCardName(name);
		xml.setMobileNo(mobile);
		xml.setTransDatetime(Toolkit.yyyyMMddHHmmss(new Date()));
		xml.setTerminalNo(terminalNo);
		xml.setIDCardType("01");
		xml.setDeviceData(authorCode);

		AuthentXmlImpl result = (AuthentXmlImpl)transact(xml, encryptKey);

		return result;
	}

	/**
	 * Plugin Test
	 * @param acqSsn
	 * @param encryptKey
	 * @param checkType
	 * @param mobileNo
	 * @param idcardNo
	 * @param idcardType
	 * @param name
	 * @param authorCode
	 * @param imsi
	 * @return
	 * @throws Exception
	 */
	public AuthentXmlImpl plugin(String acqSsn, String encryptKey, String checkType,
								 String mobileNo, String idcardNo, String idcardType, String name,
								 String authorCode, String imsi) throws Exception {
		AuthentXmlImpl xml = new AuthentXmlImpl();
		xml.setVersion("2.1.0");
		xml.setProcCode("0100");
		xml.setProcessCode("300003");
		xml.setAcqSsn(acqSsn);
		xml.setMerchantNo(merchantNo);
		xml.setCheckType(checkType);
		xml.setMobileNo(mobileNo);
		xml.setIDCardNo(idcardNo);
		xml.setIDCardType(idcardType);
		xml.setIDCardName(name);
		xml.setAuthorCode(authorCode);
		xml.setImsi(imsi);
		xml.setTransDatetime(Toolkit.yyyyMMddHHmmss(new Date()));
		xml.setTerminalNo(terminalNo);

		AuthentXmlImpl result = (AuthentXmlImpl)transact(xml, encryptKey);

		return result;
	}

	/**
	 *
	 * @param xml 交易请求报文
	 * @param encryptKey 3DES密钥
	 * @return 交易响应报文
	 * @throws Exception
	 */
	private SimpleXml transact(SimpleXml xml, String encryptKey) throws Exception {
		BaseXml request = (BaseXml)xml;
		String input = ApiV2Util.getMACString(request) + " " + merchantPwd;
		MD5 md5 = new MD5();
		request.setMAC(md5.getMD5ofStr(input));

		SimpleXml result = null;
		try {
			result = sender(request, encryptKey);
		} catch (Exception ex) {
			Toolkit.writeLog(TransactionClient.class.getName(), "Transact", ex);
			throw ex;
		}

		String rsInput = (result == null ? "" : (ApiV2Util.getMACString(result) + " " + merchantPwd));
		if (result == null || !((BaseXml)result).getMAC().toUpperCase().equals(md5.getMD5ofStr(rsInput)))
		{
			throw new Exception("返回结果MAC校验失败");
		}

		return result;
	}

	/**
	 * 发送请求报文
	 * @param request 请求报文原文
	 * @param encryptKey 3DES密钥
	 * @return 响应报文原文
	 * @throws Exception
	 */
	private SimpleXml sender(SimpleXml request, String encryptKey) throws Exception {
		try {
			// 发送连接测试报文
//    		try {
//    			if(!started){
//    				started = true;
//    				timer = new Timer();
//    				SystemMonitor monitor = new SystemMonitor(url, merchantNo, merchantPwd, terminalNo);
//    				timer.scheduleAtFixedRate(monitor, monitor.getFirstTime(), monitor.getPeriod());
//    			}
//			} catch (Exception e) {
//				Toolkit.writeLog(TransactionClient.class.getName(), "monitor error", e);
//			}

			Toolkit.writeLog(TransactionClient.class.getName(), "transact.send", url + "|" + timeout + "|" + request.toString());
			HttpURLConnection connect = null;
			if (!url.contains("https:")) {
				URL urlConnect = new URL(url);
				connect = (HttpURLConnection) urlConnect.openConnection();
			} else {
				SslConnection urlConnect = new SslConnection();
				connect = (HttpURLConnection) urlConnect.openConnection(url);
			}

			connect.setReadTimeout(timeout);
			connect.setConnectTimeout(timeout);
			connect.setRequestMethod("POST");
			connect.setDoInput(true);
			connect.setDoOutput(true);
			connect.setRequestProperty("content-type", "text/html;charset=utf-8");
//			connect.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");

			String xml = request.toString();
			Toolkit.writeLog(TransactionClient.class.getName(), "transact.xml\n", xml);
			RSAProvider rsa = new RSAProvider();
			xml = rsa.sign(encryptKey, xml, this.getServerCert());
			xml = Toolkit.padLeft(xml.length() + "", contentLength) + xml;
			Toolkit.writeLog(TransactionClient.class.getName(), "transact.sign", xml);

			connect.connect();
			BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());

			out.write(xml.getBytes("UTF-8"));
			out.flush();
			out.close();

			BufferedInputStream in = new BufferedInputStream(connect.getInputStream());
			byte[] bts = new byte[10000000];
			int totalLen = 0, len = 0;
			while ((len = in.read(bts, totalLen, 1000)) != -1) {
				totalLen += len;
			}

			String result = Toolkit.toString(new String(bts, "UTF-8"));
			Toolkit.writeLog(TransactionClient.class.getName(), "transact.result", result);
			System.out.println("contentLength: " + contentLength);
			result = rsa.verify(encryptKey, result.substring(contentLength));
			Toolkit.writeLog(TransactionClient.class.getName(), "transact.verify", result);
			return ApiV2Util.toSimpleXml(result);
		} catch (Exception e) {
			Toolkit.writeLog(LogLevel.ERROR, TransactionClient.class.getName(), e.getMessage());
			throw e;
		}
	}

	public TransactionClient() {

	}

	public String getUrl() {
		return url;
	}

	/**
	 *
	 * @param url 服务器地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 *
	 * @param merchantNo （由易联提供的）商户号
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	/**
	 *
	 * @param terminalNo 商户终端号
	 */
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getMerchantPwd() {
		return merchantPwd;
	}

	/**
	 *
	 * @param merchantPwd 商户密钥（用于生成交易报文的MAC)
	 */
	public void setMerchantPwd(String merchantPwd) {
		this.merchantPwd = merchantPwd;
	}

	public String getServerCert() {
		return serverCert;
	}

	/**
	 *
	 * @param serverCert 平台CA证书公钥（用于对报文密钥进行RSA加密）
	 */
	public void setServerCert(String serverCert) {
		this.serverCert = serverCert;
	}

	/**
	 *
	 * @param url 服务器地址
	 */
	public TransactionClient(String url) {
		this.url = url;
	}
}
