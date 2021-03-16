package com.pang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IndexController {
	
	//单位注册提交成功
	@GetMapping("/registersuccess")
	public String goToRegisterSucess() {
		System.out.println("success");
		return "registersuccess";
	}
	
	@GetMapping("/")
	public String mIndex() {
		System.out.println("index");
		return "index";
	}
	@GetMapping("/eplogin")
	public String epLogin() {
		return "eplogin";
	}
	@GetMapping("/login")
	public String sLogin() {
		return "login";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestPart("png") MultipartFile mPng,
						 @RequestPart("jpg") MultipartFile[] mJpgs) {
		return "registersucess";
	}
}
