package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 代付类型
 */
public enum PaymentType {

    分润代付("profit"),
    预支收益代付("advance")
    ;

    private PaymentType(String value){
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
        PaymentType[] values = PaymentType.values();
        for(int i=0;i<values.length;i++){
            PaymentType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
