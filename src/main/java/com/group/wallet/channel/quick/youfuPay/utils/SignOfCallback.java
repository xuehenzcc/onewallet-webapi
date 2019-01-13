package com.group.wallet.channel.quick.youfuPay.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class SignOfCallback {

	private static final String PUBLICKKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEvwG2998AV4STOEiqko5Y6d2ktHhlKeqW2EUjx2G2gJcBgOkP6daHtAldrQxzS5pucMf3kCeJaQNXnx5FcnkwLCJZJbfigBrd6DO4RojVksiWu11rmBVBM0FT6iBMOCyH73Lf3JJaVVqjQ6SgFZ13zYkVm5l4xUIHKZjGRIvwnQIDAQAB";
	private static final String PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIS/Abb33wBXhJM4SKqSjljp3aS0eGUp6pbYRSPHYbaAlwGA6Q/p1oe0CV2tDHNLmm5wx/eQJ4lpA1efHkVyeTAsIlklt+KAGt3oM7hGiNWSyJa7XWuYFUEzQVPqIEw4LIfvct/cklpVWqNDpKAVnXfNiRWbmXjFQgcpmMZEi/CdAgMBAAECgYBQlL5OumMFixrwX19FJBgDA1xHikH3bx13mitGT9cR+Tcxc3aG8XUIBZMV6WFAb+l8FzxcHIbTlAqyWuX+VEIZyAgBcYpZd9vUO9GNyNvpkVjkedltiO56ZubOw9e3bHOg2YMhncKYVjwPwzIAGBw6+XzhVyj/kw/1unrRcBoLwQJBANoMLLA5YgtLGemgBQ8/h/3UcZ+rKLqKBWRNSWjF55a5wDGdqWjSi4VT3Osz437yq5oCKLLxcB9tdArWld+LyrECQQCb2fJAbudMNJSq/srZRjYoOFY6PlPQCXPFN0Zt7e/6P/FIzsbnWDfTMkFnr6sQyWu8w+qmX+gWNMjMHjCBIyetAkBSKZpBQS4voTn/bZvadg8RV5cFGmK7f7yuYe0SfNhaVF4WlBk89XE7aTFqs1+6+0gsPToGy7F7Niwu1VMeLDfRAkBAPICA1jU3T6THJeVt0GaaBT5lGzNoV70D2FiLDrsOpjRJEhEhYVcBEYGrvtf/P0XMs+OPV5BNNsxcdUUAtQCxAkAHafXXcY5JVdlbxcd5Ul1mgMjWC+6CRJ9vXr/WSmJBKhbcnMqmLV19LLKtBS57jnUDWPr7lLZZAsrP+BCryebm";
	private static final String CHARSET = "UTF-8";
	private static final String PARAM = "reqParam";
	private static final String ACCOUNT = "18988941302";
	/**
	 * 交易异步回调验签
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String date = "QX5WlXJCHI4U84ZzBdKKtTmQAuUd3DOU4sG5QHj8J1pE96EPlzi0dRrmfuYSSxVvjXrAg64+ZKWVmFYxU0LALkJqlQQSJi0ahB9Xn8jdaYnFgdplHSmQP5thqTdwxVAHJxN/DVziLaEVNREYzFMM1jG0ENgRVHzZeSiuhrYkhUI";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("respCode", "0000");
		dataMap.put("respInfo", "交易成功");
		dataMap.put("amount", "80000");
		dataMap.put("channelNum", "100001");
		dataMap.put("appOrderId", "201807041755385462");
		dataMap.put("orderId", "180704175550150");
		System.out.println("|" + dataMap.toString());
		System.out.println("|" + getParString1(dataMap));
		String signData = Base64.encodeToString(getParString1(dataMap));
		System.out.println();
		boolean vfy = LocalUtil.verifySignature(
				Base64.decode(PUBLICKKEY.getBytes()), date,
				signData.getBytes(CHARSET));
		System.out.println("验证签名 " + vfy);
	}

	/**
	 * 解决JDK版本导致的验签不过问题
	 * 
	 * @param signMap
	 * @return
	 */
	public static String getParString1(Map<String, Object> signMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("amount=" + signMap.get("amount") + ", ");
		sb.append("respCode=" + signMap.get("respCode") + ", ");
		sb.append("channelNum=" + signMap.get("channelNum") + ", ");
		sb.append("appOrderId=" + signMap.get("appOrderId") + ", ");
		sb.append("orderId=" + signMap.get("orderId") + ", ");
		sb.append("respInfo=" + signMap.get("respInfo") + "");
		sb.append("}");
		System.out.println(sb.toString());
		return sb.toString();
	}

}
