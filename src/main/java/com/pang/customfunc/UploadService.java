package com.pang.customfunc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	
	//上传logo
	public String uploadLogo(MultipartFile logo) throws IOException;
	//上传license
	public String uploadLicense(MultipartFile license) throws IOException;
	//上传附件
	public String uploadEnclosure(MultipartFile[] enclosure) throws IOException;
	//上传wangEditor图片
	public List<String> uploadWangEditorImg(Map<String, MultipartFile> map) throws IOException;
}
