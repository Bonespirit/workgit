package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//简历投递的信息处理表
@TableName("screen_resume")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResumeProcess implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;		//编号
	private Integer stuid;	//学生id
	private Integer comid;	//单位id
	private String cname;	//单位名称
	private Integer pid;	//职位id
	private String pname;	//职位名称
	private String name;	//姓名
	private String gender;	//性别
	private String edu;		//学历
	private String major;	//专业
	private String city;	//期望城市
	@JSONField(format = "yyyy-MM-dd")
	private Date update;	//更新时间
	private String status;	//简历状态
}
