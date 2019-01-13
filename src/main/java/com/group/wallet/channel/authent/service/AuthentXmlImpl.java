package com.group.wallet.channel.authent.service;

/**
 * 认证服务交易报文
 * @author pangxingyi
 *
 */
public class AuthentXmlImpl extends SimpleXmlImpl {

	private static final long serialVersionUID = 1L;

	private String TransactType;// 交易类型
	private String CustAddress;// 户籍地址
	private String CustPhoto;// 照片

	private String AuthentNo;// 认证编号
	private String AddrResult;// 户籍地对比结果
	private String AddrRemark;// 户籍地对比备注
	private String MobilenoResult;// 手机号比对结果
	private String MobilenoRemark;
	private String IDCardNoResult;// 身份证比对结果
	private String IDCardNoRemark;
	private String IDCardNameResult;// 姓名证比对结果
	private String IDCardNameRemark;

	private String Imsi;
	private String ImsiResult;
	private String ImsiRemark;
	private String CheckType;
	private String AuthorCode;

	private String AccountNoResult;
	private String AccountNoRemark;

	public String getAccountNoResult() {
		return AccountNoResult;
	}

	public void setAccountNoResult(String accountNoResult) {
		AccountNoResult = accountNoResult;
	}

	public String getAccountNoRemark() {
		return AccountNoRemark;
	}

	public void setAccountNoRemark(String accountNoRemark) {
		AccountNoRemark = accountNoRemark;
	}

	public String getAuthorCode() {
		return AuthorCode;
	}

	public void setAuthorCode(String authorCode) {
		AuthorCode = authorCode;
	}

	public String getCheckType() {
		return CheckType;
	}

	public void setCheckType(String checkType) {
		CheckType = checkType;
	}

	public String getImsi() {
		return Imsi;
	}

	public void setImsi(String imsi) {
		Imsi = imsi;
	}

	public String getImsiResult() {
		return ImsiResult;
	}

	public void setImsiResult(String imsiResult) {
		ImsiResult = imsiResult;
	}

	public String getImsiRemark() {
		return ImsiRemark;
	}

	public void setImsiRemark(String imsiRemark) {
		ImsiRemark = imsiRemark;
	}

	public String getTransactType() {
		return TransactType;
	}

	public void setTransactType(String transactType) {
		TransactType = transactType;
	}

	public String getCustAddress() {
		return CustAddress;
	}

	public void setCustAddress(String custAddress) {
		CustAddress = custAddress;
	}

	public String getCustPhoto() {
		return CustPhoto;
	}

	public void setCustPhoto(String custPhoto) {
		CustPhoto = custPhoto;
	}

	public String getAuthentNo() {
		return AuthentNo;
	}

	public void setAuthentNo(String authentNo) {
		AuthentNo = authentNo;
	}

	public String getAddrResult() {
		return AddrResult;
	}

	public void setAddrResult(String addrResult) {
		AddrResult = addrResult;
	}

	public String getAddrRemark() {
		return AddrRemark;
	}

	public void setAddrRemark(String addrRemark) {
		AddrRemark = addrRemark;
	}

	public String getMobilenoResult() {
		return MobilenoResult;
	}

	public void setMobilenoResult(String mobilenoResult) {
		MobilenoResult = mobilenoResult;
	}

	public String getMobilenoRemark() {
		return MobilenoRemark;
	}

	public void setMobilenoRemark(String mobilenoRemark) {
		MobilenoRemark = mobilenoRemark;
	}

	public String getIDCardNoResult() {
		return IDCardNoResult;
	}

	public void setIDCardNoResult(String iDCardNoResult) {
		IDCardNoResult = iDCardNoResult;
	}

	public String getIDCardNoRemark() {
		return IDCardNoRemark;
	}

	public void setIDCardNoRemark(String iDCardNoRemark) {
		IDCardNoRemark = iDCardNoRemark;
	}

	public String getIDCardNameResult() {
		return IDCardNameResult;
	}

	public void setIDCardNameResult(String iDCardNameResult) {
		IDCardNameResult = iDCardNameResult;
	}

	public String getIDCardNameRemark() {
		return IDCardNameRemark;
	}

	public void setIDCardNameRemark(String iDCardNameRemark) {
		IDCardNameRemark = iDCardNameRemark;
	}

}
