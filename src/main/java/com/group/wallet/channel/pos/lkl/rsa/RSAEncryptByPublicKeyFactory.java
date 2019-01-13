package com.group.wallet.channel.pos.lkl.rsa;

import java.util.HashMap;
import java.util.Map;

public class RSAEncryptByPublicKeyFactory {
	private final static Map<String,RSAEncryptByPublicKey> container  = new HashMap<String, RSAEncryptByPublicKey>();
	
	public static RSAEncryptByPublicKey getInstance(String appid, String publicKey){
		RSAEncryptByPublicKey ins  = container.get(appid);
		if(ins ==null){
			synchronized (appid) {
				ins = container.get(appid);
				if(ins==null){
					ins  = new RSAEncryptByPublicKey(publicKey);
					container.put(appid, ins);
				}
			}
		}
		return ins;
	}
	
	
}
