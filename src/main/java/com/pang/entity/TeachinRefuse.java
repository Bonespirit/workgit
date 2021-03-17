package com.pang.entity;

import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//审核失败记录表
@TableName("teachin_refuse")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeachinRefuse{
	
	@TableId(value="id")
	private Integer id;		//招聘信息单位id
	private String title;		//标题
	private Date adate;			//申请时间
	private String reason;		//解释
}
