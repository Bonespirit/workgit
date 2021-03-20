package com.pang.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.User;
import com.pang.mapper.UserMapper;
import com.pang.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Override
	public String checkLogin(String username, String password, HttpServletRequest request) {
//		String sessionid = request.getRequestedSessionId();
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		User user = this.getOne(queryWrapper);
		System.out.println("111");
		if (user == null) {
			return "用户名不存在！";
		}else if(!user.getPassword().equals(password)){
//			throw new PasswordExpiredException("ashfushif");
			return "密码错误";
		}else {
//			HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
//			hashOperations.put(sessionid, "name", user.getName());
//			hashOperations.put(sessionid, "foreignid", user.getForeignkey());
//			hashOperations.put(sessionid, "telephone", user.getTelephone());
//			hashOperations.put(sessionid, "email", user.getEmail());
//			hashOperations.put(sessionid, "isschoolmate", user.getIsschoolmate());
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			System.out.println("success");
		}
		return "success";
	}
	
}
