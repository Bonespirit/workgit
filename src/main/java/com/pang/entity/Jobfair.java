package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//招聘会信息表
@TableName("jobfair")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Jobfair implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;			//简章id
	private String title;		//标题
	private String isschoolmate;//是否校友
	private Date hdate;			//举办时间
	private String btime;		//开始时间
	private String address;		//举办地点
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
}
