package com.pang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//专业信息
@TableName("majors")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Majors implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;		//文章id
	private String cid;		//学院代码
	private String cname;	//学院名称
	private String mid;		//专业代码
	private String mname;	//专业名称
}
