package com.pang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pang.customfunc.UploadService;
import com.pang.service.TeacherService;

//教职工业务处理controller
@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	UploadService uploadService;
	@Autowired
	TeacherService teacherService;
	
	//宣讲会
	@GetMapping("/sxh")
	public String goToSxh() {
		return "teacher/sxh";
	}
	
	//审核单位注册申请
	@GetMapping("/shsq/page/{page}")
	public String goToShsq(@PathVariable("page") Integer pg,Model model) {
		
		model.addAttribute("page", teacherService.getCompanyExamListByPage(pg));
		return "teacher/shsq";
	}
	//审核宣讲会预约teachin_exam
	@GetMapping("/shsqt/page/{page}")
	public String goToShsqt(@PathVariable("page") Integer pg,Model model) {
		
		model.addAttribute("page", teacherService.getTeachinExamListByPage(pg));
		return "teacher/shsqt";
	}
	
	//宣讲会模块
	@GetMapping("/xjh")
	public String goToXjh() {
		return "teacher/xjh";
	}
	//发布内容模块
	@GetMapping("/fbnr")
	public String goToFbnr() {
		return "teacher/fbnr";
	}
	//组团招聘模块
	@GetMapping("/ztzp")
	public String goToZtzp() {
		return "teacher/ztzp";
	}
	//在线招聘模块
	@GetMapping("/zxzp")
	public String goToZxzp() {
		return "teacher/zxzp";
	}
	//生源信息模块
	@GetMapping("/syxx")
	public String goToSyxx() {
		return "teacher/syxx";
	}
	//专业介绍模块
	@GetMapping("/zyjs")
	public String goToZyjs() {
		return "teacher/zyjs";
	}
}
