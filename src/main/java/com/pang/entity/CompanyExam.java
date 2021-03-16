package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//单位注册申请表
@TableName("company_exam")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyExam implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;			//单位编号
	private String cname;		//单位名称
	private String card;		//校友卡号
	@JSONField(format = "yyyy-MM-dd")
	private Date adate;			//申请时间
	private String username;	//登录名
	private String password;	//密码
	private String name;		//联系人姓名
	private String telephone;	//联系人电话
	private String cemail;		//联系邮箱
	private String isschoolmate;//是否校友
	
}
