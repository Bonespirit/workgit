package com.pang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//密码找回
@TableName("forget")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Forget implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String username;
	private String email;
	private String cname;
	private String license;
}
