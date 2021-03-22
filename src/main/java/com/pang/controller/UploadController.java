package com.pang.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.pang.customfunc.UploadService;
import com.pang.customfunc.customFunc;
import com.pang.entity.User;

//文件上传处理controller
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	@Autowired
	customFunc customFunc;
	
	//wangEditor图片上传处理
	@PostMapping("/wangEditor")
	@ResponseBody
	public JsonObject uploadWangEditor(HttpServletRequest request,
			@RequestParam("ipAddr") String ipAddr) throws IOException {
		MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
		return uploadService.uploadWangEditorImg(params.getFileMap(),request,ipAddr);
	}
	
	//附件上传处理
	@PostMapping("/enclosure")
	@ResponseBody
	public String uploadEnclosure(@RequestPart("menclosure") MultipartFile[] enclosure) throws IOException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadService.uploadEnclosure(enclosure,user.getId());
		return "success";
	}
	
	//资源上传
	@PostMapping("/sourceupload")
	public String uploadSource(@RequestPart("sourcefile") MultipartFile[] sourcefile) throws IOException {
		uploadService.uploadSourceFile(sourcefile);
		return "success";
	}
}
