package com.pang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.config.MyBatisConfig;
import com.pang.customfunc.customFunc;
import com.pang.entity.ColResume;
import com.pang.entity.Resume;
import com.pang.entity.ResumePractice;
import com.pang.entity.ResumeProcess;
import com.pang.entity.ResumeProject;
import com.pang.entity.StuDeliver;
import com.pang.entity.StudentInfo;
import com.pang.mapper.ColResumeMapper;
import com.pang.mapper.ResumeMapper;
import com.pang.mapper.ResumeProjectMapper;
import com.pang.mapper.StuDeliverMapper;
import com.pang.mapper.StudentInfoMapper;
import com.pang.service.ResumeProcessService;
import com.pang.service.ResumeProjectService;
import com.pang.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StuDeliverMapper stuDeliverMapper;
	
	@Autowired
	ResumeProcessService resumeProcessService;
	
	@Autowired
	customFunc customFunc;
	
	@Autowired
	ColResumeMapper colResumeMapper;
	
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
	
	@Cacheable(value="LongCache",key="'eims:student:resume:id:'+#id")
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
	
	@CacheEvict(value="LongCache",key="'eims:student:resume:id:'+#resume.id")
	@Transactional
	@Override
	public void updateResume(Resume resume,Integer os) {
		List<ResumeProject> resumeProjects = new ArrayList<>();
		if (resume.getName() != null ) {
			QueryWrapper<ResumeProject> queryWrapper = new QueryWrapper<>();
			for(int i=0;i<resume.getName().length;i++) {
				resumeProjects.add(new ResumeProject(null, resume.getId(), resume.getName()[i],
						resume.getDuty()[i], resume.getDescribe()[i],
						resume.getBtime()[i],resume.getEtime()[i]));
			}
			queryWrapper.eq("stuid", resume.getId());
			resumeProjectService.remove(queryWrapper);
			resumeProjectService.saveBatch(resumeProjects);
		}
		//如果os为0则插入数据，反之更新
		if (os == 0) {
			resumeMapper.insert(resume);
		}else {
			resumeMapper.updateById(resume);
		}
	}
	
	@CacheEvict(value="LongCache",key="'eims:student:collect:sid:'+#sid")
	@Override
	public void detCollect(Integer id,Integer sid) {
		colResumeMapper.deleteById(id);
	}
	
	@Override
	public Page<ColResume> getColResumePage(Integer sid, Integer pg) {
		Page<ColResume> cPage = new Page<>(pg,15);
		QueryWrapper<ColResume> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("sid", sid);
		List<String> coList = customFunc.getPosAndColId(sid).get("collect");
		Page<ColResume> page = colResumeMapper.selectPage(cPage, queryWrapper);
		//判断收藏职位是否已投递
		for(ColResume colResume:page.getRecords()) {
			if (coList.contains(colResume.getPid().toString())) {
				colResume.setDeliver(true);
			}
		}
		return page;
	}
	
	@Override
	public void putColResume(ColResume colResume) {
		colResumeMapper.insert(colResume);
	}
	
	@CacheEvict(value="LongCache",key="'eims:student:collect:sid:'+#resumeProcess.stuid")
	@Transactional
	@Override
	public void deliverResume(ResumeProcess resumeProcess) {
		QueryWrapper<StudentInfo> queryWrapper1 = new QueryWrapper<>();
		QueryWrapper<Resume> queryWrapper2 = new QueryWrapper<>();
		queryWrapper1.eq("id", resumeProcess.getStuid()).select("name","gender","edu","major","email");
		StudentInfo studentInfo = studentInfoMapper.selectOne(queryWrapper1);
		BeanUtils.copyProperties(studentInfo,resumeProcess);
		resumeProcess.setUname(studentInfo.getName());
		queryWrapper2.eq("id", resumeProcess.getStuid()).select("city");
		resumeProcess.setCity(resumeMapper.selectOne(queryWrapper2).getCity());
		resumeProcess.setMstatus("初步筛选");
		MyBatisConfig.TABLE_NAME.set("screen_resume");
		resumeProcessService.save(resumeProcess);
	}
	
	@Override
	public boolean canDeliver(ResumeProcess resumeProcess) {
		QueryWrapper<StuDeliver> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("sid", resumeProcess.getStuid());
		queryWrapper.eq("pid", resumeProcess.getPid());
		//简历已投递
		if (stuDeliverMapper.selectOne(queryWrapper) != null) {
			return false;
		}
		StuDeliver stuDeliver = new StuDeliver();
		stuDeliver.setSid(resumeProcess.getStuid());
		stuDeliver.setPid(resumeProcess.getPid());
		stuDeliverMapper.insert(stuDeliver);
		return true;
	}
}
