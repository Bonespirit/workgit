package com.pang.customfunc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

public interface UploadService {
	
	/**
	 * 上传logo
	 * @param logo
	 * @return
	 * @throws IOException
	 */
	public String uploadLogo(MultipartFile logo) throws IOException;
	/**
	 * 上传license
	 * @param license
	 * @return
	 * @throws IOException
	 */
	public String uploadLicense(MultipartFile license) throws IOException;
	/**
	 * 上传附件
	 * @param enclosure
	 * @return
	 * @throws IOException
	 */
	public String uploadEnclosure(MultipartFile[] enclosure) throws IOException;
	/**
	 * 上传wangEditor图片
	 * @param map
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public JsonObject uploadWangEditorImg(Map<String, MultipartFile> map,
			HttpServletRequest request,String ip) throws IOException;
	
	/**
	 * 获取请求ip并在记录ip于redis中
	 * @param request
	 * @return
	 */
	public String getIpAndPutInRedis(HttpServletRequest request);
	
	/**
	 * 更新redis中wnagEditor图片上传的master和branch的数据
	 * 删除ip key(branch同样)，将残留url更新到master中等待定时任务删除图片
	 * @param ip
	 * @param validurl
	 */
	public void updateWangImgMaster(String ip,String validurl);
}
