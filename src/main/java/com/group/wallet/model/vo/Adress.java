package com.group.wallet.model.vo;

import java.util.Date;

/**
 * 地址
 * @author zcc
 * 20181222
 */
public class Adress {

//	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
//	  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
//	  `type` VARCHAR(10) DEFAULT NULL COMMENT '类型（0默认地址,1其他）',
//	  `name` VARCHAR(100) DEFAULT NULL COMMENT '收件人',
//	  `telephone` VARCHAR(100) DEFAULT NULL COMMENT '联系电话',
//	  `city` VARCHAR(100) DEFAULT NULL COMMENT '城市',
//	  `address` VARCHAR(100) DEFAULT NULL COMMENT '详细地址',
//	  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	private Long id;
	private Long userId;
	private String type;
	private String name;
	private String telephone;
	private String city;
	private String address;
	private Date updateTime;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
