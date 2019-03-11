package com.elasticsearch.root.dao.service;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.stereotype.Component;

import com.elasticsearch.root.dao.DataAggregationService;

/**
 * 数据聚合查询接口
 * 
 * @author Administrator
 *
 */
@Component
public class DataAggregationServiceImpl extends BaseDaoServiceImpl implements DataAggregationService {

	@Override
	public void getCount(String field) {
		// TODO Auto-generated method stub
		AggregationBuilders.avg(field);
	}

	@Override
	public void getMax(String field) {
		// TODO Auto-generated method stub
		AggregationBuilders.max(field);
	}

	@Override
	public void getMin(String field) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAvg(String field) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSum(String field) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getStats(String field) {
		// TODO Auto-generated method stub

	}

}
