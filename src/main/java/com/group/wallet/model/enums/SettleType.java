package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 结算方式
 */
public enum SettleType {

    T0("T0"),
    T1("T1")
    ;

    private SettleType(String value){
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
        SettleType[] values = SettleType.values();
        for(int i=0;i<values.length;i++){
            SettleType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
