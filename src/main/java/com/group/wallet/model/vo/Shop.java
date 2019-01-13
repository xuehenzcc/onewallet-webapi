package com.group.wallet.model.vo;

import java.util.Date;
import java.util.List;

/**
 * 商品
 * @author zcc
 * 20181222
 */
public class Shop {

//	 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
//	  `app` VARCHAR(20) DEFAULT NULL COMMENT 'app（1pos机,2展业）',
//	  `type` VARCHAR(10) DEFAULT NULL COMMENT '类型（1大pos,2智能pos,3小pos）',
//	  `title` VARCHAR(100) DEFAULT NULL COMMENT '标题',
//	  `active_amount` VARCHAR(100) DEFAULT NULL COMMENT '激活保证金',
//	  `active_back` VARCHAR(100) DEFAULT NULL COMMENT '激活返现',
//	  `comp_back` VARCHAR(100) DEFAULT NULL COMMENT '刷满返现',
//	  `comp_require` VARCHAR(100) DEFAULT NULL COMMENT '刷满条件',
//	  `rate` VARCHAR(100) DEFAULT NULL COMMENT '费率',
//	  `pay_company` VARCHAR(100) DEFAULT NULL COMMENT '支付公司',
//	  `introduce` VARCHAR(500) DEFAULT NULL COMMENT '介绍',
//	  `image` VARCHAR(500) DEFAULT NULL COMMENT '主图片',
//	  `content` TEXT COMMENT '内容详情',
//	  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
//	  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	private Long id;
	private String app;
	private String type;
	private String title;
	private String activeAmount;
	private String activeBack;
	private String compBack;
	private String compRequire;
	private String rate;
	private String payCompany;
	private String image;
	private String content;
	private String introduce;
	private Date updateTime;
	private Date createTime;
	
	
	private List<String> images;
	
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getActiveAmount() {
		return activeAmount;
	}
	public void setActiveAmount(String activeAmount) {
		this.activeAmount = activeAmount;
	}
	public String getActiveBack() {
		return activeBack;
	}
	public void setActiveBack(String activeBack) {
		this.activeBack = activeBack;
	}
	public String getCompBack() {
		return compBack;
	}
	public void setCompBack(String compBack) {
		this.compBack = compBack;
	}
	public String getCompRequire() {
		return compRequire;
	}
	public void setCompRequire(String compRequire) {
		this.compRequire = compRequire;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPayCompany() {
		return payCompany;
	}
	public void setPayCompany(String payCompany) {
		this.payCompany = payCompany;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
