package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

public enum MerchantType {

    商家("M"),
    店长("S"),
    收银员("C")
    ;

    private MerchantType(String value){
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
        MerchantType[] values = MerchantType.values();
        for(int i=0;i<values.length;i++){
            MerchantType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
