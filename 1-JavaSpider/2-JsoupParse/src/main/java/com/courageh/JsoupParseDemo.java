package com.courageh;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupParseDemo {

	public static void main(String[] args) {
		List<String> titles = new ArrayList<String>();
	    List<String> urls = new ArrayList<String>();

	    //假设我们获取的HTML的字符内容如下
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";

	    //第一步，将字符内容解析成一个Document类
	    Document doc = Jsoup.parse(html);

	    //第二步，根据我们需要得到的标签，选择提取相应标签的内容
	    Elements elements = doc.select("div[id=blog_list]").select("div[class=blog_title]");
	    for( Element element : elements ){
	      String title = element.text();
	      titles.add(title);
	      urls.add(element.select("a").attr("href"));
	    }
	    //输出测试
	    for( String title : titles ){
	      System.out.println(title);
	    }

	    for( String url : urls ){
	      System.out.println(url);
	    }


	}
//	https://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/index.html
}
