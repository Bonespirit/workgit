package com.pang.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.customFunc;
import com.pang.entity.Jobfair;
import com.pang.entity.Recruit;
import com.pang.entity.SearchKey;
import com.pang.entity.SxhInfo;
import com.pang.entity.Teachin;
import com.pang.service.MyElasticsearchService;
import com.pang.service.ViewService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	ViewService viewService;
	@Autowired
	MyElasticsearchService myElasticsearchService;
	@Autowired
	customFunc customFunc;
	
	@GetMapping("/jobs")
	public String goToJobs() {
		return "search/jobs";
	}
	//高级检索，并分页
	@PostMapping("/jobs/{page}")
	@ResponseBody
	public Page<Map<String, Object>> searchJobs(SearchKey searchKey,@PathVariable("page") Integer pg) throws IOException, ParseException {
		return myElasticsearchService.advancedSearch(searchKey, pg);
	}
	
	//获取双选会列表并翻页
	@GetMapping("/sxh")
	public String goToSxh(@RequestParam("page") Integer pg,Model model) {
		Page<SxhInfo> page = viewService.getSxhInfoPage(pg, 15);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/sxh");
		return "search/sxh";
	}
	//关键字查询双选会
	@GetMapping("/search/sxh")
	public String searchSxhByKeyword(@RequestParam("keyword") String keyword,
			HttpServletRequest request,Model model) throws IOException, ParseException {
		String pg = request.getParameter("page");
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "sxh", "pdate", pg,15);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/sxh");
		model.addAttribute("keyword", keyword);
		return "search/sxh";
	}
	
	//获取宣讲会信息列表并翻页
	@GetMapping("/zcxj")
	public String goToTeachin(@RequestParam("page") Integer pg,
			HttpServletRequest request,Model model) {
		String schoolmate = request.getParameter("schoolmate");
		Page<Teachin> page = viewService.getTeahinInfoPage(pg, 15, schoolmate);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/zcxj");
		return "search/zcxj";
	}
	//关键字查询宣讲会
	@GetMapping("/search/zcxj")
	public String searchTeachinByKeyword(@RequestParam("keyword") String keyword,
			HttpServletRequest request,Model model) throws IOException, ParseException {
		String pg = request.getParameter("page");
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "teachin", "tdate", pg,15);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/zcxj");
		model.addAttribute("keyword", keyword);
		return "search/zcxj";
	}
	
	//获取组团招聘信息列表
	@GetMapping("/ztzp")
	public String goToJobfair(@RequestParam("page") Integer pg,
			HttpServletRequest request,Model model) {
		String schoolmate = request.getParameter("schoolmate");
		Page<Jobfair> page = viewService.getJobfairInfoPage(pg, 15,schoolmate);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/ztzp");
		return "search/ztzp";
	}
	//关键字查询组团招聘会
	@GetMapping("/search/ztzp")
	public String searchJobfair(@RequestParam("keyword") String keyword,
			HttpServletRequest request,Model model) throws IOException, ParseException {
		String pg = request.getParameter("page");
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "jobfair", "hdate", pg,15);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/ztzp");
		model.addAttribute("keyword", keyword);
		return "search/ztzp";
	}
	
	//获取在线招聘信息列表
	@GetMapping("/zxzp")
	public String goToRecruitOnline(@RequestParam("page") Integer pg,
			HttpServletRequest request,Model model) {
		String schoolmate = request.getParameter("schoolmate");
		Page<Recruit> page = viewService.getRecruitInfoPage(pg, 4, schoolmate, "1");
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/zxzp");
		return "search/zxzp";
	}
	//关键字查询在线招聘信息
	@GetMapping("/search/zxzp")
	public String searchRecruitOnline(@RequestParam("keyword") String keyword,
			HttpServletRequest request,Model model) throws IOException, ParseException{
		String pg = request.getParameter("page");
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		oList.add("0");
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "recruit", "pdate", pg,15);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/zxzp");
		model.addAttribute("keyword", keyword);
		return "search/zxzp";
	}
	
	//获取实习招聘信息列表
	@GetMapping("/sxzp")
	public String goToRecruitSx(@RequestParam("page") Integer pg,
			HttpServletRequest request,Model model) {
		String schoolmate = request.getParameter("schoolmate");
		Page<Recruit> page = viewService.getRecruitInfoPage(pg, 2, schoolmate, "2");
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/sxzp");
		return "search/sxzp";
	}
	
	//关键字查询实习招聘信息
	@GetMapping("/search/sxzp")
	public String searchRecruitSx(@RequestParam("keyword") String keyword,
			HttpServletRequest request,Model model) throws IOException, ParseException{
		String pg = request.getParameter("page");
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		oList.add("2");
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "recruit", "pdate", pg,2);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/sxzp");
		model.addAttribute("keyword", keyword);
		return "search/sxzp";
	}
}
