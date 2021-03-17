package com.pang.entity;

import java.io.Serializable;
import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//宣讲会申请表
@TableName("teachin_exam")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeachinExam implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id")
	private Integer id;			//文章id
	private Integer cid;		//招聘信息单位id
	private String isschoolmate;//是否校友
	private String title;		//标题
	private String email;		//联系邮箱
	private Date adate;			//申请时间
	private String size;		//期望参会人数
	private Date vdate;			//来访时间安排
	private String time;		//预选时间段
}
