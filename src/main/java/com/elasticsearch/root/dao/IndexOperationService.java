package com.elasticsearch.root.dao;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 索引操作接口
 * 
 * @author Administrator
 *
 */
public interface IndexOperationService extends BaseDaoService {
	/**
	 * 创建索引
	 * 
	 * @param indexName 索引名称
	 * @param type      索引类型
	 * @param builder   字段结构
	 * @param alias     索引别名
	 * @return 返回结果对象
	 * @throws Exception
	 */
	CreateIndexResponse createIndex(String indexName, String type, XContentBuilder builder, String alias)
			throws Exception;

	/**
	 * 校验索引是否存在
	 * 
	 * @param indexName
	 * @throws Exception
	 */
	boolean existsIndex(String indexName) throws Exception;

	/**
	 * 删除索引
	 * 
	 * @param indexName 索引名称
	 * @return
	 * @throws Exception
	 */
	AcknowledgedResponse deleteIndex(String indexName) throws Exception;

	/**
	 * 获取索引信息
	 * 
	 * @param indexNames 索引数组
	 * @param type       类型数组
	 * @return
	 * @throws Exception
	 */
	GetIndexResponse getIndex(String[] indexNames) throws Exception;

}
