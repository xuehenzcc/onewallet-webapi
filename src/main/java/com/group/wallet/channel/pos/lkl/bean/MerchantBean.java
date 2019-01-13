package com.group.wallet.channel.pos.lkl.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
*    
* 项目名称：rsrpaydemo   
* 类名称：MerchantBean   
* 类描述：商户进件信息   
* 创建人：
* 创建时间：2017年1月8日 下午1:59:36   
* @version 1.0   
*
 */
@JsonAutoDetect
public class MerchantBean extends BaseBean{
	/**
	 * 当前应用商户唯一编号
	 */
	protected String app_merch_no;
	/**
	 * 商户名称
	 */
	private String merch_name;
	/**
	 * 商户所在省份编号
	 */
	private String province_id;
	/**
	 * 商户所在城市编号
	 */
	private String city_id;
	/**
	 * 商户经营范围编号
	 */
	private String industry_category_code;
	
	private MerchantDetailBean merch_detail;
	
	public String getApp_merch_no() {
		return app_merch_no;
	}
	public void setApp_merch_no(String app_merch_no) {
		this.app_merch_no = app_merch_no;
	}
	public String getMerch_name() {
		return merch_name;
	}
	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getIndustry_category_code() {
		return industry_category_code;
	}
	public void setIndustry_category_code(String industry_category_code) {
		this.industry_category_code = industry_category_code;
	}
    public MerchantDetailBean getMerch_detail()
    {
        return merch_detail;
    }
    public void setMerch_detail(MerchantDetailBean merch_detail)
    {
        this.merch_detail = merch_detail;
    }
	
	
}
