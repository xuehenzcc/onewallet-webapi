package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 消息类型
 */
public enum MessageType {

    系统消息("1"),
    分润消息("2")
    ;

    private MessageType(String value){
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
        MessageType[] values = MessageType.values();
        for(int i=0;i<values.length;i++){
            MessageType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
