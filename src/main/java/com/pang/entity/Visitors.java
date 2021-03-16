package com.pang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//来访人员名单表
@TableName("visitors")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Visitors implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;			//编号
	private Integer lid;		//宣讲申请表id
	private String name;		//姓名
	private String idcard;		//身份证号码
	private String gender;		//性别
	private String duty;		//职务
	private String telephone;	//联系电话
}
