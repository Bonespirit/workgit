package com.pang.customfunc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pang.entity.MyDate;
import com.pang.entity.Teachin;

@Component("customFunc")
public class customFuncCpImpl implements customFunc{
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	JavaMailSenderImpl mailSenderImpl;

	@Override
	public String getCity(String code) throws IOException {
		String provinceCode = code.substring(0, 2)+"0000";
		String cityCode	= code;
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader("D:\\迅雷9\\city-version-4.json"));
		StringBuffer content = new StringBuffer();
		String line=null;
		while((line = reader.readLine()) != null) {
			content.append(line);
		}
		Gson gson = new Gson();
		JsonObject object = gson.fromJson(content.toString(), JsonObject.class);
		JsonArray provinceList = object.get("provinceList").getAsJsonArray();
		//如果只是省份
		if (cityCode.equals(provinceCode)) {
			for(int i=0;i<provinceList.size();i++) {
				JsonObject o = provinceList.get(i).getAsJsonObject();
				if (o.get("no").getAsString().equals(provinceCode)) {
					return o.get("name").getAsString();
				}
			}
		}
		List<String> mList = new ArrayList<>();
		JsonArray citylist=null;
		for(int i=0;i<provinceList.size();i++) {
			JsonObject o = provinceList.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(provinceCode)) {
				citylist = o.get("cityList").getAsJsonArray();
				for(int j=0;j<citylist.size();j++) {
					JsonObject o2 = citylist.get(j).getAsJsonObject();
					if (o2.get("no").getAsString().equals(cityCode)) {
						mList.add(o.get("name").getAsString());
						mList.add(o2.get("name").getAsString());
						break;
					}
				}
			}
		}
		return String.join("-", mList);
	}

	@Override
	public String getSkill(String code) throws IOException {
		String str=null;
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader("D:\\迅雷9\\skill.json"));
		StringBuffer content = new StringBuffer();
		String line=null;
		while((line = reader.readLine()) != null) {
			content.append(line);
		}
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(content.toString(), JsonArray.class);
		for(int i=0 ; i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				str = o.get("name").getAsString();
				break;
			}
		}
		return str;
	}

	@Override
	public String getIndustry(String code) throws IOException {
		String str=null;
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader("D:\\迅雷9\\industry.json"));
		StringBuffer content = new StringBuffer();
		String line=null;
		while((line = reader.readLine()) != null) {
			content.append(line);
		}
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(content.toString(), JsonArray.class);
		for(int i=0 ; i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				str = o.get("name").getAsString();
				break;
			}
		}
		return str;
	}

	@Override
	public String getMajor(String code) throws IOException {
		String college=code.substring(0, 2)+"00";
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
		for(int i=0 ; i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(college)) {
				majorList = o.get("majorList").getAsJsonArray();
				for(int j=0;j<majorList.size();j++) {
					JsonObject o2 = majorList.get(j).getAsJsonObject();
					if (o2.get("no").getAsString().equals(code)) {
						return o2.get("name").getAsString();
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getEdu(String code) {
		String edu = "[{\"name\":\"不限\",\"no\":\"0\"},{\"name\":\"本科\",\"no\":\"1\"},{\"name\":\"硕士\",\"no\":\"2\"}"
				+ ",{\"name\":\"博士\",\"no\":\"3\"}]";
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(edu.toString(), JsonArray.class);
		for(int i=0;i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				return o.get("name").getAsString();
			}
		}
		return null;
	}

	@Override
	public String getWorkNature(String code) {
		String wNature = "[{\"name\":\"不限\",\"no\":\"0\"},{\"name\":\"全职\",\"no\":\"1\"},{\"name\":\"实习\",\"no\":\"2\"}]";
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(wNature.toString(), JsonArray.class);
		for(int i=0;i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				return o.get("name").getAsString();
			}
		}
		return null;
	}

	@Override
	public String getSalary(String code) {
		String salary = "[{\"name\":\"不限\",\"no\":\"10\"},{\"name\":\"4k以下\",\"no\":\"11\"},{\"name\":\"4k-6k\",\"no\":\"12\"}"
				+ ",{\"name\":\"6k-8k\",\"no\":\"13\"},{\"name\":\"8k-10k\",\"no\":\"14\"}"
				+ ",{\"name\":\"10k以上\",\"no\":\"15\"}]";
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(salary.toString(), JsonArray.class);
		for(int i=0;i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				return o.get("name").getAsString();
			}
		}
		return null;
	}

	@Override
	public String getComNature(String code) {
		String cNature = "[{\"name\":\"机关\",\"no\":\"10\"},{\"name\":\"科研设计单位\",\"no\":\"20\"},{\"name\":\"高等教育单位\",\"no\":\"21\"},{\"name\":\"中初等教育单位\",\"no\":\"22\"},{\"name\":\"医疗卫生单位\",\"no\":\"23\"},{\"name\":\"其他事业单位\",\"no\":\"29\"},{\"name\":\"国有企业\",\"no\":\"31\"},{\"name\":\"三资企业\",\"no\":\"32\"},{\"name\":\"其他企业\",\"no\":\"39\"},{\"name\":\"部队\",\"no\":\"40\"},{\"name\":\"农村建制村\",\"no\":\"55\"},{\"name\":\"城镇社区\",\"no\":\"56\"},{\"name\":\"其他\",\"no\":\"99\"}]";
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(cNature.toString(), JsonArray.class);
		for(int i=0;i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				return o.get("name").getAsString();
			}
		}
		return null;
	}

	@Override
	public String getScale(String code) {
		String scale = "[{\"name\":\"50人以下\",\"no\":\"1\"},{\"name\":\"50人-100人\",\"no\":\"2\"},{\"name\":\"100人-300人\",\"no\":\"3\"},{\"name\":\"300人-500人\",\"no\":\"4\"},{\"name\":\"500人-1000人\",\"no\":\"5\"},{\"name\":\"1000人以上\",\"no\":\"6\"}]";
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(scale.toString(), JsonArray.class);
		for(int i=0;i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(code)) {
				return o.get("name").getAsString();
			}
		}
		return null;
	}

	@Override
	public Map<String, Long> getBeEnd(Long cur,Long pages) {
		Map<String, Long> map = new HashMap<String, Long>();
		Long curp,maxp;
		if (pages < 5) {
			curp = 1l;
			maxp = pages;
		}else {
			if (cur < 3) {
				curp = 1l;
			}else if(cur != pages){
				curp = cur + 2 > pages ? cur - 3 : cur - 2;
			}else {
				curp = cur - 4;
			}
			maxp = curp+4;
		}
		map.put("beginP", curp);
		map.put("endP", maxp);
		return map;
	}
	
	@Async
	@Override
	public void sendEmailToCp(String email, String title, String contents) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject(title);
	    message.setText(contents);
	    message.setTo(email);
	    message.setFrom("pangruting@qq.com");
	    try {
	    	mailSenderImpl.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getVisitorIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
	}
	
	@Override
	public String getCnameByColumn(Integer column) {
		String scale = "[{\"cname\":\"通知公告\",\"no\":\"0\"},"
				+ "{\"cname\":\"就业公示\",\"no\":\"1\"},"
				+ "{\"cname\":\"新闻热点\",\"no\":\"2\"},"
				+ "{\"cname\":\"就业指导\",\"no\":\"3\"},"
				+ "{\"cname\":\"就业政策\",\"no\":\"4\"}]";
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(scale.toString(), JsonArray.class);
		for(int i=0;i<arry.size();i++) {
			JsonObject o = arry.get(i).getAsJsonObject();
			if (o.get("no").getAsString().equals(""+column)) {
				return o.get("cname").getAsString();
			}
		}
		return null;
	}

	@Override
	public List<MyDate> apartDate(List<Teachin> source) {
		List<MyDate> myDates = new ArrayList<>();
		SimpleDateFormat day = new SimpleDateFormat("dd");  
		SimpleDateFormat month = new SimpleDateFormat("MM");
		for(Teachin date:source) {
			myDates.add(new MyDate(month.format(date.getTdate()), day.format(date.getTdate())));
		}
		return myDates;
	}
	
	@Override
	public <T> void getModelByPage(Page<T> page,Model model) {
		Long cur=page.getCurrent();
		Long pages = page.getPages();
		Long curp,maxp;
		if (pages < 5) {
			curp = 1l;
			maxp = pages;
		}else {
			if (cur < 3) {
				curp = 1l;
			}else if(cur != pages){
				curp = cur + 2 > pages ? cur - 3 : cur - 2;
			}else {
				curp = cur - 4;
			}
			maxp = curp+4;
		}
		model.addAttribute("begin", curp);
		model.addAttribute("end", maxp);
		model.addAttribute("page", page);
	}
	
	@Override
	public String getMajorsString(List<String> mList) throws IOException {
		if (mList.size() == 0) {
			return null;
		}
		ValueOperations<String , String> operations = redisTemplate.opsForValue();
		//先从缓存中取数据，如果没有再从本地读取在缓存到redis中，方便读取
		String content = operations.get("eims:redis:position:major.json");
		if (content == null) {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("D:\\迅雷9\\major.json"));
			StringBuffer tcontent = new StringBuffer();
			String line=null;
			while((line = reader.readLine()) != null) {
				tcontent.append(line);
			}
			content = tcontent.toString();
			operations.set("eims:redis:position:major.json", content);
		}
		Gson gson = new Gson();
		JsonArray arry = gson.fromJson(content, JsonArray.class);
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
}
