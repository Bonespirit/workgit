package com.pang.test;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class myTest {
	@Autowired
	RestHighLevelClient client;
	
	@Test
	public void test() throws IOException {
		System.out.println("1111");
		SearchRequest searchRequest = new SearchRequest("position");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		System.out.println("111");
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		System.out.println("2222");
		try {
			SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
			System.out.println(hits.getTotalHits());
			SearchHit[] searchHits = hits.getHits();
		    for(SearchHit searchHit:searchHits) {
		    	System.out.println(searchHit.toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("3333");
		
	    // 匹配到的总记录数
	    
	    
	}
	
	
//	
//	@Autowired
//	RabbitTemplate rabbitTemplate;	
//	
//	@Test
//	public void test() {
//		Map<String, Object> map = new HashMap<>();
//		map.put("msg", "hello world!");
//		rabbitTemplate.convertAndSend("exchange.direct", "pang", map);
//	}
	
//	@Test
//	public void receive() {
//		Object object = rabbitTemplate.receiveAndConvert("pang");
//		System.out.println(object);
//	}
}
