package com.couragehe.spider.website;

import java.util.ArrayList;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.couragehe.dao.JobInfoDao;
import com.couragehe.entity.JobInfo;
import com.couragehe.util.UUIDUtils;

import us.codecraft.webmagic.ResultItems;

/**
 * 拉勾网的职业信息封装
 * 拉勾网信息爬取机制繁琐，必须是首先获取搜索页的Cookie才能
 * 向拉勾的职业信息api发起请求
 *  @author CourageHe
 * */

public class LagouSpider {
	
	/**
	 * 拉勾网下载重写
	 * @param url 携带keyword、page的链接
	 * @return 发起请求的响应头
	 */
	public static CloseableHttpResponse  lagouDownloader(String url) {
		 CloseableHttpClient httpClient = getLagouClient();
    	//第一次ajax后返回json数据获得总数，对所有页面进行请求
		 int num = Integer.parseInt(url.substring(url.indexOf("&num=")+5));
		 //截取关键字
		 String keyword = url.substring(url.indexOf("keyword=")+8,url.indexOf("&num="));
		 HttpPost httpPost = new HttpPost(url);
		 
		 //添加请求头
		 httpPost.addHeader("Host", "www.lagou.com");
		 httpPost.addHeader("Accept","'application/json, text/javascript, */*; q=0.01");
		 httpPost.addHeader("Referer","'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		 httpPost.addHeader("X-Requested-With","XMLHttpRequest");
		 httpPost.addHeader("Origin","https://www.lagou.com");
		 httpPost.addHeader("X-Anit-Forge-Code","0");
		 httpPost.addHeader("X-Anit-Forge-Token","Nne");
		 httpPost.addHeader("User-Agent", "Mozilla/5.0(X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

		 //post添加请求body
		 List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		 nameValuePairList.add(new BasicNameValuePair("first","true"));
		 nameValuePairList.add(new BasicNameValuePair("pn",num+""));
		 nameValuePairList.add(new BasicNameValuePair("kd",keyword));
		 HttpEntity entity;
		try {
			entity = new  UrlEncodedFormEntity(nameValuePairList, "utf-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse response  = httpClient.execute(httpPost);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

	/**
	 * 每次请求都必须请求一次搜索页以获得新的Cookie
	 * @return 携带新Cookie的httpClient 
	 */
	public static CloseableHttpClient getLagouClient() {
		String urlHome="https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(urlHome);
		httpGet.addHeader("Host", "www.lagou.com");
		httpGet.addHeader("Accept","'application/json, text/javascript, */*; q=0.01");
		httpGet.addHeader("Referer","'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpClient;
	}

	/**
	 * 	拉勾职业信息持久化存储到数据库
	 * @param resultItems 处理结果集
	 */
	public static void lagouSave(ResultItems resultItems) {
		List<String> jobname = resultItems.get("jobname");
		List<String> salary = resultItems.get("salary");
		List<String> city = resultItems.get("city");
		List<String> experience = resultItems.get("experience");
		List<String> education = resultItems.get("education");
		List<String> companylablelist = resultItems.get("companylablelist");
		List<String> companyname = resultItems.get("companyname");
		List<String> companyaddress = resultItems.get("companyaddress");
		List<String> createtime = resultItems.get("createtime");
		List<String> positionId = resultItems.get("positionId");
		String showId = resultItems.get("showId");
		List<String> positionAdvantage = resultItems.get("positionAdvantage");
		List<String> skillLables = resultItems.get("skillLables");
		
		for(int i = 0;i<jobname.size();i++) {
			
//				BloomUtils bloomDemo=new BloomUtils();
//				 if(bloomDemo.contains(positionId.get(i))) {
//					 continue;
//				 }
//				 bloomDemo.add(positionId.get(i));
			JobInfo job = new JobInfo();
			job.setId(UUIDUtils.getUUID64());
			job.setJobname(jobname.get(i));
			job.setSalary(salary.get(i));
			job.setCity(city.get(i));
			job.setExperience(experience.get(i));
			job.setEducation(education.get(i));
			job.setCompanylablelist(companylablelist.get(i));
			job.setCompanyname(companyname.get(i));
			job.setCreatetime(createtime.get(i));
			job.setCompanyaddress(companyaddress.get(i));
			//组合成职位跳转链接
			String url = "https://www.lagou.com/jobs/"+positionId.get(i)+".html?show="+showId;
			job.setUrl(url);
			//组合成职位详情
			String detail = "职位优势："+positionAdvantage.get(i)+"技能要求："+skillLables.get(i);
			job.setDetail(detail);
			job.setWebsite("拉勾");
			System.out.println(job);
			if(job.getJobname()!=null) {
				JobInfoDao.addJobData(job);
			}
			
		}
	}
	
}
