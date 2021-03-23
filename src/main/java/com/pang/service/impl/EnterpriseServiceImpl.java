package com.pang.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.UploadService;
import com.pang.customfunc.customFunc;
import com.pang.entity.Company;
import com.pang.entity.CompanyExam;
import com.pang.entity.Majors;
import com.pang.entity.Mposition;
import com.pang.entity.PositionHtml;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinExam;
import com.pang.entity.TeachinRefuse;
import com.pang.entity.User;
import com.pang.entity.Visitors;
import com.pang.entity.ZpHtml;
import com.pang.mapper.CompanyExamMapper;
import com.pang.mapper.CompanyMapper;
import com.pang.mapper.MajorsMapper;
import com.pang.mapper.PositionHtmlMapper;
import com.pang.mapper.PositionMapper;
import com.pang.mapper.SxhInfoMapper;
import com.pang.mapper.TeachinExamMapper;
import com.pang.mapper.TeachinMapper;
import com.pang.mapper.TeachinRefuseMapper;
import com.pang.mapper.ZpHtmlMapper;
import com.pang.service.EnterpriseService;
import com.pang.service.UserService;
import com.pang.service.VisitorService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService{
	
	@Autowired
	UserService userService;
	
	@Autowired
	MajorsMapper majorsMapper;
	
	@Autowired
	SxhInfoMapper sxhInfoMapper;
	
	@Autowired
	PositionMapper positionMapper;
	
	@Autowired
	PositionHtmlMapper positionHtmlMapper;
	
	@Autowired
	TeachinMapper TeachinMapper;
	
	@Autowired
	TeachinRefuseMapper teachinRefuseMapper;
	
	@Autowired
	ZpHtmlMapper zpHtmlMapper;
	
	@Autowired 
	TeachinExamMapper teachinExamMapper;
	
	@Autowired
	VisitorService visitorService;
	
	//文件上传处理
	@Autowired
	UploadService uploadService;
	
	//自定义工具类
	@Autowired
	customFunc customFunc;
	
	@Autowired
	CompanyMapper companyMapper;
	@Autowired
	CompanyExamMapper companyExamMapper;
	
	@Transactional
	@Override
	public void putEnterpriseInfo(Company company,MultipartFile logo,MultipartFile license) throws IOException {
		CompanyExam companyExam = new CompanyExam();
		company.setLogourl(uploadService.uploadLogo(logo));
		company.setLicenseurl(uploadService.uploadLicense(license));
		company.setLocation(company.getProvinceSel()+"&"+company.getCitySel());
		company.setNature(customFunc.getComNature(company.getNature()));
		BeanUtils.copyProperties(company,companyExam);
		companyMapper.insert(company);
		companyExam.setId(company.getId());
		companyExam.setPassword(new BCryptPasswordEncoder().encode(companyExam.getPassword()));
		companyExamMapper.insert(companyExam);
	}
	
	@Transactional
	@Override
	public void putTeachinExam(TeachinExam teachinExam, String contents) {
		ZpHtml zpHtml = new ZpHtml(null, contents);
		zpHtmlMapper.insert(zpHtml);
		int zphid = zpHtml.getId();
		teachinExam.setId(zphid);
		teachinExamMapper.insert(teachinExam);
		List<Visitors> visitorlist = new ArrayList<Visitors>();
		for(int i=0;i<teachinExam.getName().length;i++) {
			visitorlist.add(new Visitors(null,zphid,
					teachinExam.getName()[i], teachinExam.getIdcard()[i],
					teachinExam.getGender()[i], teachinExam.getDuty()[i], teachinExam.getTelephone()[i]));
		}
		visitorService.saveBatch(visitorlist);
	}

	@Override
	public Page<Teachin> getTeachinByid(Integer id) {
		Page<Teachin> cPage = new Page<Teachin>(1,15);
		QueryWrapper<Teachin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("cid", id);
		return TeachinMapper.selectPage(cPage, queryWrapper);
	}

	@Override
	public Page<TeachinExam> getTeachinExamByid(Integer id) {
		Page<TeachinExam> cPage = new Page<TeachinExam>(1,15);
		QueryWrapper<TeachinExam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("cid", id);
		return teachinExamMapper.selectPage(cPage, queryWrapper);
	}

	@Override
	public Page<TeachinRefuse> getTeachinRefuseByid(Integer id) {
		Page<TeachinRefuse> cPage = new Page<TeachinRefuse>(1,15);
		QueryWrapper<TeachinRefuse> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		return teachinRefuseMapper.selectPage(cPage, queryWrapper);
	}

	@Override
	public List<SxhInfo> getSxhInfo() {
		QueryWrapper<SxhInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("hdate");
		return sxhInfoMapper.selectList(queryWrapper);
	}
	
	@Override
	public List<Majors> getMajorsInfo(Integer code) {
		QueryWrapper<Majors> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("cid", code).select("id","mname");
		return majorsMapper.selectList(queryWrapper);
	}
	
	@Transactional
	@Override
	public void putPositionInfo(Mposition mposition, String describe) {
		PositionHtml positionHtml = new PositionHtml(null,describe);
		int i = positionHtmlMapper.insert(positionHtml);
		System.out.println(i);
		mposition.setId(positionHtml.getId());
		positionMapper.insert(mposition);
	}

	@Override
	public boolean testTelephone(String telephone) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("telephone", telephone);
		if (userService.getOne(queryWrapper) !=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean testUsername(String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		if (userService.getOne(queryWrapper) !=null) {
			return true;
		}
		return false;
	}

	
	@Transactional
	@Override
	public void deleteTeachinE(Integer id) {
		QueryWrapper<Visitors> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("lid", id);
		visitorService.remove(queryWrapper);
		teachinExamMapper.deleteById(id);
		zpHtmlMapper.deleteById(id);
	}

}
