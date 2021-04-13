package com.pang.entity;

import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//记录学生已投递简历的职位id
@TableName("stu_deliver")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StuDeliver {
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	private Integer sid;	//学生id
	private Integer pid;	//职位id
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;		//投递时间
}
