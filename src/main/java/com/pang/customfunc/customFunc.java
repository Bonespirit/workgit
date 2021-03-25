package com.pang.customfunc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.MyDate;
import com.pang.entity.Teachin;

public interface customFunc {
	//获取省份、城市名称
	public String getCity(String code) throws IOException;
	//获取职能类别名称
	public String getSkill(String code) throws IOException;
	//获取行业类别名称
	public String getIndustry(String code) throws IOException;
	
	/**
	 * 待删除方法
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getMajor(String code) throws IOException;
	//获取学历要求
	public String getEdu(String code);
	//获取工作性质
	public String getWorkNature(String code);
	//获取薪资
	public String getSalary(String code);
	//获取单位性质
	public String getComNature(String code);
	//获取单位规模
	public String getScale(String code);
	
	/**
	 *	待删除方法
	 * @param cur
	 * @param pages
	 * @return
	 */
	public Map<String, Long> getBeEnd(Long cur,Long pages);
	/**
	 * 	分页获取开始和结束
	 * @param page
	 * @param model
	 */
	public <T> void getModelByPage(Page<T> page,Model model);
	/**
     * 	发送通知消息邮件--->单位
     *
     * @param email 目的邮箱地址
     * @param title 邮件标题
     * @param contents 邮件正文
     */
	public void sendEmailToCp(String email,String title,String contents);
	
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
}
