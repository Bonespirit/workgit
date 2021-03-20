package com.pang.service.impl;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pang.entity.JobPage;
import com.pang.entity.Jobs;
import com.pang.service.MyElasticsearchService;

@Service
public class MyElasticsearchServiceImpl implements MyElasticsearchService{
	
	@Autowired
	RestHighLevelClient client;
	
	@Override
	public JobPage MyMatchAllByCid1(Integer cid,Integer page) throws IOException {
		//职位表分页
		JobPage jobPage = new JobPage();
		//职位表列表
		List<Jobs> jobsList = new ArrayList<Jobs>();
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from((page-1)*10);
		searchSourceBuilder.size(((page-1)*10)+10);
		System.out.println(cid);
		searchSourceBuilder.query(QueryBuilders.termQuery("id", cid));
		searchSourceBuilder.fetchSource(
				new String[]{"pname","salary","wnature","edu","workplace","cname","cnature","scale","pdate"},
				new String[] {});
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		jobPage.setTotal(hits.getTotalHits());
		SearchHit[] searchHits = hits.getHits();
		for(SearchHit searchHit:searchHits) {
			Jobs job = new Jobs();
			job.setId(searchHit.getId());
			Map<String, Object> source = searchHit.getSourceAsMap();
			jobsList.add(new Jobs(searchHit.getId(),(String) source.get("pname"), (String) source.get("salary"), 
					(String) source.get("wnature"), (String) source.get("edu"), (String) source.get("workplace"), 
					(String) source.get("cname"), (String) source.get("cnature"), 
					(String) source.get("scale"),(Date) source.get("pdate"),null));
		}
		jobPage.setJobs(jobsList);
		return jobPage;
	}

	
	@Override
	public JobPage MyMatchAllByCid2(Integer cid) throws IOException {
		//职位表分页
		JobPage jobPage = new JobPage();
		//职位表列表
		List<Jobs> jobsList = new ArrayList<Jobs>();
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		System.out.println(cid);
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
			Jobs job = new Jobs();
			job.setId(searchHit.getId());
			Map<String, Object> source = searchHit.getSourceAsMap();
			jobsList.add(new Jobs(searchHit.getId(),(String) source.get("pname"), (String) source.get("salary"), 
					(String) source.get("wnature"), (String) source.get("edu"), (String) source.get("workplace"), 
					null, null, null,null,(String)source.get("speciality")));
		}
		jobPage.setJobs(jobsList);
		return jobPage;
	}
	
	
}
