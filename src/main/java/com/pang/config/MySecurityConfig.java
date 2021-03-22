package com.pang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pang.service.impl.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig{
	
	//用人单位权限管理
	@Configuration
	@Order(1)
	public static class EnpWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{
		
		@Autowired
		UserDetailsServiceImpl userDetailsServiceImpl;
		@Autowired
		MyAccessDeniedHandler myAccessDeniedHandler;
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/static/**","/","/upload/**","/news/**")
			.antMatchers("/search/**","/student/czsc")
			.antMatchers("/enterprise/zpzn","/enterprise/syxx","/enterprise/zyjs");
		}
		
		//授权
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().disable();
			//过滤
			http.antMatcher("/enterprise/**")
				.authorizeRequests()
				.antMatchers("/eplogin","/enterprise/register","/enterprise/forget").permitAll()
				.antMatchers("/enterprise/**").hasAnyRole("enterprise","sadmin")
				.and()
				.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
			
			//开启自动配置登录功能
			http.formLogin()
				.loginPage("/eplogin")
				.loginProcessingUrl("/eplogin")
				.defaultSuccessUrl("/enterprise/zpzn")
				.failureHandler(new MyAuthenticationFailureHandler())
				.successHandler(new MyAuthenticationSuccessHandler());
			
			http.logout().deleteCookies("JSESSIONID");
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
		}
	}
	
	//教职工权限管理
	@Configuration
	@Order(2)
	public static class StaffWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{
		
		@Autowired
		UserDetailsServiceImpl userDetailsServiceImpl;
		@Autowired
		MyAccessDeniedHandler myAccessDeniedHandler;
		
		@Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        // 设置默认的加密方式（强hash方式加密）
	        return new BCryptPasswordEncoder();
	    }
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().disable();
			//过滤
			http.authorizeRequests()
				.antMatchers("/views/**","/login").permitAll()
				.antMatchers("/views/shsqc/**","/views/shsqt/**","/teacher/**").hasAnyRole("sadmin","gadmin")
				.antMatchers("/student/**").hasAnyRole("student","sadmin")
				.and()
				.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
			
			//开启自动配置登录功能
			http.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
				.failureHandler(new MyAuthenticationFailureHandler())
				.successHandler(new MyAuthenticationSuccessHandler());
			
			http.logout().deleteCookies("JSESSIONID");
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
		}
	}
}
