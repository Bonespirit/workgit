package com.pang.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.UploadService;
import com.pang.customfunc.customFunc;
import com.pang.entity.ColResume;
import com.pang.entity.Resume;
import com.pang.entity.ResumeProcess;
import com.pang.entity.User;
import com.pang.service.ResumeProcessService;
import com.pang.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	ResumeProcessService resumeProcessService;
	
	@Autowired
	customFunc customFunc;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	UploadService uploadService;
	
	//操作手册
	@GetMapping("/czsc")
	public String goToCzsc() {
		System.out.println("czsc");
		return "student/czsc";
	}
	
	//毕业填报
	@GetMapping("/bytb")
	public String goToBytb() {
		return "student/bytb";
	}
	//填报信息入库
	@PostMapping("/bytb")
	public void putBytb() {
		
	}
	
	//解约办理
	@GetMapping("jybl")
	public String goToJybl() {
		return "student/jybl";
	}
	
	//打印申请
	@GetMapping("/dysq")
	public String goToDysq() {
		return "student/dysq";
	}
	
	//常用下载
	@GetMapping("download")
	public String goToDownload() {
		return "student/download";
	}
	
	//我的简历
	@GetMapping("/wdjl")
	public String goToWdjl(Model model) throws InterruptedException, ExecutionException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("student", studentService.getStudenInfo(user.getId()));
		return "student/wdjl";
	}
	//修改头像
	@PutMapping("/changehead")
	@ResponseBody
	public String updateHead(@RequestPart("head") MultipartFile head) throws IllegalStateException, IOException {
		return uploadService.updateHead(head);
	}
	//修改简历
	@GetMapping("/editresume")
	public String goToEditResume(Model model) throws InterruptedException, ExecutionException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("student", studentService.getStudenInfo(user.getId()));
		return "student/editresume";
	}
	//更新简历信息
	@PutMapping("/editresume")
	@ResponseBody
	public String updateResume(Resume resume,HttpServletRequest request) throws IOException, ServletException {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer sid = user.getId();
		Integer os = 1;		//sql操作类型，默认为更新操作
		resume.setId(sid);
		Part enclosure = request.getPart("enclosure");
		String filename=null;
		String[] resumeurls = {};
		if (resume.getResumeurl() !=null) {
			resumeurls = resume.getResumeurl().split("/");
		}
		if (enclosure !=null) {//学生上传了本地的简历，覆盖原来的简历
			resume.setResumeurl(uploadService.uploadResume(enclosure));
			//存在则删除原来的在线简历
			if (resumeurls.length > 0) {
				File file = new File("F://eims/file/wkhtmltopdf/"+resumeurls[2]);
				if (file.exists()) {
					file.delete();
				}
			}
			System.out.println("本次修改上传了简历");
		}else {//学生没有上传本地的简历
			//（第一次使用）数据库中没有学生简历信息，使用wkhtmltopdf生成简历并保存服务器，然后将url存入数据库
			if (resumeurls.length == 0) {
				System.out.println("第一次使用系统");
				String fileurl = UUID.randomUUID().toString().replace("-", "")+".pdf";
				resume.setResumeurl("upload/wkhtmltopdf/"+fileurl);
				filename=fileurl;
				os = 0;//插入数据操作
			}else if ("resume".equals(resumeurls[1])) {
				//学生已经上传过本地简历，不更新url，本地简历优先级高于在线简历
				System.out.println("url已存在且为本地简历");
				resume.setResumeurl(null);
			}else {
				//学生一直没有上传过本地简历，在线简历更新需要重新生成简历覆盖原来的
				System.out.println("url存在，但是是在线简历");
				resume.setResumeurl(null);
				filename = resumeurls[2];
			}
		}
		studentService.updateResume(resume, os);
		if (filename != null) {
			customFunc.htmlToPdf("http://localhost:8080/views/student/id/"+sid, 
					"F://eims/file/wkhtmltopdf/"+filename);
		}
		return "student/wdjl";
	}
	
	//投递简历
	@GetMapping("/deliver")
	public String putDeliver(ResumeProcess resumeProcess) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		resumeProcess.setStuid(user.getId());
		//判断简历是否可投递
		if (!studentService.canDeliver(resumeProcess)) {//不可投递
			return "/error/hasdeliver";
		}
		studentService.deliverResume(resumeProcess);
		return "redirect:/views/jobs/id/"+resumeProcess.getPid();
	}
	
	//我的消息
	@GetMapping("/wdxx")
	public String goToWdxx() {
		return "student/wdxx";
	}
	
	//我的投递
	//根据简历状态查询投递数据
	@GetMapping("/psearch/tag/{tag}")
	public String getResumeByTag(@PathVariable("tag") Integer tag,
			@RequestParam("page") Integer pg,Model model) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("page", 
				resumeProcessService.getDeliverResume(0,user.getId(), -1, tag, pg));
		model.addAttribute("curl", "/student/psearch/tag/"+tag);
		return "student/wdtd";
	}
	
	//获取收藏职位列表并翻页展示
	@GetMapping("/wdsc")
	public String goToWdsc(@RequestParam("page") Integer pg,Model model) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Page<ColResume> page = studentService.getColResumePage(user.getId(), pg);
		if (page.getPages() > 1) {
			customFunc.getModelByPage(page,model);
		}else {
			model.addAttribute("page", page);
		}
		model.addAttribute("curl", "student/wdsc");
		return "student/wdsc";
	}
	//收藏职位信息入库
	@PostMapping("/colPos")
	public String putColPos(ColResume colResume) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		colResume.setSid(user.getId());
		studentService.putColResume(colResume);
		return "redirect:/views/jobs/id/"+colResume.getPid();
	}
	//删除收藏职位
	@DeleteMapping("/delcollect/id/{id}")
	@ResponseBody
	public String delCol(@PathVariable("id") Integer id) {
		User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		studentService.detCollect(id,user.getId());
		return "success";
	}
	
	//学生基本信息
	@GetMapping("/jbxxs")
	public String goToJbxxs() {
		return "student/jbxxs";
	}
	
	//修改密码
	@GetMapping("/xgmms")
	public String goToXgmms() {
		return "student/xgmms";
	}
}
