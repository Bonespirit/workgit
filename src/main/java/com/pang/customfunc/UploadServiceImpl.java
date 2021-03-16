package com.pang.customfunc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService{

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
	public List<String> uploadWangEditorImg(Map<String, MultipartFile> wangEditor) throws IOException {
		List<String> fileurl = new ArrayList<String>();
		for(MultipartFile file:wangEditor.values()) {
			String filename = file.getOriginalFilename();
			String exiten = filename.substring(filename.indexOf("."));
			String turl = UUID.randomUUID().toString().replace("-", "")+exiten;
			file.transferTo(new File("F://eims/file/wangEditor/images/"+turl));
			fileurl.add("/upload/wangEditor/images/"+turl);
		}
		return fileurl;
	}
	
}
