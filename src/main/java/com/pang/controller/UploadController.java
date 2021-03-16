package com.pang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pang.customfunc.UploadService;
import com.pang.entity.WangEditorData;
import com.pang.entity.WangEditorResult;

//文件上传处理controller
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	UploadService uploadService;
	
	//wangEditor图片上传处理
	@PostMapping("/wangEditor")
	@ResponseBody
	public JsonObject uploadWangEditor(HttpServletRequest request) throws IOException {
		MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
		List<String> urls = uploadService.uploadWangEditorImg(params.getFileMap());
		WangEditorResult result = new WangEditorResult();
		result.setErrno(0);
		List<WangEditorData> datas = new ArrayList<WangEditorData>();
		for(String url:urls) {
			datas.add(new WangEditorData(url, null, null));
		}
		result.setData(datas);
		Gson gson = new Gson();
		System.out.println((JsonObject) gson.toJsonTree(result));
		return (JsonObject) gson.toJsonTree(result);
	}
	
	//附件上传处理
	@PostMapping("/enclosure")
	@ResponseBody
	public String uploadEnclosure(@RequestPart("menclosure") MultipartFile[] enclosure) throws IOException {
		String murl = uploadService.uploadEnclosure(enclosure);
		System.out.println(murl);
		return murl;
	}
}
