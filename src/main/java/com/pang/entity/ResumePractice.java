package com.pang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

//实习经历实体
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResumePractice {
	
	private String cname;	//实习单位名称
	private String duty;	//担任职务
	private String btime;	//开始时间
	private String etime;	//结束时间
}
