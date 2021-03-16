package com.pang.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pang.customfunc.customFunc;
import com.pang.entity.Company;
import com.pang.entity.TeachinExam;
import com.pang.entity.Visitors;
import com.pang.mapper.CompanyMapper;
import com.pang.mapper.TeachinExamMapper;
import com.pang.mapper.VisitorMapper;
import com.pang.service.ViewService;

@Service
public class ViewServiceImpl implements ViewService{
	
	@Autowired
	CompanyMapper CompanyMapper;
	
	@Autowired
	VisitorMapper visitorMapper;
	
	@Autowired
	TeachinExamMapper teachinExamMapper;
	
	@Autowired
	customFunc customFunc;
	
	@Override
	public Company getCompanyInfoById(Integer id) throws IOException {
		Company company = CompanyMapper.selectById(id);
		company.setLocation(customFunc.getCity(company.getLocation().split("&")[1]));
		company.setIndustry(customFunc.getIndustry(company.getIndustry()));
		company.setScale(customFunc.getScale(company.getScale()));
		return company;
	}
	
	//异步获取和申请表同时进行
	@Async
	@Override
	public List<Visitors> getVisitorsByLid(Integer lid) {
		
		QueryWrapper<Visitors> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("lid", lid);
		return visitorMapper.selectList(queryWrapper);
	}
	//异步获取和来访人员表同时进行
	@Async
	@Override
	public TeachinExam getTeachinExamByid(Integer id) {
		
		return teachinExamMapper.selectById(id);
	}
}
