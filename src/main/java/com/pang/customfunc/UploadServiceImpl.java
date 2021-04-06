package com.pang.customfunc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pang.entity.Download;
import com.pang.entity.WangEditorData;
import com.pang.entity.WangEditorResult;
import com.pang.service.DownloadService;

@Service
public class UploadServiceImpl implements UploadService{

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	@Autowired
	customFunc customFunc;
	@Autowired
	DownloadService downloadService;
	
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
	public void uploadEnclosure(MultipartFile[] enclosure,Integer id) throws IOException{
		List<Download> downloads = new ArrayList<>();
		for(MultipartFile file:enclosure) {
			String filename = file.getOriginalFilename();
			String exiten = filename.substring(filename.indexOf("."));
			String turl = UUID.randomUUID().toString().replace("-", "")+exiten;
			file.transferTo(new File("F://eims/file/enclosure/"+turl));
			downloads.add(new Download(null, id, filename, "upload/enclosure/"+turl, null, null));
		}
		downloadService.saveBatch(downloads);
	}

	@Override
	public JsonObject uploadWangEditorImg(Map<String, MultipartFile> wangEditor,
			HttpServletRequest request,String ip) throws IOException {
		List<String> fileurl = new ArrayList<String>();
		WangEditorResult result = new WangEditorResult();
		List<WangEditorData> datas = new ArrayList<WangEditorData>();
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		Gson gson = new Gson();
		result.setErrno(0);
		//判断上传次数 合理执行
		if (operations.get(ip).split(",").length <= 30) {
			for(MultipartFile file:wangEditor.values()) {
				String filename = file.getOriginalFilename();
				String exiten = filename.substring(filename.indexOf("."));
				String turl = UUID.randomUUID().toString().replace("-", "")+exiten;
				file.transferTo(new File("F://eims/file/wangEditor/images/"+turl));
				datas.add(new WangEditorData("/upload/wangEditor/images/"+turl, null, null));
				fileurl.add(turl);
			}
			operations.set(ip,(String.join(",", fileurl)+","+operations.get(ip)));
			result.setData(datas);
			return (JsonObject) gson.toJsonTree(result);
		}
		//超过次数
		result.setErrno(-999);
		return (JsonObject) gson.toJsonTree(result);
	}
	
	@Override
	public String getIpAndPutInRedis(HttpServletRequest request) {
		//分支的key
		String branch = "eims:upload:wangEditor:img:branch";
		//获取ip地址
		String ipAddr = "wangEditor:"+customFunc.getVisitorIp(request).replace(":", "");
		ValueOperations<String , String> operations = redisTemplate.opsForValue();
		//如果redis中没有ip记录，则创建记录并添加进wangEditor图片上传分支中
		if (operations.get(ipAddr) == null) {
			operations.set(ipAddr, "");
			operations.set(branch,
					(operations.get(branch) == null || operations.get(branch).length() < 5) ? ipAddr : operations.get(branch)+","+ipAddr);
		}
		return ipAddr;
	}
	
	@Async
	@Override
	public void updateWangImgMaster(String ip, String validurl) {
		//分支
		String branch = "eims:upload:wangEditor:img:branch";
		//主块
		String master = "eims:upload:wangEditor:img:master";
		ValueOperations<String , String> operations = redisTemplate.opsForValue();
		String[] urls = validurl.split(",");
		List<String> urllist = new ArrayList<String>(Arrays.asList(operations.get(ip).split(",")));
		//循环删除有效的图片，无效的将转到master域中等待定时任务删除多余图片
		for(String url:urls) {
			if (urllist.contains(url)) {
				urllist.remove(url);
			}
		}
		List<String> branchlist = new ArrayList<String>(
				Arrays.asList(operations.get(branch).split(",")));
		branchlist.remove(ip);
		redisTemplate.delete(ip);
		operations.set(branch, String.join(",", branchlist));
		//如果url不为空，存在多余图片，则转入master中
		if (urllist.size() != 0) {
			operations.set(master, String.join(",", urllist)+","+(
					operations.get(master) ==null ? "":operations.get(master)));
		}
	}
	
	@Override
	public void uploadSourceFile(MultipartFile[] sourcefile) throws  IOException {
		List<Download> downloads = new ArrayList<>();
		for(MultipartFile file:sourcefile) {
			String filename = file.getOriginalFilename();
			String exiten = filename.substring(filename.indexOf("."));
			String turl = UUID.randomUUID().toString().replace("-", "")+exiten;
			file.transferTo(new File("F://eims/file/sourcefile/"+turl));
			downloads.add(new Download(null, null, filename, "upload/sourcefile/"+turl, null, null));
		}
		downloadService.saveBatch(downloads);
	}

	@Override
	public String uploadResume(Part enclosure) throws IOException {
		String filename = enclosure.getSubmittedFileName();
		String exiten = filename.substring(filename.indexOf("."));
		String newfile = UUID.randomUUID().toString().replace("-", "")+exiten;
		BufferedInputStream bis = new BufferedInputStream(enclosure.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream("F://eims/file/resume/"+newfile));
		byte[] b=new byte[1024];
		int length = 0 ;
		while( (length = bis.read( b ) )!= -1 ){
			bos.write(b, 0, length );
		}
		bos.close();
		bis.close();
		return "upload/resume/"+newfile;
	}
	
	@Override
	public String updateHead(MultipartFile head) throws IllegalStateException, IOException {
		String filename = head.getOriginalFilename();
		String exiten = filename.substring(filename.indexOf("."));
		String newfile = UUID.randomUUID().toString().replace("-", "")+exiten;
		head.transferTo(new File("F://eims/file/headpicture/"+newfile));
		return "/upload/headpicture/"+newfile;
	}
}
