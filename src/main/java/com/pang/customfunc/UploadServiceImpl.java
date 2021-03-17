package com.pang.customfunc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pang.entity.WangEditorData;
import com.pang.entity.WangEditorResult;

@Service
public class UploadServiceImpl implements UploadService{

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	@Autowired
	customFunc customFunc;
	
	@Override
	public String uploadLogo(MultipartFile logo) throws IOException {
		String filename = logo.getOriginalFilename();
		String exiten = filename.substring(filename.indexOf("."));
		String fileurl = UUID.randomUUID().toString().replace("-", "")+exiten;
		logo.transferTo(new File("F://eims/file/logo/"+fileurl));
		return "upload/logo/"+fileurl;
	}

	@Override
	public String uploadLicense(MultipartFile license) throws IOException{
		String filename = license.getOriginalFilename();
		String exiten = filename.substring(filename.indexOf("."));
		String fileurl = UUID.randomUUID().toString().replace("-", "")+exiten;
		license.transferTo(new File("F://eims/file/license/"+fileurl));
		return "upload/license/"+fileurl;
	}

	@Override
	public String uploadEnclosure(MultipartFile[] enclosure) throws IOException{
		List<String> fileurl = new ArrayList<String>();
		for(MultipartFile file:enclosure) {
			String filename = file.getOriginalFilename();
			String exiten = filename.substring(filename.indexOf("."));
			String turl = UUID.randomUUID().toString().replace("-", "")+exiten;
			file.transferTo(new File("F://eims/file/enclosure/"+turl));
			fileurl.add("upload/enclosure/"+turl);
		}
		return String.join(",", fileurl);
	}

	@Override
	public JsonObject uploadWangEditorImg(Map<String, MultipartFile> wangEditor,
			HttpServletRequest request) throws IOException {
		List<String> fileurl = new ArrayList<String>();
		WangEditorResult result = new WangEditorResult();
		List<WangEditorData> datas = new ArrayList<WangEditorData>();
		Gson gson = new Gson();
		//获取IP地址用于记录上传次数
		String ip = customFunc.getVisitorIp(request).replace(":", "");
		result.setErrno(0);
		for(MultipartFile file:wangEditor.values()) {
			String filename = file.getOriginalFilename();
			String exiten = filename.substring(filename.indexOf("."));
			String turl = UUID.randomUUID().toString().replace("-", "")+exiten;
			file.transferTo(new File("F://eims/file/wangEditor/images/"+turl));
			fileurl.add("/upload/wangEditor/images/"+turl);
		}
		for(String url:fileurl) {
			datas.add(new WangEditorData(url, null, null));
		}
		result.setIpaddr(ip);
		return (JsonObject) gson.toJsonTree(result);
		//判断上传次数 合理执行
//		if (judgeNumber(fileurl,ip)) {
//			for(String url:fileurl) {
//				datas.add(new WangEditorData(url, null, null));
//			}
//			result.setIpaddr(ip);
//			return (JsonObject) gson.toJsonTree(result);
//		}
		//超过次数
//		result.setErrno(-999);
//		return (JsonObject) gson.toJsonTree(result);
	}
	
	public boolean judgeNumber(List<String> urls,String ip) {
		int number=0;
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		//redis记录ip上传次数
		if (operations.get(ip) == null) {
			number = urls.size();
		}else {
			number = Integer.parseInt(operations.get(ip))+urls.size();
		}
		//超过30次禁止
		if (number > 5) {
			return false;
		}
		//否则叠加次数
		operations.set(ip,number+"");
		//redis记录所有url
		operations.set("eims:upload:wangEditor:img", 
				(String.join(",", urls)+","+operations.get("eims:upload:wangEditor:img")));
		return true;
	}
}
