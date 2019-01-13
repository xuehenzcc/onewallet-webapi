package com.group.wallet.channel.pos.lkl.rsa;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA加、解密基础类
 * 
 * @author boc
 * @className RSABase.java
 * @createDate 2014-11-29
 * @copyright huangjie org
 * @Version 1.0
 */
public class RSAEncryptBase {
	protected static final int MAX_ENCRYPT_BLOCK = 117;
	protected static final int MAX_DECRYPT_BLOCK = 128;
	protected static final String CHARSET = "UTF-8";
	protected static final String TRANSFORMATION = "RSA";
	protected static final String KEY_ALGORITHM = "RSA";
	/**
	 * RSA加密
	 * 
	 * @param data
	 * @param offSet
	 * @param inputLength
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	protected static byte[] encryptByKey(byte[] data, int offSet,
			int inputLength, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data, offSet, inputLength);
	}

	/**
	 * RSA 解密
	 * 
	 * @param data
	 * @param offSet
	 * @param inputLenth
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static byte[] decryptByKey(byte[] data, int offSet,
			int inputLenth, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data, offSet, inputLenth);
	}
	
	/** 
     * 从字符串中加载公钥 
     * @param publicKeyStr 公钥数据字符串 
     */  
	protected RSAPublicKey loadPublicKey(String publicKey){
        try {  
            byte[] buffer= base64Decode(publicKey);
            KeyFactory keyFactory= KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
        		e.printStackTrace();
        }
        return null;
    }
    

    /** 
     * 从文件中输入流中加载公钥 
     * @param in 公钥输入流 
     * @throws Exception 加载公钥时产生的异常
     */  
	protected RSAPublicKey loadPublicKey(InputStream in){
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String readLine= null;
            StringBuilder sb= new StringBuilder();
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            return loadPublicKey(sb.toString());  
        } catch (Exception e) {
        		e.printStackTrace();
        } 
        
        return null;
    }  
    
    /** 
     * 从字符串中加载私钥 
     * @param publicKeyStr 公钥数据字符串 
     */ 
	protected RSAPrivateKey loadPrivateKey(String privateKey){
        try {  
            byte[] buffer= base64Decode(privateKey); 
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory= KeyFactory.getInstance(KEY_ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace(); 
        } 
        return null;
    }
    
    /** 
     * 从文件中加载私钥 
     */  
	protected RSAPrivateKey loadPrivateKey(InputStream in){
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String readLine= null;
            StringBuilder sb= new StringBuilder();
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            return loadPrivateKey(sb.toString());  
        } catch (Exception e) {
        		e.printStackTrace();
        } 
        return null;
    }
	
	/**
	 * base64加密
	 * @param str
	 * @return
	 */
	protected static String base64Encode(byte []str){
		return new BASE64Encoder().encode(str);
	}
	
	/**
	 * base64解密
	 * @param str
	 * @return
	 */
	protected static byte[] base64Decode(String str) {
		try {
			return new BASE64Decoder().decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
