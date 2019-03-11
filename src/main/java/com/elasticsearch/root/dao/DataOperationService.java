package com.elasticsearch.root.dao;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

/**
 * 数据操作接口
 * 
 * @author Administrator
 *
 */
public interface DataOperationService extends BaseDaoService {

	// -------------数据操作--------开始---------
	/**
	 * 新增数据
	 * 
	 * @param index      索引
	 * @param indexType  类型
	 * @param documentId 文档id
	 * @param document   文档内容（只支持，json字符串、Map、XContentBuilder类型的参数）
	 * @param listener   异步执行的时候将会用到;异步执行无返回值，返回内容将在ActionListener的实现中体现。
	 * @return
	 * @throws Exception
	 */
	IndexResponse addData(String index, String indexType, String documentId, Object documentJson,
			ActionListener<IndexResponse> listener) throws Exception;

	/**
	 * 删除数据
	 * 
	 * @param index      索引
	 * @param indexType  类型
	 * @param documentId 文档id
	 * @param listener   异步执行的时候将会用到;异步执行无返回值，返回内容将在ActionListener的实现中体现。
	 * @return
	 * @throws Exception
	 */
	DeleteResponse deleteData(String index, String indexType, String documentId,
			ActionListener<DeleteResponse> listener) throws Exception;

	/**
	 * 更新数据
	 * 
	 * @param index      索引
	 * @param indexType  类型
	 * @param documentId 文档id
	 * @param document   文档内容（只支持，json字符串、Map、XContentBuilder类型的参数）
	 * @param isUpsert   当更新的文档不存在时：如果设置为ture，将会新增这条数据；设置为false，则不会新增，会报elasticSearchException异常
	 * @param includes   结果集中的记录，包含哪些字段（全输出时，可以设置为null，与excludes参数最好只使用一个）
	 * @param excludes   结果集中的记录，排除哪些字段（全输出时，可以设置为null，与includes参数最好只使用一个）
	 * @param listener   异步执行的时候将会用到;异步执行无返回值，返回内容将在ActionListener的实现中体现。
	 * @return
	 * @throws Exception
	 */
	UpdateResponse updateData(String index, String indexType, String documentId, Object document, boolean isUpsert,
			String[] includes, String[] excludes, ActionListener<UpdateResponse> listener) throws Exception;

	/**
	 * 判断指定数据是否存在
	 * 
	 * @param index      索引
	 * @param indexType  类型
	 * @param documentId 文档id
	 * @param listener   异步执行的时候将会用到;异步执行无返回值，返回内容将在ActionListener的实现中体现。
	 * @return
	 * @throws Exception
	 */
	Boolean existsData(String index, String indexType, String documentId, ActionListener<Boolean> listener)
			throws Exception;

	/**
	 * 批量执行，增删改操作
	 * 
	 * @param listener 异步执行的时候将会用到;异步执行无返回值，返回内容将在ActionListener的实现中体现。
	 * @param request  增删改请求数组
	 * @return
	 * @throws Exception
	 */
	BulkResponse combinationBulkRequest(ActionListener<BulkResponse> listener, DocWriteRequest<?>... request)
			throws Exception;

	/**
	 * 批量执行，增删改操作;允许它们被添加到所述处理器透明地执行索引/更新/删除的操作简化了批量API的使用。
	 * 
	 * @param listener 异步执行的时候将会用到;异步执行无返回值，返回内容将在ActionListener的实现中体现。
	 * @param request  增删改请求数组
	 * @return
	 * @throws Exception
	 */
	Boolean combinationBulkProcessor(BulkProcessor.Listener listener, DocWriteRequest<?>... request) throws Exception;
	// -------------数据操作--------结束---------
}
