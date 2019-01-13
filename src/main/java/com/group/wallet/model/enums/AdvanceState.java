package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 预支收益状态
 */
public enum AdvanceState {

    已提交("1"),
    审核通过("2"),
    审核不通过("3"),
    //已放款("4"),
    已到账("5"),
    已还清("6")
    ;

    private AdvanceState(String value){
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
        AdvanceState[] values = AdvanceState.values();
        for(int i=0;i<values.length;i++){
            AdvanceState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
