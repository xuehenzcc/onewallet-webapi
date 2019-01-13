package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 客服类型
 */
public enum CustomerServiceType {

    微信公众号客服("official"),
    微信客服("weixin"),
    QQ客服("QQ"),
    电话客服("phone")
    ;

    private CustomerServiceType(String value){
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
        CustomerServiceType[] values = CustomerServiceType.values();
        for(int i=0;i<values.length;i++){
            CustomerServiceType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
