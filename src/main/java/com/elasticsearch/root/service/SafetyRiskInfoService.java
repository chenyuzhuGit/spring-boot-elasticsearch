package com.elasticsearch.root.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 隐患查询接口
 * 
 * @author Administrator
 *
 */
public interface SafetyRiskInfoService {

	String safetyRiskInfoList(HttpServletRequest request);

	String idsQuery(String index, String type, String ids);

}
