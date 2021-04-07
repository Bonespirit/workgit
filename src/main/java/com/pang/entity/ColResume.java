package com.pang.entity;

import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//收藏职位
@TableName("col_resume")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ColResume{
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	private Integer sid;	//学生id
	private Integer pid;	//职位id
	private Integer cid;	//单位id
	private String pname;	//职位名称
	private String cname;	//单位名称
	private String address;	//工作地点
	@JSONField(format = "yyyy-MM-dd")
	private Date cdate;		//收藏时间
	
	@TableField(exist=false)
	private boolean isDeliver; //是否已投递
}
