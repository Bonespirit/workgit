package com.pang.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WangEditorResult {
	
	private Integer errno;
	private String ipaddr;
	private List<WangEditorData> data;
}
