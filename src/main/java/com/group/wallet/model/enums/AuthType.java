package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/11/8 下午5:40
 * 描述：认证类型
 */
public enum AuthType {

    身份证认证("1"),
    人脸认证("2")
    ;

    private AuthType(String value){
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
        AuthType[] values = AuthType.values();
        for(int i=0;i<values.length;i++){
            AuthType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
