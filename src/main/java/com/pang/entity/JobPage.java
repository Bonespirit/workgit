package com.pang.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//封装高级检索模块职位表信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPage {
	private Long total;
	private List<Jobs> jobs;
}
