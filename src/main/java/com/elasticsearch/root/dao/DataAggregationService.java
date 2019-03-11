package com.elasticsearch.root.dao;

/**
 * 数据聚合查询接口
 * 
 * @author Administrator
 *
 */
public interface DataAggregationService extends BaseDaoService {
	void getCount(String field) throws Exception;

	void getMax(String field) throws Exception;

	void getMin(String field) throws Exception;

	void getAvg(String field) throws Exception;

	void getSum(String field) throws Exception;

	void getStats(String field) throws Exception;
}
