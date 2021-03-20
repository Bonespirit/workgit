package com.pang.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pang.customfunc.UploadService;
import com.pang.entity.Company;
import com.pang.entity.Forget;
import com.pang.entity.Majors;
import com.pang.entity.Mposition;
import com.pang.entity.Recruit;
import com.pang.entity.TeachinExam;
import com.pang.entity.User;
import com.pang.service.EnterpriseService;
import com.pang.service.MyElasticsearchService;
import com.pang.service.ViewService;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	
	@Autowired
	UploadService uploadService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	MyElasticsearchService myElasticsearchService;
	@Autowired
	ViewService viewService;
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
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
	public String gotToJbxx(Model model) throws IOException {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("company", viewService.getCompanyInfoById(user.getForeignkey()));
		return "enterprise/jbxx";
	}
	
	//生源信息模块
	@GetMapping("/syxx")
	public String goToSyxx(Model model) {
		model.addAttribute("news", enterpriseService.getNewsByColumnId(9));
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
	
	//宣讲会预约模块
	@GetMapping("/xjhyy")
	public String goToXjhyy(Model model) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", enterpriseService.getTeachinByid(user.getForeignkey()));
		return "enterprise/xjhyy";
	}
	@GetMapping("/xjhyye")
	public String goToXjhyye(Model model) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", enterpriseService.getTeachinExamByid(user.getForeignkey()));
		return "enterprise/xjhyye";
	}
	@GetMapping("/xjhyyu")
	public String goToXjhyyu(Model model) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", enterpriseService.getTeachinRefuseByid(user.getForeignkey()));
		return "enterprise/xjhyyu";
	}
	@GetMapping("/xjhyyp")
	public String goToXjhyyp(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "enterprise/xjhyyp";
	}
	//宣讲会申请信息导入申请表
	@PostMapping("/xjhyyp")
	public String putXjhyy(TeachinExam teachinExam,
			@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		uploadService.updateWangImgMaster(ip, validurl);
		enterpriseService.putTeachinExam(teachinExam, contents);
		return "redirect:xjhyye";
	}
	
	//职位管理模块
	@GetMapping("/zwgl")
	public String goToZwgl(Model model) throws IOException {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("positions", myElasticsearchService.MyMatchAllByCid1(user.getForeignkey(),1));
		return "enterprise/zwgl";
	}
	//新增职位
	@GetMapping("/xzzw")
	public String goToXzzw() {
		return "enterprise/xzzw";
	}
	//新增职位入库
	@PostMapping("/xzzw")
	public String putXzzw(Mposition mposition,@RequestParam("describe") String describe) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mposition.setCid(user.getId());
		mposition.setName(user.getName());
		mposition.setTelephone(user.getTelephone());
		enterpriseService.putPositionInfo(mposition, describe);
		return "zwgl";
	}
	
	//专业介绍模块
	@GetMapping("/zyjs")
	public String goToZyjs() {
		return "enterprise/zyjs";
	}
	//获取学院专业列表
	@GetMapping("/college/{code}")
	@ResponseBody
	public List<Majors> getMajors(@PathVariable("code") Integer code,Model model) {
		System.out.println(code);
		return enterpriseService.getMajorsInfo(code);
	}
}
