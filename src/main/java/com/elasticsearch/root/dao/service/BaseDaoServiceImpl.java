package com.elasticsearch.root.dao.service;

import java.util.Collection;
import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elasticsearch.root.config.DataBaseConnectionInfo;
import com.elasticsearch.root.dao.BaseDaoService;
import com.elasticsearch.root.dao.BoolQueryCombination;
import com.elasticsearch.root.tools.RestHighLevelClientFactory;

@Component
public class BaseDaoServiceImpl implements BaseDaoService {
	@Autowired
	private DataBaseConnectionInfo dataBaseInfo;
	// 获取单例的RestHighLevelClient对象 ,使用这个对象，执行对es数据库的操作
	private BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	private DisMaxQueryBuilder dixMaxQueryBuilder = QueryBuilders.disMaxQuery();

	@Override
	public void matchAllQuery(BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.matchAllQuery());
	}

	@Override
	public void matchQuery(String field, String value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.matchQuery(field, value));
	}

	@Override
	public void matchPhraseQuery(String field, String value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.matchPhraseQuery(field, value));
	}

	@Override
	public void matchPhrasePrefixQuery(String field, String value, BoolQueryCombination bqueryCombinnation)
			throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.matchQuery(field, value));
	}

	@Override
	public void multiMatchQuery(Object field, String[] fieldNames, BoolQueryCombination bqueryCombinnation)
			throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.multiMatchQuery(field, fieldNames));
	}

	@Override
	public void queryStringQuery(String field, String value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.queryStringQuery(field).defaultField(value));
	}

	@Override
	public void simpleQueryStringQuery(String field, String value, SimpleQueryStringFlag flag,
			BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.simpleQueryStringQuery(field).flags(flag));
	}

	@Override
	public void commonTermsQuery(String field, String value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.matchQuery(field, value));

	}

	@Override
	public void termQuery(String field, String value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.matchQuery(field, value));
	}

	@Override
	public void termsQuery(String field, Collection<?> values, BoolQueryCombination bqueryCombinnation)
			throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, values));
	}

	@Override
	public void termsQuery(String field, double[] value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, value));
	}

	@Override
	public void termsQuery(String field, float[] value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, value));
	}

	@Override
	public void termsQuery(String field, int[] value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, value));
	}

	@Override
	public void termsQuery(String field, Object[] value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, value));
	}

	@Override
	public void termsQuery(String field, long[] value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, value));
	}

	@Override
	public void termsQuery(String field, String[] value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.termsQuery(field, value));
	}

	@Override
	public void rangeQuery(String field, Object from, Object to, boolean includeLower, boolean includeUpper,
			BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.rangeQuery(field).from(from).to(to)
				.includeLower(includeLower).includeUpper(includeUpper));
	}

	@Override
	public void existsQuery(String field, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.existsQuery(field));

	}

	@Override
	public void prefixQuery(String field, String prefix, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.prefixQuery(field, prefix));

	}

	@Override
	public void wildcardQuery(String field, String prefix, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.wildcardQuery(field, prefix));
	}

	@Override
	public void regexpQuery(String field, String regexp, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.regexpQuery(field, regexp));

	}

	@Override
	public void fuzzyQuery(String field, String value, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.fuzzyQuery(field, value));

	}

	@Override
	public void typeQuery(String type, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.typeQuery(type));

	}

	@Override
	public void idsQuery(String[] types, String[] ids, BoolQueryCombination bqueryCombinnation) throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.idsQuery(types).addIds(ids));

	}

	@Override
	public void constantScoreQuery(QueryBuilder queryBuilder, BoolQueryCombination bqueryCombinnation)
			throws Exception {
		// TODO Auto-generated method stub
		bqueryCombinnation.combination(boolQueryBuilder, QueryBuilders.constantScoreQuery(queryBuilder));
	}

	@Override
	public void boolQuery(AbstractQueryBuilder<?> queryBuilder, boolQueryType type) throws Exception {
		// TODO Auto-generated method stub
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
		for (QueryBuilder queryBuilder : queryBuilders) {
			dixMaxQueryBuilder.add(queryBuilder);
		}
		dixMaxQueryBuilder.boost(boost).tieBreaker(tieBreaker);
	}

	public RestHighLevelClient getClient() {
		return RestHighLevelClientFactory.getRestHighLevelClientBean(dataBaseInfo);
	}

}
