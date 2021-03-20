package com.pang.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.Company;
import com.pang.entity.Majors;
import com.pang.entity.Mposition;
import com.pang.entity.News;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinExam;
import com.pang.entity.TeachinRefuse;

public interface EnterpriseService{
	/**
	 * 	单位注册信息导入数据库
	 * @param company
	 * @param logo
	 * @param license
	 * @throws IOException
	 */
	public void putEnterpriseInfo(Company company,MultipartFile logo,MultipartFile license) throws IOException;
	
	/**
	 * 	宣讲会信息入库
	 * @param teachinExam
	 * @param contents
	 */
	public void putTeachinExam(TeachinExam teachinExam,String contents);
	
	/**
	 * 	通过单位id获取宣讲会信息列表
	 * @param id
	 * @return
	 */
	public Page<Teachin> getTeachinByid(Integer id);
	
	/**
	 * 通过单位id获取宣讲会待审核列表
	 * @param id
	 * @return
	 */
	public Page<TeachinExam> getTeachinExamByid(Integer id);
	
	/**
	 * 根据id获取宣讲会失败记录列表
	 * @param id
	 * @return
	 */
	public Page<TeachinRefuse> getTeachinRefuseByid(Integer id);
	
	/**
	 * 根据栏目id获取内容
	 * @param scolumn 栏目id
	 * @return
	 */
	public List<News> getNewsByColumnId(Integer scolumn);
	
	/**
	 * 获取双选会信息列表
	 * @return
	 */
	public List<SxhInfo> getSxhInfo();
	
	/**
	 * 根据学院代码获取专业列表
	 * @param code
	 * @return
	 */
	public List<Majors> getMajorsInfo(Integer code);
	
	/**
	 * 职位表信息入库
	 * @param mposition	职位表实体封装类
	 * @param describe 职位描述
	 */
	public void putPositionInfo(Mposition mposition,String describe);
	
}
