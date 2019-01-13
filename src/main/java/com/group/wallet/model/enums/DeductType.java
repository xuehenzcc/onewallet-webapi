package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

public enum DeductType {

    通道("channel"),
    商户("mer"),
    还卡("credit")
    ;

    private DeductType(String value){
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
        DeductType[] values = DeductType.values();
        for(int i=0;i<values.length;i++){
            DeductType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
