package com.elasticsearch.root;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elasticsearch.root.dao.service.DataOperationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchSearchApplicationTests {
	@Autowired
	private DataOperationServiceImpl service;

	@Test
	public void contextLoads() throws Exception {
		IndexRequest request = new IndexRequest("chenyuzhu_cessssss", "doc");
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("user", "kimchy");
		jsonMap.put("postDate", new Date());
		jsonMap.put("message", "trying out Elasticsearch");
		request.source(XContentType.JSON);
//		request.source(jsonMap).opType(OpType.CREATE);
		service.getClient().index(request, RequestOptions.DEFAULT);
//		String[] unitNames = new String[2];
//		unitNames[0] = "accidentLocation.province"; // 字段1名称
//		unitNames[1] = "accidentLocation.city"; // 字段2名称
//		service.setBoolQueryBuilder(QueryBuilders.boolQuery());
//		service.multiMatchQuery("安阳市", unitNames, BoolQueryType.MUST);
//		String[] fields = new String[3];
//		fields[0] = "id"; // 字段1名称
//		fields[1] = "unitName"; // 字段2名称
//		fields[2] = "accidentTitle"; // 字段2名称
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		sourceBuilder.query(service.getBoolQueryBuilder());
//
//		SearchRequest searchRequest = new SearchRequest();
//		searchRequest.indices(DataBaseIndex.ACCIDENT_CASE_INDEX);
//		searchRequest.source(sourceBuilder);
//		searchRequest.types(DataBaseType.DOC_TYPE);
//		// 设置返回字段，包含哪些，排除哪些
//		sourceBuilder.fetchSource(fields, null);
//		try {
//			RestHighLevelClient client = service.getClient();
//			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//			RestStatus restStatus = searchResponse.status();
//			SearchHits searchHits = searchResponse.getHits();
//			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
//			for (SearchHit hit : searchHits.getHits()) {
//				Map<String, Object> source = hit.getSourceAsMap();
//				results.add(source);
//			}
//			ObjectMapper mapper = new ObjectMapper();
//
//			System.out.println(mapper.writeValueAsString(results));
//			System.out.println(searchRequest.source().toString());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
}
