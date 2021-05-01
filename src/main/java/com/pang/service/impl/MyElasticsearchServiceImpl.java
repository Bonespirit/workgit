package com.pang.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.customfunc.customFunc;
import com.pang.entity.SearchKey;
import com.pang.service.MyElasticsearchService;

@Service
public class MyElasticsearchServiceImpl implements MyElasticsearchService{
	
	@Autowired
	customFunc customFunc;
	
	@Autowired
	RestHighLevelClient client;
	
	@Override
	public Page<Map<String, Object>> MyMatchAllByCid1(Integer cid,Integer pg) throws IOException, ParseException {
		System.out.println("来到单位查看职位列表模块");
		Page<Map<String, Object>> page = new Page<>(pg,10);
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from((pg-1)*10);
		searchSourceBuilder.size(10);
		searchSourceBuilder.query(QueryBuilders.termQuery("cid", cid));
		searchSourceBuilder.fetchSource(
				new String[]{"id","pname","salary","wnature","edu","workplace","cname","cnature","scale","pdate"},
				new String[] {});
		searchRequest.source(searchSourceBuilder);
		//按时间降序
		searchSourceBuilder.sort("pdate", SortOrder.DESC);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		Long total = hits.getTotalHits();
		page.setTotal(total);
		page.setPages((total%10 == 0) ? total/10 : (total/10+1));
		SearchHit[] searchHits = hits.getHits();
		List<Map<String, Object>> olist = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			String md = dateFormat.format(dateFormat.parse((String)source.get("pdate")));
			source.put("pdate", md);
			source.put("salary", customFunc.getSalary((String)source.get("salary")));
			source.put("scale", customFunc.getScale((String)source.get("scale")));
			String[] citys = ((String)source.get("workplace")).split("&");
			source.put("workplace", 
					citys.length > 1 ? customFunc.getCity(citys[1]) : customFunc.getCity(citys[0]));
			olist.add(source);
		}
		page.setRecords(olist);
		return page;
	}
	
	@Cacheable(value="LongCache",keyGenerator="myKeyGenerator",unless="#result.total==0")
	@Override
	public Page<Map<String, Object>> MyMatchAllByCid2(Integer cid) throws IOException {
		System.out.println("来到获取职位列表展示简章页面模块");
		int total = getTotal(cid).intValue();
		Page<Map<String, Object>> page = new Page<>(1,total);
		page.setTotal(total);
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery("cid", cid));
		searchSourceBuilder.size(total);
		searchSourceBuilder.fetchSource(
				new String[]{"id","pname","salary","wnature","edu","workplace","speciality"},
				new String[] {});
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		List<Map<String, Object>> olist = new ArrayList<>();
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			source.put("salary", customFunc.getSalary((String)source.get("salary")));
			String[] citys = ((String)source.get("workplace")).split("&");
			source.put("workplace", 
					citys.length > 1 ? customFunc.getCity(citys[1]) : customFunc.getCity(citys[0]));
			String speciality = customFunc.getMajorsString(new ArrayList<>(Arrays.asList((String)source.get("speciality"))));
			source.put("speciality", speciality);
			olist.add(source);
		}
		page.setRecords(olist);
		return page;
	}
	
	//获取总数
	public Long getTotal(Integer cid) throws IOException {
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery("cid", cid));
		searchSourceBuilder.fetchSource(new String[]{},new String[] {});
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		return searchResponse.getHits().getTotalHits();
	}
	
	@Override
	public Page<Map<String, Object>> getSearchResult(
			List<String> keyword,String index,String sortname,String pg,Integer number) throws IOException, ParseException {
		if (pg == null) {
			pg="1";
		}
		Integer mpage = Integer.parseInt(pg);
		Page<Map<String, Object>> page = new Page<>(mpage,number);
		mpage = mpage-1;
		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchQuery("title", keyword.get(0)));
		if (keyword.size() > 1) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("extrakey", keyword.get(1)));
		}
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.from(mpage*number);
		searchSourceBuilder.size(number);
		searchRequest.source(searchSourceBuilder);
		//排序字段
		searchSourceBuilder.sort(sortname, SortOrder.DESC);
		//关键字高亮显示
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<strong style=\"color:#f40;\">");
		highlightBuilder.postTags("</strong>");
		highlightBuilder.field("title");
		searchSourceBuilder.highlighter(highlightBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		Long total = hits.getTotalHits();
		page.setTotal(total);
		page.setPages((total%15 == 0) ? total/15 : (total/15+1));
		SearchHit[] searchHits = hits.getHits();
		if (index.equals("teachin") || index.equals("jobfair")) {
			page.setRecords(getResultList(searchHits));
			page.setCountId("-99");
			return page;
		}
		List<Map<String, Object>> olist = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			String md = dateFormat.format(dateFormat.parse((String)source.get("pdate")));
			source.put("pdate", md);
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			if (highlightFields != null) {
				HighlightField title = highlightFields.get("title");
				if (title != null) {
					Text[] fragments = title.getFragments();
	                StringBuffer stringBuffer = new StringBuffer();
	                for (Text str : fragments) {
	                    stringBuffer.append(str.string());
	                }
	                //高亮展示title
	                source.put("title", stringBuffer.toString());
				}
			}
			olist.add(source);
		}
		page.setRecords(olist);
		return page;
	}
	
	public List<Map<String, Object>> getResultList(SearchHit[] searchHits) throws ParseException{
		List<Map<String, Object>> olist = new ArrayList<>();
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			if (highlightFields != null) {
				HighlightField title = highlightFields.get("title");
				if (title != null) {
					Text[] fragments = title.getFragments();
	                StringBuffer stringBuffer = new StringBuffer();
	                for (Text str : fragments) {
	                    stringBuffer.append(str.string());
	                }
	                //高亮展示title
	                source.put("htitle", stringBuffer.toString());
				}
			}
			olist.add(source);
		}
		return olist;
	}
	
	@Override
	public Page<Map<String, Object>> advancedSearch(SearchKey searchKey,Integer pg) throws IOException, ParseException {
		Page<Map<String, Object>> page = new Page<>(pg,15);
		pg = pg-1;
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from(pg*15);
		searchSourceBuilder.size(15);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		//关键字查询（职位名称或单位名称）
		if (!searchKey.getKeyword().isEmpty()) {
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchKey.getKeyword(), "pname","cname"));
		}
		if (!searchKey.getWnature().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("wnature", searchKey.getWnature()));
		}
		if (!searchKey.getCitycode().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("workplace", searchKey.getCitycode()));
		}
		if (!searchKey.getMajorcode().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.matchQuery("speciality", 
					searchKey.getMajorcode().replace("%", ",")));
		}
		if (!searchKey.getIndustry().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("industry", searchKey.getIndustry()));
		}
		if (!searchKey.getSkill().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("skill", searchKey.getSkill()));
		}
		if (!searchKey.getSalary().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("salary", searchKey.getSalary()));
		}
		if (!searchKey.getCnature().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("cnature", 
					customFunc.getComNature(searchKey.getCnature())));
		}
		if (!searchKey.getEdu().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("edu", searchKey.getEdu()));
		}
		if (!searchKey.getScale().isEmpty()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("scale", searchKey.getScale()));
		}
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.sort("pdate", SortOrder.DESC);
		searchSourceBuilder.fetchSource(
				new String[]{"id","cid","pname","salary","wnature","edu","workplace","cname","cnature","scale","pdate"}, 
				new String[] {});
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		Long total = hits.getTotalHits();
		page.setTotal(total);
		page.setPages((total%15 == 0) ? total/15 : (total/15+1));
		SearchHit[] searchHits = hits.getHits();
		List<Map<String, Object>> olist = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			String md = dateFormat.format(dateFormat.parse((String)source.get("pdate")));
			source.put("pdate", md);
			source.put("salary", customFunc.getSalary((String)source.get("salary")));
			source.put("scale", customFunc.getScale((String)source.get("scale")));
			String[] citys = ((String)source.get("workplace")).split("&");
			source.put("workplace", 
					citys.length > 1 ? customFunc.getCity(citys[1]) : customFunc.getCity(citys[0]));
			olist.add(source);
		}
		page.setRecords(olist);
		return page;
	}
}
