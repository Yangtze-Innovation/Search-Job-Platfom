package com.couragehe.webmagic;



import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.utils.HttpConstant;

public class Controller {
	protected static String url="https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false";
	protected static String cookies ="";
	protected static String keyword = "";
	//	获取页面访问的Cookie
	public static void getCookies(String key) {
		keyword = key;
		//组合拉勾网查询地址
		String url1 = "https://www.lagou.com/jobs/list_"+key+"?labelWords=&fromSearch=true&suginput=";
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url1);
			getMethod.addRequestHeader("Referer","https://www.lagou.com/jobs/list_"+keyword+"?labelWords=&fromSearch=true&suginput=");
			getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		try {
			int statusCode = client.executeMethod(getMethod);
			Header[]headers = getMethod.getRequestHeaders("Cookie");
			if(headers.length!=0) {
				for(Header header:headers) {
					cookies = cookies.concat(header.getValue());
				}
			}
			if(getMethod.getResponseHeader("Set-Cookie")!=null) {
				String setcookies = getMethod.getResponseHeader("Set-Cookie").getValue();
				cookies = cookies.concat(setcookies);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public static Request addTargetRequest(String url,int pageCount) {
		getCookies(keyword) ;
		Request request = new Request(url);
		request.setMethod(HttpConstant.Method.POST);
		
		request.setRequestBody(HttpRequestBody.json("{'first':true,pn:"+pageCount+",kd:"+keyword+"}","utf-8"));
		request.addHeader("Accept-Encoding", "gzip, deflate, br")
			.addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
			.addHeader("Connection", "keep-alive")
			.addHeader("Upgrade-Insecure-Requests","1")
			.addHeader("Accept","application/json, text/javascript, */*; q=0.01")
			.addHeader("Cookie", cookies)
			.addHeader("Host", "www.lagou.com")
			.addHeader("Referer","https://www.lagou.com/jobs/list_"+keyword+"?labelWords=&fromSearch=true&suginput=");
		return request;
	}

	public static void main(String[] args) {	
		getCookies("JAVA");
		String url = "https://www.lagou.com";
		HttpClientDownloader downloader = new HttpClientDownloader(){
				@Override
				protected void onError(Request request) {
					setProxyProvider(SimpleProxyProvider.from(new Proxy("202.109.157.47",9000)));
				}
		};

		Spider.create(new LagouPageProcessor())
				//从该网页开始抓取
				.addRequest(addTargetRequest(url,1))
//				.addUrl(url)
//				.setDownloader(downloader)
				//自定义结果处理、计算、持久化
				.addPipeline(new LagouSavePipeline())
				//开启线程抓取
				.thread(5)
				//启动爬虫
				.run();		
	}

}
