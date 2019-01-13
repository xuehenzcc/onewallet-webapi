package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 通道类型
 */
public enum ChannelType {

    快捷渠道("Q1"),
    H5快捷渠道("Q2"),
    二维码渠道("C"),
    代付渠道("P"),
    刷卡渠道("S")
    ;

    private ChannelType(String value){
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
        ChannelType[] values = ChannelType.values();
        for(int i=0;i<values.length;i++){
            ChannelType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
