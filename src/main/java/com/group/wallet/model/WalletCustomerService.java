package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

import javax.persistence.Transient;
import java.util.Date;

public class WalletCustomerService extends BaseEntity2{

    private String type;

    @Transient
    private String typeName;

    private String name;

    private Date createTime;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}