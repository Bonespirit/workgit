package com.pang.service.impl;

import java.io.Serializable;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.ZpHtml;
import com.pang.mapper.ZpHtmlMapper;
import com.pang.service.ZpHtmlService;

@Service
public class ZpHtmlServiceImpl extends ServiceImpl<ZpHtmlMapper, ZpHtml> implements ZpHtmlService{
	
	@Cacheable(value="ShortCache",keyGenerator="myKeyGenerator")
	@Override
	public ZpHtml getById(Serializable id) {
		return super.getById(id);
	}
}
