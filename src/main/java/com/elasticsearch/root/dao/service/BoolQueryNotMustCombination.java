package com.elasticsearch.root.dao.service;

import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;

import com.elasticsearch.root.dao.BoolQueryCombination;

public class BoolQueryNotMustCombination extends BoolQueryCombination {
	@Override
	public void combination(BoolQueryBuilder boolQueryBuilder, AbstractQueryBuilder<?> queryBuilder) {
		// TODO Auto-generated method stub
		combinationBefore();
		boolQueryBuilder.mustNot(queryBuilder);
		combinationAfter();
	}
}