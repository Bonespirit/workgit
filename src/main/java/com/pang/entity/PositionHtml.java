package com.pang.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//职位描述表
@TableName("position_html")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionHtml {
	
	private Integer id;		//职位id
	private String contents;//职位描述
}
