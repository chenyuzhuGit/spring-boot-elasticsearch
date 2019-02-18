package com.elasticsearch.root.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.root.config.DataBaseIndexType;
import com.elasticsearch.root.service.AccidentInfoService;
import com.elasticsearch.root.service.SafetyRiskInfoService;

/**
 * 智慧风控数据查询接口
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/riskcontrolsystem")
public class WisdomOfCiskController {

	@Autowired
	private SafetyRiskInfoService safetyRiskInfoService;
	@Autowired
	private AccidentInfoService accidentInfoService;

	/**
	 * 隐患数据查询
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getSafetyRisks")
	public String getSafetyRisks(HttpServletRequest request, ModelMap modelMap) {
		String safetyRiskInfos = safetyRiskInfoService.safetyRiskInfoList(request);
		return safetyRiskInfos;
	}

	/**
	 * 根据ids隐患数据查询
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getSafetyRisksById")
	public String getSafetyRiskByIds(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");

		String safetyRiskInfos = safetyRiskInfoService.idsQuery(DataBaseIndexType.SAFETY_RISK_INFO_INDEX,
				DataBaseIndexType.TYPE, id);
		return safetyRiskInfos;
	}

	/**
	 * 事故数据查询
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getAccidentCases")
	public String getAccidentCases(HttpServletRequest request, ModelMap modelMap) {
		String safetyRiskInfos = accidentInfoService.accidentInfoServiceList(request);
		return safetyRiskInfos;
	}

	/**
	 * 根据ids隐患数据查询
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getAccidentCasesById")
	public String getAccidentCasesByIds(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");

		String safetyRiskInfos = safetyRiskInfoService.idsQuery(DataBaseIndexType.ACCIDENT_CASE_INDEX,
				DataBaseIndexType.TYPE, id);
		return safetyRiskInfos;
	}
}
