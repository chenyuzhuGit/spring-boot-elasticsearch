package com.elasticsearch.root.dao;

import java.util.Collection;
import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.SimpleQueryStringFlag;

/**
 * dao基类
 * 
 * @author Administrator
 *
 */
public interface BaseDaoService {
	// --------------------Match All Queries---start------------
	/**
	 * 搜索指定索引、类型的全部文档
	 * 
	 * @throws Exception
	 */
	void matchAllQuery(boolQueryType type) throws Exception;
	// --------------------Match All Queries---End------------

	// --------------------Full Text Queries---start------------
	/**
	 * 分词查询。
	 * 
	 * @throws Exception
	 */
	void matchQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * 短语匹配查询。 首先解析查询字符串value，产生一个词条列表。然后根据词条列表的所有词条，查询出包含所有词条并且符合词条列表顺序的文档
	 * 以短语形式查询，查询时关键字不会被分词，而是直接以一个字符串的形式查询
	 * 
	 * @throws Exception
	 */
	void matchPhraseQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * 短语匹配查询。 首先解析查询字符串value，产生一个词条列表。然后根据词条列表的所有词条，查询出包含所有词条并且符合词条列表顺序的文档
	 * 以短语形式查询，查询时关键字不会被分词，而是直接以一个字符串的形式查询 和phraseQuery 用法是一样的，区别就在于它允许对最后一个词条前缀匹配
	 * 
	 * @throws Exception
	 */
	void matchPhrasePrefixQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * 多字段匹配查询。 多个字段匹配一个值（为全匹配查询）
	 * 
	 * @throws Exception
	 */
	void multiMatchQuery(Object field, String[] fieldNames, boolQueryType type) throws Exception;

	/**
	 * 查询解析查询字符串 如下查询 hotelName 字段 查询 QueryString 为 "四 AND 酒 AND 店 " 查询结果是 hotelName
	 * 同时包含 "四" "酒" "店" 如果不明确 AND 则是OR的关系，包含任意一个term即被命中
	 * 
	 * @throws Exception
	 */
	void queryStringQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * 简单查询解析查询字符串 如下查询 hotelName 字段 查询 QueryString 为 "四 AND 酒 AND 店 " 查询结果是
	 * hotelName 同时包含 "四" "酒" "店" 如果不明确 AND 则是OR的关系，包含任意一个term即被命中
	 * 
	 * @throws Exception
	 */
	void simpleQueryStringQuery(String field, String value, SimpleQueryStringFlag flag, boolQueryType type)
			throws Exception;
	// --------------------Full Text Queries---End------------

	// --------------------Term level Queries---start------------
	/**
	 * 常用词查询
	 * 
	 * @throws Exception
	 */
	void commonTermsQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * 全匹配查询。 不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到 【相当于sql：field = 1】
	 * 
	 * @throws Exception
	 */
	void termQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * in查询。 一个字段匹配多个值（为全匹配查询） termsQuery【相当于sql：field in (1,2,3)】
	 * 
	 * @throws Exception
	 */
	void termsQuery(String field, Collection<?> values, boolQueryType type) throws Exception;

	void termsQuery(String field, double[] value, boolQueryType type) throws Exception;

	void termsQuery(String field, float[] value, boolQueryType type) throws Exception;

	void termsQuery(String field, int[] value, boolQueryType type) throws Exception;

	void termsQuery(String field, Object[] value, boolQueryType type) throws Exception;

	void termsQuery(String field, long[] value, boolQueryType type) throws Exception;

	void termsQuery(String field, String[] value, boolQueryType type) throws Exception;

	/**
	 * 范围查询。 rangeQuery【相当于sql：field > 1 and field<30】
	 * 
	 * @throws Exception
	 */
	void rangeQuery(String field, Object from, Object to, boolean includeLower, boolean includeUpper,
			boolQueryType type) throws Exception;

	/**
	 * 字段非空查询。 查询某个字段值非空的记录 existsQuery【相当于sql：field is not null】
	 * 
	 * @throws Exception
	 */
	void existsQuery(String field, boolQueryType type) throws Exception;

	/**
	 * 字段前缀查询。 查询某个字段值非空的记录 prefixQuery【相当于sql：field like '张*'】
	 * 
	 * @throws Exception
	 */
	void prefixQuery(String field, String prefix, boolQueryType type) throws Exception;

	/**
	 * 通配符查询。 查询某个字段值非空的记录 wildcardQuery【相当于sql：field like '*张_三*'】
	 * 查找指定字段包含与指定模式匹配的术语的文档，其中模式支持单字符通配符（？）和多字符通配符（*）
	 * 
	 * @throws Exception
	 */
	void wildcardQuery(String field, String prefix, boolQueryType type) throws Exception;

	/**
	 * 正则表达式查询。 查找指定字段包含与指定的正则表达式匹配的术语的文档。
	 * 
	 * @throws Exception
	 */
	void regexpQuery(String field, String regexp, boolQueryType type) throws Exception;

	/**
	 * 模糊度查询。 通过fuzziness设置可以错几个
	 * 查找指定字段包含与指定术语模糊相似的术语的文档。模糊度是以Levenshtein编辑距离1或2来衡量的。
	 * 
	 * @throws Exception
	 */
	void fuzzyQuery(String field, String value, boolQueryType type) throws Exception;

	/**
	 * 类型查询。 相当于sql：select * from table 查找指定类型的文档。
	 * 
	 * @throws Exception
	 */
	void typeQuery(String indextype, boolQueryType type) throws Exception;

	/**
	 * 类型主键查询。 相当于sql：select * from table where id in (1,2) 查找具有指定类型和ID的文档。
	 * 
	 * @throws Exception
	 */
	void idsQuery(String[] types, String[] ids, boolQueryType type) throws Exception;

	// --------------------Term level Queries---End---------
	// --------------------Compound Queries---start---------
	// 复合查询包装其他复合或叶子查询，以组合其结果和分数，更改其行为，或从查询切换到筛选器上下文。
	/**
	 * 包装另一个查询并仅返回与查询中每个文档的查询升压相等的常量分数的查询。 参数:queryBuilder—将查询包装在一个常量分数查询中
	 * 
	 * @throws Exception
	 */
	void constantScoreQuery(QueryBuilder queryBuilder, boolQueryType type) throws Exception;

	/**
	 * 用于组合多个叶子或复合查询子句的默认查询，包含must, should, must_not, or filter
	 * 。must和should子句将它们的分数组合在一起 - 匹配子句越多越好 - 而must_not和filter子句在过滤器上下文中执行。
	 * must：返回的文档必须满足must子句的条件，并且参与计算分值 filter：返回的文档必须满足filter子句的条件。不参与计算分值
	 * must_not：返回的文档必须不满足must_not定义的条件。不参与评分。
	 * should：返回的文档可能满足should子句的条件。在一个Bool查询中，如果没有must或者filter，有一个或者多个should子句，那么只要满足一个就可以返回。minimum_should_match参数定义了至少满足几个子句。
	 * shold理解【匹配的相关度】
	 * 
	 * @throws Exception
	 */
	void boolQuery(AbstractQueryBuilder<?> queryBuilder, boolQueryType type) throws Exception;

	/**
	 * boolQuery查询的类型
	 * 
	 * @author Administrator
	 *
	 */
	enum boolQueryType {
		MUST, MUST_NOT, SHOULD, FILTER
	}

	/**
	 * 一个接受多个查询的查询，并返回与任何查询子句匹配的任何文档。虽然bool查询组合了所有匹配查询的分数，但dis_max查询使用单个最佳匹配查询子句的分数。
	 * 一个查询，它生成由其子查询生成的文档的并集，并为每个文档评分由任何子查询生成的该文档的最大分数，以及任何其他匹配子查询的平局增量。
	 * 当在具有不同增强因子的多个字段中搜索单词时，这非常有用（因此不能将字段等效地组合到单个搜索字段中）。我们希望主要分数是与最高提升相关联的分数，而不是字段分数的总和（如布尔查询所给出的）。如果查询是“albino
	 * elephant”，则这确保匹配一个字段的“albino”和匹配另一个的“elephant”获得比匹配两个字段的“albino”更高的分数。要获得此结果，请同时使用Boolean
	 * Query和DisjunctionMax
	 * Query：对于每个术语，DisjunctionMaxQuery在每个字段中搜索它，而将这些DisjunctionMaxQuery的集合组合成BooleanQuery。
	 * 
	 * @throws Exception
	 */
	void disMaxQuery(List<QueryBuilder> queryBuilders, Float boost, Float tieBreaker) throws Exception;

	/**
	 * 获取数据库操作对象
	 * 
	 * @return
	 */
	RestHighLevelClient getClient();

	/**
	 * 获取bollQuery对象
	 * 
	 * @return
	 */
	BoolQueryBuilder getBoolQueryBuilder();

	/**
	 * 获取DisMaxQueryBuilder对象
	 * 
	 * @return
	 */
	DisMaxQueryBuilder getDisMaxQueryBuilder();

	/**
	 * 清空查询条件
	 * 
	 * @return
	 */
	void cleanSearchConditions();
}
