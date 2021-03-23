package com.pang.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.Company;
import com.pang.entity.Download;
import com.pang.entity.Jobfair;
import com.pang.entity.News;
import com.pang.entity.Recruit;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinExam;
import com.pang.entity.Visitors;

public interface ViewService {
	
	/**
	 * 通过id获取单位详细信息
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public Company getCompanyInfoById(Integer id) throws IOException; 
	
	/**
	 * 通过宣讲会id获取来访人员名单
	 * @param lid
	 * @return
	 */
	public List<Visitors> getVisitorsByLid(Integer lid);
	
	/**
	 * 通过id获取宣讲会申请表信息
	 * @param id
	 * @return
	 */
	public TeachinExam getTeachinExamById(Integer id);
	
	/**
	 * 通过文章id获取文章数据
	 * @param id
	 * @return
	 */
	public News getNewsInfoById(Integer id);
	
	/**
	 * 通过栏目获取文章类模块相关数据并翻页
	 * @param column	栏目代码
	 * @param pg		页码
	 * @param number	单页数据量
	 * @return
	 */
	public Page<News> getNewsListByColumn(Integer column,Integer pg,Integer number);
	
	/**
	 * 获取宣讲会信息列表并翻页
	 * @param pg	页码
	 * @param number单页数据量
	 * @param isschoolmate 是否校友约束:null表示查询全部，'0':非校友，'1':校友
	 * @return
	 */
	public Page<Teachin> getTeahinInfoPage(Integer pg,Integer number,String isschoolmate);
	
	/**
	 * 获取在线招聘信息并翻页
	 * @param pg			页码
	 * @param number		单页数据量
	 * @param isschoolmate	是否校友约束:null表示查询全部，'0':非校友，'1':校友
	 * @param nature		工作性质约束:null表示查询全部，'0':全职,'1':实习
	 * @return
	 */
	public Page<Recruit> getRecruitInfoPage(Integer pg,Integer number,String isschoolmate,String nature);
	
	/**
	 * 获取双选会信息并翻页
	 * @param pg 页码
	 * @param number	单页数据量
	 * @return
	 */
	public Page<SxhInfo> getSxhInfoPage(Integer pg,Integer number);
	
	/**
	 * 获取组团招聘信息并翻页
	 * @param pg
	 * @param number
	 * @return
	 */
	public Page<Jobfair> getJobfairInfoPage(Integer pg,Integer number,String isschoolmate);
	
	/**
	 * 通过id获取宣讲会内容
	 * @param id
	 * @return
	 */
	public Teachin getTeachinInfo(Integer id);
	
	/**
	 * 通过id获取在线招聘简章
	 * @param id
	 * @return
	 */
	public Recruit getRecruitInfo(Integer id);
	
	/**
	 * 通过id获取招聘会信息
	 * @param id
	 * @return
	 */
	public Jobfair getJobfairInfo(Integer id);
	
	/**
	 * 通过id获取双选会信息
	 * @param id
	 * @return
	 */
	public SxhInfo getSxhInfo(Integer id);
	
	/**
	 * 获取资源列表并翻页
	 * @param id
	 * @return
	 */
	public Page<Download> getDownloadPage(Integer pg);
}
