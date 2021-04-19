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

//专业信息
@TableName("majors")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Majors implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;		//文章id
	private String cid;		//学院代码
	private String cname;	//学院名称
	private String mid;		//专业代码
	private String mname;	//专业名称
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;		//发布时间
}
