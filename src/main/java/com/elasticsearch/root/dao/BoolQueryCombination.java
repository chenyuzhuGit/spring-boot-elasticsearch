package com.elasticsearch.root.dao;

import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * boolQuery的组合
 * 
 * @author Administrator
 *
 */
public abstract class BoolQueryCombination {
	protected void combinationBefore() {

	}

	protected void combinationAfter() {

	}

	/**
	 * 组合
	 */
	abstract public void combination(BoolQueryBuilder boolQueryBuilder, AbstractQueryBuilder<?> queryBuilder);
}
