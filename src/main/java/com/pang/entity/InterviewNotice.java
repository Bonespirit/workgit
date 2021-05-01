package com.pang.entity;

import java.sql.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//面试通知数据实体
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InterviewNotice {
	
	private List<Integer> idlist;		//操作学生id列表
	private String title;		//面试主题
	@JSONField(format = "yyyy-MM-dd")
	private Date time;			//面试时间
	private String address;		//面试地点
	private String name;		//联系人姓名
	private String telephone;	//联系人电话
	private String msgcontent;	//补充内容
}
