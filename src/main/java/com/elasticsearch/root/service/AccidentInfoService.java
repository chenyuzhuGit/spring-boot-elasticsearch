package com.elasticsearch.root.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 事故查询接口
 * 
 * @author Administrator
 *
 */
public interface AccidentInfoService {

	String accidentInfoServiceList(HttpServletRequest request);

	String idsQuery(String index, String type, String ids);

}
