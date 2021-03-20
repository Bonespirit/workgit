package com.pang.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.entity.User;

public interface UserService extends IService<User>{
	
	/**
	 * 	登录验证
	 * @param username
	 * @param password
	 * @param sessionid
	 * @return
	 */
	public String checkLogin(String username,String password,HttpServletRequest request);
	
}
