package com.pang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.CompanyExam;
import com.pang.entity.Jobfair;
import com.pang.entity.Majors;
import com.pang.entity.News;
import com.pang.entity.Recruit;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinExam;
import com.pang.entity.TeachinRefuse;

public interface TeacherService{
	
	/**
	 * 获取单位注册申请表列表并翻页
	 * @param page
	 * @return
	 */
	public Page<CompanyExam> getCompanyExamListByPage(Integer page);
	
	/**
	 * 获取宣讲会申请表列表并翻页
	 * @param page
	 * @return
	 */
	public Page<TeachinExam> getTeachinExamListByPage(Integer page);
	
	/**
	 * 宣讲会审核失败
	 * 删除申请表数据，更新失败记录，发送失败原因至用户邮箱
	 * @param id
	 * @param email 目的邮箱
	 * @param teachinRefuse 实体类型
	 */
	public void dsiTeachinExam(Integer id,String email,TeachinRefuse teachinRefuse);
	
	/**
	 * 宣讲会审核成功
	 * 删除申请表并发布宣讲会信息
	 * @param teachin
	 * @param email 成功通知
	 */
	public void idTeachin(Teachin teachin,String email);
	
	/**
	 * 单位注册审核失败
	 * @param id
	 * @param email
	 * @param reason
	 */
	public void dsCinfoAndExam(Integer id,String email,String reason);
	
	/**
	 * 单位注册成功
	 * @param id
	 * @param email
	 */
	public void idCompany(Integer id,String email);
	
	/**
	 * 发布文章
	 * @param news
	 * @param contents
	 */
	public void pNews(News news,String contents);
	
	/**
	 * 发布在线招聘
	 * @param recruit
	 * @param contents
	 * @param cid 单位编号，没有请设为-1
	 */
	public void pRecruit(Recruit recruit,String contents,Integer cid);
	
	/**
	 * 招聘会信息发布
	 * @param jobfair
	 * @param contents
	 */
	public void pJobfair(Jobfair jobfair,String contents,String eurl);
	
	/**
	 * 双选会信息发布
	 * @param sxhInfo
	 * @param contents
	 */
	public void pSxhInfo(SxhInfo sxhInfo,String contents,String eurl);
	
	/**
	 * 宣讲会信息发布
	 * @param teachin
	 * @param contents
	 * @param cid 单位编号，没有请设为-1
	 */
	public void pTeachin(Teachin teachin,String contents,Integer cid);
	
	/**
	 * 发布专业介绍
	 * @param majors
	 * @param contents
	 */
	public void pMajors(Majors majors,String contents);
	
}
