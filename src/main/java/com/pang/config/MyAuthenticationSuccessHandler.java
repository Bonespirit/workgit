package com.pang.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.pang.entity.Role;
import com.pang.entity.User;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		RequestCache cache = new HttpSessionRequestCache();
		SavedRequest savedRequest = cache.getRequest(request, response);
		String url = null;
		if (savedRequest != null) {
			url = savedRequest.getRedirectUrl();
		}
		if (url == null) {
			for(Role role:user.getRoleList()) {
				if (role.getRolename().equals("ROLE_sadmin") || role.getRolename().equals("ROLE_gadmin")) {
					response.sendRedirect(request.getContextPath()+"/teacher/shsq/page/1");
					break;
				}else if (role.getRolename().equals("ROLE_enterprise")) {
					System.out.println("enter");
					response.sendRedirect(request.getContextPath()+"/enterprise/zpzn");
					break;
				}else {
					System.out.println("student");
					response.sendRedirect(request.getContextPath()+"/student/czsc");
					break;
				}
			}
		}else {
			System.out.println(url);
			response.sendRedirect(request.getContextPath()+url);
		}
	}
}
