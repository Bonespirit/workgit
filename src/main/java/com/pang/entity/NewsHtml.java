package com.pang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//news 的html代码数据表
@TableName("news_html")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsHtml {
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;		//文章正文id
	private String contents;//文章正文
}
