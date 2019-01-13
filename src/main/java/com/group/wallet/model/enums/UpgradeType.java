package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 用户升级类型
 */
public enum UpgradeType {

    用户等级升级("1"),
    换卡等级升级("2")
    ;

    private UpgradeType(String value){
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
        UpgradeType[] values = UpgradeType.values();
        for(int i=0;i<values.length;i++){
            UpgradeType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
