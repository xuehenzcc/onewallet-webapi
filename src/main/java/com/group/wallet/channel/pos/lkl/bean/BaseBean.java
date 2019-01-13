package com.group.wallet.channel.pos.lkl.bean;

import com.alibaba.fastjson.JSON;
import com.group.wallet.channel.pos.lkl.config.LklPayConfig;
import com.group.wallet.channel.pos.lkl.rsa.RSAEncryptByPrivateKey;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseBean {

	protected String appid = LklPayConfig.APP_ID;
	protected String sign;
	public String getAppid() {
		return appid;
	}
	public String getSign() {
		return sign;
	}
	
	
	/**
	 * 将Javabean 转为 json格式的字符串
	 * @return
	 */
	private String getJsonFromBean(){
		Map map = toMap();
		String json = JSON.toJSONString(this);
		System.out.println(json);
		return json;
	}
	
	private Map toMap(){
		Map map = new HashMap();
		Class clazz = this.getClass();
		for(; clazz != Object.class; clazz = clazz.getSuperclass()){
			
			Field[] fields =clazz.getDeclaredFields();
			for(Field f : fields){
				f.setAccessible(true);
				try {
					Object obj  = f.get(this);
					if(null !=obj){
						String k = f.getName();
						map.put(k, obj);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return map;
	}
	/**
	 * 将当前对象加密，并生成json格式字符串
	 * @return
	 */
	public String requestParam(){
		String json = getJsonFromBean();
		//用私钥加密
		String _sign = RSAEncryptByPrivateKey.getInstance(LklPayConfig.APP_Private_Key).encryptPrivateRSA(json);
		this.sign = _sign;
		return getJsonFromBean();
	}
	/**
	 * 验证明文与加密数据是否一致
	 * @return
	 */
	public boolean verify(){
		if(sign==null||sign.length()==0){
			return false;
		}
		//私钥解密
		String data = RSAEncryptByPrivateKey.getInstance(LklPayConfig.APP_Private_Key).decryptPrivateRSA(sign);
		if("".equals(data)){
			//解密失败
			return false;
		}
		Map map = JSON.parseObject(data);
		if(null==map){
			
			return false;
		}
		//获取当前对象的所有属性
		Field[] fields = this.getClass().getDeclaredFields();
		 for (Field field : fields) {
			 //设置访问private属性权限
			 field.setAccessible(true);
			 try {
				Object obj  = field.get(this);
				//获取属性名
				String key = field.getName();
				//忽略对sign的校验
				if("sign".equals(key)){
					continue;
				}
				Object mapValue  = map.get(key);
				if(obj==null){
					if(mapValue!=null){
						return false;
					}
				}else{
					if(obj instanceof String){
						if(!obj.equals(mapValue))
							return false;
					}else if(obj instanceof Integer){
						int _v  = (Integer)obj;
						if(_v!=(Integer)mapValue){
							return false;
						}
					}else if(obj instanceof Long){
						long _v = (Long)obj;
						if(_v!= Long.valueOf(mapValue+"")){
							return false;
						}
					}else if (obj instanceof Double){
						double _v = (Double) obj;
						if(_v != Double.valueOf(mapValue+"")){
							return false;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
		 }
		return true;
	}
	
	
}
