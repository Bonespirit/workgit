package com.pang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//附件表
@TableName("enclosure")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Enclosure {
	@TableId(value="id")
	private Integer id;			//文章id
	private String enclosureurl;//附件的url
}
