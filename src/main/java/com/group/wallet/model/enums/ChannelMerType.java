package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

public enum ChannelMerType {

    大商户("1"),
    一对一商户("2")
    ;

    private ChannelMerType(String value){
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
        ChannelMerType[] values = ChannelMerType.values();
        for(int i=0;i<values.length;i++){
            ChannelMerType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
