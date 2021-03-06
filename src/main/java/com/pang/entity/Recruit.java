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

//在线招聘信息实体
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("recruit")
public class Recruit implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;		//文章id
	private Integer cid;		//招聘信息单位id
	private String isschoolmate;//是否校友
	private String title;		//标题
	private String nature;		//工作性质
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
	
}
