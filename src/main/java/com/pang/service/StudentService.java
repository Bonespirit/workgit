package com.pang.service;

import java.util.concurrent.ExecutionException;

import com.pang.entity.Resume;
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
}
