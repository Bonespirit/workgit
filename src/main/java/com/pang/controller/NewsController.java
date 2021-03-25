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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.customFunc;
import com.pang.entity.News;
import com.pang.entity.NewsHtml;
import com.pang.mapper.NewsHtmlMapper;
import com.pang.service.MyElasticsearchService;
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
	MyElasticsearchService myElasticsearchService;
	
	@Autowired
	customFunc customFunc;
	
	//常用下载
	@GetMapping("/download")
	public String getDownloadList(@RequestParam("page") Integer pg,Model model) {
		model.addAttribute("page", viewService.getDownloadPage(pg,-1));
		model.addAttribute("curl", "news/download");
		return "news/download";
	}
	
	//通过栏目获取数据并翻页,包含查询
	@GetMapping("/search")
	public String goToTzgg(HttpServletRequest request,@RequestParam("column") Integer mcolumn,Model model) throws IOException, ParseException {
		News news = new News();
		news.setCname(customFunc.getCnameByColumn(mcolumn));
		news.setMcolumn(mcolumn);
		String keyword = request.getParameter("keyword");
		String pg = request.getParameter("page");
		if (keyword !=null && !keyword.isEmpty()) {
			System.out.println("es");
			List<String> oList = new ArrayList<String>();
			oList.add(keyword);
			oList.add(mcolumn.toString());
			Page<Map<String, Object>> page = 
					myElasticsearchService.getSearchResult(oList, "news", "pdate", pg,15);
			customFunc.getModelByPage(page, model);
			model.addAttribute("keyword", keyword);
		}else {
			Page<News> page = viewService.getNewsListByColumn(mcolumn, Integer.parseInt(pg), 2);
			customFunc.getModelByPage(page, model);
		}
		model.addAttribute("news", news);
		model.addAttribute("column", mcolumn);
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
