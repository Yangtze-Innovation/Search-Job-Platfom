package com.couragehe.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.couragehe.util.UUIDUtils;


public class Test {
	protected static Map<String, String>cookieMap = new HashMap<String, String>();
	protected static String Referer = "";
	protected static Integer Count = 0;
	@org.junit.Test
	public void handleCookie() throws ClientProtocolException, IOException {
		
		List<String> list = new ArrayList<String>();
		list.add("https://www.lagou.com/");
		list.add("https://www.lagou.com/zhaopin/Java/?labelWords=label");
		list.add("https://www.lagou.com/zhaopin/Java/2/");
		list.add("https://www.lagou.com/zhaopin/Java/3/");
		list.add("https://www.lagou.com/zhaopin/Java/4/");
		list.add("https://www.lagou.com/zhaopin/Java/5/");
		list.add("https://www.lagou.com/zhaopin/Java/6/");
		list.add("https://www.lagou.com/zhaopin/Java/7/");
		list.add("https://www.lagou.com/zhaopin/Java/8/");
		list.add("https://www.lagou.com/jobs/6214424.html?show=05675cf79c8c4bada0fbb3182d33dce4");
		list.add("https://www.lagou.com/jobs/6346843.html?show=b0c0830d6b7d466bab5269b1419f198b");
		list.add("https://www.lagou.com/jobs/6214424.html?show=05675cf79c8c4bada0fbb3182d33dce4");
		list.add("https://www.lagou.com/jobs/6346843.html?show=b0c0830d6b7d466bab5269b1419f198b");
		list.add("https://www.lagou.com/jobs/6214424.html?show=05675cf79c8c4bada0fbb3182d33dce4");
		list.add("https://www.lagou.com/jobs/6346843.html?show=b0c0830d6b7d466bab5269b1419f198b");
		list.add("https://www.lagou.com/jobs/6214424.html?show=05675cf79c8c4bada0fbb3182d33dce4");
		list.add("https://www.lagou.com/jobs/6346843.html?show=b0c0830d6b7d466bab5269b1419f198b");
		list.add("https://www.lagou.com/jobs/6214424.html?show=05675cf79c8c4bada0fbb3182d33dce4");
		list.add("https://www.lagou.com/jobs/6346843.html?show=b0c0830d6b7d466bab5269b1419f198b");
		for(String url :list) {
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(url);
			addHeader(getMethod);
			Referer=url;
			CloseableHttpResponse response;
			int statusCode = httpClient.executeMethod(getMethod);
			InputStream in = getMethod.getResponseBodyAsStream();
			InputStreamReader isr = new InputStreamReader(in,"UTF-8");
			//线程安全
			StringBuffer sb = new StringBuffer();
			char[] b = new char[4096];
            for(int n; (n = isr.read(b)) != -1;) {
                sb.append(new String(b, 0, n));
            }
            String result = sb.toString();
			System.out.println(result);
			System.out.println("状态码："+statusCode);
			System.out.println(++Count);
		}
	}
	public void handleCookie2(CloseableHttpResponse response) throws ClientProtocolException, IOException {
		
		Header[]headers = response.getHeaders("Set-Cookie");
		if(headers != null) {
			for(Header header : headers) {
				String value = header.getValue().substring(0,header.getValue().indexOf(";")+1);
				String name = value.substring(0,value.indexOf("="));
				cookieMap.put(name, value);
			}
		}	
	}
	public void addHeader(GetMethod getMethod) {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<String, String>> entries =  cookieMap.entrySet().iterator();
		while(entries.hasNext()){
		    String value = entries.next().getValue();
		    if(!entries.hasNext()) { 
//		    	value = value.substring(0,value.indexOf(";"));
		    }
		    builder.append(value);
		}
		String cookie = builder.toString();
		getMethod.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
		getMethod.addRequestHeader("Accept-Language","zh-CN,zh;q=0.9");
		getMethod.addRequestHeader("Connection", "keep-alive");
		getMethod.addRequestHeader("Upgrade-Insecure-Requests","1");
		getMethod.addRequestHeader("Host", "www.lagou.com");
		if(Referer !="")getMethod.addRequestHeader("Referer", Referer);
		getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		
	}
	public static void main(String[]args) throws Exception {
		new Test().handleCookie();
	}
}
