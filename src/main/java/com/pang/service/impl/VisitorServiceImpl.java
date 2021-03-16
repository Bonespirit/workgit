package com.pang.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.Visitors;
import com.pang.mapper.VisitorMapper;
import com.pang.service.VisitorService;

@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitors> implements VisitorService{

}
