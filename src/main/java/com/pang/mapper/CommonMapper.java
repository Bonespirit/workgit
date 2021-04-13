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
}
