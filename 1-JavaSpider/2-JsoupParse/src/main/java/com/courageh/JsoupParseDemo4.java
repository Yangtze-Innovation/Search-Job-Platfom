package com.courageh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
/**
 *修改HTML数据
 *增加class：addClass("mylinkclass")
 *删除class: removeClas("mylinkclass")
 *修改属性：attr("rel", "nofollow"); 
 *删除属性：removeAttr("onclick"); 
 *清空值：val(""); 
 *清空文本：text("")
 */
public class JsoupParseDemo4 {

	public static void main(String[] args) throws IOException {
		//1、字符串转化为Document对象
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    Document doc = Jsoup.parse(html);
	    Elements elements = doc.select("div[id=blog_list] a");
	    for(Element element : elements) {
	    	element.addClass("myclass");
	    	element.attr("href", "123");
	    	element.parent().removeAttr("class");
	    	element.text("");
	    }
	    System.out.println(doc.toString());
	}
}
