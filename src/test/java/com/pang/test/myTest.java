package com.pang.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.pang.customfunc.customFunc;

@SpringBootTest
public class myTest {
	
	@Autowired
	JavaMailSenderImpl mailSenderImpl;
	
	@Test
	public void test() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("注册成功");
	    message.setText("您已经成功注册了，快来发布招聘信息吧！");
	    message.setTo("1607039428@qq.com");
	    message.setFrom("pangruting@qq.com");
	    try {
	    	mailSenderImpl.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
