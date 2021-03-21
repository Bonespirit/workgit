package com.pang.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//高级检索keyword实体
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchKey {
	
	private String keyword;		//关键字
	private String industry;	//行业
	private String skill;		//职位类别
	private String majorcode;	//专业
	private String citycode;	//工作地点
	private String salary;		//薪资
	private String wnature;		//工作性质
	private String edu;			//学历要求
	private String cnature;		//单位性质
	private String scale;		//单位规模
	private Date pdate;			//发布时间
	
}
