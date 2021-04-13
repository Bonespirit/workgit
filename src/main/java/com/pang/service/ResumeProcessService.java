package com.pang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.entity.InterviewNotice;
import com.pang.entity.ResumeProcess;

public interface ResumeProcessService extends IService<ResumeProcess>{
	
	/**
	 * 根据状态获取简历投递信息列表
	 * @param utype 0:学生操作，1：用人单位操作
	 * @param sid	学生id 不为0有效
	 * @param cid	单位id 不为0有效
	 * @param tag	标签类型
	 * @param pg	当前页
	 * @return
	 */
	public Page<ResumeProcess> getDeliverResume(Integer utype,Integer sid,Integer cid,Integer tag,Integer pg);
	
	/**
	 * 通过职位名称查询投递简历
	 * @param cid
	 * @param tag
	 * @param pg
	 * @param keyword
	 * @return
	 */
	public Page<ResumeProcess> searchByKey(Integer cid,Integer tag,Integer pg,String keyword);
	
	/**
	 * 面试通知
	 * @param interviewNotice
	 * @param tag			0：自行通知，1：系统邮件通知
	 */
	public void interviewNotice(InterviewNotice interviewNotice,Integer tag);
	
	/**
	 * 初次筛选，将简历放入待处理区域等待进一步筛选
	 * @param idlist
	 */
	public void initialScreen(String idlist);
	
	/**
	 * 简历不合适处理
	 * @param idlist
	 */
	public void resumeOut(String idlist,String sheet);
}
