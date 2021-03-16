package com.pang.service.impl;

import java.io.Serializable;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.NewsHtml;
import com.pang.mapper.NewsHtmlMapper;
import com.pang.service.NewsHtmlService;

@Service
public class NewsHtmlServiceImpl extends ServiceImpl<NewsHtmlMapper, NewsHtml> implements NewsHtmlService{
	
	@Cacheable(value="newshtml",key="#id")
	@Override
	public NewsHtml getById(Serializable id) {
		// TODO Auto-generated method stub
		return super.getById(id);
	}
}
