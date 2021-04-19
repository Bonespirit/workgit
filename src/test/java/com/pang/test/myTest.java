package com.pang.test;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.pang.customfunc.customFunc;
import com.pang.entity.ResumeProcess;

@SpringBootTest
public class myTest {
	
	@Autowired
	customFunc customFunc;
	
	@Test
	public void test() {
		customFunc.htmlToPdf("https://www.guet.edu.cn/jy/info/1074/9780.htm", "F:\\eims\\file\\wkhtmltopdf\\9700.pdf");
	}
	
//	@Autowired
//	JavaMailSenderImpl mailSenderImpl;
//	
//	@Test
//	public void test() {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setSubject("注册成功");
//	    message.setText("您已经成功注册了，快来发布招聘信息吧！");
//	    message.setTo("1607039428@qq.com");
//	    message.setFrom("pangruting@qq.com");
//	    try {
//	    	mailSenderImpl.send(message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
////	
//	@Autowired
//	RabbitTemplate rabbitTemplate;	
////	
//	@Test
//	public void test() {
//		ResumeProcess resumeProcess = new ResumeProcess();
//		rabbitTemplate.convertAndSend("exchange.direct", "pang", resumeProcess);
//	}
	
//	@Test
//	public void receive() {
//		Object object = rabbitTemplate.receiveAndConvert("pang");
//		System.out.println(object);
//	}
}
