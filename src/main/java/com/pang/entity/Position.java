package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//职位表
@TableName("position")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Position implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;			//文章id
	private Integer cid;		//职位发布单位id
	private String pname;		//职位名称
	private Integer salary;		//薪资
	private String workplace;	//工作地点
	@TableField(exist=false)
	private String provinceSel; //省份
	@TableField(exist=false)
	private String citySel;		//城市
	private String nature;		//工作性质
	private String edu;			//学历要求
	private String skill;		//职位类别
	private Integer number;		//招聘人数
	private Integer validity;	//有效期限
	private String name;		//联系人
	private String telephone;	//联系电话
	private String speciality;	//需求专业
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
	
}
