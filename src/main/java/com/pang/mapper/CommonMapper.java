package com.pang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface CommonMapper {
	
	/**
	 * 获取收藏职位列表
	 * @param sid
	 * @return
	 */
	@Select("select pid from col_resume where sid=#{sid}")
	public List<String> getColPosIdfromSR(Integer sid);
	
	/**
	 * 获取已投递职位
	 * @param sid
	 * @return
	 */
	@Select("select pid from stu_deliver where sid=#{sid}")
	public List<String> getDeliverPos(Integer sid);
	
	/**
	 * 根据id批量查询并返回resumeurl字段数据
	 * @param idList
	 * @return
	 */
	@Select("select resumeurl from resume_ge_info where id in #{idList}")
	public List<String> getResumeUrlList(List<Integer> idList);
}
