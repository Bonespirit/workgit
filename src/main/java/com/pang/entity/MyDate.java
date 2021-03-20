package com.pang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//拆分时间为年月日
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyDate {
	
	private String month;
	private String day;
}
