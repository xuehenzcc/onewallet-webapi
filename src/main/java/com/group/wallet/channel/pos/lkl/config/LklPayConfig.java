package com.group.wallet.channel.pos.lkl.config;

public class LklPayConfig {

    /**
     * rsr支付接口地址
     */
    public static String RSR_PAY_URL_Prefix = "http://114.55.108.209:8208/";

    /**
     * 商户信息查询地址
     */
    public static String URL_MERCH_QUERY =RSR_PAY_URL_Prefix + "pay/merch/query";
    /**
     * 商户信息查询地址
     */
    String URL_IMG_UPLOAD ="http://localhost:8888/posp/upload";
    /**
     * 商户进件地址
     */
    String URL_Merch =RSR_PAY_URL_Prefix+ "pay/merch/save";
    /**
     * 商户绑定银行卡
     */
    String URL_Card_Bind=RSR_PAY_URL_Prefix+"pay/merch/cardbind";
    /**
     * 创建支付订单
     */
    String URL_Order=RSR_PAY_URL_Prefix+"pay/order";
    /**
     * 主动查询订单支付结果
     */
    String URL_Order_Result_Check=RSR_PAY_URL_Prefix+"pay/order/result";
    
    /**
     * 创建快捷支付预付订单
     */
    String URL_Fastpay_Order = RSR_PAY_URL_Prefix+"pay/fast/order";
    
    /**
     * 主动查询快捷支付订单支付结果
     */
    String URL_Fastpay_Result_Check=RSR_PAY_URL_Prefix+"pay/fast/order/result";
    String URL_Order_Detail=RSR_PAY_URL_Prefix+"pay/order/detail";
    
    /**
     * 快捷支付验证码交易
     */
    String URL_Fastpay_Order_Trans = RSR_PAY_URL_Prefix+"pay/fast/trans";


    /****************************************************************
     * 以下配置属于接入应用私有配置
     ****************************************************************/
    /**
     * 瑞升蓉为接入应用分配的appid
     */
    //瑞芯付
    public static String APP_ID="77CB6BDB55D575CA3E5BAD02BD1C3D72";

    /**
     * 瑞升蓉为接入应用分配的rsa私钥，
     * 用于加密请求参数
     * 解密瑞升蓉返回数据和回调的参数
     */

    public static String APP_Private_Key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIry2UJLGUN4xwrFxHyb/eedzQy/" +
            "28/DESBXcGT2EaSstC1Psna681BZVqGObv04FJn2fPimsXA4GKR/PjAJpNHb4EoMYJ+sk5acqLO8" +
            "DLXzRHjVXQ9IVvRGHuJabvE1q0VOvbzeNDwJGHJW7LsTszFoYrDENDe68eQwM3MVzhHpAgMBAAEC" +
            "gYBieHqJ5cjwrS56Lu/x02ccdxOzPmDcG4UOo4kOs+iLCK3kEsAKLrFytu2TLurvX4gSGMI2QYQs" +
            "YETuhxKeXqdD0SuG7BfpBv/t5PxPhnhiD6WvWZKE4GTqBpe3QH7/oi+5lQsBkRTskCFbkfHjFrEm" +
            "e8lgp3qteNytNpW00bIGgQJBAPfpQrV60UNEckgB/J1qgtwX7rITjfFraatj8iSxQOSOqr6tBB1J" +
            "wuqEQ6b5frkVc6Xp3eQETDemyHjXQkFSXHkCQQCPe3OSuB4WGKbiynZwU26Aye/zwNXWXgbVkMz8" +
            "3MIySB8XkQeeB0ZVIWjpzlVbtjO7KflAFjqzBSPM/qg7ZCTxAkEA9H/GPevaFOideS0UckAIvX6Q" +
            "+GnWtQIFnnYc61kxsfiOpy1GAIitxtMZUcWA2JMykrBbGbWvYbeIwb0y5++NqQJBAIySlpiYamGb" +
            "pWVslr1WAF2NetmFg/uSA4AZbbwPTPOWVV/xZ1UcK3wcT31B+b7ws54bcWeoX7okvv6IQKpvYwEC" +
            "QQC8GhnOi3AH/VhjRLTAkPd5ZkazyCeO7hy1wvnt3HQCY4j+dwfFTcmzKvmIjbOY+sK73W3QJJwj" +
            "9FFa/znty9gd";
    
    /**
     * 支付结果回调地址
     */
    String URL_Notify="http://192.168.3.198:8098/rsr/pay/callback";
    
    /**
     * 快捷支付结果回调地址
     */
    String URL_Fastpay_Notify="http://192.168.3.198:8098/rsr/pay/fast/callback";
}
