package com.pang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public Page<Map<String, Object>> searchJobs(SearchKey searchKey,@PathVariable("page") Integer pg) throws IOException {
		return myElasticsearchService.advancedSearch(searchKey, pg);
	}
	
	//获取news列表并翻页
	@GetMapping("/news")
	public String goToNews(@RequestParam("column") String mcolumn,
			@RequestParam("page") Integer pg,
			@RequestParam("keyword") String keyword,Model model) throws IOException {
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		oList.add(mcolumn);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "news", "pdate", pg);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/news?column="+mcolumn+"&keyword="+keyword);
		return "news/browse";
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
			@RequestParam("page")Integer pg,Model model) throws IOException {
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "sxh", "pdate", pg);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/sxh?keyword="+keyword);
		return "search/sxh";
	}
	
	//获取宣讲会信息列表并翻页
	@GetMapping("/zcxj")
	public String goToTeachin(@RequestParam("page") Integer pg,
			@RequestParam("schoolmate") String schoolmate,Model model) {
		Page<Teachin> page = viewService.getTeahinInfoPage(pg, 15, schoolmate);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/zcxj");
		return "search/zcxj";
	}
	//关键字查询宣讲会
	@GetMapping("/search/zcxj")
	public String searchTeachinByKeyword(@RequestParam("keyword") String keyword,
			@PathVariable("page")Integer pg,Model model) throws IOException {
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "teachin", "tdate", pg);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/zcxj?keyword="+keyword);
		return "search/zcxj";
	}
	
	//获取组团招聘信息列表
	@GetMapping("/ztzp")
	public String goToJobfair(@RequestParam("page") Integer pg,
			@RequestParam("schoolmate") String schoolmate,Model model) {
		Page<Jobfair> page = viewService.getJobfairInfoPage(pg, 15,schoolmate);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/ztzp");
		return "search/ztzp";
	}
	//关键字查询组团招聘会
	@GetMapping("/search/ztzp")
	public String searchJobfair(@RequestParam("keyword") String keyword,
			@RequestParam("page")Integer pg,Model model) throws IOException {
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "jobfair", "hdate", pg);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/ztzp?keyword="+keyword);
		return "search/ztzp";
	}
	
	//获取在线招聘信息列表
	@GetMapping("/zxzp")
	public String goToRecruitOnline(@RequestParam("page") Integer pg,
			@RequestParam("schoolmate") String schoolmate,Model model) {
		Page<Recruit> page = viewService.getRecruitInfoPage(pg, 4, schoolmate, "0");
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/zxzp");
		return "search/zxzp";
	}
	//关键字查询在线招聘信息
	@GetMapping("/search/zxzp")
	public String searchRecruitOnline(@RequestParam("keyword") String keyword,
			@RequestParam("page")Integer pg,Model model) throws IOException{
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		oList.add("0");
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "recurit", "pdate", pg);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/zxzp?keyword="+keyword);
		return "search/zxzp";
	}
	
	//获取实习招聘信息列表
	@GetMapping("/sxzp")
	public String goToRecruitSx(@RequestParam("page") Integer pg,
			@RequestParam("schoolmate") String schoolmate,Model model) {
		Page<Recruit> page = viewService.getRecruitInfoPage(pg, 4, schoolmate, "1");
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/sxzp");
		return "search/sxzp";
	}
	
	//关键字查询实习招聘信息
	@GetMapping("/search/sxzp")
	public String searchRecruitSx(@RequestParam("keyword") String keyword,
			@RequestParam("page")Integer pg,Model model) throws IOException{
		List<String> oList = new ArrayList<String>();
		oList.add(keyword);
		oList.add("1");
		Page<Map<String, Object>> page = 
				myElasticsearchService.getSearchResult(oList, "recurit", "pdate", pg);
		customFunc.getModelByPage(page,model);
		model.addAttribute("curl", "search/search/sxzp?keyword="+keyword);
		return "search/sxzp";
	}
}
