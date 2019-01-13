package com.group.wallet.channel.pos.lkl.rsa;


/*
 * 文件名：RSAEncryptVerify.java
 * 版权：Copyright by www.rsrtech.net
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年4月13日
 */


import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public class RSAEncryptVerify
{
   
    /**
     * 将当前对象加密，并生成json格式字符串
     * @param t 
     *          加密对象JavaBean
     * @param APP_Private_Key
     *          私钥
     * @return
     */
    /*  public static <T extends Object> String requestParam(T t, String APP_Private_Key)
    {
        //用私钥加密
        String _sign = RSAEncryptByPrivateKey.getInstance(APP_Private_Key).encryptPrivateRSA(JSON.toJSONString(t));
        Map map = TransBeanMapUtils.transBeanToMap(t);
        map.put("sign", _sign);
        return JSON.toJSONString(map);
    }*/
    
    /**
     * 验证明文与加密数据是否一致
     * @param APP_Private_Key
     *           私钥
     * @param sign
     *           签名
     * @param t
     *           加密实体JavaBean
     * @return
     */
    public static <T extends Object> boolean verify(String APP_Private_Key, String sign, T t)
    {
        
       /* if(t instanceof Map){
            return verifyMap(APP_Private_Key, sign, (Map)t);
        }*/
        
        if (sign == null || sign.length() == 0)
        {
            return false;
        }
        //私钥解密
        String data = RSAEncryptByPrivateKey.getInstance(APP_Private_Key).decryptPrivateRSA(sign);
       
        Map map =  (Map) JSON.parse(data);
        //获取当前对象的所有属性
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            //设置访问private属性权限
            field.setAccessible(true);
            try
            {
                Object obj = field.get(t);
                //获取属性名
                String key = field.getName();
                //忽略对sign的校验
                if ("sign".equals(key))
                {
                    continue;
                }
                Object mapValue = map.get(key);
                if (null!=obj)
                {
              /*      if (null==mapValue||"".equals(mapValue))
                    {
                        return false;
                    }
                }
                else
                {*/
                    if (null!=mapValue)
                    {
                        if (obj instanceof String)
                        {
                            if (!obj.equals(mapValue))
                            {
                                if (!(mapValue instanceof String))
                                {
                                    if (!obj.equals(mapValue + ""))
                                    {
                                        return false;
                                    }
                                }else{
                                    return false;
                                }
                            }
                        }
                        else if (obj instanceof Integer)
                        {
                            int _v = (Integer)obj;
                            if (_v != Integer.valueOf(mapValue + ""))
                            {
                                return false;
                            }
                        }
                        else if (obj instanceof Long)
                        {
                            long _v = (Long)obj;
                            if (_v != Long.valueOf(mapValue + ""))
                            {
                                return false;
                            }
                        }
                        else if (obj instanceof Double)
                        {
                            double _mapv = Double.valueOf(obj + "");
                            double _v = Double.valueOf(mapValue + "");
                            if (_v != _mapv)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                System.out.println(e);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    
    
    public static  boolean verifyMap(String APP_Private_Key, String sign, Map t)
    {
        if (sign == null || sign.length() == 0)
        {
            return false;
        }
        //私钥解密
        String data = RSAEncryptByPrivateKey.getInstance(APP_Private_Key).decryptPrivateRSA(sign);
       
        Map<String,Object> map =  (Map) JSON.parse(data);
        //获取当前对象的所有属性
//        Field[] fields = t.getClass().getDeclaredFields();
        for (Entry<String, Object> en : map.entrySet())
        {
            //设置访问private属性权限
           // field.setAccessible(true);
            try
            {
                Object obj = en.getValue();
                //获取属性名
                String key =en.getKey();
                //忽略对sign的校验
                if ("sign".equals(key))
                {
                    continue;
                }
                Object mapValue = t.get(key);
                if (null!=obj)
                {
                    if (null!=mapValue&& !"".equals(mapValue))
                    {
                        return false;
                    }
                }
                else
                {
                    if (null!=mapValue)
                    {
                        if (obj instanceof String)
                        {
                            if (!obj.equals(mapValue))
                            {
                                if (!(mapValue instanceof String))
                                {
                                    if (!obj.equals(mapValue + ""))
                                    {
                                        return false;
                                    }
                                }
                            }else{
                                return false;
                            }
                        }
                        else if (obj instanceof Integer)
                        {
                            int _v = (Integer)obj;
                            if (_v != Integer.valueOf(mapValue + ""))
                            {
                                return false;
                            }
                        }
                        else if (obj instanceof Long)
                        {
                            long _v = (Long)obj;
                            if (_v != Long.valueOf(mapValue + ""))
                            {
                                return false;
                            }
                        }
                        else if (obj instanceof Double)
                        {
                            double _mapv = Double.valueOf(obj + "");
                            double _v = Double.valueOf(mapValue + "");
                            if (_v != _mapv)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                System.out.println(e);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
