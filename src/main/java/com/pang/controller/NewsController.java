package com.pang.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pang.customfunc.customFunc;
import com.pang.entity.News;
import com.pang.entity.NewsHtml;
import com.pang.mapper.NewsHtmlMapper;
import com.pang.service.NewsHtmlService;
import com.pang.service.ViewService;

//文章内容板块
@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	NewsHtmlMapper newsHtmlMapper;
	
	@Autowired
	NewsHtmlService newsHtmlService;
	
	@Autowired
	customFunc customFunc;
	
	//常用下载
	@GetMapping("/download")
	public String getDownloadList(@RequestParam("page") Integer pg,Model model) {
		model.addAttribute("page", viewService.getDownloadPage(pg));
		model.addAttribute("curl", "news/download");
		return "news/download";
	}
	
	//通过栏目获取数据并翻页
	@GetMapping("/search")
	public String goToTzgg(@RequestParam("column") Integer column,@RequestParam("page") Integer pg,Model model) {
		News news = new News();
		Map<String, String> map = customFunc.getCnameByColumn(column);
		news.setCname(map.get("cname"));
		news.setMcolumn(column);
		model.addAttribute("page", viewService.getNewsListByColumn(column, pg, 15));
		model.addAttribute("news", news);
		model.addAttribute("curl", "news/search?column="+column);
		return "news/browse";
	}
	
	@GetMapping("/id/{id}")
	@ResponseBody
	public NewsHtml getArtById(@PathVariable("id") Integer id) {
		NewsHtml newsHtml = new NewsHtml();
		newsHtml = newsHtmlService.getById(id);
		System.out.println(newsHtml.toString());
		return newsHtml;
	}
	
	@GetMapping("/browse")
	public String hoToBrowse() {
		return "news/browse";
	}
}
