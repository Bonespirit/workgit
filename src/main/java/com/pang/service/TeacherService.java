package com.pang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.CompanyExam;
import com.pang.entity.TeachinExam;

public interface TeacherService{
	
	//获取单位注册申请表列表并翻页
	public Page<CompanyExam> getCompanyExamListByPage(Integer page);
	
	//获取宣讲会申请表列表并翻页
	public Page<TeachinExam> getTeachinExamListByPage(Integer page);
}
