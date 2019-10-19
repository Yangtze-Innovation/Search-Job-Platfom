package com.couragehe.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Java本身提供了关于网络访问的包，在java.net中，但是不够强大。
 * 于是Apache基金会发布了开源的http请求的包，即httpclient，
 * 这个包提供了非常多的网络访问的功能。
 * @author 52423
 *
 */
public class HttpClientDemo {
	public static void main(String[]args) {
		//建立一个新的请求客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//使用httpGet方式请求网址
		HttpGet httpGet= new HttpGet("https://www.lagou.com");
		//获取网址的返回结果
		CloseableHttpResponse response = null;
		try {
			response =  httpClient.execute(httpGet);
			//获取返回结果中的实体
			HttpEntity entity = response.getEntity();
			//奖返回的实体输出
			System.out.println(EntityUtils.toString(entity));
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}