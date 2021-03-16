package com.pang.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("t_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
	
	private Integer id;
	private Integer userID;
	private Integer roleID;

}
