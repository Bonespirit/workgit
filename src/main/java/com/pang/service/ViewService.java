package com.pang.service;

import java.io.IOException;
import java.util.List;

import com.pang.entity.Company;
import com.pang.entity.TeachinExam;
import com.pang.entity.Visitors;

public interface ViewService {
	
	//获取单位详细信息
	public Company getCompanyInfoById(Integer id) throws IOException; 
	
	//获取来访人员名单
	public List<Visitors> getVisitorsByLid(Integer lid);
	//获取宣讲会申请表信息
	public TeachinExam getTeachinExamById(Integer id);
}
