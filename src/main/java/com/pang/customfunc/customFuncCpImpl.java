package com.pang.customfunc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pang.entity.MyDate;
import com.pang.entity.Teachin;
import com.pang.mapper.CommonMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("customFunc")
public class customFuncCpImpl implements customFunc{
	
	@Autowired
	CommonMapper commonMapper;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	JavaMailSenderImpl mailSenderImpl;

	@Override
	public String getCity(String code) throws IOException {
		String provinceCode = code.substring(0, 2)+"0000";
		String cityCode	= code;
		String content = getRedisContent("eims:redis:position:city-version-4.json", "city-version-4.json");
		Gson gson = new Gson();
		JsonObject object = gson.fromJson(content, JsonObject.class);
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
				+ ",{\"name\":\"10k-15k\",\"no\":\"15\"},{\"name\":\"15k以上\",\"no\":\"16\"}]";
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
	
	@Async
	@Override
	public void sendEmailToUser(String email, String title, String contents) {
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
		String content = getRedisContent("eims:redis:position:major.json","major.json");
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
	
	@Cacheable(value="LongCache",key="'eims:student:collect:sid:'+#sid",unless="#result.size==0")
	@Transactional
	public Map<String, List<String>> getPosAndColId(Integer sid){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("deliver", commonMapper.getDeliverPos(sid));
		map.put("collect", commonMapper.getColPosIdfromSR(sid));
		return map;
	}
	
	/**
	 * 获取缓存中的json文件数据，如果没有则从本地加载并缓存
	 * @param ckey		缓存的key
	 * @param filename	本地文件名
	 * @return			json文件数据
	 * @throws IOException
	 */
	public String getRedisContent(String ckey,String filename) throws IOException { 
		ValueOperations<String , String> operations = redisTemplate.opsForValue();
		//先从缓存中取数据，如果没有再从本地读取在缓存到redis中，方便读取
		String content = operations.get(ckey);
		if (content == null) {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("D:\\迅雷9\\"+filename));
			StringBuffer tcontent = new StringBuffer();
			String line=null;
			while((line = reader.readLine()) != null) {
				tcontent.append(line);
			}
			content = tcontent.toString();
			operations.set(ckey,tcontent.toString(),Duration.ofHours(1));//缓存两个个钟
		}
		return content.trim();
	}
	
	/**
	 * 当java调用wkhtmltopdf时，用于获取wkhtmltopdf返回的内容
	 */
	class HtmlToPdfInter extends Thread{
		private InputStream is;
	    public HtmlToPdfInter(InputStream is) {
	        this.is = is;
	    }
	    public void run() {
	        try {
	            InputStreamReader isr = new InputStreamReader(is, "utf-8");
	            BufferedReader br = new BufferedReader(isr);
	            br.readLine();
	        } catch (IOException e) {
	            log.error(e.getMessage());
	            e.printStackTrace();
	        }
	    }
	}
	
	@Override
	public boolean htmlToPdf(String srcPath, String destPath) {
		StringBuilder cmd = new StringBuilder();
        cmd.append("wkhtmltopdf");
        cmd.append(" ");
        cmd.append("-s A4");// a4纸
        cmd.append(" ");
        cmd.append("-B 0mm");// 底部边距
        cmd.append(" ");
        cmd.append("-T 0mm");// 顶部边距
        cmd.append(" ");
        cmd.append("-L 0mm");// 左部边距
        cmd.append(" ");
        cmd.append("-R 0mm");// 右部边距
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInter error = new HtmlToPdfInter(
                    proc.getErrorStream());
            HtmlToPdfInter output = new HtmlToPdfInter(
                    proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
            log.info("HTML2PDF成功，参数---html路径：{},pdf保存路径 ：{}", new Object[] {srcPath, destPath });
        } catch (Exception e) {
            log.error("HTML2PDF失败，srcPath地址：{},错误信息：{}", new Object[]{srcPath, e.getMessage()});
            result = false;
        }
        return result;
	}
	
	@Override
	public void writerResumeCache(HttpServletResponse response,
			List<String> resumeurls, List<String> namelist, List<String> majorlist,
			List<String> plist) throws IOException {
		//获取年和月
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		//简历压缩路径
		String zipFileName = year+"-"+month+"-"+"校招-桂林电子科技大学.zip";
		String zipPath = "F://eims/file/cachefile/"+zipFileName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
		for(int i=0;i<resumeurls.size();i++) {
			String murl = resumeurls.get(i).substring(resumeurls.get(i).indexOf("/"));
			String exiten = murl.substring(murl.indexOf("."));
			out.putNextEntry(new ZipEntry(
					"校招-"+plist.get(i)+"-"+"桂林电子科技大学-"+majorlist.get(i)+"-"+namelist.get(i)+exiten));
			BufferedInputStream bis=new BufferedInputStream(new FileInputStream("F://eims/file"+murl));
			byte[] bytes=new byte[1024*5];
            int len;
            while ((len=bis.read(bytes))!=-1){
                out.write(bytes,0,len);
            }
            bis.close();
		}
		out.close();
		//再将压缩文件读取出来写入response的输出流中，并设置请求头
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipPath));
		//设置Headers
        response.setHeader("Content-Type","application/octet-stream");
        //设置下载的文件的名称-该方式已解决中文乱码问题
        response.setHeader("Content-Disposition","attachment;filename=" +  new String( zipFileName.getBytes("gb2312"), "ISO8859-1" ));
		ServletOutputStream outputStream = response.getOutputStream();
		FileCopyUtils.copy(bis, outputStream);
		bis.close();
		//删除缓存的压缩文件
		new File(zipPath).delete();
	}
}
