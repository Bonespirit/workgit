package com.pang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pang.entity.Company;
import com.pang.entity.Forget;
import com.pang.entity.Majors;
import com.pang.entity.Position;
import com.pang.entity.Recruit;
import com.pang.entity.TeachinExam;
import com.pang.entity.Visitors;
import com.pang.entity.ZpHtml;
import com.pang.mapper.TeachinExamMapper;
import com.pang.mapper.ZpHtmlMapper;
import com.pang.service.EnterpriseService;
import com.pang.service.VisitorService;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	
	@Autowired
	EnterpriseService enterpriseService;
	
	@Autowired
	VisitorService visitorService;
	@Autowired 
	TeachinExamMapper teachinExamMapper;
	@Autowired
	ZpHtmlMapper zpHtmlMapper;
	
	//单位注册
	@GetMapping("/register")
	public String epRegister() {
		System.out.println("register");
		return "enterprise/epregister";
	}
	//单位注册信息入库
	@PostMapping("/register")
	public String putEnterpriseInfo(Company company,
			@RequestPart("logo") MultipartFile logo,
			@RequestPart("license") MultipartFile license) throws IOException {	
		
		enterpriseService.putEnterpriseInfo(company, logo, license);
		return "redirect:/registersuccess";
	}
	
	//单位找回密码
	@GetMapping("/forget")
	public String epForget() {
		System.out.println("forget");
		return "enterprise/forget";
	}
	//密码找回审核
	@PostMapping("/forget")
	public String findPassword(Forget forget) {
		System.out.println(forget.toString());
		return "redirect:/registersuccess";
	}
	
	//招聘指南
	@GetMapping("/zpzn")
	public String goToZpzn() {
		System.out.println("zpzn");
		return "enterprise/zpzn";
	}
	//单位基本信息模块
	@GetMapping("/jbxx")
	public String gotToJbxx() {
		System.out.println("jbxx");
		return "enterprise/jbxx";
	}
	
	//双选会预约模块
	@GetMapping("/sxhyy")
	public String goToSxhyy() {
		System.out.println("sxhyy");
		return "enterprise/sxhyy";
	}
	
	//生源信息模块
	@GetMapping("/syxx")
	public String goToSyxx() {
		System.out.println("syxx");
		return "enterprise/syxx";
	}
	
	//网络招聘发布模块
	@GetMapping("/wlzp")
	public String goToWlzp() {
		return "enterprise/wlzp";
	}
	//网络招聘信息发布导入数据库
	@ResponseBody
	@PostMapping("/wlzp")
	public String putWlzpInfo(Recruit recruit,@RequestParam("contents") String contents) {
		System.out.println(recruit.toString());
		System.out.println(contents);
		System.out.println(contents.length());
		return contents;
	}
	
	//修改密码模块
	@GetMapping("/xgmm")
	public String goToXgmm() {
		return "enterprise/xgmm";
	}
	//修改密码事务操作
	@PutMapping("/xgmm")
	@ResponseBody
	public String setXgmm(@RequestParam("oldpw") String oldpw,@RequestParam("newpw") String newpw) {
		System.out.println(oldpw+" "+newpw);
		return "修改成功!";
	}
	
	//宣讲会预约模块
	@GetMapping("/xjhyy")
	public String goToXjhyy() {
		return "enterprise/xjhyy";
	}
	//宣讲会申请信息导入申请表
	@PostMapping("/xjhyy")
	public String putXjhyy(TeachinExam teachinExam,
			@RequestParam("name") String[] name,
			@RequestParam("idcard") String[] idcard,
			@RequestParam("gender") String[] gender,
			@RequestParam("duty") String[] duty,
			@RequestParam("telephone") String[] telephone,
			@RequestParam("contents") String contents) {
		ZpHtml zpHtml = new ZpHtml(null, contents);
		zpHtmlMapper.insert(zpHtml);
		int zphid = zpHtml.getId();
		System.out.println("zphid"+zphid);
		teachinExam.setHid(zphid);
		teachinExam.setCid(4);
		teachinExamMapper.insert(teachinExam);
		List<Visitors> visitorlist = new ArrayList<Visitors>();
		for(int i=0;i<name.length;i++) {
			visitorlist.add(new Visitors(null,zphid,name[i], idcard[i], gender[i], duty[i], telephone[i]));
		}
		visitorService.saveBatch(visitorlist);
		return "redirect:xjhyy";
	}
	
	//职位管理模块
	@GetMapping("/zwgl")
	public String goToZwgl() {
		return "enterprise/zwgl";
	}
	//新增职位
	@GetMapping("/xzzw")
	public String goToXzzw() {
		return "enterprise/xzzw";
	}
	//新增职位入库
	@PostMapping("/xzzw")
	public void putXzzw(Position position,@RequestParam("describe") String describe) {
		System.out.println(position.toString());
		System.out.println(describe);
	}
	
	//专业介绍模块
	@GetMapping("/zyjs")
	public String goToZyjs() {
		return "enterprise/zyjs";
	}
	//获取学院专业列表
	@GetMapping("/college/{code}")
	public Majors getMajors(@PathVariable("code") String code) {
		System.out.println(code);
		Majors majors = new Majors();
		return majors;
	}
}
