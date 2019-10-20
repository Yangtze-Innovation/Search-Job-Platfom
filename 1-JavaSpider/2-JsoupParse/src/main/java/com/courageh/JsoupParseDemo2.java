package com.courageh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 文档输入的三种方式
 * 1、字符串转化为Document对象
 * 2、通过Jsoup的一些简单请求URL获取
 * 3、解析本地html文件
 * @author 52423
 *
 */
public class JsoupParseDemo2 {

	public static void main(String[] args) throws IOException {
		//1、字符串转化为Document对象
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    Document doc = Jsoup.parse(html);

	    // 2、通过Jsoup的一些简单请求URL获取
	    //2.1 get请求
	    Document doc1 = Jsoup.connect("http://www.oschina.net/").get(); 
	    //2.2 post请求
	    Document doc2 = Jsoup.connect("http://www.oschina.net/") 
	    		  .data("query", "Java")   // 请求参数
	    		  .userAgent("I ’ m jsoup") // 设置 User-Agent 
	    		  .cookie("auth", "token") // 设置 cookie 
	    		  .timeout(3000)           // 设置连接超时时间
	    		  .post();                 // 使用 POST 方法访问 URL 

	    // 3、解析本地html文件
	    //第三个参数为html中的相对路径加上前缀
	    File input = new File("D:/test.html"); 
	    Document doc3 = Jsoup.parse(input,"UTF-8","http://www.oschina.net/"); 
	}

}
