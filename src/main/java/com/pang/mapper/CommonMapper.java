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
}
