package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//宣讲会详情表
@TableName("teachin")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Teachin implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer hid;		//文章id
	private Integer cid;		//招聘信息单位id
	private String isschoolmate;//是否校友
	private String title;		//标题
	private Date tdate;			//宣讲日期
	private String btime;		//开始时间
	private String address;		//宣讲地点
	private String school;		//宣讲学校
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
}
