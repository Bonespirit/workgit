package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

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
	
	private Integer hid;		//文章id
	private Integer cid;		//招聘信息单位id
	private String isschoolmate;//是否校友
	private String title;		//标题
	private String nature;		//工作性质
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
	
}
