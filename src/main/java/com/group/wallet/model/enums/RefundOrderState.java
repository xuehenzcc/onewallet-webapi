package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

public enum RefundOrderState {

    未支付("1"),
    支付成功("2")
    ;

    private RefundOrderState(String value){
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
        RefundOrderState[] values = RefundOrderState.values();
        for(int i=0;i<values.length;i++){
            RefundOrderState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
