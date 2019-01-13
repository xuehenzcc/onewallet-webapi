package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 付款方式
 */
public enum PayWay {

    快捷支付("quick"),
    支付宝("zfb"),
    微信("weix"),
    QQ钱包("qq")
    ;

    private PayWay(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getEnumName(String value){
        if(StringUtils.isEmpty(value)) return null;
        PayWay[] values = PayWay.values();
        for(int i=0;i<values.length;i++){
            PayWay enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
