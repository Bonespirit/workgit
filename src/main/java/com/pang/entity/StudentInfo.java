package com.pang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//学生基本信息表
@TableName("stu_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;
	private String name;		//姓名
	private String gender;		//性别
	private String age;			//年龄
	private String nation;		//民族
	private String edu;			//学历
	private String live;		//常住地区
	private String politics;	//政治面貌
	private String major;		//专业
	private String telephone;	//手机
	private String email;		//邮箱
	
	@TableField(exist=false)
	private Resume resume;		//学生简历内容
	
}
