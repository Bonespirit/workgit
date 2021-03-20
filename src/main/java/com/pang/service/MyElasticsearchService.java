package com.pang.service;

import java.io.IOException;

import com.pang.entity.JobPage;

public interface MyElasticsearchService {
	
	/**
	 * 	通过单位编号获取职位列表
	 * @param cid
	 * @param page
	 * @return
	 * @throws IOException
	 */
	public JobPage MyMatchAllByCid1(Integer cid,Integer page) throws IOException;
	
	/**
	 * 通过单位编号获取职位列表,并展示于招聘文章页
	 * @param cid
	 * @return
	 * @throws IOException
	 */
	public JobPage MyMatchAllByCid2(Integer cid) throws IOException;
}
