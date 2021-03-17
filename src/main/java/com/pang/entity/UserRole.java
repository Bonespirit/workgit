package com.pang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//权限和用户关系表
@TableName("t_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	private Integer userid;
	private Integer roleid;

}
