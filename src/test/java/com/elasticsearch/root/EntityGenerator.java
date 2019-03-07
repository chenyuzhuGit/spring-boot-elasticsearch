package com.elasticsearch.root;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elasticsearch.root.dao.service.BaseDaoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 实体生成器
 * 
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityGenerator {
	@Autowired
	private BaseDaoServiceImpl service;
	private RestHighLevelClient client;
	// 索引
	private String index = "safety_risk_info";
	// 索引类型
	private String type = "doc";
	// 文件路径
	private String filePath = "C:\\\\Users\\\\Administrator\\\\Desktop";
	// 文件名称
	private String fileName = "safetyRiskInfo";
	// 文件类型
	private String fileType = "java";

	@Test
	public void contextLoads() {
		client = service.getClient();
		try {
			getElasticClient(index, type);
			createFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据索引和类型，获取对应的字段及字段类型
	 * 
	 * @param jsonObject
	 * @throws JSONException
	 */
	public Object getElasticClient(String index, String type) throws Exception {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		GetMappingsRequest request = new GetMappingsRequest();
		request.indices(index);
		request.types(type);

		request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
		request.masterNodeTimeout("1m");

		request.indicesOptions(IndicesOptions.lenientExpandOpen());

		GetMappingsResponse getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);

		ActionListener<GetMappingsResponse> listener = new ActionListener<GetMappingsResponse>() {
			@Override
			public void onResponse(GetMappingsResponse putMappingResponse) {
				// System.out.println("111111111111111111111111111111");
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("222222222222222222222222222222222");

			}
		};

		client.indices().getMappingAsync(request, RequestOptions.DEFAULT, listener);

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = getMappingResponse.mappings();
		MappingMetaData typeMapping = allMappings.get(index).get(type);
		Map<String, Object> mapping = typeMapping.sourceAsMap();

		ObjectMapper mapper = new ObjectMapper();
//		System.out.println(mapper.writeValueAsString(mapping));
		JSONObject js = new JSONObject(mapper.writeValueAsString(mapping));
		// 遍历字段json组，并组合新生成文件的字段弄
		System.out.println("--------字段信息开始---(名称:类型)----------");
		traverseJsonArray(js);
		System.out.println("--------字段信息结束---(名称:类型)----------");
		return list;
	}

	/**
	 * 遍历字段json组，并组合新生成文件的字段弄
	 * 
	 * @param jsonObject
	 * @throws JSONException
	 */
	private String parentKey = "";
	private JSONObject parentJson = new JSONObject();

	public void traverseJsonArray(JSONObject jsonObject) throws JSONException {
		@SuppressWarnings("unchecked")
		Iterator<String> it = jsonObject.keys();
		while (it.hasNext()) {
			// 获得key
			String key = it.next();
//			String value = jsonObject.getString(key);

			JSONObject js;
			try {
				// 如果json的key为fields时，跳过
				if (key.equals("fields")) {
					continue;
				}
				// 获取当前key下的json内容
				js = jsonObject.getJSONObject(key);
				// 如果js中包含properties属性时
				if (js.has("properties")) {
					parentKey = key;
					parentJson = js.getJSONObject("properties");
				}
				// 如果js包含type属性，并且不包括keyword属性时
				if (js.has("type") && !"keyword".equals(key)) {
					if (parentKey != null && parentKey.length() > 0) {
						System.out.println(parentKey + "." + key + ":" + js.getString("type"));
						// 组合后面想输出文件，写入的内容
						combination(js.getString("type"), parentKey + "." + key);
					} else {
						System.out.println(key + ":" + js.getString("type"));
						// 组合后面想输出文件，写入的内容
						combination(js.getString("type"), key);
					}
				}
				traverseJsonArray(js);
				if (!parentJson.has(key)) {
					parentKey = "";
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}

		}
	}

	/**
	 * 写入内容并输出java文件
	 * 
	 * @throws IOException
	 */
	public void createFile() throws IOException {
		File dir = new File(filePath);
		// 一、检查放置文件的文件夹路径是否存在，不存在则创建
		if (!dir.exists()) {
			dir.mkdirs();// mkdirs创建多级目录
		}
		File checkFile = new File(filePath + "/" + fileName + "." + fileType);
		FileWriter writer = null;
		try {
			// 二、检查目标文件是否存在，不存在则创建
			if (checkFile.exists()) {
				checkFile.delete();
			}
			checkFile.createNewFile();// 创建目标文件
			// 三、向目标文件中写入内容
			// FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
			writer = new FileWriter(checkFile, true);
			writer.append("package com.elasticsearch.root.entity;");
			writer.append(System.getProperty("line.separator"));
			writer.append("import java.io.Serializable;");
			writer.append(System.getProperty("line.separator"));
			writer.append("import java.util.Date;");
			writer.append(System.getProperty("line.separator"));
			writer.append("import org.springframework.data.annotation.Id;");
			if (isHasDateField) {
				writer.append(System.getProperty("line.separator"));
				writer.append("import org.springframework.format.annotation.DateTimeFormat;");
			}
			writer.append(System.getProperty("line.separator"));
			writer.append("import org.springframework.data.elasticsearch.annotations.Document;");
			writer.append(System.getProperty("line.separator"));
			writer.append(System.getProperty("line.separator"));
			writer.append("@Document(indexName = \"" + index + "\", type = \"" + type
					+ "\", indexStoreType = \"fs\", shards = 5, replicas = 1, refreshInterval = \"-1\")");
			writer.append(System.getProperty("line.separator"));
			writer.append("public class " + fileName + " implements Serializable {");
			for (String string : list) {
				writer.append(System.getProperty("line.separator"));
				writer.append(string);
			}
			writer.append(System.getProperty("line.separator"));
			writer.append("	private Integer pageSize = 10;");
			writer.append(System.getProperty("line.separator"));
			writer.append("	private Integer pageIndex = 1;");
			writer.append(System.getProperty("line.separator"));
			writer.append("}");

			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer)
				writer.close();
		}
	}

	/**
	 * 
	 * @param fieldType
	 * @param fieldName
	 */
	// list保存将要插入文件的字段内容
	List<String> list = new ArrayList<String>();

	public void combination(String fieldType, String fieldName) {
		String finalFieldType = typeConversion(fieldType);
		String finalFieldName = fieldConversion(fieldName);
		if ("Date".equals(finalFieldType)) {
			list.add("	@DateTimeFormat(pattern = \"yyyy-MM-dd\")");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("	");
		sb.append("private");
		sb.append("	");
		sb.append(finalFieldType);
		sb.append("	");
		sb.append(finalFieldName);
		sb.append(";");
		list.add(sb.toString());
	}

	boolean isHasDateField = false;// 是否包含date类型字段

	/**
	 * 映射es数据库字段类型和java字段类型 注：这里只是基本的类型转换，其他复杂/组合类型，视情况定
	 * 
	 * @param type
	 * @return
	 */
	public String typeConversion(String type) {
		if ("text".equals(type) || "keyword".equals(type)) {
			return "String";
		} else if ("long".equals(type)) {
			return "Long";
		} else if ("integer".equals(type)) {
			return "Integer";
		} else if ("short".equals(type)) {
			return "Short";
		} else if ("byte".equals(type)) {
			return "Byte";
		} else if ("double".equals(type)) {
			return "Double";
		} else if ("float".equals(type)) {
			return "Float";
		} else if ("date".equals(type)) {
			isHasDateField = true;
			return "Date";
		} else if ("boolean".equals(type)) {
			return "Boolean";
		} else {
			return type;
		}
	}

	// 处理带.的字段名称
	public String fieldConversion(String field) {
		if (field != null && field.length() > 0) {
			if (field.contains(".")) {
				field = field.replace(".", "_");
			}
		}
		return field;
	}

}
