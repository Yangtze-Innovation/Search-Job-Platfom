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
 *解析并提取HTML元素
 *jsoup
 *获取元素：getElementById 和 getElementsByTag 、getElementsByClass、select复合选择
 *获取属性： attr();
 *获取文本：text();
 */
public class JsoupParseDemo3 {

	public static void main(String[] args) throws IOException {
		//1、字符串转化为Document对象
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    Document doc = Jsoup.parse(html);
	    //select复合选择器 类似jquery 的$选择符;
//	    Elements elements = doc.select("div[id=blog_list] ").select("div[class=blog_title]");
	    //类JavaScript的选择器
	    Elements elements = doc.getElementById("blog_list").getElementsByClass("blog_title");
	    for( Element element : elements ){
	    	System.out.print(element.getElementsByTag("a").text()+" ");
	     System.out.println(element.select("a").attr("href"));
	    }
	
	}

}
