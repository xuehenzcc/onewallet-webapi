package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 结算类型
 */
public enum SettleWay {

    渠道结算("1"),
    平台结算("2")
    ;

    private SettleWay(String value){
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
        SettleWay[] values = SettleWay.values();
        for(int i=0;i<values.length;i++){
            SettleWay enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
