package com.elasticsearch.root.dao.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Component;

import com.elasticsearch.root.dao.DataOperationService;

/**
 * 数据操作接口
 * 
 * @author Administrator
 *
 */
@Component
public class DataOperationServiceImpl extends BaseDaoServiceImpl implements DataOperationService {
	@Override
	public IndexResponse addData(String index, String indexType, String documentId, Object documentJson,
			ActionListener<IndexResponse> listener) throws Exception {
		// TODO Auto-generated method stub
		// 参数：索引、类型、记录id
		IndexRequest request = new IndexRequest(index, indexType, documentId);
//		request.routing("routing");
//		request.parent("parent");
		request.timeout(TimeValue.timeValueSeconds(1));
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		// 判断新增操作的类型，索引操作有两种，一种是INDEX，当要索引的文档id已经存在时，是更新原来文档。一种是CREATE，当索引文档id存在时，会抛出该文档已存在的错误。
		request.opType(DocWriteRequest.OpType.CREATE);
//		request.setPipeline("pipeline");
		if (documentJson instanceof String) {
			// 需要制定数据包装类型
			request.source((String) documentJson, XContentType.JSON);
		} else if (documentJson instanceof XContentBuilder) {
			// 作为XContentBuilder对象提供的文档源，Elasticsearch内置帮助程序以生成JSON内容
			request.source((XContentBuilder) documentJson);
		} else if (documentJson instanceof Map<?, ?>) {
			// 提供的文档源Map自动转换为JSON格式
			request.source((Map<?, ?>) documentJson);
		}
		IndexResponse indexResponse = null;
		if (listener == null) {
			indexResponse = getClient().index(request, RequestOptions.DEFAULT);
		} else {
			// 异步执行无返回值，返回内容将在ActionListener的实现中体现。
			getClient().indexAsync(request, RequestOptions.DEFAULT, listener);
		}

		return indexResponse;
	}

	@Override
	public DeleteResponse deleteData(String index, String indexType, String documentId,
			ActionListener<DeleteResponse> listener) throws Exception {
		// TODO Auto-generated method stub
		DeleteRequest request = new DeleteRequest(index, indexType, documentId);
		request.timeout(TimeValue.timeValueMinutes(2));
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		DeleteResponse deleteResponse = null;
		if (listener == null) {
			deleteResponse = getClient().delete(request, RequestOptions.DEFAULT);
		} else {
			// 异步执行无返回值，返回内容将在ActionListener的实现中体现。
			getClient().deleteAsync(request, RequestOptions.DEFAULT, listener);
		}
		return deleteResponse;
	}

	@Override
	public UpdateResponse updateData(String index, String indexType, String documentId, Object document,
			boolean isUpsert, String[] includes, String[] excludes, ActionListener<UpdateResponse> listener)
			throws Exception {
		// TODO Auto-generated method stub
		UpdateRequest request = new UpdateRequest(index, indexType, documentId);

		request.timeout(TimeValue.timeValueSeconds(1));
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		request.retryOnConflict(3);
		request.detectNoop(false);// 禁用noop检测
		request.docAsUpsert(isUpsert);// 如果尚未存在，则表明必须将部分文档用作upsert文档。
		// 指示无论文档是否存在，脚本都必须运行，即如果文档尚不存在，脚本将负责创建文档。
//		request.scriptedUpsert(isUpsert);//该版本暂无脚本更新数据功能2018-03-11
		if (document instanceof String) {
			// 需要制定数据包装类型
			request.doc((String) document, XContentType.JSON);
		} else if (document instanceof XContentBuilder) {
			// 作为XContentBuilder对象提供的文档源，Elasticsearch内置帮助程序以生成JSON内容
			request.doc((XContentBuilder) document);
		} else if (document instanceof Map<?, ?>) {
			// 提供的文档源Map自动转换为JSON格式
			request.doc((Map<?, ?>) document);
		}

		if (includes == null && excludes == null) {
			// 启动源检索，会将更新后的数据查询出来，并包含在request对象中
			request.fetchSource(true);
		} else {
			// 配置输出内容，包含哪些字段，排除哪些字段
			request.fetchSource(new FetchSourceContext(true, includes, excludes));
		}

		UpdateResponse updateResponse = null;
		if (listener == null) {
			updateResponse = getClient().update(request, RequestOptions.DEFAULT);
		} else {
			// 异步执行无返回值，返回内容将在ActionListener的实现中体现。
			getClient().updateAsync(request, RequestOptions.DEFAULT, listener);
		}

		return updateResponse;
	}

	@Override
	public Boolean existsData(String index, String indexType, String documentId, ActionListener<Boolean> listener)
			throws Exception {
		// TODO Auto-generated method stub
		GetRequest getRequest = new GetRequest(index, indexType, documentId);
		// 由于exists()只返回true或false，我们建议关闭提取_source和任何存储的字段，以便请求能更快执行
		// 禁止返回_source内容（结果集不返回该记录的内容）
		getRequest.fetchSourceContext(new FetchSourceContext(false));
		// 禁止抓取储存字段（不返回该记录的内容）
		getRequest.storedFields("_none_");
		Boolean exists = null;
		if (listener == null) {
			exists = getClient().exists(getRequest, RequestOptions.DEFAULT);
		} else {
			// 异步执行无返回值，返回内容将在ActionListener的实现中体现。
			getClient().existsAsync(getRequest, RequestOptions.DEFAULT, listener);
		}
		return exists;
	}

	@Override
	public BulkResponse combinationBulkRequest(ActionListener<BulkResponse> listener, DocWriteRequest<?>... request)
			throws Exception {
		// TODO Auto-generated method stub
		BulkRequest combinationRequest = new BulkRequest();
		combinationRequest.timeout(TimeValue.timeValueMinutes(2));
		combinationRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		combinationRequest.add(request);
		BulkResponse bulkResponse = null;
		if (listener == null) {
			bulkResponse = getClient().bulk(combinationRequest, RequestOptions.DEFAULT);
		} else {
			getClient().bulkAsync(combinationRequest, RequestOptions.DEFAULT, listener);
		}
		return bulkResponse;
	}

	@Override
	public Boolean combinationBulkProcessor(BulkProcessor.Listener listener, DocWriteRequest<?>... request)
			throws Exception {
		// TODO Auto-generated method stub

		// 这里使用的java8lambda表达式，创建BiConsumer对象，并实现BiConsumer的接口
		BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer = (bulkRequest, bulkListener) -> getClient()
				.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkListener);

		// BulkProcessor通过build()从中调用方法来
		// 创建BulkProcessor.Builder。该RestHighLevelClient.bulkAsync()
		// 方法将用于执行BulkRequest引擎盖。
		BulkProcessor.Builder builder = BulkProcessor.builder(bulkConsumer, listener);
		builder.setBulkActions(500);// 根据当前添加的操作数设置何时刷新新的批量请求（默认为1000，使用-1禁用它）
		builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));// 设置何时根据当前添加的操作大小刷新新的批量请求（默认为5Mb，使用-1禁用它）
		builder.setConcurrentRequests(0);// 设置允许执行的并发请求数（默认为1，使用0只允许执行单个请求）
		builder.setFlushInterval(TimeValue.timeValueSeconds(10L));// BulkRequest如果间隔超过，则 设置刷新间隔刷新任何挂起（默认为未设置）
		builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));// 设置一个最初等待1秒的常量后退策略，最多重试3次。见BackoffPolicy.noBackoff()，
		BulkProcessor bulkProcessor = builder.build();
		for (int i = 0; i < request.length; i++) {
			bulkProcessor.add(request[i]);
		}
		bulkProcessor.flush();
		// 如果所有大容量请求都已完成，则该方法返回true;如果在所有大容量请求完成之前的等待时间已经过去，则返回false
		boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
		// close()方法可用于立即关闭BulkProcessor,马上关闭，不管还有多少没有处理完(不建议使用)
//		bulkProcessor.close();
		// 这两种方法都在关闭处理器之前刷新添加到处理器的请求，并且禁止向处理器添加任何新请求。
		return terminated;
	}

}
