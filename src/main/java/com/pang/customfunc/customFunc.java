package com.pang.customfunc;

import java.io.IOException;
import java.util.Map;

public interface customFunc {
	//获取省份、城市名称
	public String getCity(String code) throws IOException;
	//获取职能类别名称
	public String getSkill(String code) throws IOException;
	//获取行业类别名称
	public String getIndustry(String code) throws IOException;
	//获取需求专业
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
	//分页获取开始和结束
	public Map<String, Integer> getBeEnd(int cur,int pages);
}
