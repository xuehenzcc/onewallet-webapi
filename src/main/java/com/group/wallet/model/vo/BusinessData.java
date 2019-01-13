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
public class BusinessData {

//	 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
//	  `status` VARCHAR(10) DEFAULT NULL COMMENT '状态',
//	  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注1',
//	  `remark2` VARCHAR(100) DEFAULT NULL COMMENT '备注2',
//	  `remark3` VARCHAR(100) DEFAULT NULL COMMENT '备注3',
//	  `amt` DECIMAL(18,2) NOT NULL COMMENT '交易金额',
//	  `sn` VARCHAR(100) NOT NULL COMMENT 'SN机器码',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	  
	 private Long id;
	 private String status;
	 private String userId;
	 private String remark;
	 private String remark2;
	 private String remark3;
	 private BigDecimal amt;
	 private String sn;
	 private Date createTime;
	 private Date businessTime;
	 
	 private String startTime;
	 private String endTime;
	 
	 
	 
	 
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	 
	 
}
