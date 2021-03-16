package com.pang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pang.service.impl.UserDetailsServiceImpl;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	//授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		//过滤
		http.authorizeRequests().antMatchers("/index/**").permitAll()
			.antMatchers("/table/**").hasRole("VIP");
		
		//开启自动配置登录功能
		http.formLogin().loginPage("/userLogin")
			.usernameParameter("username")
			.passwordParameter("password");
		
		http.logout().logoutSuccessUrl("/index.html").permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//			.withUser("pang").password(new BCryptPasswordEncoder().encode("123")).roles("VIP");
	}
}
