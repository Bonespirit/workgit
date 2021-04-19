package com.pang.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pang.customfunc.UploadService;
import com.pang.entity.Jobfair;
import com.pang.entity.Majors;
import com.pang.entity.News;
import com.pang.entity.Recruit;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.entity.TeachinRefuse;
import com.pang.entity.User;
import com.pang.mapper.ZpHtmlMapper;
import com.pang.service.TeacherService;

//教职工业务处理controller
@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	UploadService uploadService;
	@Autowired
	TeacherService teacherService;
	@Autowired
	ZpHtmlMapper zpHtmlMapper;
	
	//查看招聘简章
	@GetMapping("/preview/id/{id}")
	@ResponseBody
	public String preview(@PathVariable("id") Integer id) {
		return zpHtmlMapper.selectById(id).getContents();
	}
	
	//审核宣讲会预约teachin_exam
	@GetMapping("/shsqt")
	public String goToShsqt(@RequestParam("page") Integer pg,Model model) {
		model.addAttribute("page", teacherService.getTeachinExamListByPage(pg));
		model.addAttribute("curl", "teacher/shsqt");
		return "teacher/shsqt";
	}
	//宣讲会审核失败处理
	@DeleteMapping("/shsqt/fail/{tid}")
	public String tFail(@PathVariable("tid") Integer id,
			TeachinRefuse teachinRefuse,
			@RequestParam("email") String email) {
		
		teacherService.dsiTeachinExam(id,email,teachinRefuse);
		return "redirect:/teacher/shsqt?page=1";
	}
	//宣讲会审核成功
	@PostMapping("/shsqt/success")
	public String tSuccess(Teachin teachin,@RequestParam("email") String email) {
		System.out.println(teachin.toString());
		teacherService.idTeachin(teachin,email);
		return "redirect:/teacher/shsqt?page=1";
	}
	
	//单位注册申请列表
	@GetMapping("/shsq")
	public String goToShsq(@RequestParam("page") Integer pg,Model model) {
		model.addAttribute("page", teacherService.getCompanyExamListByPage(pg));
		model.addAttribute("curl", "teacher/shsq");
		return "teacher/shsq";
	}
	//注册审核失败处理
	@DeleteMapping("/shsqc/fail/{id}")
	public String cFail(@PathVariable("id") Integer id,
			@RequestParam("email") String email,
			@RequestParam("reason") String reason) {
		
		teacherService.dsCinfoAndExam(id, email, reason);
		return "redirect:/teacher/shsq?page=1";
	}
	//注册成功
	@GetMapping("/shsqc/success/{id}")
	public String cSuccess(@PathVariable("id") Integer id,@RequestParam("email") String email) {
		teacherService.idCompany(id,email);
		return "redirect:/teacher/shsq?page=1";
	}
	
	//宣讲会编辑模块,并且开始记录主机操作
	@GetMapping("/xjh")
	public String goToXjh(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/xjh";
	}
	//宣讲会信息发布
	@PostMapping("/xjh")
	public String putTeachin(Teachin teachin,
			@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		uploadService.updateWangImgMaster(ip, validurl);
		teacherService.pTeachin(teachin, contents, -1);
		return "redirect:/teacher/xjh";
	}
	
	//发布内容编辑模块
	@GetMapping("/fbnr")
	public String goToFbnr(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/fbnr";
	}
	//发布内容
	@PostMapping("/fbnr")
	public String putNews(News news,
			@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		uploadService.updateWangImgMaster(ip, validurl);
		teacherService.pNews(news, contents);
		return "redirect:/teacher/fbnr";
	}
	
	//组团招聘编辑模块
	@GetMapping("/ztzp")
	public String goToZtzp(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/ztzp";
	}
	//组团招聘信息发布
	@PostMapping("/ztzp")
	public String putJobfair(Jobfair jobfair,@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadService.updateWangImgMaster(ip, validurl);
		teacherService.pJobfair(jobfair, contents,user.getId());
		return "redirect:/teacher/ztzp";
	}
	
	//双选会编辑模块
	@GetMapping("/sxh")
	public String goToSxh(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/sxh";
	}
	//双选会信息发布
	@PostMapping("/sxh")
	public String putSxhInfo(SxhInfo sxhInfo,@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadService.updateWangImgMaster(ip, validurl);
		teacherService.pSxhInfo(sxhInfo, contents,user.getId());
		return "redirect:/teacher/sxh";
	}
	
	//在线招聘编辑模块
	@GetMapping("/zxzp")
	public String goToZxzp(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/zxzp";
	}
	//在线招聘发布
	@PostMapping("/zxzp")
	public String putRecruit(Recruit recruit,
			@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		uploadService.updateWangImgMaster(ip, validurl);
		teacherService.pRecruit(recruit, contents,-1);
		return "redirect:/teacher/zxzp";
	}
	
	//生源信息编辑模块
	@GetMapping("/syxx")
	public String goToSyxx(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/syxx";
	}
	
	//专业介绍编辑模块
	@GetMapping("/zyjs")
	public String goToZyjs(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "teacher/zyjs";
	}
	//专业介绍内容发布
	@PostMapping("/zyjs")
	public String putMajors(Majors majors,@RequestParam("contents") String contents) {
		teacherService.pMajors(majors, contents);
		return "redirect:/teacher/zyjs";
	}
	
	//资源文件上传
	@GetMapping("/uploadsource")
	public String goToUploadsource() {
		return "teacher/uploadsource";
	}
	
	//附件上传处理
	@PostMapping("/enclosure")
	@ResponseBody
	public String uploadEnclosure(@RequestPart("menclosure") MultipartFile[] enclosure) throws IOException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadService.uploadEnclosure(enclosure,user.getId());
		return "success";
	}
	
}
