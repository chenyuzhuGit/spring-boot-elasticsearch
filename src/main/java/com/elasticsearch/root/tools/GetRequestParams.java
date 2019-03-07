package com.elasticsearch.root.tools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求参数
 * 
 * @author Administrator
 *
 */
public class GetRequestParams {
	/**
	 * 根据request对象获取参数 
	 * 注：
	 * 	1.相同字段的参数将以，"field_index"作为map中的key，如：id有多个值，id_0=1,id_1=2……
	 * 	2.参数的类型都是String类型。
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> GetParamsByRequestObj(HttpServletRequest request) {
		Map<String, Object> results = new HashMap<String, Object>();
		Map<String, String[]> params = request.getParameterMap();
		for (String param : params.keySet()) {
			String[] valueArray = params.get(param);
			int length = valueArray.length;
			for (int i = 0; i < length; i++) {
				if (length > 1) {
					results.put(param + "_" + i, valueArray[i]);
				} else {
					results.put(param, valueArray[i]);
				}
			}
		}
		return results;
	}
}
