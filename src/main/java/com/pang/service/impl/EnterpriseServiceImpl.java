package com.pang.service.impl;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pang.customfunc.UploadService;
import com.pang.customfunc.customFunc;
import com.pang.entity.Company;
import com.pang.entity.CompanyExam;
import com.pang.mapper.CompanyExamMapper;
import com.pang.mapper.CompanyMapper;
import com.pang.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService{
	
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
		companyExamMapper.insert(companyExam);
//		System.out.println(company.toString());
//		System.out.println(companyExam.toString());
	}
	
}
