package com.pang.entity;

import org.springframework.security.core.GrantedAuthority;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//权限表
@Data
@TableName("s_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private Integer id;			//权限ID
	private String rolename;	//权限名称：ROLE_student(学生)、ROLE_admin(管理员)、ROLE_enterprise(企业)
	private String remark;		//备注 解析权限
	
	@Override
	public String getAuthority() {
		return rolename;
	}

}
