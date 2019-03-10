package com.elasticsearch.root;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elasticsearch.root.dao.IndexOperationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexOperationTest {
	@Autowired
	private IndexOperationService service;

	@Test
	public void contextLoads() throws Exception {
//		createIndexMethods();
//		boolean exists=existsIndexMethods();
//		System.out.println(exists);
		try {
//			deleteIndexMethods();
		} catch (ElasticsearchException exception) {
			if (exception.status() == RestStatus.NOT_FOUND) {
				System.out.println("删除索引失败：没有找到您要删除的索引");
			}
		} catch (Exception exception) {

		}
//		getIndexMethods();

	}

	/**
	 * 创建索引；创建完成后，表中会生成一条记录
	 * 
	 * @throws Exception
	 */
	public void createIndexMethods() throws Exception {
		String type = "doc";
		XContentBuilder builder = XContentFactory.jsonBuilder();
		// TODO Auto-generated method stub
		builder.startObject();
		{
			{
				builder.startObject(type);
				{
					{
						builder.startObject("properties");
						{
							{
								builder.startObject("user");
								{
									{
										builder.field("type", "text");
									}
								}
								builder.endObject();
							}
							{
								builder.startObject("postDate");
								{
									{
										builder.field("type", "date");
									}
								}
								builder.endObject();
							}
							{
								builder.startObject("message");
								{
									{
										builder.field("type", "text");
									}
								}
								builder.endObject();
							}
						}
						builder.endObject();
					}
				}
				builder.endObject();
			}
		}
		builder.endObject();
		@SuppressWarnings("unused")
		CreateIndexResponse response = service.createIndex("chenyuzhu_9", type, builder, "测试");
	}

	public boolean existsIndexMethods() throws Exception {
		return (service.existsIndex("xinweike"));
	}

	public void deleteIndexMethods() throws Exception {
		AcknowledgedResponse response = service.deleteIndex("chenyuzhu_9");
		if (response.isAcknowledged()) {
			System.out.println("删除索引成功");
		}
	}

	public void getIndexMethods() throws Exception {
		@SuppressWarnings("unused")
		GetIndexResponse response = service.getIndex(new String[] { "chenyuzhu_9", "chenyuzhu_8" });
	}
}
