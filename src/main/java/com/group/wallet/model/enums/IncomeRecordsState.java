package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 分润记录状态
 */
public enum IncomeRecordsState {

    已提交("1"),
    审核通过("2"),
    已放款("3"),
    已到账("4")
    ;

    private IncomeRecordsState(String value){
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
        IncomeRecordsState[] values = IncomeRecordsState.values();
        for(int i=0;i<values.length;i++){
            IncomeRecordsState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
