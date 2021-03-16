package com.pang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
	
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
	public String goToWdjl() {
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
