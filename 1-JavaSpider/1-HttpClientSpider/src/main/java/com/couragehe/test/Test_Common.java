package com.couragehe.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
public class Test_Common {
	
	protected static Map<String, String>cookieMap = new HashMap<String, String>();
	protected static Integer Count = 0;
	protected String urlHome="https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=";
	protected String urlAjax="https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false";
	
	protected static String sid = "";
	

	public void handleCookie() throws ClientProtocolException, IOException {
		
		for(int i=1;i<200;i++) {			
			HttpClient httpClient = new HttpClient();
			getCookie(httpClient);
			int page = i;
			String keyword = "JAVA";
			PostMethod postMethod = new PostMethod(urlAjax);
			String data = "{"+
							"first: true,"+ 
							"pn:"+ page+","+
							"kd:"+keyword+
							"}"; 
			//以UTF-8编码的json格式作为 body
			RequestEntity entity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(entity);
			//添加请求头
			postMethod.addRequestHeader("Host", "www.lagou.com");
			postMethod.addRequestHeader("Accept","'application/json, text/javascript, */*; q=0.01");
			postMethod.addRequestHeader("Referer","'https://www.lagou.com/jobs/list_JAVA/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
			postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

			httpClient.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
//			sid = result.substring(result.indexOf("showId")+9,result.indexOf("hrInfoMap")-3);
//			System.out.println(sid);
			System.out.println(result);		
			System.out.println(page);
		}
	
	}

	//获取搜索页的Cookie
	public void getCookie(HttpClient httpClient) {
		GetMethod getMethod = new GetMethod(urlHome);
		getMethod.addRequestHeader("Host", "www.lagou.com");
		getMethod.addRequestHeader("Accept","'application/json, text/javascript, */*; q=0.01");
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
		try {
			httpClient.executeMethod(getMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Cookie[]cookies = httpClient.getState().getCookies();
//		for(Cookie cookie : cookies) {
//			System.out.println(cookie.getName()+":"+cookie.getValue());			
//		}
	}
	public static void main(String[]args) throws Exception {
		new Test_Common().handleCookie();
	}
}

