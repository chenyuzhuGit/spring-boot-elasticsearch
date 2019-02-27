package com.elasticsearch.root.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.elasticsearch.root.config.DataBaseConnectionInfo;
import com.elasticsearch.root.config.DataBaseIndexType;
import com.elasticsearch.root.service.SafetyRiskInfoService;
import com.elasticsearch.root.tools.RestHighLevelClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 隐患查询
 * 
 * @author Administrator
 *
 */
@Service
public class SafetyRiskInfoServiceImpl implements SafetyRiskInfoService {

	private Logger log = Loggers.getLogger(SafetyRiskInfoServiceImpl.class);
	@Autowired
	private DataBaseConnectionInfo dataBaseInfo;

	/**
	 * 隐患查询
	 */
	@Override
	public String safetyRiskInfoList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();

		String hiddenPlaceProvince = request.getParameter("hiddenPlaceProvince");// 隐患地点---省
		String hiddenPlaceCity = request.getParameter("hiddenPlaceCity");// 隐患地点---市
		String hiddenPlaceArea = request.getParameter("hiddenPlaceArea");// 隐患地点---区
		String hazardLevel = request.getParameter("hazardLevel");// 隐患等级
		String hazardUnits = request.getParameter("hazardUnits");// 隐患单位
		String hazardCategoryLevelOne = request.getParameter("hazardCategoryLevelOne");// 隐患类别--一级
		String hazardCategoryLevelTwo = request.getParameter("hazardCategoryLevelTwo");// 隐患类别--二级
		String industryLevelOne = request.getParameter("industryLevelOne");// 行业分类--一级
		String industryLevelTwo = request.getParameter("industryLevelTwo");// 行业分类--二级
		String probableAccident = request.getParameter("probableAccident");// 可能导致的事故

		String pageSize = request.getParameter("pageSize");// 每页数量
		Integer numberPage = pageSize != null && !StringUtils.isEmpty(pageSize) ? Integer.parseInt(pageSize) : 10;
		String pageIndex = request.getParameter("pageIndex");// 页码数
		Integer pagination = pageIndex != null && !StringUtils.isEmpty(pageIndex) ? Integer.parseInt(pageIndex) : 1;
		Integer startIndex = (pagination - 1) * numberPage;

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(startIndex);// 起始位置
		sourceBuilder.size(numberPage);// 查询数量
		sourceBuilder.sort(new FieldSortBuilder("checkTime").order(SortOrder.ASC));
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		// 隐患单位为空时，使用行业分类查询出来的企业信息设置隐患单位
		// 根据行业分类从company_info索引获取企业项目信息
		SearchHits companyProjects = getProjectByIndustry(industryLevelOne, industryLevelTwo, hazardUnits);
		List<String> copmpanyProjectResults = new ArrayList<String>();
		List<String> copmpanyNameResults = new ArrayList<String>();
		// 设置查询参数
		if (companyProjects != null) {
			for (SearchHit hit : companyProjects.getHits()) {
				String projectName = (String) hit.getSourceAsMap().get("projectName");
				String unitName = (String) hit.getSourceAsMap().get("unitName");
				if (!StringUtils.isEmpty(projectName)) {
					copmpanyProjectResults.add(projectName);
				}
				if (!StringUtils.isEmpty(unitName)) {
					copmpanyNameResults.add(unitName);
				}
			}
		}

		// matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
		// termQuery：不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到。
		// fuzzyQuery：模糊查询。
		// rangeQuery：范围内查询。
		// 企业名称
		if (copmpanyNameResults.size() != 0 && !"null".equals(copmpanyNameResults)) {
			// terms表示in查询，projectName加上".keyword",表示精确匹配
			boolQueryBuilder.must(QueryBuilders.termsQuery("unitName.keyword", copmpanyNameResults));
		}
		// 项目名称
		if (copmpanyProjectResults.size() != 0 && !"null".equals(copmpanyProjectResults)) {
			// terms表示in查询，projectName加上".keyword",表示精确匹配
			boolQueryBuilder.must(QueryBuilders.termsQuery("projectName.keyword", copmpanyProjectResults));
		}
		// 隐患地点---省
		if (!StringUtils.isEmpty(hiddenPlaceProvince) && !"null".equals(hiddenPlaceProvince)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("checkLocation.province", hiddenPlaceProvince));
		}
		// 隐患地点---市
		if (!StringUtils.isEmpty(hiddenPlaceCity) && !"null".equals(hiddenPlaceCity)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("checkLocation.city", hiddenPlaceCity));
		}
		// 隐患地点---区
		if (!StringUtils.isEmpty(hiddenPlaceArea) && !"null".equals(hiddenPlaceArea)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("checkLocation.county", hiddenPlaceArea));
		}
		// 隐患等级
		if (!StringUtils.isEmpty(hazardLevel) && !"null".equals(hazardLevel)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("riskLevel", hazardLevel));
		}
		// 隐患类别--一级
		if (!StringUtils.isEmpty(hazardCategoryLevelOne) && !"null".equals(hazardCategoryLevelOne)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("riskCategory.first", hazardCategoryLevelOne));
		}
		// 隐患等级--二级
		if (!StringUtils.isEmpty(hazardCategoryLevelTwo) && !"null".equals(hazardCategoryLevelTwo)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("riskCategory.second", hazardCategoryLevelTwo));
		}
		// 可能导致的事故
		if (!StringUtils.isEmpty(probableAccident) && !"null".equals(probableAccident)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("possibleAccidents", probableAccident));
		}

		sourceBuilder.query(boolQueryBuilder);

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(DataBaseIndexType.SAFETY_RISK_INFO_INDEX);
		searchRequest.source(sourceBuilder);
		searchRequest.types(DataBaseIndexType.TYPE);

		try {
			RestHighLevelClient client = RestHighLevelClientFactory.getRestHighLevelClientBean(dataBaseInfo);
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

			RestStatus restStatus = searchResponse.status();
			if (restStatus != RestStatus.OK) {
				resultMap.put("data", "");
				resultMap.put("status", "0");
				resultMap.put("total", 0);
				return mapper.writeValueAsString(resultMap);
			}
			SearchHits searchHits = searchResponse.getHits();
			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
			for (SearchHit hit : searchHits.getHits()) {
				Map<String, Object> source = hit.getSourceAsMap();
				results.add(source);

			}
			/*
			 * json转换实体 List<CompanyInfo> list = new ArrayList<>(); SearchHits searchHits =
			 * searchResponse.getHits(); ObjectMapper mapper = new ObjectMapper(); for
			 * (SearchHit hit : searchHits.getHits()) { String source =
			 * hit.getSourceAsString(); CompanyInfo book = mapper.readValue(source,
			 * CompanyInfo.class); list.add(book); }
			 */
			long totalHits = searchHits.getTotalHits();

			TimeValue took = searchResponse.getTook();
			log.info("查询成功！请求参数: {}, 用时{}毫秒", searchRequest.source().toString(), took.millis());
			resultMap.put("data", results);
			resultMap.put("status", "1");
			resultMap.put("total", totalHits);
			return mapper.writeValueAsString(resultMap);
		} catch (IOException e) {
			log.error("查询失败！原因: {}", e.getMessage(), e);
		}

		return "{'data':'','status':'','total':0}";
	}

	/**
	 * 根据索引、类型、id查询数据
	 * 
	 * @return
	 */
	@Override
	public String idsQuery(String index, String type, String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(id)) {
				return "{'data':'','status':'1','total':0}";
			}
			RestHighLevelClient client = RestHighLevelClientFactory.getRestHighLevelClientBean(dataBaseInfo);
			IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(id);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(queryBuilder);
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.source(sourceBuilder);
			searchRequest.types(type);

			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits searchHits = searchResponse.getHits();
			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
			for (SearchHit hit : searchHits.getHits()) {
				Map<String, Object> source = hit.getSourceAsMap();
				results.add(source);

			}
			ObjectMapper mapper = new ObjectMapper();

			TimeValue took = searchResponse.getTook();
			log.info("查询成功！请求参数: {}, 用时{}毫秒", searchRequest.source().toString(), took.millis());
			resultMap.put("data", results);
			resultMap.put("status", "1");
			resultMap.put("total", "1");
			return mapper.writeValueAsString(resultMap);
		} catch (IOException e) {
			log.error("查询失败！原因: {}", e.getMessage(), e);
		}
		return "{'data':'','status':'0','total':0}";
	}

	// 根据行业分类从company_info索引获取企业项目信息
	private SearchHits getProjectByIndustry(String industryLevelOne, String industryLevelTwo, String hazardUnits) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.size(10000);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolean isreturn = true;
		// 行业分类---一级
		if (!StringUtils.isEmpty(industryLevelOne) && !"null".equals(industryLevelOne)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("firstIndustry", industryLevelOne));
			isreturn = false;
		}
		// 行业分类---二级
		if (!StringUtils.isEmpty(industryLevelTwo) && !"null".equals(industryLevelTwo)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("secondIndustry", industryLevelTwo));
			isreturn = false;
		}
		// 隐患单位（模糊查询）
		if (!StringUtils.isEmpty(hazardUnits) && !"null".equals(hazardUnits)) {
			boolQueryBuilder.must(QueryBuilders.fuzzyQuery("unitName", hazardUnits));
			isreturn = false;
		}
		// 查询条件都为空时，直接返回
		if (isreturn) {
			return null;
		}
		sourceBuilder.query(boolQueryBuilder);

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(DataBaseIndexType.COMPANY_INFO_INDEX);
		searchRequest.source(sourceBuilder);
		searchRequest.types(DataBaseIndexType.TYPE);

		try {
			RestHighLevelClient client = RestHighLevelClientFactory.getRestHighLevelClientBean(dataBaseInfo);
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

			RestStatus restStatus = searchResponse.status();
			if (restStatus != RestStatus.OK) {
				return null;
			}
			SearchHits searchHits = searchResponse.getHits();

			TimeValue took = searchResponse.getTook();
			log.info("查询成功！请求参数: {}, 用时{}毫秒", searchRequest.source().toString(), took.millis());

			return searchHits;
		} catch (IOException e) {
			log.error("查询失败！原因: {}", e.getMessage(), e);
		}
		return null;
	}

}
