package com.pang.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.pang.mapper.UserMapper;
import com.pang.mapper.UserRoleMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private RoleMapper roleMapper;
	private UserMapper userMapper;
	private UserRoleMapper userRoleMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("用户名不能为空！");
		}
		User user = userMapper.selectOne(
				new QueryWrapper<User>().lambda().eq(User::getUsername, username)
		);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在！");
		}
		List<UserRole> userRoles = userRoleMapper.selectList(
				new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserid, user.getId())
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
