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
 * HTML文档过滤清理
 * none() 只允许包含文本信息
 * basic()允许的标签包括：a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, ul, 以及合适的属性
 * simpleText() 只允许 b, em, i, strong, u 这些标签
 * basicWithImages()	在 basic() 的基础上增加了图片
 * relaxed() 支持很多很多标签
 * 扩大搜索范围
 * whitelist.addTags("embed","object","param","span","div"); 
 * addAttributes(":all", "href")
 * addProtocols(":all", "href","http")
 * 然后自定义选择标签
 */
public class JsoupParseDemo5 {

	public static void main(String[] args) throws IOException {
//		1、字符串转化为Document对象
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"http://www.baidu.com\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    String safe = Jsoup.clean(html, Whitelist.basic().addProtocols("a", "href", "http"));
	    System.out.println(safe);
	

	}
}
