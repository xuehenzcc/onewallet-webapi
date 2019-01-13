package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 轮播图，公告，通知
 */
public enum ModuleType {

    一键办卡("1"),
    一键贷款("2"),
    更多服务("3")
    ;

    private ModuleType(String value){
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
        ModuleType[] values = ModuleType.values();
        for(int i=0;i<values.length;i++){
            ModuleType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
