package com.pang.service;

import java.util.concurrent.ExecutionException;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.ColResume;
import com.pang.entity.Resume;
import com.pang.entity.ResumeProcess;
import com.pang.entity.StudentInfo;

public interface StudentService{
	
	/**
	 * 通过id获取学生简历信息
	 * @param id
	 * @return
	 */
	public StudentInfo getStudenInfo(Integer id) 
			throws InterruptedException, ExecutionException;
	
	/**
	 * 更新简历
	 * @param resume
	 */
	public void updateResume(Resume resume,Integer stuid);
	
	/**
	 * 删除收藏职位并更新缓存
	 * @param id
	 */
	public void detCollect(Integer id,Integer sid);
	
	/**
	 * 根据学生id获取收藏职位列表并翻页
	 * @param sid	学生id
	 * @param pg	当前页
	 * @return
	 */
	public Page<ColResume> getColResumePage(Integer sid,Integer pg);
	
	/**
	 * 收藏职位信息入库
	 * @param colResume
	 */
	public void putColResume(ColResume colResume);
	
	/**
	 * 简历投递信息入库
	 * @param resumeProcess
	 */
	public void deliverResume(ResumeProcess resumeProcess);

}
