//package com.pang.config;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.pang.exception.ValidateCodeException;
//
//@Component
//public class ValidateCodeFilter extends OncePerRequestFilter{
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		if(StringUtils.pathEquals("/login",request.getRequestURI()) && "post".equalsIgnoreCase(request.getMethod()))
//        {	
//			String code = ((String) request.getSession().getAttribute("VerifyCode")).toLowerCase();
//        	String mark = request.getParameter("mark");
//        	if (!code.equals(mark.toLowerCase())) {
//        		new MyAuthenticationFailureHandler().onAuthenticationFailure(request,response,new ValidateCodeException("验证码错误"));
//        		return;
//    		}
//        }
//		filterChain.doFilter(request, response);
//	}
//	
//}
