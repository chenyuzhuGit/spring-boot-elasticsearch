package com.elasticsearch.root.dao;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * dao基类
 * 
 * @author Administrator
 *
 */
public interface BaseDaoService {
	/**
	 * 获取数据库操作对象
	 * 
	 * @return
	 */
	RestHighLevelClient getClient();
}
