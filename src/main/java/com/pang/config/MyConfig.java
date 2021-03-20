package com.pang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.pang.interceptor.LoginInterceptor;


@Configuration
public class MyConfig implements WebMvcConfigurer{
	
	//拦截器配置，其中静态资源需要手动放行 可以添加多个拦截器 使用register注册即可
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoginInterceptor())
//				.addPathPatterns("/**")
//				.excludePathPatterns("/error","/","/login","/css/**","/fonts/**","/images/**","/js/**");
//	}
	
//	@Bean
//	public WebMvcConfigurer webMvcConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void configurePathMatch(PathMatchConfigurer configurer) {
//				UrlPathHelper urlPathHelper = new UrlPathHelper();
//				//不移除 “；”后面的内容，矩阵变量生效
//				urlPathHelper.setRemoveSemicolonContent(false);
//				configurer.setUrlPathHelper(urlPathHelper);
//			}
//		};
//	}
	
	//开启矩阵变量的第一种方式
//	@Override
//	public void configurePathMatch(PathMatchConfigurer configurer) {
//		UrlPathHelper urlPathHelper = new UrlPathHelper();
//		//不移除 “；”后面的内容，矩阵变量生效
//		urlPathHelper.setRemoveSemicolonContent(false);
//		configurer.setUrlPathHelper(urlPathHelper);
//	}
}
