package com.pang.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.customFunc;
import com.pang.entity.Teachin;
import com.pang.entity.User;
import com.pang.mapper.CommonMapper;
import com.pang.service.UserService;
import com.pang.service.ViewService;

@Controller
public class IndexController {
	
	@Autowired
	CommonMapper commonMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	customFunc customFunc;
	
	//修改密码事务操作
	@PutMapping("/xgmm")
	@ResponseBody
	public String setXgmm(@RequestParam("oldpw") String oldpw,@RequestParam("newpw") String newpw) {
		System.out.println(oldpw+" "+newpw);
		//获取当前登录用户信息
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (new BCryptPasswordEncoder().matches(oldpw, user.getPassword())) {
			UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
			updateWrapper.set("password", new BCryptPasswordEncoder().encode(newpw));
			userService.update(updateWrapper);
			return "success";
		}else {
			return "fail";
		}
	}
	
	//单位注册提交成功
	@GetMapping("/registersuccess")
	public String goToRegisterSucess() {
		System.out.println("success");
		return "registersuccess";
	}
	
	//首页
	@GetMapping("/")
	public String mIndex(Model model) {
		//首页展示数据有：通知公告、就业公示、新闻热点、宣讲会、校友专场、在线招聘、实习招聘
		//获取通知公告
		model.addAttribute("tpage", viewService.getNewsListByColumn(0,1,8));
		//获取就业公示
		model.addAttribute("jpage", viewService.getNewsListByColumn(1,1,8));
		//获取新闻热点
		model.addAttribute("xpage", viewService.getNewsListByColumn(2,1,8));
		//获取宣讲会信息
		Page<Teachin> page = viewService.getTeahinInfoPage(1, 6,"0");
		List<Teachin> teachins = page.getRecords();
		model.addAttribute("mdate", customFunc.apartDate(teachins));
		model.addAttribute("teachin", page);
		return "index";
	}
	
	//首页异步获取数据
	@GetMapping("/ajax/id/{id}")
	@ResponseBody
	public Map<String, Object> ajaxGetData(@PathVariable("id")Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (id) {
			case 1:
				//获取校友专场信息
				Page<Teachin> page = viewService.getTeahinInfoPage(1, 6,"0");
				List<Teachin> teachins = page.getRecords();
				map.put("mdate", customFunc.apartDate(teachins));
				map.put("teachin", page);
				return map;
			case 2:
				//获取在线招聘信息
				map.put("news", viewService.getRecruitInfoPage(1, 6, null, "1"));
				return map;
			case 3:
				//获取实习招聘信息
				map.put("news", viewService.getRecruitInfoPage(1, 6, null, "2"));
				return map;
		}
		return map;
	}
	
	//用人单位登录模块
	@GetMapping("/eplogin")
	public String goToEpLogin() {
		return "eplogin";
	}
	
	@GetMapping("/login")
	public String goToStLogin() {
		return "login";
	}
	
	@GetMapping("/back")
	public String goToback() {
		return "back";
	}
	
	//下载简历
	@GetMapping("/downloadresume")
	@ResponseBody
	public List<String> downloadresume(@RequestParam("idlist") String tidlist) {
		//获取id列表
		List<Integer> idList = new ArrayList<>();
		List<String> sidlist = new ArrayList<String>(Arrays.asList(tidlist));
		for(int i=0;i<sidlist.size();i++) {
			idList.add(Integer.parseInt(sidlist.get(i)));
		}
		List<String> resumeurls = commonMapper.getResumeUrlList(idList);
		return resumeurls;
	}
}
