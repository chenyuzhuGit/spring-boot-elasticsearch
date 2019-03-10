package com.elasticsearch.root;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elasticsearch.root.dao.DataOperationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataOperationTest {
	@Autowired
	private DataOperationService service;

	@Test
	public void contextLoads() {
		try {
			createDataMethods();
		} catch (ElasticsearchStatusException e) {
			// TODO Auto-generated catch block
			//这里可能是版本冲突，也可能是创建时，对象已存在的冲突
			if(e.status()==RestStatus.CONFLICT) {
				System.out.println("添加的数据，id已经存在。");
			}
//			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 创建索引；创建完成后，表中会生成一条记录
	 * 
	 * @throws Exception
	 */
	public void createDataMethods() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("user", "1kimchy");
		jsonMap.put("postDate", new Date());
		jsonMap.put("message", "1trying out Elasticsearch");
		service.addDatas("chenyuzhu_9", "doc", "1", jsonMap);
	}

//	public boolean existsDataMethods() throws Exception {
//	}

	public void deleteDataMethods() throws Exception {
	}

	public void getDataMethods() throws Exception {
	}
}
