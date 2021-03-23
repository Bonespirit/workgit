package com.pang.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.entity.Download;
import com.pang.mapper.DownloadMapper;
import com.pang.service.DownloadService;

@Service
public class DownloadServiceImpl extends ServiceImpl<DownloadMapper, Download> implements DownloadService{

}
