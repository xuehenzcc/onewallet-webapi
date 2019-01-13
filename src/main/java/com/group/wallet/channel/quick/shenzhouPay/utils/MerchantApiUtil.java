package com.group.wallet.channel.quick.shenzhouPay.utils;

import com.group.utils.MD5;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * <b>功能说明:商户API工具类 </b>
 * 
 * @author 
 */
public class MerchantApiUtil {

	/**
	 * 获取参数签名
	 * 
	 * @param paramMap
	 *            签名参数
	 * @param paySecret
	 *            签名密钥
	 * @return
	 */
	public static String getSign(Map<String, String> paramMap, String paySecret) throws Exception{
		SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
		StringBuffer stringBuffer = new StringBuffer();
		for (Map.Entry<String, Object> m : smap.entrySet()) {
			Object value = m.getValue();
			if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
				stringBuffer.append(m.getKey()).append("=").append(m.getValue()).append("&");
			}
		}
		stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

		String argPreSign = stringBuffer.append("&paySecret=").append(paySecret).toString();
		String signStr = MD5.getMessageDigest(argPreSign.getBytes("utf-8")).toUpperCase();
		return signStr;
	}

	/**
	 * 获取参数拼接串
	 * 
	 * @param paramMap
	 * @return
	 */
	public static String getParamStr(Map<String, Object> paramMap) {
		SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
		StringBuffer stringBuffer = new StringBuffer();
		for (Map.Entry<String, Object> m : smap.entrySet()) {
			Object value = m.getValue();
			if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
				stringBuffer.append(m.getKey()).append("=").append(value).append("&");
			}
		}
		stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

		return stringBuffer.toString();
	}

	/**
	 * 验证商户签名
	 * 
	 * @param paramMap
	 *            签名参数
	 * @param paySecret
	 *            签名私钥
	 * @param signStr
	 *            原始签名密文
	 * @return
	 */
	public static boolean isRightSign(Map<String, String> paramMap, String paySecret, String signStr) throws Exception{

		if (StringUtils.isBlank(signStr)) {
			return false;
		}

		String sign = getSign(paramMap, paySecret);
		if (signStr.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @return 提交表单HTML文本
	 */
	public static String buildRequest(Map<String, Object> sParaTemp, String strMethod, String strButtonName, String actionUrl) {
		// 待请求参数数组
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());
		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"rppaysubmit\" name=\"rppaysubmit\" action=\"" + actionUrl + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			Object object = sParaTemp.get(name);
			String value = "";

			if (object != null) {
				value = (String) sParaTemp.get(name);
			}

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['rppaysubmit'].submit();</script>");

		return sbHtml.toString();
	}

}