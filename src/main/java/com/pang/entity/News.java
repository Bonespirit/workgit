package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("news")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;		//文章id
	private String title;	//文章标题
	private Integer mcolumn;	//文章栏目
	private Integer grade;	//文章
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;		//发布时间
	private Integer hot;	//浏览次数
	
	@TableField(exist=false)
	private String cname;	//栏目名称
	@TableField(exist=false)
	private String curl;	//栏目导航地址
}
