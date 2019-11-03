package com.couragehe.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test_Native {
	
	protected static Map<String, String>cookieMap = new HashMap<String, String>();
	protected static Integer Count = 0;
	protected String urlHome="https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=";
	protected String urlAjax="https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&page=2";
	protected CookieStore cookieStore = new BasicCookieStore();
	protected static String sid = "";
	
	

	public void handleCookie() throws IOException, InterruptedException {

//		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		for(int i=1;i<200;i++) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			getCookie(httpClient);
			int page = i;
			String keyword = "JAVA";
			HttpPost httpPost = new HttpPost(urlAjax);
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			nameValuePairList.add(new BasicNameValuePair("first","true"));
			nameValuePairList.add(new BasicNameValuePair("pn",page+""));
			nameValuePairList.add(new BasicNameValuePair("kd",keyword));
//			if(sid != "") nameValuePairList.add(new BasicNameValuePair("sid",sid));
			//post添加请求body
			HttpEntity entity = new  UrlEncodedFormEntity(nameValuePairList, "utf-8");
			httpPost.setEntity(entity);

			//添加请求头
			httpPost.addHeader("Host", "www.lagou.com");
			httpPost.addHeader("Accept","'application/json, text/javascript, */*; q=0.01");
			httpPost.addHeader("Referer","'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
			httpPost.addHeader("X-Requested-With","XMLHttpRequest");
			httpPost.addHeader("Origin","https://www.lagou.com");
			httpPost.addHeader("X-Anit-Forge-Code","0");
			httpPost.addHeader("X-Anit-Forge-Token","Nne");
			httpPost.addHeader("User-Agent", "Mozilla/5.0(X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			CloseableHttpResponse response  = httpClient.execute(httpPost);
			//输出得到的结果
			String result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
			sid = result.substring(result.indexOf("showId")+9,result.indexOf("hrInfoMap")-3);
			System.out.println(sid);
			//关闭HttpEntity输出流
			EntityUtils.consume(response.getEntity());
			System.out.println(page);
		}
	
	}
	//获取搜索页的Cookie
	public void getCookie(CloseableHttpClient httpClient) {
	
		HttpGet httpGet = new HttpGet(urlHome);
		//清除Cookie无法起到复用的效果
		cookieStore.clear();
		httpGet.addHeader("Host", "www.lagou.com");
		httpGet.addHeader("Accept","'application/json, text/javascript, */*; q=0.01");
		httpGet.addHeader("Referer","'https://www.lagou.com/jobs/list_JAVA/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
//			System.out.println(EntityUtils.toString(response.getEntity()));;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		 List<Cookie> cookies = cookieStore.getCookies();
//		for(Cookie cookie : cookies) {
//			System.out.println(cookie.getName()+":"+cookie.getValue());			
//		}
	}
	public static void main(String[]args) throws Exception {
		new Test_Native().handleCookie();

	}
}

