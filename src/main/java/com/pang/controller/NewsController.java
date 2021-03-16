package com.pang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.News;
import com.pang.entity.NewsHtml;
import com.pang.mapper.NewsHtmlMapper;
import com.pang.mapper.NewsMapper;
import com.pang.service.NewsHtmlService;

//文章内容板块
@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsMapper newsMapper;
	
	@Autowired
	NewsHtmlMapper newsHtmlMapper;
	
	@Autowired
	NewsHtmlService newsHtmlService;
	
	
	//通知公告模块
	@GetMapping("/column/{col}/page/{pg}")
	public String goToTzgg(@PathVariable("col") Integer column,@PathVariable("pg") Integer pg,Model model) {
		
		Page<News> newsPage = new Page<>(pg,15);
		QueryWrapper<News> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("column", column);
		Page<News> page = newsMapper.selectPage(newsPage, queryWrapper);
		model.addAttribute("page", page);
		
		return "news/tzgg";
	}
	
	@GetMapping("/id/{id}")
	@ResponseBody
	public NewsHtml getArtById(@PathVariable("id") Integer id) {
		System.out.println("获取文章");
		NewsHtml newsHtml = new NewsHtml();
		newsHtml = newsHtmlService.getById(id);
		System.out.println(newsHtml.toString());
		return newsHtml;
	}
	
	//就业公示模块
	@GetMapping("/jygs")
	public String goToJygs() {
		return "news/jygs";
	}
	
	//新闻热点模块
	@GetMapping("/xwrd")
	public String goToXwrd() {
		return "news/xwrd";
	}
	
	//就业指导模块
	@GetMapping("/jyzd")
	public String goToJyzd() {
		return "news/jyzd";
	}
	
	//就业政策模块
	@GetMapping("/jyzc")
	public String goToJyzc() {
		return "news/jyzc";
	}
}
