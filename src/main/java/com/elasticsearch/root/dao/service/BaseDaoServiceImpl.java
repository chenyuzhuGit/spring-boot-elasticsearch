package com.elasticsearch.root.dao.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.elasticsearch.root.config.DataBaseConnectionInfo;
import com.elasticsearch.root.dao.BaseDaoService;
import com.elasticsearch.root.tools.RestHighLevelClientFactory;

/**
 * dao基类
 * 
 * @author Administrator
 *
 */
public class BaseDaoServiceImpl implements BaseDaoService {
	@Autowired
	private DataBaseConnectionInfo dataBaseInfo;

	@Override
	public RestHighLevelClient getClient() {
		// 获取单例的RestHighLevelClient对象 ,使用这个对象，执行对es数据库的操作
		return RestHighLevelClientFactory.getRestHighLevelClientBean(dataBaseInfo);
	}

}
