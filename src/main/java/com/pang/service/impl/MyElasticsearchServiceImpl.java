package com.pang.service.impl;

import java.io.IOException;
import java.sql.Date;
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
import com.pang.entity.JobPage;
import com.pang.entity.Jobs;
import com.pang.entity.SearchKey;
import com.pang.service.MyElasticsearchService;

@Service
public class MyElasticsearchServiceImpl implements MyElasticsearchService{
	
	@Autowired
	customFunc customFunc;
	
	@Autowired
	RestHighLevelClient client;
	
	@Cacheable(value="ShortCache",keyGenerator="myKeyGenerator")
	@Override
	public JobPage MyMatchAllByCid1(Integer cid,Integer page) throws IOException {
		//职位表分页
		JobPage jobPage = new JobPage();
		//职位表列表
		List<Jobs> jobsList = new ArrayList<Jobs>();
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from((page-1)*10);
		searchSourceBuilder.size(10);
		searchSourceBuilder.query(QueryBuilders.termQuery("id", cid));
		searchSourceBuilder.fetchSource(
				new String[]{"pname","salary","wnature","edu","workplace","cname","cnature","scale","pdate"},
				new String[] {});
		searchRequest.source(searchSourceBuilder);
		//按时间降序
		searchSourceBuilder.sort("pdate", SortOrder.DESC);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		jobPage.setTotal(hits.getTotalHits());
		SearchHit[] searchHits = hits.getHits();
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			jobsList.add(new Jobs(searchHit.getId(),(String) source.get("pname"), (String) source.get("salary"), 
					(String) source.get("wnature"), (String) source.get("edu"), (String) source.get("workplace"), 
					(String) source.get("cname"), (String) source.get("cnature"), 
					(String) source.get("scale"),(Date) source.get("pdate"),null));
		}
		jobPage.setJobs(jobsList);
		return jobPage;
	}
	
	@Cacheable(value="LongCache",keyGenerator="mykeyGenerator")
	@Override
	public JobPage MyMatchAllByCid2(Integer cid) throws IOException {
		//职位表分页
		JobPage jobPage = new JobPage();
		//职位表列表
		List<Jobs> jobsList = new ArrayList<Jobs>();
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery("id", cid));
		searchSourceBuilder.fetchSource(
				new String[]{"pname","salary","wnature","edu","workplace","speciality"},
				new String[] {});
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		jobPage.setTotal(hits.getTotalHits());
		SearchHit[] searchHits = hits.getHits();
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			//将专业代码转为中文解释
			String speciality = customFunc.getMajorsString(new ArrayList<>(Arrays.asList((String)source.get("speciality"))));
			jobsList.add(new Jobs(searchHit.getId(),(String) source.get("pname"), (String) source.get("salary"), 
					(String) source.get("wnature"), (String) source.get("edu"), (String) source.get("workplace"), 
					null, null, null,null,speciality));
		}
		jobPage.setJobs(jobsList);
		return jobPage;
	}
	
	@Override
	public Page<Map<String, Object>> getSearchResult(
			List<String> keyword,String index,String sortname,Integer pg) throws IOException {
		Page<Map<String, Object>> page = new Page<>(pg,15);
		pg = pg-1;
		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchQuery("title", keyword.get(0)));
		if (keyword.size() > 1) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("extrakey", keyword.get(1)));
		}
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.from(pg*15);
		searchSourceBuilder.size(15);
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
		List<Map<String, Object>> olist = new ArrayList<>();
		for(SearchHit searchHit:searchHits) {
			Map<String, Object> source = searchHit.getSourceAsMap();
			String mtitle = (String) source.get("title");
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			if (highlightFields != null) {
				HighlightField title = highlightFields.get("title");
				if (title != null) {
					Text[] fragments = title.getFragments();
	                StringBuffer stringBuffer = new StringBuffer();
	                for (Text str : fragments) {
	                    stringBuffer.append(str.string());
	                }
	                //替换原来的title，展示高亮
	                mtitle = stringBuffer.toString();
				}
			}
			source.put("title", mtitle);
			olist.add(source);
		}
		page.setRecords(olist);
		return page;
	}
	
	@Override
	public Page<Map<String, Object>> advancedSearch(SearchKey searchKey,Integer pg) throws IOException {
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
			boolQueryBuilder.filter(QueryBuilders.matchQuery("speciality", searchKey.getMajorcode()));
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
			boolQueryBuilder.filter(QueryBuilders.termQuery("cnature", searchKey.getCnature()));
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
		for(SearchHit searchHit:searchHits) {
			olist.add(searchHit.getSourceAsMap());
		}
		page.setRecords(olist);
		return page;
	}
}
