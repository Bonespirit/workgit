package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//常用资源下载
@TableName("dload_source")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Download implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;			//资源id
	private Integer hid;		//简章id 默认为-1：表示一般资源，不为-1：为招聘简章的附件
	private String filename;	//资源名称
	private String addr;		//资源物理地址
	private Integer download;	//资源下载次数
	@JSONField(format = "yyyy-MM-dd")
	private Date pdate;			//资源上传时间
}
