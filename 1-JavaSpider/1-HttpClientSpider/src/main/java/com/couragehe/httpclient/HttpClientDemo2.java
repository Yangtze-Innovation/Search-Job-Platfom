package com.couragehe.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Java本身提供了关于网络访问的包，在java.net中，但是不够强大。
 * 于是Apache基金会发布了开源的http请求的包，即httpclient，
 * 这个包提供了非常多的网络访问的功能。
 * @author 52423
 *
 */
public class HttpClientDemo2 {
	public static void main(String[]args) throws URISyntaxException, ParseException, IOException {
		 String url = "";    //请求路径
		//构造路径参数
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		nameValuePairList.add(new BasicNameValuePair("username","test"));
		nameValuePairList.add(new BasicNameValuePair("password","password"));
		//构造请求路径，并添加参数
	    URI uri = new URIBuilder(url).addParameters(nameValuePairList).build();

		//构造Headers
	    List<Header> headerList = new ArrayList<Header>();
	    headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING,"gzip, deflate"));
	    headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
	   //构造HttpClient
	    HttpClient httpClient = HttpClients.custom().setDefaultHeaders(headerList).build();
	    
	    //构造HttpGet请求
	    HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(uri).build();
	    //获取结果
	    HttpResponse httpResponse = httpClient.execute(httpUriRequest);
	    
	    //获取返回结果中的实体
	    HttpEntity entity = httpResponse.getEntity();
	   //查看页面内容结果
	    String rawHTMLContent = EntityUtils.toString(entity);
	    System.out.println(rawHTMLContent);
	    //关闭HttpEntity流
	    EntityUtils.consume(entity);

	}
}
