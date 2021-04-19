package com.pang.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.Mposition;
import com.pang.mapper.PositionMapper;
import com.pang.service.MyPositionService;

@Service
public class MyPositionServiceImpl extends ServiceImpl<PositionMapper, Mposition> implements MyPositionService{
	
}
