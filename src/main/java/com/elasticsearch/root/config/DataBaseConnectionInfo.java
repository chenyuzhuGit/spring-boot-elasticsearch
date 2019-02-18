package com.elasticsearch.root.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库连接信息
 * 
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix = "custom.elasticsearch")
public class DataBaseConnectionInfo {
	private String scheme;
	private String ip;
	private int post;// -1表示默认端口

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

}
