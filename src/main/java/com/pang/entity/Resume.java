package com.pang.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//学生简历基本信息表
@TableName("resume_ge_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Resume implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;
	private String pname;		//期望职位
	private String salary;		//期望薪资
	private String city;		//期望城市
	private String language;	//语言能力
	private String assess;		//自我评价
	private String award;		//获奖情况
	private String hobby;		//个人爱好
	private String practice;	//实习经历
	private String skill;		//技能特长
	private String headurl;		//头像
	private String resumeurl;	//简历附件
	
	@TableField(exist=false)
	private String[] name;		//项目名称
	@TableField(exist=false)
	private String[] duty;		//担任职务
	@TableField(exist=false)
	private String[] btime;		//开始时间
	@TableField(exist=false)
	private String[] etime;		//结束时间
	@TableField(exist=false)
	private String[] describe;	//项目描述
	@TableField(exist=false)
	private List<ResumePractice> practices;//实习经历
	@TableField(exist=false)
	private List<ResumeProject> project;		//项目经历
}
