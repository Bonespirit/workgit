package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//双选会信息表
@TableName("sxh")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SxhInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;			//简章id
	private String title;		//标题
	private Date hdate;			//举办时间
	private String time;		//举办时间
	private String address;		//举办地点
	private Date pdate;			//发布时间
	private Integer hot;		//浏览次数
	
	@TableField(exist=false)	
	private String btime;		//开始时间
	@TableField(exist=false)
	private String etime;		//结束时间
}
