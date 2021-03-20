package com.pang.entity;

import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//高级检索的职位表信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobs {
	private String id;			//职位编号
	private String pname;		//职位名称
	private String salary;		//薪资
	private String wnature;		//工作性质
	private String edu;			//学历要求
	private String workplace;	//工作地点
	private String cname;		//单位名称
	private String cnature;		//单位性质
	private String scale;		//单位规模
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;			//发布时间
	private String speciality;		//需求专业
}
