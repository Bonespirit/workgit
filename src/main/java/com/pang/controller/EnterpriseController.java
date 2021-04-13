package com.pang.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pang.customfunc.UploadService;
import com.pang.entity.Company;
import com.pang.entity.Forget;
import com.pang.entity.InterviewNotice;
import com.pang.entity.Majors;
import com.pang.entity.Mposition;
import com.pang.entity.Recruit;
import com.pang.entity.TeachinExam;
import com.pang.entity.User;
import com.pang.entity.VerifyCode;
import com.pang.mapper.ZpHtmlMapper;
import com.pang.service.EnterpriseService;
import com.pang.service.IVerifyCodeGen;
import com.pang.service.MyElasticsearchService;
import com.pang.service.ResumeProcessService;
import com.pang.service.TeacherService;
import com.pang.service.ViewService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	
	@Autowired
	ResumeProcessService resumeProcessService;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	EnterpriseService enterpriseService;
	
	@Autowired
	MyElasticsearchService myElasticsearchService;
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	ZpHtmlMapper zpHtmlMapper;
	
	@Autowired
    IVerifyCodeGen iVerifyCodeGen;
    
	//获取图形验证码
	@GetMapping("/verifyCode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
	    try {
	        //设置长宽
	        VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
	        String code = verifyCode.getCode();
	        log.info(code);
	        //将VerifyCode绑定session
	        request.getSession().setAttribute("VerifyCode", code);
	        //设置响应头
	        response.setHeader("Pragma", "no-cache");
	        //设置响应头
	        response.setHeader("Cache-Control", "no-cache");
	        //在代理服务器端防止缓冲
	        response.setDateHeader("Expires", 0);
	        //设置响应内容类型
	        response.setContentType("image/jpeg");
	        response.getOutputStream().write(verifyCode.getImgBytes());
	        response.getOutputStream().flush();
	    }
	    catch (IOException e) {
	        log.info("", e);
	    }
	}
	
	//检查验证码
	@PostMapping("/checkMark")
	@ResponseBody
	public String checkMark(@RequestParam("mark") String mark,
			HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String code = ((String) request.getSession().getAttribute("VerifyCode")).toLowerCase();
		if (code.equals(mark.toLowerCase())) {
			return "true";
		}else {
			return "false";
		}
	}
	
	//检查手机号注册情况
	@PostMapping("/testtelephone")
	public void testTelephone(@RequestParam("telephone") String telephone,
			HttpServletResponse response) throws IOException {
		if (enterpriseService.testTelephone(telephone)) {
			response.getWriter().write("{\"valid\":false}");
		}else {
			response.getWriter().write("{\"valid\":true}");
		}
	}
	//检查用户名注册情况
	@PostMapping("/testusername")
	public void testUsername(@RequestParam("loginName") String username,
			HttpServletResponse response) throws IOException {
		if (enterpriseService.testUsername(username)) {
			response.getWriter().write("{\"valid\":false}");
		}else {
			response.getWriter().write("{\"valid\":true}");
		}
	}
	
	//审查宣讲会申请情况
	@GetMapping("/checkteachinE/id/{id}")
	public String goToCheckTeachinE(@PathVariable("id") Integer id,Model model) {
		TeachinExam teachinExam = viewService.getTeachinExamById(id);
		if (teachinExam == null) {
			return "redirect:/error/404";
		}
		model.addAttribute("teachin", teachinExam);
		model.addAttribute("visitors", viewService.getVisitorsByLid(id));
		model.addAttribute("contents", zpHtmlMapper.selectById(id).getContents());
		return "enterprise/checkteache";
	}
	//取消宣讲会申请
	@DeleteMapping("/cancel/{id}")
	public String deleteTeachin(@PathVariable("id") Integer id) {
		enterpriseService.deleteTeachinE(id);
		return "redirect:/enterprise/xjhyye";
	}
	
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
		model.addAttribute("news", viewService.getNewsListByColumn(9, 1, 10));
		return "enterprise/syxx";
	}
	
	//网络招聘发布模块
	@GetMapping("/wlzp")
	public String goToWlzp(Model model) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", viewService.getRecruitPage(user.getForeignkey()));
		return "enterprise/wlzp";
	}
	@GetMapping("/precruit")
	public String goToPRecruit(HttpServletRequest request,Model model) {
		model.addAttribute("ipAddr", uploadService.getIpAndPutInRedis(request));
		return "enterprise/precruit";
	}
	//网络招聘信息发布导入数据库
	@PostMapping("/wlzp")
	public String putWlzpInfo(Recruit recruit,
			@RequestParam("contents") String contents,
			@RequestParam("validurl") String validurl,
			@RequestParam("ipAddr") String ip) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		teacherService.pRecruit(recruit, contents, user.getForeignkey());
		uploadService.updateWangImgMaster(ip, validurl);
		return "redirect:wlzp";
	}
	
	//修改密码模块
	@GetMapping("/xgmm")
	public String goToXgmm() {
		return "enterprise/xgmm";
	}
	
	//宣讲会预约模块已成功
	@GetMapping("/xjhyy")
	public String goToXjhyy(Model model) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", enterpriseService.getTeachinByid(user.getForeignkey()));
		return "enterprise/xjhyy";
	}
	//待审核
	@GetMapping("/xjhyye")
	public String goToXjhyye(Model model) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", enterpriseService.getTeachinExamByid(user.getForeignkey()));
		return "enterprise/xjhyye";
	}
	//申请失败
	@GetMapping("/xjhyyu")
	public String goToXjhyyu(Model model) {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", enterpriseService.getTeachinRefuseByid(user.getForeignkey()));
		return "enterprise/xjhyyu";
	}
	//发布申请
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
		
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadService.updateWangImgMaster(ip, validurl);
		teachinExam.setCid(user.getForeignkey());
		teachinExam.setEmail(user.getEmail());
		enterpriseService.putTeachinExam(teachinExam, contents);
		return "redirect:xjhyye";
	}
	
	//职位管理模块
	@GetMapping("/zwgl")
	public String goToZwgl(@RequestParam("page") Integer pg,Model model) throws IOException, ParseException {
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", myElasticsearchService.MyMatchAllByCid1(user.getForeignkey(),pg));
		model.addAttribute("curl", "enterprise/zwgl");
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
		mposition.setCid(user.getForeignkey());
		mposition.setName(user.getName());
		mposition.setTelephone(user.getTelephone());
		enterpriseService.putPositionInfo(mposition, describe);
		return "redirect:zwgl?page=1";
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
	
	//简历处理模块
	//根据简历状态获取简历列表并翻页
	@GetMapping("/psearch/tag/{tag}")
	public String searchPos(@PathVariable("tag") Integer tag,
			@RequestParam("page") Integer pg,Model model) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", 
				resumeProcessService.getDeliverResume(1,-1, user.getForeignkey(), tag, pg));
		model.addAttribute("tag", tag);
		model.addAttribute("curl", "/enterprise/psearch/tag/"+tag);
		return getQzsqUrl(tag);
	}
	//职位查询简历
	@GetMapping("/ksearch/tag/{tag}")
	public String searchPosByKey(@PathVariable("tag") Integer tag,
			@RequestParam("keyword") String keyword,
			HttpServletRequest request,Model model) {
		int pg = 1;
		if (request.getParameter("page") != null) {
			pg = Integer.parseInt(request.getParameter("page"));
		}
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", 
				resumeProcessService.searchByKey(user.getForeignkey(), tag, pg, keyword));
		model.addAttribute("curl", "/enterprise/ksearch/tag/"+tag);
		model.addAttribute("keyword", keyword);
		model.addAttribute("tag", tag);
		return "redirect:"+getQzsqUrl(tag);
	}
	//不合适处理
	@PostMapping("/resumeout")
	@ResponseBody
	public String resumeOut(@RequestParam("idlist") String idlist,
			@RequestParam("sheet") String sheet) {
		resumeProcessService.resumeOut(idlist, sheet);
		return "success";
	}
	//简历放入待处理区域处理
	@PostMapping("/resumecollect")
	@ResponseBody
	public String resumeCllect(@RequestParam("idlist") String idlist) {
		resumeProcessService.initialScreen(idlist);
		return "success";
	}
	//面试通知
	@PostMapping("/noticestudent")
	public String noticeStudent(InterviewNotice interviewNotice) {
		resumeProcessService.interviewNotice(interviewNotice,1);
		return "redirect:/enterprise/psearch/tag/3?page=1";
	}
	//自行通知面试
	@PutMapping("/selfnotice")
	@ResponseBody
	public String selfNotice(InterviewNotice interviewNotice) {
		resumeProcessService.interviewNotice(interviewNotice,0);
		return "success";
	}
	
	public String getQzsqUrl(Integer tag) {
		switch (tag) {
		case 1:
			return "enterprise/qzsq";
		case 2:
			return "enterprise/qzsqpending";
		case 3:
			return "enterprise/qzsqselect";
		default:
			return "enterprise/qzsqout";
		}
	}
}
