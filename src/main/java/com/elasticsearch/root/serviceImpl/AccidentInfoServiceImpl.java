package com.elasticsearch.root.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.elasticsearch.root.config.DataBaseIndexType;
import com.elasticsearch.root.service.AccidentInfoService;
import com.elasticsearch.root.tools.RestHighLevelClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 事故查询
 * 
 * @author Administrator
 *
 */
@Service
public class AccidentInfoServiceImpl implements AccidentInfoService {

	private Logger log = Loggers.getLogger(AccidentInfoServiceImpl.class);

	/**
	 * 事故查询
	 */
	@Override
	public String accidentInfoServiceList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();

		String startTimeOccurrence = request.getParameter("startTimeOccurrence");// 发生时间---开始
		String endTimeOccurrence = request.getParameter("endTimeOccurrence");// 发生时间---结束
		String accidentType = request.getParameter("accidentType");// 事故类型
		String takePlaceProvince = request.getParameter("takePlaceProvince");// 发生地点---省
		String takePlaceCity = request.getParameter("takePlaceCity");// 发生地点---市
		String takePlaceArea = request.getParameter("takePlaceArea");// 发生地点---区
		String hazardUnits = request.getParameter("hazardUnits");// 单位名称
		String industryLevelOne = request.getParameter("industryLevelOne");// 行业分类--一级
		String industryLevelTwo = request.getParameter("industryLevelTwo");// 行业分类--二级

		String pageSize = request.getParameter("pageSize");// 每页数量
		Integer numberPage = pageSize != null && !StringUtils.isEmpty(pageSize) ? Integer.parseInt(pageSize) : 10;
		String pageIndex = request.getParameter("pageIndex");// 页码数
		Integer pagination = pageIndex != null && !StringUtils.isEmpty(pageIndex) ? Integer.parseInt(pageIndex) : 1;
		Integer startIndex = (pagination - 1) * numberPage;

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(startIndex);// 起始位置
		sourceBuilder.size(numberPage);// 查询数量
		sourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		try {
			// matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
			// termQuery：不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到。
			// fuzzyQuery：模糊查询。
			// rangeQuery：范围内查询。
			// 事故类型
			if (!StringUtils.isEmpty(accidentType)) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("accidentType", accidentType));
			}

			// 发生时间---开始、发生时间---结束
			if (!StringUtils.isEmpty(startTimeOccurrence)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));// es数据库默认是0时区，我们使用的北京时间东八区，所以这里设置默认时区为0时区
				//结束时间为空时，默认为当前时间
				Date endTime = null;
				if (StringUtils.isEmpty(endTimeOccurrence)) {
					endTime = new Date();
				} else {
					endTime = sdf.parse(endTimeOccurrence);
				}
				boolQueryBuilder.must(QueryBuilders.rangeQuery("accidentTime").from(sdf.parse(startTimeOccurrence))// 开始时间
						.to(endTime)// 结束时间
						.includeLower(true)// 包含上界
						.includeUpper(true)// 包含上界
				);
			}
			// 事故地点---省
			if (!StringUtils.isEmpty(takePlaceProvince)) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("accidentLocation.province", takePlaceProvince));
			}
			// 事故地点---市
			if (!StringUtils.isEmpty(takePlaceCity)) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("accidentLocation.city", takePlaceCity));
			}
			// 事故地点---区
			if (!StringUtils.isEmpty(takePlaceArea)) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("accidentLocation.county", takePlaceArea));
			}
			// 企业名称
			if (!StringUtils.isEmpty(hazardUnits)) {
				// fuzzy表示模糊查询
				boolQueryBuilder.must(QueryBuilders.fuzzyQuery("unitName", hazardUnits));
			}
			// 行业分类---一级
			if (!StringUtils.isEmpty(industryLevelOne)) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("industryFirst", industryLevelOne));
			}
			// 行业分类---二级
			if (!StringUtils.isEmpty(industryLevelTwo)) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("industrySecond", industryLevelTwo));
			}

			sourceBuilder.query(boolQueryBuilder);

			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(DataBaseIndexType.ACCIDENT_CASE_INDEX);
			searchRequest.source(sourceBuilder);
			searchRequest.types(DataBaseIndexType.TYPE);

			RestHighLevelClient client = RestHighLevelClientFactory.getRestHighLevelClientBean();
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
			long totalHits = searchHits.getTotalHits();

			TimeValue took = searchResponse.getTook();
			log.info("查询成功！请求参数: {}, 用时{}毫秒", searchRequest.source().toString(), took.millis());
			resultMap.put("data", results);
			resultMap.put("status", "1");
			resultMap.put("total", totalHits);
			return mapper.writeValueAsString(resultMap);
		} catch (IOException e) {
			log.error("查询失败！原因: {}", e.getMessage(), e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			RestHighLevelClient client = RestHighLevelClientFactory.getRestHighLevelClientBean();
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

}
