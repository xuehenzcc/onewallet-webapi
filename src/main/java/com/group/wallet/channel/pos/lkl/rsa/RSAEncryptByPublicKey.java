package com.group.wallet.channel.pos.lkl.rsa;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA公钥加、解密
 * @author boc
 * @className RSAEncryptByPublicKey.java
 * @createDate 2014-11-29
 * @copyright huangjie org
 * @Version 1.0
 */
public class RSAEncryptByPublicKey extends RSAEncryptBase {
	
	
	
	private RSAPublicKey rsaPublicKey = null;
	public RSAEncryptByPublicKey(String publicKey){
		rsaPublicKey = loadPublicKey(publicKey);
	}
	
	public RSAEncryptByPublicKey(FileInputStream publicKeyFIS){
		rsaPublicKey = loadPublicKey(publicKeyFIS);
	}
	
	
	
	/**
	 * 公钥加密
	 * @param str 被加密的明文串
	 * @return 加密后的BASE64串
	 */
	public String encryptPublicRSA(String str) {
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
							rsaPublicKey);
				} else {
					cache = encryptByKey(data, offSet, inputLen-offSet,
							rsaPublicKey);
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
	 * 公钥解密
	 * @param str 被私钥加密后的BASE64串
	 * @return 解密后的明文串
	 */
	public String decryptPublicRSA(String str) {
        try {
        		byte[] encryptedData = base64Decode(str);
        		int inputLen = encryptedData.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = decryptByKey(encryptedData, offSet, MAX_DECRYPT_BLOCK,rsaPublicKey);
	            } else {
	                cache = decryptByKey(encryptedData, offSet, inputLen - offSet,rsaPublicKey);
	            }
	            out.write(cache, 0, cache.length);
	            offSet = (++i) * MAX_DECRYPT_BLOCK;
	        }
	        
	        byte[] decryptedData = out.toByteArray();
			return new String(decryptedData, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        		return "";
        }
	}
	
	/**
	 * 验证签名是否正常
	 * @param sign
	 * @param base64String
	 * @return
	 */
	public boolean verify(String sign, String base64String){
		try {
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(rsaPublicKey);
			signature.update(base64Decode(base64String));
			return signature.verify(base64Decode(sign));
		} catch (Exception e) {
			return false;
		}
	}
	
}
