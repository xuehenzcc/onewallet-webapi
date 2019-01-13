package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 扫描方式
 * Created by Administrator on 2017/7/18.
 */
public enum ScanType {

    快捷("quick"),
    主扫("active"),
    被扫("passive"),
    公众号("open");

    private ScanType(String value){
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
        ScanType[] values = ScanType.values();
        for(int i=0;i<values.length;i++){
            ScanType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
