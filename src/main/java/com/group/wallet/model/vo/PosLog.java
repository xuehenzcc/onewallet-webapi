/**
 * 版权：zcc
 * 作者：c0z00k8
 * @data 2018年12月28日
 */
package com.group.wallet.model.vo;

import java.util.Date;

/**
 * @author c0z00k8
 * 记录POS机的归属
 */
public class PosLog {

//	  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
//	  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
//	  `type` VARCHAR(10) DEFAULT NULL COMMENT '类型（1大pos,2智能pos,3小pos）',
//	  `status` VARCHAR(10) DEFAULT NULL COMMENT '状态（1过去,0现在）',
//	  `sn` VARCHAR(100) NOT NULL COMMENT 'SN机器码',
//	  `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
//	  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	  
	private Long id;
	private Long userId;
	private String type;
	private String status;
	private String sn;
	private Date startTime;
	private Date endTime;
	private Date createTime;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
