package com.pang.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.customFunc;
import com.pang.entity.CompanyExam;
import com.pang.entity.Download;
import com.pang.entity.Jobfair;
import com.pang.entity.Majors;
import com.pang.entity.News;
import com.pang.entity.NewsHtml;
import com.pang.entity.Recruit;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinExam;
import com.pang.entity.TeachinRefuse;
import com.pang.entity.User;
import com.pang.entity.UserRole;
import com.pang.entity.ZpHtml;
import com.pang.mapper.CompanyExamMapper;
import com.pang.mapper.CompanyMapper;
import com.pang.mapper.JobfairMapper;
import com.pang.mapper.MajorsMapper;
import com.pang.mapper.NewsMapper;
import com.pang.mapper.RecruitMapper;
import com.pang.mapper.RoleMapper;
import com.pang.mapper.SxhInfoMapper;
import com.pang.mapper.TeachinExamMapper;
import com.pang.mapper.TeachinMapper;
import com.pang.mapper.TeachinRefuseMapper;
import com.pang.mapper.UserRoleMapper;
import com.pang.mapper.ZpHtmlMapper;
import com.pang.service.DownloadService;
import com.pang.service.NewsHtmlService;
import com.pang.service.TeacherService;
import com.pang.service.UserService;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	DownloadService downloadService;
	
	@Autowired
	MajorsMapper majorsMapper;
	
	@Autowired
	JobfairMapper jobfairMapper;
	
	@Autowired
	SxhInfoMapper sxhInfoMapper;
	
	@Autowired
	RecruitMapper recruitMapper;
	
	@Autowired
	ZpHtmlMapper zpHtmlMapper;
	
	@Autowired
	NewsHtmlService newsHtmlService;
	
	@Autowired
	NewsMapper newsMapper;
	
	@Autowired
	RoleMapper roleMapper;

	@Autowired
	UserRoleMapper userRoleMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyMapper companyMapper;
	
	@Autowired
	CompanyExamMapper companyExamMapper;
	
	@Autowired
	TeachinExamMapper teachinExamMapper;
	
	@Autowired
	TeachinRefuseMapper teachinRefuseMapper;
	
	@Autowired
	TeachinMapper teachinMapper;
	
	@Autowired
	customFunc customFunc;
	
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
		queryWrapper.select("id","isschoolmate","title","adate").orderByDesc("isschoolmate");
		return teachinExamMapper.selectPage(cPage, queryWrapper);
	}
	
	@Transactional
	@Override
	public void dsiTeachinExam(Integer id, String email, TeachinRefuse teachinRefuse) {
		//删除申请表
		teachinExamMapper.deleteById(id);
		//删除简章
		zpHtmlMapper.deleteById(id);
		//失败记录表
		teachinRefuseMapper.insert(teachinRefuse);
		//发送email通知
		customFunc.sendEmailToCp(email, "宣讲会申请失败", teachinRefuse.getReason());
	}
	
	@Transactional
	@Override
	public void idTeachin(Teachin teachin,String email) {
		//发布宣讲信息
		teachinMapper.insert(teachin);
		//删除申请表数据
		teachinExamMapper.deleteById(teachin.getId());
		//发送email通知
		customFunc.sendEmailToCp(email, "宣讲会申请通过","感谢用人单位对我校就业工作的支持和对毕业生的肯定!");
	}

	@Transactional
	@Override
	public void dsCinfoAndExam(Integer id, String email, String reason) {
		//删除单位信息表
		companyMapper.deleteById(id);
		//删除申请表
		companyExamMapper.deleteById(id);
		//发送email通知
		customFunc.sendEmailToCp(email, "注册失败", reason);
	}
	
	@Transactional
	@Override
	public void idCompany(Integer id,String email) {
		QueryWrapper<CompanyExam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id).select("username","password","name","telephone","cemail");
		CompanyExam companyExam = companyExamMapper.selectOne(queryWrapper);
		User user = new User();
		BeanUtils.copyProperties(companyExam,user);
		System.out.println(user.toString());
		user.setEmail(companyExam.getCemail());
		user.setForeignkey(id);
		//更新用户表
		userService.save(user);
		//授权
		userRoleMapper.insert(new UserRole(null, user.getId(), 3));
		companyExamMapper.deleteById(id);
		//发送email通知
		customFunc.sendEmailToCp(email, "注册成功", "您已经成功注册了，快来发布招聘信息吧！");
	}
	
	@Transactional
	@Override
	public void pNews(News news, String contents) {
		NewsHtml newsHtml = new NewsHtml();
		newsHtml.setContents(contents);
		newsHtmlService.save(newsHtml);
		news.setId(newsHtml.getId());
		newsMapper.insert(news);
	}
	
	@Transactional
	@Override
	public void pRecruit(Recruit recruit, String contents,Integer cid) {
		ZpHtml zpHtml = new ZpHtml();
		zpHtml.setContents(contents);
		zpHtmlMapper.insert(zpHtml);
		recruit.setId(zpHtml.getId());
		recruit.setCid(cid);
		recruitMapper.insert(recruit);
	}
	
	@Transactional
	@Override
	public void pJobfair(Jobfair jobfair, String contents,Integer id) {
		ZpHtml zpHtml = new ZpHtml();
		zpHtml.setContents(contents);
		zpHtmlMapper.insert(zpHtml);
		int zid = zpHtml.getId();
		jobfair.setId(zid);
		jobfairMapper.insert(jobfair);
		UpdateWrapper<Download> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("hid", id).set("hid", zid);
		downloadService.update(updateWrapper);
	}
	
	@Transactional
	@Override
	public void pSxhInfo(SxhInfo sxhInfo, String contents,Integer id) {
		ZpHtml zpHtml = new ZpHtml();
		zpHtml.setContents(contents);
		zpHtmlMapper.insert(zpHtml);
		int zid = zpHtml.getId();
		sxhInfo.setTime(sxhInfo.getBtime()+"-"+sxhInfo.getEtime());
		sxhInfo.setId(zid);
		sxhInfoMapper.insert(sxhInfo);
		UpdateWrapper<Download> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("hid", id).set("hid", zid);
		downloadService.update(updateWrapper);
	}

	@Transactional
	@Override
	public void pTeachin(Teachin teachin, String contents,Integer cid) {
		ZpHtml zpHtml = new ZpHtml();
		zpHtml.setContents(contents);
		zpHtmlMapper.insert(zpHtml);
		teachin.setId(zpHtml.getId());
		teachin.setCid(cid);
		teachinMapper.insert(teachin);
	}

	@Transactional
	@Override
	public void pMajors(Majors majors, String contents) {
		NewsHtml newsHtml = new NewsHtml();
		newsHtml.setContents(contents);
		newsHtmlService.save(newsHtml);
		majors.setId(newsHtml.getId());
		majorsMapper.insert(majors);
	}

}
