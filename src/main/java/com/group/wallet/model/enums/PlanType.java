package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 计划类型
 */
public enum PlanType {

    智能还卡("1"),
    智能代还("2")
    ;

    private PlanType(String value){
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
        PlanType[] values = PlanType.values();
        for(int i=0;i<values.length;i++){
            PlanType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
