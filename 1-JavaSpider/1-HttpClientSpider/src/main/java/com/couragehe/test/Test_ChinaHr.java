package com.couragehe.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test_ChinaHr {

    private final String url = "http://www.chinahr.com/sou/?orderField=relate&keyword=%s&city=%s&page=%d";
    private int page;
    private String city = "34,398;36,400;25,292;25,291;23,264";
//    private String keyWord;
//    private String experience;
//    private CityKeyChinaHr cityKeyChinaHr;
//    private ExperienceKey experienceKey;
    public  void questChinaHr() throws Exception {
    	String []keyList = {"JAVA", "Python", "行政总监","在线客服","电脑打字", "人力专员","销售专员"};
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	for(String keyWord :keyList) {
    		HttpGet get = new HttpGet(String.format(url,keyWord, city,page));
    		CloseableHttpResponse response =  httpClient.execute(get);
    		String result = EntityUtils.toString(response.getEntity());
    		System.out.println(result);
    		
    	}
    }
    public static void main(String[]args) throws Exception {
    	new Test_ChinaHr().questChinaHr();
    }
}
