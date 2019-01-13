package com.group.wallet.channel.authent.service;

import com.group.wallet.channel.authent.common.Toolkit;

import java.lang.reflect.Method;


/**
 * 交易报文
 *
 */
public class BaseXml implements SimpleXml {

	private static final long serialVersionUID = 1L;

	private String Version = "";
	private String ProcCode = "";
	private String AccountNo = "";
	private String ProcessCode = "";
	private String Amount = "";
	private String TransDatetime = "";
	private String AcqSsn = "";
	private String OrderNo = "";
	private String TransData = "";
	private String Reference = "";
	private String RespCode = "";
	private String TerminalNo = "";
	private String MerchantNo = "";
	private String MerchantOrderNo = "";
	private String OrderState = "";
	private String Remark = "";
	private String MAC = "";

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getProcCode() {
		return ProcCode;
	}

	public void setProcCode(String procCode) {
		ProcCode = procCode;
	}

	public String getAccountNo() {
		return AccountNo;
	}

	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}

	public String getProcessCode() {
		return ProcessCode;
	}

	public void setProcessCode(String processCode) {
		ProcessCode = processCode;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getTransDatetime() {
		return TransDatetime;
	}

	public void setTransDatetime(String transDatetime) {
		TransDatetime = transDatetime;
	}

	public String getAcqSsn() {
		return AcqSsn;
	}

	public void setAcqSsn(String acqSsn) {
		AcqSsn = acqSsn;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getTransData() {
		return TransData;
	}

	public void setTransData(String transData) {
		TransData = transData;
	}

	public String getReference() {
		return Reference;
	}

	public void setReference(String reference) {
		Reference = reference;
	}

	public String getRespCode() {
		return RespCode;
	}

	public void setRespCode(String respCode) {
		RespCode = respCode;
	}

	public String getTerminalNo() {
		return TerminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}

	public String getMerchantNo() {
		return MerchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		MerchantNo = merchantNo;
	}

	public String getMerchantOrderNo() {
		return MerchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		MerchantOrderNo = merchantOrderNo;
	}

	public String getOrderState() {
		return OrderState;
	}

	public void setOrderState(String orderState) {
		OrderState = orderState;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	@Override
	public String toString() {
		return toXml();
	}

	public String toXml() {
		Object o = this;
		String append = "\n    ";
		StringBuilder sb = new StringBuilder("");
		Object[] agrs = new Object[] {};
		Class[] cs = new Class[] {};
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		sb.append("<x:NetworkRequest xmlns:x=\"http://www.payeco.com\" xmlns:xsi=\"http://www.w3.org\">"
				+ append);
		for (Method m : o.getClass().getMethods()) {
			String fieldName = m.getName();
			if (fieldName.startsWith("get")
					&& m.toString().contains(
					o.getClass().getPackage().toString()
							.replace("package ", ""))) {
				String suffixName = fieldName.replace("get", "");
				try {
					Object obj = m.invoke(o, agrs);
					if (!Toolkit.isNullOrEmpty(obj)) {
						sb.append("<" + suffixName + ">");
						sb.append(obj);
						sb.append("</" + suffixName + ">" + append);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String str = sb.toString().substring(0, sb.toString().length() - 4)
				+ "</x:NetworkRequest>";
		return str;
	}

}
