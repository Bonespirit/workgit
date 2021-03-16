package com.pang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//招聘简章表
@TableName("zp_html")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZpHtml {
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;		//简章id
	private String contents;//简章内容
}
