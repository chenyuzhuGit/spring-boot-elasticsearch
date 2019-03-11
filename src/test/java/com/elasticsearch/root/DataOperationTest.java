package com.elasticsearch.root;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elasticsearch.root.dao.DataOperationService;
import com.elasticsearch.root.dao.DataSearchService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataOperationTest {
	@Autowired
	private DataOperationService service;

	@Test
	public void contextLoads() {
//		createDataMethods();
//		updateDataMethods();
//		combinationBulkRequest();
//		combinationBulkProcessor();

	}

	/**
	 * 创建索引；创建完成后，表中会生成一条记录
	 * 
	 * @throws Exception
	 */
	public void createDataMethods() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("user", "2kimchy");
		jsonMap.put("postDate", new Date());
		jsonMap.put("message", "2trying out Elasticsearch");
		IndexResponse indexResponse;
		try {
			indexResponse = service.addData("chenyuzhu_9", "doc", "2", jsonMap, null);
			String currentIndex = indexResponse.getIndex();
			String type = indexResponse.getType();
			String id = indexResponse.getId();
			long version = indexResponse.getVersion();
			if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
				System.out.println("创建了记录");
			} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
				System.out.println("更新了记录");
			}
			ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
			if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
				System.out.println("失败了：" + (shardInfo.getTotal() - shardInfo.getSuccessful()) + "条");
			}
			if (shardInfo.getFailed() > 0) {
				for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
					String reason = failure.reason();
				}
			}
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			// 这里可能是版本冲突，也可能是创建时，对象已存在的冲突
			if (e.status() == RestStatus.CONFLICT) {
				System.out.println("添加的数据，id已经存在。");
			}
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteDataMethods() {
		try {
			DeleteResponse deleteResponse = service.deleteData("chenyuzhu_9", "doc", "1", null);
			String index = deleteResponse.getIndex();
			String type = deleteResponse.getType();
			String id = deleteResponse.getId();
			long version = deleteResponse.getVersion();
			ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
			if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

			}
			if (shardInfo.getFailed() > 0) {
				for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
					String reason = failure.reason();
				}
			}
			if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
				System.out.println("未找到需要删除的内容");
			}
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			// 出现版本冲突时。会报这个异常
			if (e.status() == RestStatus.CONFLICT) {

			}
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateDataMethods() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("user", "3kimchy");
		jsonMap.put("postDate", new Date());
		jsonMap.put("message", "3trying out Elasticsearch");

		UpdateResponse updateResponse;
		try {
			updateResponse = service.updateData("chenyuzhu_9", "doc", "6", jsonMap, false, null, null, null);
			String currentIndex = updateResponse.getIndex();
			String type = updateResponse.getType();
			String id = updateResponse.getId();
			long version = updateResponse.getVersion();

			if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
				System.out.println("创建了记录");
			} else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
				System.out.println("更新了记录");
			} else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
				System.out.println("删除了记录");

			} else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
				System.out.println("什么都没做");
			}

			GetResult result = updateResponse.getGetResult();
			if (result.isExists()) {
				String sourceAsString = result.sourceAsString();
				Map<String, Object> sourceAsMap = result.sourceAsMap();
				byte[] sourceAsBytes = result.source();
			} else {

			}

			ReplicationResponse.ShardInfo shardInfo = updateResponse.getShardInfo();
			if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

			}
			if (shardInfo.getFailed() > 0) {
				for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
					String reason = failure.reason();
				}
			}
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("未找到要更新的数据");
			}
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void combinationBulkRequest() {
		ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse bulkResponse) {
				for (BulkItemResponse bulkItemResponse : bulkResponse) {
					DocWriteResponse itemResponse = bulkItemResponse.getResponse();
					switch (bulkItemResponse.getOpType()) {
					case INDEX:
					case CREATE:
						IndexResponse indexResponse = (IndexResponse) itemResponse;
						System.out.println("新增操作");
						break;
					case UPDATE:
						UpdateResponse updateResponse = (UpdateResponse) itemResponse;
						System.out.println("更新操作");
						break;
					case DELETE:
						DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
						System.out.println("删除操作");
					}
				}

				if (bulkResponse.hasFailures()) {
					System.out.println("发生错误");

				}

				for (BulkItemResponse bulkItemResponse : bulkResponse) {
					if (bulkItemResponse.isFailed()) {
						BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
					}
				}
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("添加的数据，id已经存在。");
			}
		};
		try {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("user", "3kimchy");
			jsonMap.put("postDate", new Date());
			jsonMap.put("message", "3trying out Elasticsearch");

			service.combinationBulkRequest(listener,
					new IndexRequest("chenyuzhu_9", "doc", "6").source(jsonMap).opType(DocWriteRequest.OpType.CREATE));
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			// 这里可能是版本冲突，也可能是创建时，对象已存在的冲突
			if (e.status() == RestStatus.CONFLICT) {
				System.out.println("添加的数据，id已经存在。");
			}
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void combinationBulkProcessor() {
		BulkProcessor.Listener listener = new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				int numberOfActions = request.numberOfActions();
				System.out.println("在每次执行a之前调用此方法 BulkRequest");
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				if (response.hasFailures()) {
					System.out.println("有failures");
				} else {
					System.out.println("没有failures");
				}
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("BulkRequest失败 时调用此方法");
			}
		};
		try {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("user", "3kimchy");
			jsonMap.put("postDate", new Date());
			jsonMap.put("message", "3trying out Elasticsearch");
			boolean isSeccussful = service.combinationBulkProcessor(listener,
					new IndexRequest("chenyuzhu_9", "doc", "8").source(jsonMap).opType(DocWriteRequest.OpType.CREATE),
					new IndexRequest("chenyuzhu_9", "doc", "9").source(jsonMap).opType(DocWriteRequest.OpType.CREATE));
		} catch (ElasticsearchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
