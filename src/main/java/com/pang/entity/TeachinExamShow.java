package com.pang.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//宣讲会审核展示列表，用于展示申请列表
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeachinExamShow {
	
	private Integer hid;		//文章id
	private String isschoolmate;//是否校友
	private String title;		//标题
	private Date adate;			//申请时间
}
