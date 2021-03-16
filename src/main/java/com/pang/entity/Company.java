package com.pang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//单位信息实体类
@TableName("company")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable{
	private static final long serialVersionUID = 1L;
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;				//单位编号
	private String cname;			//单位名称
	private String introduce;		//单位简介
	private String logourl;			//单位logo url路径
	private Integer capital;		//单位注册资本
	private String officetelephone;	//单位办公电话
	private String postcode;		//邮编
	private String fax;				//传真
	private String zpemail;			//招聘邮箱
	private String nature;			//单位性质
	private String industry;		//单位行业
	private String scale;			//单位规模
	private String location;		//单位所在地
	private String address;			//单位详细地址
	private String isschoolmate;	//是否校友
	private String licenseurl;		//营业执照license url路径
	
	@TableField(exist=false)
	private String provinceSel; 	//省份
	@TableField(exist=false)
	private String citySel;			//城市
	@TableField(exist=false)
	private String card;			//校友卡号
	@TableField(exist=false)
	private String username;		//登录名
	@TableField(exist=false)
	private String password;		//密码
	@TableField(exist=false)
	private String name;			//联系人姓名
	@TableField(exist=false)
	private String telephone;		//联系人电话
	@TableField(exist=false)
	private String cemail;			//联系邮箱
}