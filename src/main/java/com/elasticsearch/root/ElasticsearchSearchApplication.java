package com.elasticsearch.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * spring boot程序启动入口
 * 
 * @author Administrator
 *
 */
@SpringBootApplication
public class ElasticsearchSearchApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchSearchApplication.class, args);
	}

	// 为了打包springboot项目(使用外部tomcat时)
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ElasticsearchSearchApplication.class);
	}

}
