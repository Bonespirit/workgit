package com.pang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//mapper扫描 不需要再每个mapper添加@mapper注解
@MapperScan("com.pang.mapper")
@SpringBootApplication
//@EnableCaching  	//开启缓存注解
//@EnableRabbit 		//开启rabbitmq注解模式
@EnableAsync		//开启异步注解模式
//@EnableScheduling	//开启定时任务注解
public class TestBegin {
	public static void main(String[] args) {
		SpringApplication.run(TestBegin.class, args);
	}
}
