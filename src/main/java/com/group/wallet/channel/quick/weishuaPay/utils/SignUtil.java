package com.group.wallet.channel.quick.weishuaPay.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 签名工具类
 * 
 * @className SignUtil
 * @Description
 * @author xuguiyi
 * @contact
 * @date 2016-6-7 下午11:11:00
 */
/*
public class SignUtil {
	

	*/
/**
	 * 根据map key升序排序
	 * @param sortedParams
	 * @return
	 *//*

	public static String getSign(Map<String, String> sortedParams, String signkey) throws Exception {
		
		StringBuffer signSrc = new StringBuffer();
		List<String> keys = new ArrayList<String>(sortedParams.keySet());
		Collections.sort(keys);
		int index = 0;
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = sortedParams.get(key);
			if (key != null && !"".equals(key) && value != null && !"sign".equals(key)) {
				signSrc.append(key + "=" + value);
				index++;
			}
		}
		
		String sign = DigestUtils.md5Hex(signSrc.toString() + signkey);
		
		return sign;
		
	}
	
	
	public static void main(String[] args) throws Exception {
	}
	
	
	

}
*/
