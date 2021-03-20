package com.pang.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pang.service.MySchedulService;

@Service
public class MySchedulServiceImpl implements MySchedulService{
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Scheduled(cron="0 0 5 * * ?")
	@Override
	public void delWangImg() {
		System.out.println("111");
		ValueOperations<String , String> operations = redisTemplate.opsForValue();
		//图片的路径
		String path = "F://eims/file/wangEditor/images/";
		//分支
		String branch = "eims:upload:wangEditor:img:branch";
		//主块
		String master = "eims:upload:wangEditor:img:master";
		List<String> branchIpList = null;
		List<String> masterList = null;
		//获取branch中的ip删除ip中存放的图片并删除ip key
		if (operations.get(branch) != null) {
			branchIpList = new ArrayList<String>(
					Arrays.asList(operations.get(branch).split(",")));
			int blength = branchIpList.size();
			for(int i=0;i<blength;i++) {
				String ip = branchIpList.get(i);
				List<String> ipUrlList = new ArrayList<String>(
						Arrays.asList(operations.get(ip).split(",")));
				for(String url:ipUrlList) {
					String imgurl = path+url;
					File file = new File(imgurl);
					if (file.exists()) {
						file.delete();
						System.out.println("branch删除"+url+"成功");
					}
				}
				redisTemplate.delete(ip);
			}
			branchIpList.removeAll(branchIpList);
			operations.set(branch, String.join(",", branchIpList));
		}
		//获取主块中的url删除图片
		if (operations.get(master) != null) {
			masterList = new ArrayList<String>(
					Arrays.asList(operations.get(master).split(",")));
			int mlength = masterList.size();
			for(int i=0;i<mlength;i++) {
				String murl = masterList.get(i);
				String imgurl = path+murl;
				File file = new File(imgurl);
				if (file.exists()) {
					file.delete();
					System.out.println("master删除"+murl+"成功");
				}
			}
			masterList.removeAll(masterList);
			System.out.println(masterList.toString());
			operations.set(master, String.join(",", masterList));
		}
	}

}
