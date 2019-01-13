package com.group.wallet.channel.quick.youfuPay.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.util.*;

public class JSONUtil {
	/**
	 * @Title: packJson
	 * @Description: 打包请求的json
	 * @param request
	 * @return String 返回类型
	 * @throws
	 */
	public static String packJson(Object object) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * @Title: parseJson
	 * @Description: 解析json
	 * @param json
	 * @return
	 * @throws Exception
	 *             设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	public static Object parseJson(String json, Class<?> clz) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object object = JSONObject.toBean(jsonObject, clz);
		return object;
	}

	/**
	 * @Title: parseJson2
	 * @Description: 解析json
	 * @param json
	 * @return
	 * @throws Exception
	 *             设定文件
	 * @return JSONObject 返回类型
	 * @throws
	 */
	public static JSONObject parseJson2(String json) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject;
	}
	public static void main(String[] args){
		String msgJson = "'request':{'account':'15153172003','passWord':'123456'}";
//		Map<String, Object> aa=jsonToMap("{'response':{'sign':'J1/mFpaKf9i2+hmGFhCqX20/E0cQIqEJGqFwnksE6nbo0W5H7mgiKjXbUd2prNjTV5Ni0nqHKmZ3lxEbGGUdLDiMNimU8JBUi+n8snLeUHyCNlye0gHopLJt69YeIuh0ULaJUMKnpnVGngXjgbAff5KtLs+igqnuqYh1Do1u+dyrXCQ+1tHWz9aP32OrXwuZDTBRGfsM0JHo4EtrIIhvlrWc/REZvpc5dM1DzTUJ7XicI9+1n3R31k8QilSukRYor584CjL7y3otMk2vki3fr6+RBSSi5i7A5hVx1hm23HU4WF6BQ668/xhxY1SiJIhL+QwVUmrFif1ME1OFC8txFs92wO+bXIIl2lwDe15+fvisRkF/VgWjeqZdJM1tAb6k8qVwX7ibLZt0B9G/77sHWnPRTCAq2WaNP7V4/KhOI4DJlqn5jp6YSIN/zBUr7Wtgfc2tEScnZ4WOPAT9EscJ9YHwr/s+42UcBmNmrWuH4oBNf3Fd74x0zkAaeaUdRr5XkOlQODd8upOKBkje/maBPVXXuH90jscIDs3H6mlfzLnU5V5FEkVXtKgbKwv415P4wuGIFTDyoD0ErfeP1JFrBYUSNnIH49wEHQkJQ9ViN5kh+u7eOY2rmtkhRhw0YrV1JNXtKfaxwuS7m3EMSgPvzG8V/ldZyCNdVUc4ehMvEEBXUko5A/h7cpJJ3hMaYhMttWw0oO7WbaDsvoC/aEbxRI2zNuhpEqWgtxC66GWA0cF18L3tFNCrsgW1WNd6Agh7/I7C32cErO3/ZAV7cAcqZEXAFKFgbF3czh5feprxJviMHJ0lMv3UrdKEJhrii+1QUvZnPLzL2RKOpiI4T0mIsK2fFdg4X2GBZlybXHj9xjEUvNLZGERHywIttyX0G/54LcNCM7hBT/VvMAYb1SabfqkHmtI8CzhK71lccXohA06bw/xR92mrZxZK5cFBTS1JjmKkIxAckcBTvYB4f2EhtKf333xB3L+PF6oj1gWGoUN1yu0+zKxEFgKs58/v9YkWjlCX4sdbIQRNFHt22lfG5PDgM5TgtML0Ki3d/da6iDXm5+Q8czkKh8ua2PMDfqApM/sd7UGmUuIx7mxmTMD0hq/JugDwGDzPoNxR4fp3dwGjkiJCclJRfY2ddA0IZ4hLSkTQb4IuqZq1Kv0=','respCode':'0023','respInfo':'同步商户签约出现异常，检查业务参数信息！'},'serviceCode':'A0500'}'");
		Map<String, Object> aa=JSONUtil.parseJSON2Map(msgJson);
		System.out.println(aa.get("response"));
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("map", Map.class);
		Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(json), Map.class, classMap);
		// 转换null
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = map.get(key);
			if (value instanceof JSONNull) {
				map.put(key, null);
			}
		}
		return map;
	}

	/**
	 * 将jsonString转成Map
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), String.valueOf(v));
			}
		}
		return map;
	}

	/**
	 * MAP转JSON字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<String, Object> map) {
		Set<String> keys = map.keySet();
		String key = "";
		Object value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key);
			jsonBuffer.append(key + ":" + "\"" + value + "\"");
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}
}
