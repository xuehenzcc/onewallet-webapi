package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 订单类型
 */
public enum OrderType {

    升级订单("U"),
    购买刷卡机订单("B"),
    提前还款订单("R")
    ;

    private OrderType(String value){
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
        OrderType[] values = OrderType.values();
        for(int i=0;i<values.length;i++){
            OrderType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
