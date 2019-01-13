package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 交易状态
 */
public enum TradeState {

    未支付("1"),
    支付中("2"),
    支付成功("3")
    ;

    private TradeState(String value){
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
        TradeState[] values = TradeState.values();
        for(int i=0;i<values.length;i++){
            TradeState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
