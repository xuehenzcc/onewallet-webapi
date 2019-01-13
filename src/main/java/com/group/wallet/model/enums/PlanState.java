package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 还款状态
 */
public enum PlanState {

    创建("1"),
    执行中("2"),
    执行完成("3"),
    终止("4")
    ;

    private PlanState(String value){
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
        PlanState[] values = PlanState.values();
        for(int i=0;i<values.length;i++){
            PlanState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
