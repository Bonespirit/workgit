package com.pang.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pang.customfunc.customFunc;
import com.pang.entity.Company;
import com.pang.entity.Majors;
import com.pang.entity.Mposition;
import com.pang.entity.News;
import com.pang.entity.Recruit;
import com.pang.entity.Teachin;
import com.pang.entity.User;
import com.pang.service.MyElasticsearchService;
import com.pang.service.NewsHtmlService;
import com.pang.service.StudentService;
import com.pang.service.ViewService;
import com.pang.service.ZpHtmlService;

@Controller
@RequestMapping("/views")
public class ViewsController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	customFunc customFunc;
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	ZpHtmlService zpHtmlService;
	
	@Autowired
	MyElasticsearchService myElasticsearchService;
	
	@Autowired
	NewsHtmlService newsHtmlService;
	
	//获取职位信息
	@GetMapping("/jobs/id/{id}")
	public String goToJobs(@PathVariable("id") Integer id,Model model) throws IOException {
		Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Mposition mposition = viewService.getPositionById(id);
		model.addAttribute("position", mposition);
		boolean isCollect = false;//是否已收藏
		boolean isDeliver = false;//是否已投递
		if (!"anonymousUser".equals(o)) {
			User  user = (User) o;
			if ("ROLE_student".equals(user.getRoleList().get(0).getRolename())) {
				Map<String, List<String>> map = customFunc.getPosAndColId(user.getId());
				System.out.println(map);
				isCollect = map.get("collect").contains(mposition.getId().toString());
				isDeliver = map.get("deliver").contains(mposition.getId().toString());
			}
		}
		model.addAttribute("isCollect", isCollect);
		model.addAttribute("isDeliver", isDeliver);
		model.addAttribute("company", viewService.getComPartInfo(mposition.getCid()));
		return "views/jobsview";
	}
	
	
	//获取单位注册申请表信息
	@GetMapping("/shsqc/id/{id}")
	public String goToShsqc(@PathVariable("id") Integer id,
			@RequestParam("card") String card,
			@RequestParam("email") String email,
			Model model) throws IOException {
		Company company = viewService.getCompanyInfoById(id);
		company.setCemail(email);
		company.setCard(card);
		model.addAttribute("company", company);
		return "views/shsqcview";
	}
	
	//获取宣讲会申请表和来访人员名单信息 teachin_exam、visitors
	@GetMapping("/shsqt/id/{id}")
	public String goToShsqt(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("teachin", viewService.getTeachinExamById(id));
		model.addAttribute("visitors", viewService.getVisitorsByLid(id));
		return "views/shsqtview";
	}
	
	//获取news文章内容
	@GetMapping("/news/id/{id}")
	public String getNews(@PathVariable("id")Integer id,Model model) {
		model.addAttribute("news", viewService.getNewsInfoById(id));
		model.addAttribute("contents", newsHtmlService.getById(id).getContents());
		return "views/browse";
	}
	
	//宣讲会详情信息
	@GetMapping("/xjh/id/{id}")
	public String getTeachinInfo(@PathVariable("id") Integer id,Model model) throws IOException {
		System.out.println("views");
		model.addAttribute("contents", zpHtmlService.getById(id).getContents());
		Teachin teachin = viewService.getTeachinInfo(id);
		model.addAttribute("page", myElasticsearchService.MyMatchAllByCid2(teachin.getCid()));
		model.addAttribute("teachin", teachin);
		return "views/xjhview";
	}
	
	//在线招聘信息详情
	@GetMapping("/zxzp/id/{id}")
	public String getRecruitInfo(@PathVariable("id") Integer id,Model model) throws IOException {
		model.addAttribute("contents", zpHtmlService.getById(id).getContents());
		Recruit recruit = viewService.getRecruitInfo(id);
		model.addAttribute("page", myElasticsearchService.MyMatchAllByCid2(recruit.getCid()));
		model.addAttribute("recruit", recruit);
		return "views/zxzpview";
	}
	
	//组团招聘信息详情
	@GetMapping("/ztzp/id/{id}")
	public String getJObfair(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("contents", zpHtmlService.getById(id).getContents());
		model.addAttribute("jobfair", viewService.getJobfairInfo(id));
		model.addAttribute("page", viewService.getDownloadPage(1, id));
		return "views/ztzpview";
	}
	
	//双选会招聘信息详情
	@GetMapping("/sxh/id/{id}")
	public String getSxhInfo(@PathVariable("id") Integer id,Model model) {
		model.addAttribute("contents", zpHtmlService.getById(id).getContents());
		model.addAttribute("sxh", viewService.getSxhInfo(id));
		model.addAttribute("page", viewService.getDownloadPage(1, id));
		return "views/sxhview";
	}
	
	//专业介绍详细信息
	@GetMapping("/zyjs/id/{id}")
	public String getMajorInfo(@PathVariable("id") Integer id,Model model) {
		News news = new News();
		Majors majors = viewService.getMajorInfoById(id);
		news.setTitle(majors.getMname());
		news.setPdate(majors.getPdate());
		model.addAttribute("news", news);
		model.addAttribute("contents", newsHtmlService.getById(id).getContents());
		return "views/browse";
	}
	
	//浏览学生完整简历
	@GetMapping("/student/id/{id}")
	public String browseResume(@PathVariable("id") Integer sid,Model model) throws InterruptedException, ExecutionException {
		model.addAttribute("student", studentService.getStudenInfo(sid));
		return "resumeview";
	}
}
