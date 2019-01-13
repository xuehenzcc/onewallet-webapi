package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 轮播图，公告，通知
 */
public enum AdverType {

    轮播图("slideshow"),
    公告("affiche"),
    通知("notice"),
    文章("article")
    ;

    private AdverType(String value){
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
        AdverType[] values = AdverType.values();
        for(int i=0;i<values.length;i++){
            AdverType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
