package com.group.wallet.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * pos机器
 * @author Administrator
 * 20181222
 */
public class Pos {

//	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
//	  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
//	  `active_user_id` BIGINT(20) DEFAULT NULL COMMENT '当前用户ID',
//	  `action` VARCHAR(10) DEFAULT NULL COMMENT '动作（1下放,2召回,3同意召回,4拒绝召回）',
//	  `type` VARCHAR(10) DEFAULT NULL COMMENT '类型（1大pos,2智能pos,3小pos）',
//	  `status` VARCHAR(10) DEFAULT NULL COMMENT '状态（1激活,0未激活）',
//	  `sn` VARCHAR(100) NOT NULL COMMENT 'SN机器码',
//	  `active_time` DATETIME DEFAULT NULL COMMENT '激活时间',
//	  `update_time` DATETIME DEFAULT NULL COMMENT '激活时间',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	  private Long id;
	  private Long userId;
	  private Long activeUserId;
	  private String action;
	  private String type;
	  private String status;
	  private String logo;//logo
	  private String brand;//品牌
	  private String sn;
	  private Integer reach;//达标
	  private Integer limitnum;//期限
	  private BigDecimal compAmt;
	  private BigDecimal amt;
	  private BigDecimal backAmt;
	  private Date backTime;
	  private Date activeTime;
	  private Date updateTime;
	  private Date createTime;
	  
	  private Integer count;//总计数量
	  private Integer activeNum;//激活数量
	  
	  private String lookNum;//看板（1我的机具2召回机具3被召回机具）
	  private String activeDown;//下发（0不显示1显示）
	  private String activeBack;//召回（0不显示1显示）
	  
	
	  
	  
	public String getLookNum() {
		return lookNum;
	}
	public void setLookNum(String lookNum) {
		this.lookNum = lookNum;
	}
	public String getActiveDown() {
		return activeDown;
	}
	public void setActiveDown(String activeDown) {
		this.activeDown = activeDown;
	}
	public String getActiveBack() {
		return activeBack;
	}
	public void setActiveBack(String activeBack) {
		this.activeBack = activeBack;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getActiveNum() {
		return activeNum;
	}
	public void setActiveNum(Integer activeNum) {
		this.activeNum = activeNum;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public Integer getReach() {
		return reach;
	}
	public void setReach(Integer reach) {
		this.reach = reach;
	}
	
	public Integer getLimitnum() {
		return limitnum;
	}
	public void setLimitnum(Integer limitnum) {
		this.limitnum = limitnum;
	}
	public BigDecimal getCompAmt() {
		return compAmt;
	}
	public void setCompAmt(BigDecimal compAmt) {
		this.compAmt = compAmt;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public BigDecimal getBackAmt() {
		return backAmt;
	}
	public void setBackAmt(BigDecimal backAmt) {
		this.backAmt = backAmt;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getActiveUserId() {
		return activeUserId;
	}
	public void setActiveUserId(Long activeUserId) {
		this.activeUserId = activeUserId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	  
}
