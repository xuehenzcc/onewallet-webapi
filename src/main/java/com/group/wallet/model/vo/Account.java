/**
 * 版权：zcc
 * 作者：c0z00k8
 * @data 2018年12月25日
 */
package com.group.wallet.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author c0z00k8
 *
 */
public class Account {

//	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
//	  `pos_type` VARCHAR(100) NOT NULL COMMENT '0 all,1大pos,2智能pos,3小pos',
//	  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
//	  `user_name` VARCHAR(100) NOT NULL COMMENT '用户名称',
//	  `user_telephone` VARCHAR(100) NOT NULL COMMENT '用户电话',
//	  `user_leave` VARCHAR(100) NOT NULL COMMENT '用户等级',
//	  `amount` DECIMAL(10,2) NOT NULL COMMENT '收益金额',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	private Long id;
	private String app; 
	private String sn; 
	private String posType; 
	private Long userId; 
	private String userName; 
	private String userTelephone; 
	private String userLeave; 
	private Date createTime; 
	private BigDecimal amount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPosType() {
		return posType;
	}
	public void setPosType(String posType) {
		this.posType = posType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTelephone() {
		return userTelephone;
	}
	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}
	public String getUserLeave() {
		return userLeave;
	}
	public void setUserLeave(String userLeave) {
		this.userLeave = userLeave;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	} 
	
	
}
