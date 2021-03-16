package com.pang.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pang.entity.User;

public class LoginInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if(user != null)
		{
			System.out.println("放行1"+path);
			return true;
		}
//		else {
//			//session无效，从cookie中取值
//			Cookie[] cookies = request.getCookies();
//			if(cookies != null) {
//				for(Cookie cookie:cookies) {
//					if(cookie.getName() == "account") {
//						String[] account = cookie.getValue().split("#");
//						System.out.println("cookie");
//						session.setAttribute("user", new User(account[0],account[1]));
//						return true;
//					}
//				}
//			}
//		}
		request.setAttribute("msg", "请先登录!");
		//response.sendRedirect("/");
		request.getRequestDispatcher("/").forward(request, response);
		return false;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
