package com.pang.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
        String respBean = "登录失败";
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            respBean = "用户名不存在或者密码输入错误!";
        }
        response.setStatus(401);
        request.getSession().setAttribute("msg", respBean);
        response.sendRedirect(request.getContextPath()+"/login");
	}
	
}
