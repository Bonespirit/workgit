package com.pang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pang.entity.Resume;
import com.pang.entity.ResumePractice;
import com.pang.entity.ResumeProject;
import com.pang.entity.StudentInfo;
import com.pang.mapper.ResumeMapper;
import com.pang.mapper.ResumeProjectMapper;
import com.pang.mapper.StudentInfoMapper;
import com.pang.service.ResumeProjectService;
import com.pang.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	ResumeProjectService resumeProjectService;
	
	@Autowired
	StudentInfoMapper studentInfoMapper;
	
	@Autowired
	ResumeMapper resumeMapper;
	
	@Autowired
	ResumeProjectMapper resumeProjectMapper;
	
	//异步获取学生基本信息
	@Async
	public Future<StudentInfo> getStuInfoAsync(Integer id){
		System.out.println("StudentInfo running");
		StudentInfo studentInfo = studentInfoMapper.selectById(id);
		return new AsyncResult<StudentInfo>(studentInfo);
	}
	//异步获取学生简历基本信息
	@Async
	public Future<Resume> getStuResumeAsync(Integer id){
		System.out.println("Resume running");
		Resume resume = resumeMapper.selectById(id);
		return new AsyncResult<Resume>(resume);
	}
	//异步获取学生简历项目经历
	@Async
	public Future<List<ResumeProject>> getStuResumeProjectAsync(Integer id){
		System.out.println("ResumeProject running");
		QueryWrapper<ResumeProject> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("stuid", id);
		List<ResumeProject> resumeProjects = resumeProjectMapper.selectList(queryWrapper);
		return new AsyncResult<List<ResumeProject>>(resumeProjects);
	}
	
	@Cacheable(value="ShortCache",key="'eims:student:resume:id:'+#id")
	@Transactional
	@Override
	public StudentInfo getStudenInfo(Integer id) throws InterruptedException, ExecutionException {
		Future<StudentInfo> siftask = getStuInfoAsync(id);
		Future<Resume> futureResume = getStuResumeAsync(id);
		Future<List<ResumeProject>> futureResumeProjects = getStuResumeProjectAsync(id);
		StudentInfo studentInfo = siftask.get();
		Resume resume = futureResume.get();
		List<ResumeProject> resumeProjects = futureResumeProjects.get();
		if (resume != null) {
			resume.setProject(resumeProjects);
			List<ResumePractice> resumePractices = new ArrayList<ResumePractice>();
			String[] pl = resume.getPractice().split(",");
			for(String practice:pl) {
				String[] practices = practice.split(":");
				resumePractices.add(new ResumePractice(practices[0], practices[1], 
						practices[2], practices[3]));
			}
			resume.setPractices(resumePractices);
			studentInfo.setResume(resume);
		}else {
			studentInfo.setResume(new Resume());
		}
		return studentInfo;
	}
	
	@CacheEvict(value="ShortCache",key="'eims:student:resume:id:'+#stuid")
	@Transactional
	@Override
	public void updateResume(Resume resume,Integer stuid) {
		resume.setId(stuid);
		List<ResumeProject> resumeProjects = new ArrayList<>();
		if (resume.getName() != null ) {
			for(int i=0;i<resume.getName().length;i++) {
				resumeProjects.add(new ResumeProject(null, stuid, resume.getName()[i],
						resume.getDuty()[i], resume.getDescribe()[i],
						resume.getBtime()[i],resume.getEtime()[i]));
			}
			resumeProjectService.saveBatch(resumeProjects);
		}
		resumeMapper.updateById(resume);
	}
}
