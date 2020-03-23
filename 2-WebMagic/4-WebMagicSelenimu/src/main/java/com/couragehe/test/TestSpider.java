package com.couragehe.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.lucene.analysis.CharArrayMap.EntrySet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
//import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.couragehe.util.ConnPoolUtils;
import com.couragehe.util.MyConn;
import com.mysql.jdbc.Driver;

import us.codecraft.webmagic.selector.Html;

public class TestSpider {
	
	@Test
	public void Test1() {
		System.out.println(ConnPoolUtils.getInstance().toString());
		MyConn conn = null;
		//在连接池种取出空闲的连接对象
		for(int i=0;i<10;i++) {
			conn = ConnPoolUtils.getInstance().getMyConn();			
			System.out.println(ConnPoolUtils.getInstance());
			WebDriver driver = conn.getDriver();
			driver .get("https://www.lagou.com/gongsi/52162.html");
			System.out.println(driver.getPageSource());
		}
		//将连接对象 释放至连接池
		ConnPoolUtils.getInstance().setFree(conn);
	}
	@Test
	public void Test2() {
		String str = "1天前  发布于拉勾网" ;
		System.out.println(str.substring(0,str.indexOf("发")-1));
	}
	@org.junit.Test
	public void Test3() {
		String str = "<html>\r\n" + 
				" <head>\r\n" + 
				"  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n" + 
				"  <meta name=\"renderer\" content=\"webkit\">\r\n" + 
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
				" </head>\r\n" + 
				" <body>\r\n" + 
				"  <script src=\"/utrack/track.js?version=1.0.1.0\" type=\"text/javascript\"></script>\r\n" + 
				"  <script type=\"text/javascript\" src=\"https://www.lagou.com/utrack/trackMid.js?version=1.0.0.3&amp;t=1572101133\"></script>\r\n" + 
				"  <input type=\"hidden\" id=\"KEY\" value=\"POqT6tkxloMCVm5NRCOak4PC9q8prpR3h1qaF7sb\">\r\n" + 
				"  <script type=\"text/javascript\">FwlaxJXy();</script>页面加载中...\r\n" + 
				"  <script type=\"text/javascript\" src=\"https://www.lagou.com/upload/oss.js\"></script> \r\n" + 
				" </body>\r\n" + 
				"</html>"; 

		if(str.indexOf("傻逼") != -1) {
			System.out.println("加载中……"); 
		}else {
			System.out.println("加载成功");					
		}
	}
	@org.junit.Test
	public void Test4() {
		MyConn conn = ConnPoolUtils.getInstance().getMyConn();
		WebDriver driver  = conn.getDriver();
		driver.get("https://www.lagou.com/");
		Html html = Html.create(driver.getPageSource());
		if(Pattern.matches("https://www.lagou.com/.*", "https://www.agou.com/")){
			System.out.println("正确");
			ConnPoolUtils.getInstance().setFree(conn);
		}else {
			System.out.println("错误");
			ConnPoolUtils.getInstance().closeErrorDriver(conn);
		}
		System.out.println(ConnPoolUtils.getInstance());
	}
	@Test
	public void Test5() {
		String url = "https://www.lagou.com";
		 if(url.indexOf("positionAjax") != -1){
			 System.out.println("里面");
	     }else {
	      System.out.println("外面");  	
	      }
	}
	
}
