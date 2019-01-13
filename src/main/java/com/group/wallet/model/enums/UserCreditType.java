package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 用户还款级别
 */
public enum UserCreditType {

    个体商户("A"),
    代理商("B"),
    合伙人("C")
    ;

    private UserCreditType(String value){
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
        UserCreditType[] values = UserCreditType.values();
        for(int i=0;i<values.length;i++){
            UserCreditType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
