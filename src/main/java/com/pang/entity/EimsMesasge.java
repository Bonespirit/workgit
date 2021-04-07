package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//消息表
@TableName("message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EimsMesasge implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	private Integer rid;	//接收者
	private String type;	//类型
	private String from;	//来源
	private Date pdate;		//发送时间
}
