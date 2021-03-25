package com.pang.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.entity.SearchKey;

public interface MyElasticsearchService {
	
	/**
	 * 	通过单位编号获取职位列表
	 * @param cid
	 * @param page
	 * @return
	 * @throws IOException
	 */
	public Page<Map<String, Object>> MyMatchAllByCid1(Integer cid,Integer page) throws IOException,ParseException;
	
	/**
	 * 通过单位编号获取职位列表,并展示于招聘文章页
	 * @param cid
	 * @return
	 * @throws IOException
	 */
	public Page<Map<String, Object>> MyMatchAllByCid2(Integer cid) throws IOException;
	
	/**
	 * 通过关键字查询信息并翻页，并高亮显示查询关键字
	 * @param keyword	关键字
	 * @param index		索引名称
	 * @param sortname	排序字段
	 * @param pg		当前页
	 * @return
	 * @throws IOException
	 * @throws java.text.ParseException 
	 */
	public Page<Map<String, Object>> getSearchResult(
			List<String> keyword,String index,String sortname,String pg,Integer number) throws IOException, ParseException;
	
	/**
	 * 高级检索并翻页
	 * @param searchKey	检索key实体
	 * @param pg		当前页
	 * @return
	 */
	public Page<Map<String, Object>> advancedSearch(SearchKey searchKey,Integer pg) throws IOException,ParseException;
}
