package com.group.wallet.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 收入类型
 */
public enum IncomeType {

    刷卡收益("A1"),
    快捷分润("A2"),
    扫码支付("A3"),
    升级收益("A4"),
    商户分润("A5"),
    直推返点("A6"),
    管理费("A7"),
    
    激活返现("A8"),
    盟友激活返现("A9"),
    退还保证金("A10"),

    提现("B1"),
    购买pos机("B2")
    ;

    private IncomeType(String value){
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
        IncomeType[] values = IncomeType.values();
        for(int i=0;i<values.length;i++){
            IncomeType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
