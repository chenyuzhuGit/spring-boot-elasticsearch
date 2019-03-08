package com.elasticsearch.root.dao.service;

import java.util.Collection;
import java.util.List;

import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.SimpleQueryStringFlag;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.TypeQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.stereotype.Component;

import com.elasticsearch.root.dao.DataOperationService;
import com.elasticsearch.root.enums.BoolQueryType;

/**
 * 数据操作接口
 * 
 * @author Administrator
 *
 */
@Component
public class DataOperationServiceImpl extends BaseDaoServiceImpl implements DataOperationService {
	private BoolQueryBuilder boolQueryBuilder = null;
	private DisMaxQueryBuilder dixMaxQueryBuilder = null;

	@Override
	public AbstractQueryBuilder<?> matchAllQuery(BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> matchQuery(String field, String value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		MatchQueryBuilder query = QueryBuilders.matchQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> matchPhraseQuery(String field, String value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		MatchPhraseQueryBuilder query = QueryBuilders.matchPhraseQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> matchPhrasePrefixQuery(String field, String value, BoolQueryType type)
			throws Exception {
		// TODO Auto-generated method stub
		MatchQueryBuilder query = QueryBuilders.matchQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> multiMatchQuery(Object text, String[] fieldNames, BoolQueryType type)
			throws Exception {
		// TODO Auto-generated method stub
		MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(text, fieldNames);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> queryStringQuery(String field, String value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		QueryStringQueryBuilder query = QueryBuilders.queryStringQuery(field).defaultField(value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> simpleQueryStringQuery(String field, String value, SimpleQueryStringFlag flag,
			BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		SimpleQueryStringBuilder query = QueryBuilders.simpleQueryStringQuery(field).flags(flag);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> commonTermsQuery(String field, String value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		CommonTermsQueryBuilder query = QueryBuilders.commonTermsQuery(field, value);
		boolQuery(query, type);

		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termQuery(String field, String value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermQueryBuilder query = QueryBuilders.termQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, Collection<?> values, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, values);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, double[] value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, float[] value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, int[] value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, Object[] value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, long[] value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> termsQuery(String field, String[] value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TermsQueryBuilder query = QueryBuilders.termsQuery(field, value);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> rangeQuery(String field, Object from, Object to, boolean includeLower,
			boolean includeUpper, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		RangeQueryBuilder query = QueryBuilders.rangeQuery(field).from(from).to(to).includeLower(includeLower)
				.includeUpper(includeUpper);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> existsQuery(String field, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		ExistsQueryBuilder query = QueryBuilders.existsQuery(field);
		boolQuery(query, type);

		return query;
	}

	@Override
	public AbstractQueryBuilder<?> prefixQuery(String field, String prefix, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		PrefixQueryBuilder query = QueryBuilders.prefixQuery(field, prefix);
		boolQuery(query, type);

		return query;
	}

	@Override
	public AbstractQueryBuilder<?> wildcardQuery(String field, String prefix, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		WildcardQueryBuilder query = QueryBuilders.wildcardQuery(field, prefix);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> regexpQuery(String field, String regexp, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		RegexpQueryBuilder query = QueryBuilders.regexpQuery(field, regexp);
		boolQuery(query, type);

		return query;
	}

	@Override
	public AbstractQueryBuilder<?> fuzzyQuery(String field, String value, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		FuzzyQueryBuilder query = QueryBuilders.fuzzyQuery(field, value);
		boolQuery(query, type);

		return query;
	}

	@Override
	public AbstractQueryBuilder<?> typeQuery(String indextype, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		TypeQueryBuilder query = QueryBuilders.typeQuery(indextype);
		boolQuery(query, type);
		return query;
	}

	@Override
	public AbstractQueryBuilder<?> idsQuery(String[] types, String[] ids, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		if (ids == null || ids.length == 0) {
			return null;
		}
		IdsQueryBuilder query = null;
		if (types == null || types.length == 0) {
			query = QueryBuilders.idsQuery();
		} else {
			query = QueryBuilders.idsQuery(types);
		}
		query.addIds(ids);
		boolQuery(query, type);

		return query;
	}

	@Override
	public AbstractQueryBuilder<?> constantScoreQuery(QueryBuilder queryBuilder, BoolQueryType type) throws Exception {
		// TODO Auto-generated method stub
		ConstantScoreQueryBuilder query = QueryBuilders.constantScoreQuery(queryBuilder);
		boolQuery(query, type);
		return query;
	}

	@Override
	public void boolQuery(AbstractQueryBuilder<?> queryBuilder, BoolQueryType type) throws Exception {
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
	public AbstractQueryBuilder<?> disMaxQuery(List<QueryBuilder> queryBuilders, Float boost, Float tieBreaker)
			throws Exception {
		// TODO Auto-generated method stub
		for (QueryBuilder queryBuilder : queryBuilders) {
			dixMaxQueryBuilder.add(queryBuilder);
		}
		dixMaxQueryBuilder.boost(boost).tieBreaker(tieBreaker);
		return dixMaxQueryBuilder;
	}

	public void setBoolQueryBuilder(BoolQueryBuilder boolQueryBuilder) {
		this.boolQueryBuilder = boolQueryBuilder;
	}

	@Override
	public BoolQueryBuilder getBoolQueryBuilder() {
		// TODO Auto-generated method stub
		return boolQueryBuilder;
	}

	@Override
	public void setDixMaxQueryBuilder(DisMaxQueryBuilder dixMaxQueryBuilder) {
		this.dixMaxQueryBuilder = dixMaxQueryBuilder;
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
