package com.pang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.CompanyExam;
import com.pang.entity.TeachinExam;
import com.pang.mapper.CompanyExamMapper;
import com.pang.mapper.TeachinExamMapper;
import com.pang.service.TeacherService;

public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	CompanyExamMapper companyExamMapper;
	
	@Autowired
	TeachinExamMapper teachinExamMapper;
	
	@Override
	public Page<CompanyExam> getCompanyExamListByPage(Integer pg) {
		
		Page<CompanyExam> cPage = new Page<>(pg, 15);
		QueryWrapper<CompanyExam> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("isschoolmate");
		return companyExamMapper.selectPage(cPage, queryWrapper);
	}

	@Override
	public Page<TeachinExam> getTeachinExamListByPage(Integer pg) {
		
		Page<TeachinExam> cPage = new Page<>(pg, 15);
		QueryWrapper<TeachinExam> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("hid","isschoolmate","title","adate").orderByDesc("isschoolmate");
		return teachinExamMapper.selectPage(cPage, queryWrapper);
	}
	
	
}
