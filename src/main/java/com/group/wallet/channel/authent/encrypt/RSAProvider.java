package com.group.wallet.channel.authent.encrypt;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.group.wallet.channel.authent.common.Toolkit;

public class RSAProvider {

	/**
	 * 版本号：RSA.3DES.MD5 或 RSA.3DES.MD5withRSA
	 * 商户发送的请求报文使用版本RSA.3DES.MD5
	 * 认证服务平台的响应报文使用版本RSA.3DES.MD5withRSA
	 */
	private String version = "";

	/**
	 * BASE64(签名公钥)
	 */
	private String cert = "";

	/**
	 * BASE64(RSA(报文加密密钥))
	 */
	private String keyEncrypt = "";

	/**
	 * 随机加密密钥
	 */
	private String key = "";

	/**
	 * BASE64(3DES(报文原文))
	 */
	private String srcEncrypt = "";

	/**
	 * 报文原文
	 */
	private String src = "";

	/**
	 * BASE64(MD5(报文原文))
	 */
	private String srcSign = "";

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public RSAProvider() {
		this.version = "RSA.3DES.MD5";
	}

	public RSAProvider(String version) {
		this.version = version;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyEncrypt() {
		return keyEncrypt;
	}

	public void setKeyEncrypt(String keyEncrypt) {
		this.keyEncrypt = keyEncrypt;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrcEncrypt() {
		return srcEncrypt;
	}

	public void setSrcEncrypt(String srcEncrypt) {
		this.srcEncrypt = srcEncrypt;
	}

	public String getSrcSign() {
		return srcSign;
	}

	public void setSrcSign(String srcSign) {
		this.srcSign = srcSign;
	}

	/**
	 * 密钥加密算法, 报文加密算法, 报文签名算法
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 对请求报文数据进行加密签名，加密后的请求报文体格式：
	 * BASE64(版本号))｜BASE64(签名公钥)|BASE64(RSA(报文加密密钥))|BASE64(3DES(报文原文))| BASE64(MD5(报文原文))
	 *
	 * @param cert 认证服务平台CA证书公钥，如果为空，不生成报文加密密钥．
	 * @param srcPara 报文原文
	 * @param keyPara 3DES密钥，如果为空自动随机生成
	 * @return 加密报文体
	 * @throws Exception
	 */
	public String sign(String keyPara, String srcPara, String cert) throws Exception {
		if (Toolkit.isNullOrEmpty(keyPara)) {
			keyPara = Toolkit.random(24);
		}
		this.setKey(keyPara);
		this.setSrc(srcPara);

		if (!Toolkit.isNullOrEmpty(cert)) { // 公钥为空，不加密密钥
			byte[] keyEnc = RSA.encrypt64(key.getBytes("UTF-8"), cert);
			this.setKeyEncrypt(Toolkit.base64Encode(keyEnc));
		}

		byte[] srcEnc = TripleDes.encrypt(key.getBytes("UTF-8"),
				src.getBytes("UTF-8"));
		this.setSrcEncrypt(Toolkit.base64Encode(srcEnc));

		MD5 md5 = new MD5();
		String strSign = md5.getMD5ofByte(src.getBytes("UTF-8"));
		this.setSrcSign(Toolkit.base64Encode(strSign.getBytes("UTF-8")));

		String tData = Toolkit.base64Encode(this.getVersion().getBytes("UTF-8"));
		tData += "|" + this.getCert();
		tData += "|" + this.getKeyEncrypt();
		tData += "|" + this.getSrcEncrypt();
		tData += "|" + this.getSrcSign();
		return tData;
	}

	/**
	 * 对响应报文进行解密验签，解密前的响应报文体格式：
	 * BASE64(版本号))｜BASE64(签名公钥)|BASE64(RSA(报文加密密钥))|BASE64(3DES(报文原文))|BASE64(MD5withRSA(报文原文))
	 *
	 * @param sign 加密报文体
	 * @param keyPara 3DES密钥，与请求报文使用同一密钥
	 * @return 报文原文
	 * @throws Exception
	 */
	public String verify(String keyPara, String sign) throws Exception {
		String[] values = sign.split("\\|");
		this.version = new String(Toolkit.base64Decode(values[0]), "UTF-8");
		this.setCert(values[1]);
		this.setKeyEncrypt(values[2]);
		this.setSrcEncrypt(values[3]);
		this.setSrcSign(values[4]);
		this.setKey(keyPara);

		byte[] srcEnc = Toolkit.base64Decode(this.getSrcEncrypt());
		byte[] srcSigned = Toolkit.base64Decode(this.getSrcSign());

		byte[] keyBt = keyPara.getBytes("UTF-8");
		byte[] srcBt = TripleDes.decrypt(keyBt, srcEnc);

		this.setSrc(new String(srcBt, "UTF-8"));

		if (this.getVersion().equals("RSA.3DES.MD5withRSA") && !Toolkit.isNullOrEmpty(cert)) {
			if (!RSA.verify(srcSigned, cert, src)) {
				throw new Exception("fail to verifySignedData");
			}
		} else {
			MD5 md5 = new MD5();
			String strSign = md5.getMD5ofByte(srcBt);
			if (!strSign.equals(new String(srcSigned, "UTF-8"))) {
				throw new Exception("fail to verifySignedData");
			}
		}

		return src;
	}

}
