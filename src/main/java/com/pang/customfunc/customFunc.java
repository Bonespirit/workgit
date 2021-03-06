package com.pang.customfunc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.MyDate;
import com.pang.entity.Teachin;

public interface customFunc {
	/**
	 * 获取省份、城市中文解释
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getCity(String code) throws IOException;
	
	/**
	 * 获取职能类别中文解释
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getSkill(String code) throws IOException;
	
	/**
	 * 获取行业类别中文解释
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getIndustry(String code) throws IOException;
	
	/**
	 * 获取学历中文解释
	 * @param code
	 * @return
	 */
	public String getEdu(String code);
	
	/**
	 * 获取工作性质中文解释
	 * @param code
	 * @return
	 */
	public String getWorkNature(String code);
	/**
	 * 获取薪资中文解释
	 * @param code
	 * @return
	 */
	public String getSalary(String code);
	/**
	 * 获取单位性质中文解释
	 * @param code
	 * @return
	 */
	public String getComNature(String code);
	/**
	 * 获取单位规模中文解释
	 * @param code
	 * @return
	 */
	public String getScale(String code);
	
	/**
	 * 	分页获取开始和结束
	 * @param page
	 * @param model
	 */
	public <T> void getModelByPage(Page<T> page,Model model);
	
	/**
     * 	发送通知消息邮件
     *
     * @param email 目的邮箱地址
     * @param title 邮件标题
     * @param contents 邮件正文
     */
	public void sendEmailToUser(String email,String title,String contents);
	
	/**
	 * 获取用户真实ip
	 * @param request
	 * @return
	 */
	public String getVisitorIp(HttpServletRequest request);
	
	/**
	 * 根据栏目标号获取栏目名称
	 * @param column
	 * @return
	 */
	public String getCnameByColumn(Integer column);
	
	/**
	 * 将数据集里的日期格式化为分开的年月日存入MyDate实体中
	 * @param source 原数据
	 * @return
	 */
	public List<MyDate> apartDate(List<Teachin> source);
	
	/**
	 * 获取专业的中文解释
	 * @param mList	需求专业集合
	 * @return
	 */
	public String getMajorsString(List<String> mList) throws IOException;
	
	/**
	 * 获取收藏职位和投递职位并缓存
	 * @param sid	学生id
	 * @return
	 */
	public Map<String, List<String>> getPosAndColId(Integer sid);
	
	/**
	 * html转pdf
	 * @param srcPath	html路径，可以是硬盘上的路径，也可以是网络路径
	 * @param destPath	pdf保存路径
	 * @return			转换成功返回true
	 */
	public boolean htmlToPdf(String srcPath, String destPath);
	
	/**
	 * 将批量简历压缩然后缓存到cachefile文件中等待下载
	 * 压缩文件名称为	年-月+校招+桂林电子科技大学.zip 如:"2021-4-校招-桂林电子科技大学.zip"
	 * @param resumeurls	简历路径集合
	 * @param namelist		学生姓名集
	 * @param majorlist		学生专业集
	 * @param plist			学生应聘职位集
	 * @return	zipPath 压缩文件路径
	 */
	public void writerResumeCache(HttpServletResponse response,List<String> resumeurls,
			List<String> namelist,List<String> majorlist,List<String> plist) throws IOException;
}
