package com.pang.entity;

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
	
	@TableId(value="id")
	private Integer id;
	private String deliver;	//已投递职位id
}
