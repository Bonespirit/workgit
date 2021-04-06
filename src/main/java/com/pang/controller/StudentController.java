package com.pang.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pang.customfunc.UploadService;
import com.pang.entity.Resume;
import com.pang.entity.User;
import com.pang.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	UploadService uploadService;
	
	//操作手册
	@GetMapping("/czsc")
	public String goToCzsc() {
		System.out.println("czsc");
		return "student/czsc";
	}
	
	//毕业填报
	@GetMapping("/bytb")
	public String goToBytb() {
		return "student/bytb";
	}
	//填报信息入库
	@PostMapping("/bytb")
	public void putBytb() {
		
	}
	
	//解约办理
	@GetMapping("jybl")
	public String goToJybl() {
		return "student/jybl";
	}
	
	//打印申请
	@GetMapping("/dysq")
	public String goToDysq() {
		return "student/dysq";
	}
	
	//常用下载
	@GetMapping("download")
	public String goToDownload() {
		return "student/download";
	}
	
	//我的简历
	@GetMapping("/wdjl")
	public String goToWdjl(Model model) throws InterruptedException, ExecutionException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("student", studentService.getStudenInfo(user.getId()));
		return "student/wdjl";
	}
	//修改头像
	@PutMapping("/changehead")
	@ResponseBody
	public String updateHead(@RequestPart("head") MultipartFile head) throws IllegalStateException, IOException {
		return uploadService.updateHead(head);
	}
	//修改简历
	@GetMapping("/editresume")
	public String goToEditResume(Model model) throws InterruptedException, ExecutionException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("student", studentService.getStudenInfo(user.getId()));
//		StudentInfo studentInfo = new StudentInfo();
//		studentInfo.setResume(new Resume());
//		model.addAttribute("student", studentInfo);
		return "student/editresume";
	}
	//更新简历信息
	@PutMapping("/editresume")
	@ResponseBody
	public String updateResume(Resume resume,HttpServletRequest request) throws IOException, ServletException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Part enclosure = request.getPart("enclosure");
		if (enclosure !=null) {
			resume.setResumeurl(uploadService.uploadResume(enclosure));
		}
		System.out.println(resume.toString());
		studentService.updateResume(resume, user.getId());
		return "student/wdjl";
	}
	//我的消息
	@GetMapping("/wdxx")
	public String goToWdxx() {
		return "student/wdxx";
	}
	
	//我的投递
	@GetMapping("/wdtd")
	public String goToWdtd() {
		return "student/wdtd";
	}
	
	//学生基本信息
	@GetMapping("/jbxxs")
	public String goToJbxxs() {
		return "student/jbxxs";
	}
	
	//修改密码
	@GetMapping("/xgmms")
	public String goToXgmms() {
		return "student/xgmms";
	}
}
