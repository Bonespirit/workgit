package com.pang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//简历项目经历表
@TableName("resume_pro_exp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResumeProject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;		//编号
	private Integer stuid;	//学生编号
	private String name;	//项目名称
	private String duty;	//担任职务
	private String mdescribe;//项目描述
	private String btime;		//开始时间
	private String etime;		//结束时间
}
