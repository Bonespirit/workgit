package com.pang.test;


import java.io.IOException;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollAction;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import com.pang.customfunc.customFunc;

@SpringBootTest
public class myTest {
//	@Autowired
//	customFunc customFunc;
//	
//	@Test
//	public void test() {
//		customFunc.getComNature("21");
//	}
//	
//	
//	@Autowired
//	RestHighLevelClient client;
//	
//	@Test
//	public void test() throws IOException {
//		
////		NativeSearchQueryBuilder
////		client.indices();
//		SearchRequest searchRequest = new SearchRequest("mytest");
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		SearchScrollRequest scrollRequest = new SearchScrollRequest();
//		scrollRequest.scroll(TimeValue.timeValueMinutes(5));
//		searchRequest.scroll(TimeValue.timeValueMinutes(5));
//		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
////		clearScrollRequest.
////		SearchScrollRequestBuilder scrollRequestBuilder = new SearchScrollRequestBuilder(client,);
////		client.scroll(searchRequest, RequestOptions.class);
////		searchSourceBuilder
//		//dis_max查询
////		DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery()
////				.add(QueryBuilders.matchQuery("password", "1101"))
////				.add(QueryBuilders.matchQuery("username", "1101"));
////		disMaxQueryBuilder.add(QueryBuilders.matchQuery("username", "1101"))
////						.add(QueryBuilders.matchQuery("password", "1101"));
//		
////		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
////				.must(QueryBuilders.matchQuery("username", "1101"))
////				.filter(QueryBuilders.rangeQuery("date")
////						.gte("2021-02-06").lte("2021-03-06").format("yyyy-MM-dd"));
//		
////		searchSourceBuilder.sort("username",SortOrder.ASC)
////							.sort("password", SortOrder.DESC);
//		searchSourceBuilder.query(QueryBuilders.multiMatchQuery("1101", "username","password")
//				.field("username", 10).minimumShouldMatch("70%"));
//		searchRequest.source(searchSourceBuilder);
//		SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
////		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//	}
	
	
//	@Autowired
//	DocxToHtmlService docxToHtmlService;
//	
//	@Test
//	public void test() throws Exception {
//		String filename = docxToHtmlService.docxToHtml();
//		System.out.println(filename);
//	}
//	
//	@Test
//	public void test1() throws FileNotFoundException {
//		System.out.println(ResourceUtils.getURL("classpath:").getPath());
//	}
//	@Autowired
//	JavaMailSenderImpl mailSenderImpl;
//	
//	@DisplayName("邮件测试")
//	@Test
//	public void test() throws MessagingException {
//		MimeMessage mimeMessage = mailSenderImpl.createMimeMessage();
//		MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
//		
//		message.setSubject("开会通知");
//		message.setText("各部门注意:明天下午14:00进行2020年度总结大会！");
//		message.setTo("1607039428@qq.com");
//		message.setFrom("pangruting@qq.com");
//		
//		message.addAttachment("1.bmp", new File("‪C:\\Users\\panggtx\\Pictures\\1.bmp"));
//		mailSenderImpl.send(mimeMessage);
//	}
	
	
//	@Autowired
//	AmqpAdmin AmqpAdmin;
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
