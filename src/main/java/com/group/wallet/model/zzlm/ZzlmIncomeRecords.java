package com.group.wallet.model.zzlm;

import com.group.core.model.BaseEntity2;
import com.group.wallet.model.enums.IncomeRecordsState;
import com.group.wallet.model.enums.IncomeType;
import com.group.wallet.model.enums.UserType;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

public class ZzlmIncomeRecords extends BaseEntity2{

    private Long userId;

    private Long fromUserId;

    @Column(name="(select phone from wallet_user_info where id = from_user_id) phone", insertable=false)
    private String phone;

    @Column(name="(select real_name from wallet_user_info where id = from_user_id) name", insertable=false)
    private String name;

    @Column(name="(select user_type from wallet_user_info where id = from_user_id) userType", insertable=false)
    private String userType;

    private String orderNum;

    private String channelOrderNum;

    private String type;

    @Transient
    private String typeName;

    //分润/提现金额
    private BigDecimal amount;

    //原始金额
    private BigDecimal pristineAmount;

    //剩余金额
    private BigDecimal surplusAmount;

    private String state;

    @Transient
    private String stateName;

    //备注
    private String descp;
    private String batch;//批次号

    private Date createTime;
    private Date businessTime;//交易时间


    public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPristineAmount() {
        return pristineAmount;
    }

    public void setPristineAmount(BigDecimal pristineAmount) {
        this.pristineAmount = pristineAmount;
    }

    public BigDecimal getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(BigDecimal surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        if(StringUtils.isNotEmpty(userType)){
            userType = UserType.getEnumName(userType);
        }
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getChannelOrderNum() {
        return channelOrderNum;
    }

    public void setChannelOrderNum(String channelOrderNum) {
        this.channelOrderNum = channelOrderNum;
    }

    public String getTypeName() {
        return IncomeType.getEnumName(type);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStateName() {
        return IncomeRecordsState.getEnumName(state);
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }
}