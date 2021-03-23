package com.pang.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.customFunc;
import com.pang.entity.Company;
import com.pang.entity.Download;
import com.pang.entity.Jobfair;
import com.pang.entity.News;
import com.pang.entity.Recruit;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinExam;
import com.pang.entity.Visitors;
import com.pang.mapper.CompanyMapper;
import com.pang.mapper.JobfairMapper;
import com.pang.mapper.NewsMapper;
import com.pang.mapper.RecruitMapper;
import com.pang.mapper.SxhInfoMapper;
import com.pang.mapper.TeachinExamMapper;
import com.pang.mapper.TeachinMapper;
import com.pang.mapper.VisitorMapper;
import com.pang.service.DownloadService;
import com.pang.service.ViewService;

@Service
public class ViewServiceImpl implements ViewService{
	
	@Autowired
	DownloadService downloadService;
	
	@Autowired
	SxhInfoMapper sxhInfoMapper;
	
	@Autowired
	JobfairMapper jobfairMapper;
	
	@Autowired
	RecruitMapper recruitMapper;
	
	@Autowired
	NewsMapper newsMapper;
	
	@Autowired
	CompanyMapper CompanyMapper;
	
	@Autowired
	VisitorMapper visitorMapper;
	
	@Autowired
	TeachinMapper teachinMapper;
	
	@Autowired
	TeachinExamMapper teachinExamMapper;
	
	@Autowired
	customFunc customFunc;
	
	@Override
	public Company getCompanyInfoById(Integer id) throws IOException {
		Company company = CompanyMapper.selectById(id);
		String[] citys = company.getLocation().split("&");
		company.setLocation(
				citys.length > 1 ? customFunc.getCity(citys[1]) : customFunc.getCity(citys[0]));
		company.setIndustry(customFunc.getIndustry(company.getIndustry()));
		company.setScale(customFunc.getScale(company.getScale()));
		return company;
	}
	
	@Override
	public List<Visitors> getVisitorsByLid(Integer lid) {
		QueryWrapper<Visitors> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("lid", lid);
		return visitorMapper.selectList(queryWrapper);
	}
	
	@Override
	public TeachinExam getTeachinExamById(Integer id) {
		return teachinExamMapper.selectById(id);
	}
	
	@Override
	public News getNewsInfoById(Integer id) {
		QueryWrapper<News> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id).select("title","mcolumn","pdate","hot");
		News news = newsMapper.selectOne(queryWrapper);
		Map<String, String> map = customFunc.getCnameByColumn(news.getMcolumn());
		news.setCname(map.get("cname"));
		news.setCurl(map.get("curl"));
		return news;
	}
	
	@Override
	public Page<News> getNewsListByColumn(Integer column,Integer pg,Integer number) {
		Page<News> cPage =  new Page<>(pg,number);
		QueryWrapper<News> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mcolumn", column).select("id","title","grade","pdate");
		return newsMapper.selectPage(cPage, queryWrapper);
	}
	
	@Override
	public Page<Teachin> getTeahinInfoPage(Integer pg, Integer number,String isschoolmate) {
		Page<Teachin> cPage =  new Page<>(pg,number);
		QueryWrapper<Teachin> queryWrapper = new QueryWrapper<>();
		if (isschoolmate.isEmpty()) {
		}else {
			queryWrapper.eq("isschoolmate", isschoolmate);
		}
		queryWrapper.orderByDesc("tdate").select("id","title","tdate","btime","address","school","hot");
		return teachinMapper.selectPage(cPage, queryWrapper);
	}
	
	@Override
	public Page<Recruit> getRecruitInfoPage(Integer pg, Integer number, String isschoolmate, String nature) {
		Page<Recruit> cPage =  new Page<>(pg,number);
		QueryWrapper<Recruit> queryWrapper = new QueryWrapper<>();
		if (!isschoolmate.isEmpty()) {
			queryWrapper.eq("isschoolmate", isschoolmate);
		}
		if (nature !=null) {
			queryWrapper.eq("nature", nature);
		}
		queryWrapper.orderByDesc("pdate").select("id","title","pdate");
		return recruitMapper.selectPage(cPage, queryWrapper);
	}
	
	@Override
	public Teachin getTeachinInfo(Integer id) {
		QueryWrapper<Teachin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id).select("cid","title","isschoolmate","tdate","btime","address","school","hot");
		return teachinMapper.selectOne(queryWrapper);
	}
	
	@Override
	public Recruit getRecruitInfo(Integer id) {
		QueryWrapper<Recruit> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id).select("cid","title","isschoolmate","pdate","hot");
		return recruitMapper.selectOne(queryWrapper);
	}

	@Override
	public Jobfair getJobfairInfo(Integer id) {
		QueryWrapper<Jobfair> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id).select("title","isschoolmate","btime","address","hdate","hot");
		return jobfairMapper.selectOne(queryWrapper);
	}
	
	@Override
	public SxhInfo getSxhInfo(Integer id) {
		QueryWrapper<SxhInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id).select("title","time","address","hdate","hot");
		return sxhInfoMapper.selectOne(queryWrapper);
	}
	
	@Override
	public Page<SxhInfo> getSxhInfoPage(Integer pg, Integer number) {
		Page<SxhInfo> cPage =  new Page<>(pg,number);
		QueryWrapper<SxhInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("pdate").select("id","title","pdate");
		return sxhInfoMapper.selectPage(cPage, queryWrapper);
	}
	
	@Override
	public Page<Jobfair> getJobfairInfoPage(Integer pg, Integer number,String isschoolmate) {
		Page<Jobfair> cPage =  new Page<>(pg,number);
		QueryWrapper<Jobfair> queryWrapper = new QueryWrapper<>();
		if (isschoolmate.isEmpty()) {
		}else {
			queryWrapper.eq("isschoolmate", isschoolmate);
		}
		queryWrapper.orderByDesc("hdate").select("id","title","hdate","btime","address");
		return jobfairMapper.selectPage(cPage, queryWrapper);
	}

	@Override
	public Page<Download> getDownloadPage(Integer pg) {
		Page<Download> dPage = new Page<>(pg,15);
		return downloadService.page(dPage, null);
	}
}
