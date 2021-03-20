package com.pang.service.impl;

import java.io.Serializable;

import org.springframework.cache.annotation.Cacheable;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.Mposition;
import com.pang.mapper.PositionMapper;
import com.pang.service.MyPositionService;

public class MyPositionServiceImpl extends ServiceImpl<PositionMapper, Mposition> implements MyPositionService{
	
	@Cacheable(value="ShortCache",keyGenerator="myKeyGenerator")
	@Override
	public Mposition getById(Serializable id) {
		return super.getById(id);
	}
}
