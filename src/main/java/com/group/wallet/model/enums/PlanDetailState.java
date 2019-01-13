package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

public enum PlanDetailState {

    创建("1"),
    未执行("2"),
    执行成功("3"),
    执行失败("4"),
    终止("5")
    ;

    private PlanDetailState(String value){
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
        PlanDetailState[] values = PlanDetailState.values();
        for(int i=0;i<values.length;i++){
            PlanDetailState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
