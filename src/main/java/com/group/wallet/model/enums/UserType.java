package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

public enum UserType {

    个体商户("A"),
    市级代理商("B"),
    省级代理商("C"),
    初级合伙人("D"),
    高级合伙人("E"),
    超级合伙人("F"),
    超级合伙人A("G")
    ;

    private UserType(String value){
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
        UserType[] values = UserType.values();
        for(int i=0;i<values.length;i++){
            UserType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
