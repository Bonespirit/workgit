package com.pang.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.pang.entity.Company;

public interface EnterpriseService{
	
	//单位注册信息导入数据库
	public void putEnterpriseInfo(Company company,MultipartFile logo,MultipartFile license) throws IOException;
}
