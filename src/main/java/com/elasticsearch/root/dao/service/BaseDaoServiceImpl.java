package com.elasticsearch.root.dao.service;

import java.util.Collection;
import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elasticsearch.root.config.DataBaseConnectionInfo;
import com.elasticsearch.root.dao.BaseDaoService;
import com.elasticsearch.root.tools.RestHighLevelClientFactory;

@Component
public class BaseDaoServiceImpl implements BaseDaoService {
	@Autowired
	private DataBaseConnectionInfo dataBaseInfo;
	private BoolQueryBuilder boolQueryBuilder = null;
	private DisMaxQueryBuilder dixMaxQueryBuilder = null;

	@Override
	public void matchAllQuery(boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.matchAllQuery(), type);
	}

	@Override
	public void matchQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.matchQuery(field, value), type);
	}

	@Override
	public void matchPhraseQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.matchPhraseQuery(field, value), type);
	}

	@Override
	public void matchPhrasePrefixQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.matchQuery(field, value), type);
	}

	@Override
	public void multiMatchQuery(Object field, String[] fieldNames, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.multiMatchQuery(field, fieldNames), type);
	}

	@Override
	public void queryStringQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.queryStringQuery(field).defaultField(value), type);
	}

	@Override
	public void simpleQueryStringQuery(String field, String value, SimpleQueryStringFlag flag, boolQueryType type)
			throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.simpleQueryStringQuery(field).flags(flag), type);
	}

	@Override
	public void commonTermsQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.commonTermsQuery(field, value), type);

	}

	@Override
	public void termQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termQuery(field, value), type);
	}

	@Override
	public void termsQuery(String field, Collection<?> values, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, values), type);
	}

	@Override
	public void termsQuery(String field, double[] value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, value), type);
	}

	@Override
	public void termsQuery(String field, float[] value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, value), type);
	}

	@Override
	public void termsQuery(String field, int[] value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, value), type);
	}

	@Override
	public void termsQuery(String field, Object[] value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, value), type);
	}

	@Override
	public void termsQuery(String field, long[] value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, value), type);
	}

	@Override
	public void termsQuery(String field, String[] value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.termsQuery(field, value), type);
	}

	@Override
	public void rangeQuery(String field, Object from, Object to, boolean includeLower, boolean includeUpper,
			boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(
				QueryBuilders.rangeQuery(field).from(from).to(to).includeLower(includeLower).includeUpper(includeUpper),
				type);
	}

	@Override
	public void existsQuery(String field, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.existsQuery(field), type);

	}

	@Override
	public void prefixQuery(String field, String prefix, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.prefixQuery(field, prefix), type);

	}

	@Override
	public void wildcardQuery(String field, String prefix, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.wildcardQuery(field, prefix), type);
	}

	@Override
	public void regexpQuery(String field, String regexp, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.regexpQuery(field, regexp), type);

	}

	@Override
	public void fuzzyQuery(String field, String value, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.fuzzyQuery(field, value), type);

	}

	@Override
	public void typeQuery(String indextype, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.typeQuery(indextype), type);

	}

	@Override
	public void idsQuery(String[] types, String[] ids, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		if (ids == null || ids.length == 0) {
			return;
		}
		IdsQueryBuilder query = null;
		if (types == null || types.length == 0) {
			query = QueryBuilders.idsQuery();
		} else {
			query = QueryBuilders.idsQuery(types);
		}
		query.addIds(ids);
		boolQuery(query, type);

	}

	@Override
	public void constantScoreQuery(QueryBuilder queryBuilder, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		boolQuery(QueryBuilders.constantScoreQuery(queryBuilder), type);
	}

	@Override
	public void boolQuery(AbstractQueryBuilder<?> queryBuilder, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		if (boolQueryBuilder == null) {
			boolQueryBuilder = QueryBuilders.boolQuery();
		}
		switch (type) {
		case MUST:
			boolQueryBuilder.must(queryBuilder);
			break;
		case MUST_NOT:
			boolQueryBuilder.mustNot(queryBuilder);
			break;
		case SHOULD:
			boolQueryBuilder.should(queryBuilder);
			break;
		case FILTER:
			boolQueryBuilder.filter(queryBuilder);
			break;
		default:
			break;
		}
	}

	@Override
	public void disMaxQuery(List<QueryBuilder> queryBuilders, Float boost, Float tieBreaker) throws Exception {
		// TODO Auto-generated method stub
		if (dixMaxQueryBuilder == null) {
			dixMaxQueryBuilder = QueryBuilders.disMaxQuery();
		}
		for (QueryBuilder queryBuilder : queryBuilders) {
			dixMaxQueryBuilder.add(queryBuilder);
		}
		dixMaxQueryBuilder.boost(boost).tieBreaker(tieBreaker);
	}

	@Override
	public RestHighLevelClient getClient() {
		// 获取单例的RestHighLevelClient对象 ,使用这个对象，执行对es数据库的操作
		return RestHighLevelClientFactory.getRestHighLevelClientBean(dataBaseInfo);
	}

	@Override
	public BoolQueryBuilder getBoolQueryBuilder() {
		// TODO Auto-generated method stub
		return boolQueryBuilder;
	}

	@Override
	public DisMaxQueryBuilder getDisMaxQueryBuilder() {
		// TODO Auto-generated method stub
		return dixMaxQueryBuilder;
	}

	@Override
	public void cleanSearchConditions() {
		// TODO Auto-generated method stub
		boolQueryBuilder = null;
		dixMaxQueryBuilder = null;
	}

}
