package com.group.wallet.channel.pos.lkl.rsa;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

/**
 * RSA私钥加、解密
 * 
 * @author boc
 * @className RSAEncrypt.java
 * @createDate 2014-11-29
 * @copyright huangjie org
 * @Version 1.0
 */
public class RSAEncryptByPrivateKey extends RSAEncryptBase {
	private static RSAEncryptByPrivateKey instance = null;
	private static RSAPrivateKey rsaPrivateKey = null;
	private RSAEncryptByPrivateKey(String privateKey){
		rsaPrivateKey = loadPrivateKey(privateKey);
	}
	
	private RSAEncryptByPrivateKey(FileInputStream privateKeyFIS){
		rsaPrivateKey = loadPrivateKey(privateKeyFIS);
	}
	
	/**
	 * 获取一个RSA私钥加密对象
	 * @param privateKey 私钥字符串
	 * @return
	 */
	public static RSAEncryptByPrivateKey getInstance(String privateKey){
		if(instance == null) {
			instance = new RSAEncryptByPrivateKey(privateKey);
		}
		return instance;
	}
	
	/**
	 * 获取一个RSA私钥加密对象
	 * @param privateKeyFIS 私钥文件对象
	 * @return
	 */
	public static RSAEncryptByPrivateKey getInstance(FileInputStream privateKeyFIS){
		if(instance == null) {
			instance = new RSAEncryptByPrivateKey(privateKeyFIS);
		}
		return instance;
	}

	/**
	 * 私钥加密
	 * 
	 * @param str 被加密的明文串
	 * @return 加密后的Base64串
	 */
	public String encryptPrivateRSA(String str) {
		try {
			byte[] data = str.getBytes(CHARSET);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = encryptByKey(data, offSet, MAX_ENCRYPT_BLOCK,
							rsaPrivateKey);
				} else {
					cache = encryptByKey(data, offSet, inputLen - offSet,
							rsaPrivateKey);
				}

				out.write(cache, 0, cache.length);
				offSet = (++i) * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			return base64Encode(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
     * 私钥解密
     * @param str 被公钥加密后的BASE64串 
	 * @return 解密后的明文串
     */  
    public String decryptPrivateRSA(String str){
        try {
	        	byte[] encryptedData = base64Decode(str);
        		int inputLen = encryptedData.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = decryptByKey(encryptedData, offSet, MAX_DECRYPT_BLOCK,rsaPrivateKey);
	            } else {
	                cache = decryptByKey(encryptedData, offSet, inputLen - offSet,rsaPrivateKey);
	            }
	            out.write(cache, 0, cache.length);
	            offSet = (++i) * MAX_DECRYPT_BLOCK;
	        }
	        
	        byte[] decryptedData = out.toByteArray();
			return new String(decryptedData, CHARSET);
        } catch (Exception e) {
        		return "";
        }       
    }
    
    /**
     * 用私钥对信息生成数字签名
     * @param base64String
     * @return
     */
    public String sign(String base64String){
		try {
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(rsaPrivateKey);
			signature.update(base64Decode(base64String));
			return base64Encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
