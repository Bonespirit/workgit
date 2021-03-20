package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
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
	
	@TableId(value="id")
	private Integer id;		//文章id
	private Integer cid;		//招聘信息单位id
	private String isschoolmate;//是否校友
	private String title;		//标题
	@JSONField(format = "yyyy-MM-dd")
	private Date tdate;			//宣讲日期
	private String btime;		//开始时间
	private String address;		//宣讲地点
	private String school;		//宣讲学校
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
}
