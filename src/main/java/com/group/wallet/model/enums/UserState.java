package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 用户状态
 */
public enum UserState {

    注册("0"),
    已认证储蓄卡("7"),
    待审核("1"),
    已开通("2"),
    审核不通过("3"),
    信息不全("4"),
    冻结("5"),
    待复审("6")
    ;

    private UserState(String value){
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
        UserState[] values = UserState.values();
        for(int i=0;i<values.length;i++){
            UserState enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
