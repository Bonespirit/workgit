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
import com.pang.service.ViewService;

@Controller
@RequestMapping("/views")
public class ViewsController {
	
	@Autowired
	ViewService viewService;
	
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
}
