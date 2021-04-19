package com.pang.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.config.MyBatisConfig;
import com.pang.customfunc.customFunc;
import com.pang.entity.InterviewNotice;
import com.pang.entity.ResumeProcess;
import com.pang.mapper.ResumeProcessMapper;
import com.pang.service.ResumeProcessService;

@Service
public class ResumeProcessServiceImpl extends ServiceImpl<ResumeProcessMapper, ResumeProcess> implements ResumeProcessService{
	
	@Autowired
	customFunc customFunc;
	
	@Autowired
	ResumeProcessMapper resumeProcessMapper;
	
	@Override
	public Page<ResumeProcess> getDeliverResume(Integer utype,Integer sid,Integer cid,Integer tag,Integer pg) {
		Page<ResumeProcess> cPage = new Page<>(pg,15);
		QueryWrapper<ResumeProcess> queryWrapper = new QueryWrapper<>();
		//判断搜索源是学生还是用人单位
		if (sid > 0) {
			queryWrapper.eq("stuid", sid);
		}else {
			queryWrapper.eq("comid", cid);
		}
		switch (tag) {
			case 1:
				MyBatisConfig.TABLE_NAME.set("screen_resume");
				break;
			case 2:
				MyBatisConfig.TABLE_NAME.set("pending_resume");
				break;
			case 3:
				MyBatisConfig.TABLE_NAME.set("under_way_resume");
				break;
			case 5:
				MyBatisConfig.TABLE_NAME.set("out_resume");
				break;
			default:
				return null;
		}
		if (utype == 0) {
			queryWrapper.select("pid","cname","pname","renew","mstatus");
		}else {
			queryWrapper.select("id","stuid","pid","pname","uname","gender","edu","major","city",
					"renew","mstatus");
		}
		queryWrapper.orderByDesc("renew");
		return this.page(cPage, queryWrapper);
	}

	@Override
	public Page<ResumeProcess> searchByKey(Integer cid,Integer tag, Integer pg, String keyword) {
		Page<ResumeProcess> cPage = new Page<>(pg,15);
		QueryWrapper<ResumeProcess> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pname", keyword).eq("comid", cid);
		switch (tag) {
			case 1:
				MyBatisConfig.TABLE_NAME.set("screen_resume");
				break;
			case 2:
				MyBatisConfig.TABLE_NAME.set("pending_resume");
				break;
			case 3:
				MyBatisConfig.TABLE_NAME.set("under_way_resume");
				break;
			case 5:
				MyBatisConfig.TABLE_NAME.set("out_resume");
				break;
			default:
				return null;
		}
		queryWrapper.select("id","stuid","pid","pname","uname","gender","edu","major","city",
				"renew","mstatus").orderByDesc("renew");
		return this.page(cPage, queryWrapper);
	}
	
	@Transactional
	@Override
	public void interviewNotice(InterviewNotice interviewNotice,Integer tag) {
		List<ResumeProcess> resumeProcesses = updateDeliver(
				interviewNotice.getIdlist(), "面试阶段", "pending_resume", "under_way_resume");
		if (tag == 1 ) {
			sendEmailToStudent(resumeProcesses,interviewNotice);
		}
	}
	
	@Override
	public void initialScreen(String idlist) {
		updateDeliver(idlist, "二次筛选", "screen_resume", "pending_resume");
	}
	
	@Override
	public void resumeOut(String idlist,String sheet) {
		updateDeliver(idlist, "不合适", sheet, "out_resume");
	}
	
	/**
	 * 发送面试通知到学生邮箱
	 * @param resumeProcesses	学生投递简历信息实体
	 * @param interviewNotice	通知信息体
	 */
	@Async
	public void sendEmailToStudent(List<ResumeProcess> resumeProcesses,
			InterviewNotice interviewNotice) {
		String contents = "<html>\n" +
		        "<body>\n" +
		        "<p>面试时间：" + interviewNotice.getTime() + "</p>\n" +
		        "<p>面试地点：" + interviewNotice.getAddress() + "</p>\n" +
		        "<p>联系人："+interviewNotice.getName()+"</p>\n" +
		        "<p>联系电话："+interviewNotice.getTelephone()+"</p>\n" +
		        "<p>补充说明："+interviewNotice.getMsgcontent()+"</p>\n" +
		        "</body>\n" +
		        "</html>";
		for(ResumeProcess resumeProcess : resumeProcesses) {
			customFunc.sendEmailToUser(resumeProcess.getEmail(), interviewNotice.getTitle(), contents);
		}
	}

	
	/**
	 * 更新状态，并剪切更新数据到新表
	 * @param idlist	简历id
	 * @param status	状态
	 * @param rfrom		源表
	 * @param rto		目标表
	 * @return List<ResumeProcess>
	 */
	public List<ResumeProcess> updateDeliver(String idlist,String status,String rfrom,String rto) {
		//获取id列表
		List<Integer> idList = new ArrayList<>();
		List<String> sidlist = new ArrayList<String>(Arrays.asList(idlist));
		for(int i=0;i<sidlist.size();i++) {
			idList.add(Integer.parseInt(sidlist.get(i)));
		}
		//获取源表中简历信息列表
		MyBatisConfig.TABLE_NAME.set(rfrom);
		List<ResumeProcess> resumeProcesses = resumeProcessMapper.selectBatchIds(idList);
		for(ResumeProcess resumeProcess : resumeProcesses) {
			resumeProcess.setMstatus(status);
		}
		//设置为新表并插入数据
		MyBatisConfig.TABLE_NAME.set(rto);
		this.saveBatch(resumeProcesses);
		//删除旧表中数据
		MyBatisConfig.TABLE_NAME.set(rfrom);
		this.removeByIds(idList);
		return resumeProcesses;
	}

}
