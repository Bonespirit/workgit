package com.pang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WangEditorData {
	
	private String url;		//url
	private String alt;		//图片文字说明
	private String href;	//跳转链接
}
