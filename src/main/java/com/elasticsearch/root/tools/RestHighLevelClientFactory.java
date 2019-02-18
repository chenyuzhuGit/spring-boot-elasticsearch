package com.elasticsearch.root.tools;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.elasticsearch.root.config.DataBaseConnectionInfo;

/**
 * 返回单例的client
 * 
 * @author Administrator
 *
 */
public class RestHighLevelClientFactory {
	private static volatile RestHighLevelClient restHighLevelClient = null;

	public static RestHighLevelClient getRestHighLevelClientBean(DataBaseConnectionInfo info) {
		if (restHighLevelClient == null) {
			synchronized (RestHighLevelClient.class) {
				if (restHighLevelClient == null) {
					restHighLevelClient = new RestHighLevelClient(
							RestClient.builder(new HttpHost(info.getIp(), info.getPost(), info.getScheme())));
				}
			}
		}
		return restHighLevelClient;
	}
}
