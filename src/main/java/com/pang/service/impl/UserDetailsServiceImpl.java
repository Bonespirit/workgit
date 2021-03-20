package com.pang.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pang.entity.Role;
import com.pang.entity.User;
import com.pang.entity.UserRole;
import com.pang.mapper.RoleMapper;
import com.pang.mapper.UserRoleMapper;
import com.pang.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserService userService;
	@Autowired
	UserRoleMapper userRoleMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("开始验证");
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("用户名不能为空！");
		}
		User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在！");
		}
		List<UserRole> userRoles = userRoleMapper.selectList(
				new QueryWrapper<UserRole>().eq("userid", user.getId())
		);
		if (userRoles != null && !userRoles.isEmpty()) {
			List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleid).collect(Collectors.toList());
			List<Role> roles = roleMapper.selectList(
					new QueryWrapper<Role>().lambda().in(Role::getId, roleIds)
			);
			
			user.setRoleList(roles);
		}
		return user;
	}
}
