package com.couragehe.commons;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 * 在jsp/Servlet编程中的response.sendRedirect方法就是i使用HTTP协议中的重定向机制。
 * 它与JSP中的<jsp:forward>的区别在于后者是在服务器中实现页面的跳转，也就是说应用容器加载了
 * 索要跳转的页面内容并返回给客户端。而前者是返回一个状态码，这些状态码可能值见下表，
 * 然后客户端读取需要跳转到的页面的URL并重新加载新的页面。就是这样一个过程，所以我们编程的时候就要
 * 通过HttpMethod.getStatusCode()方法判断返回值是否为下表中的某个值来判断是否需要跳转。
 * 如果已经确认需要进行页面跳转了，那么可以通过读取HTTP头中的location属性来获取新的地址。
 * @author 52423
 *
 */
public class CommonsHttpClient3{
	public static void OperateSendRedirect(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		HttpMethod post = new PostMethod(url);
		
		client.executeMethod(post);
		System.out.println(post.getStatusLine().toString());
		post.releaseConnection();
		// 检查是否重定向
		int statuscode = post.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) || 
				(statuscode ==HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			// 读取新的 URL 地址 
			Header header=post.getResponseHeader("location");
			if (header!=null){
				String newuri=header.getValue();
				if((newuri==null)||(newuri.equals("")))
					newuri="/";
				//请求新的地址
				GetMethod redirect=new GetMethod(newuri);
				client.executeMethod(redirect);
				System.out.println("Redirect:"+redirect.getStatusLine().toString());
				redirect.releaseConnection();
			}else 
				System.out.println("Invalid redirect");
		}
		
	}
}
