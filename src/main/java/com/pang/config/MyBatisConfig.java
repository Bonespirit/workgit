package com.pang.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

@Configuration
public class MyBatisConfig {
	
	public static ThreadLocal<String> TABLE_NAME = new ThreadLocal<String>();
	
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
		//添加动态表名处理器
		DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
		Map<String, TableNameHandler> tableNameHandlerMap = new HashMap<String, TableNameHandler>();
		tableNameHandlerMap.put("screen_resume", new TableNameHandler() {
			@Override
			public String dynamicTableName(String sql, String tableName) {
				return TABLE_NAME.get();
			}
		});
		dynamicTableNameInnerInterceptor.setTableNameHandlerMap(tableNameHandlerMap);
		mybatisPlusInterceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
		return mybatisPlusInterceptor;
	}
}
