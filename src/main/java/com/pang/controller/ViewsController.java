package com.pang.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pang.entity.Company;
import com.pang.entity.Recruit;
import com.pang.entity.Teachin;
import com.pang.service.MyElasticsearchService;
import com.pang.service.NewsHtmlService;
import com.pang.service.ViewService;
import com.pang.service.ZpHtmlService;

@Controller
@RequestMapping("/views")
public class ViewsController {
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	ZpHtmlService zpHtmlService;
	
	@Autowired
	MyElasticsearchService myElasticsearchService;
	
	@Autowired
	NewsHtmlService newsHtmlService;
	
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
		model.addAttribute("contents", zpHtmlService.getById(id));
		Teachin teachin = viewService.getTeachinInfo(id);
		model.addAttribute("position", myElasticsearchService.MyMatchAllByCid2(teachin.getCid()));
		model.addAttribute("teachin", teachin);
		return "views/xjhview";
	}
	
	//在线招聘信息详情
	@GetMapping("/zxzp/id/{id}")
	public String getRecruitInfo(@PathVariable("id") Integer id,Model model) throws IOException {
		model.addAttribute("contents", zpHtmlService.getById(id));
		Recruit recruit = viewService.getRecruitInfo(id);
		model.addAttribute("position", myElasticsearchService.MyMatchAllByCid2(recruit.getCid()));
		model.addAttribute("recruit", recruit);
		return "views/zxzpview";
	}
}
