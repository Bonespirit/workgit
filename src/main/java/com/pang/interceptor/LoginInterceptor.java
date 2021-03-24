package com.pang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getParameter("mark") == null) {
			request.setAttribute("msg", "请输入验证码");
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		}
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		String checkcode = request.getParameter("mark").toLowerCase();
		System.out.println(checkcode);
		System.out.println(((String)session.getAttribute("VerifyCode")).toLowerCase());
		if(checkcode.equals(((String)session.getAttribute("VerifyCode")).toLowerCase()))
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
		request.setAttribute("msg", "验证码错误");
		//response.sendRedirect("/");
		request.getRequestDispatcher("/login").forward(request, response);
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
