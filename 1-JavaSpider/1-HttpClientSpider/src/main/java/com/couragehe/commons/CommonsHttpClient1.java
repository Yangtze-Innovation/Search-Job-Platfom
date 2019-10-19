package com.couragehe.commons;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
/*
 * 此处采用的是apache讲httpclient封装后得到的commons-httpclient包
 * apache很多commons系列的包都是封装起来贼好用的感觉
 * 此处以爬取小说为例（get）
 */
public class CommonsHttpClient1{
	public static void main(String[]args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		//设置代理服务器和端口
//		client.getHostConfiguration().setProxy("proxyHost", "proxyPort");
		//使用get方法，如果服务器需要通过HTTPS链接,那只需要奖下面的url中的http换成https
		HttpMethod method = new GetMethod("http://www.bjkgjlu.com/64621hnb/328224105.html");
		//使用post方法
//		HttpMethod method = new PostMethod("http://www.bjkgjlu.com/64621hnb/328224105.html");
		client.executeMethod(method);
		//打印服务器返回的状态
		System.out.println(method.getStatusLine());
		//打印返回的信息
		System.out.println(method.getResponseBodyAsString());
		//释放链接
		method.releaseConnection();
	}
}
//https://www.cnblogs.com/ITtangtang/p/3968093.html
