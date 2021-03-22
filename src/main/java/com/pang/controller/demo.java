package com.pang.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class demo {
	
	public static String getMajorsString(List<String> mList) throws IOException {
		if (mList.size() == 0) {
			return null;
		}
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader("D:\\迅雷9\\major.json"));
		StringBuffer content = new StringBuffer();
		String line=null;
		while((line = reader.readLine()) != null) {
			content.append(line);
		}
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(content.toString(), JsonArray.class);
		JsonArray majorList=null;
		Map<String, String> map = new HashMap<>();
		for(String str:mList) {
			String college = str.substring(0, 2)+"00";
			map.put(college, map.get(college)+","+str);
		}
		String college = null;
		String majors = null;
		List<String> majorsl = new ArrayList<>();
		for(int i=0 ; i<arry.size();i++) {
			//已经找完
			if (mList.size() == 0) {
				break;
			}
			JsonObject o = arry.get(i).getAsJsonObject();
			college = o.get("no").getAsString();
			if (map.containsKey(college)) {
				majorList = o.get("majorList").getAsJsonArray();
				//该学院包含的专业
				majors = map.get(college);
				for(int j=0;j<majorList.size();j++) {
					JsonObject o2 = majorList.get(j).getAsJsonObject();
					String cur = o2.get("no").getAsString();
					//找到专业
					if (Pattern.compile(cur).matcher(majors).find()) {
						majorsl.add(o2.get("name").getAsString());
						mList.remove(cur);
					}
				}
			}
		}
		return String.join(",", majorsl);
	}
	
	public static void main(String[] args) throws Exception {
//		String scale = "[{\"cname\":\"通知公告\",\"curl\":\"news/column/0/page/1\",\"no\":\"0\"},"
//				+ "{\"cname\":\"就业公示\",\"curl\":\"news/column/1/page/1\",\"no\":\"1\"},"
//				+ "{\"cname\":\"新闻热点\",\"curl\":\"news/column/2/page/1\",\"no\":\"2\"},"
//				+ "{\"cname\":\"就业指导\",\"curl\":\"news/column/3/page/1\",\"no\":\"3\"},"
//				+ "{\"cname\":\"就业政策\",\"curl\":\"news/column/4/page/1\",\"no\":\"4\"}]";
//		Gson gson = new Gson();
//		JsonArray arry = gson.fromJson(scale.toString(), JsonArray.class);
//		Pattern pattern;
//		System.out.println((Pattern.compile("1104").matcher("1101,1102,1103").find()));
		List<String> mList = new ArrayList<String>();
		mList.add("1101");
		mList.add("1203");
		mList.add("1103");
		mList.add("1201");
		mList.add("1301");
		long starttime = new Date().getTime();
		System.out.println(getMajorsString(mList));
		System.out.println(new Date().getTime()-starttime);
	}
	
	
}
