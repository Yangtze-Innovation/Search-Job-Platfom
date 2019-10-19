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

	    //�������ǻ�ȡ��HTML���ַ���������
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">��һƪ����</a></div><div class=\"blog_title\"><a href=\"url2\">�ڶ�ƪ����</a></div><div class=\"blog_title\"><a href=\"url3\">����ƪ����</a></div></div></html>";

	    //��һ�������ַ����ݽ�����һ��Document��
	    Document doc = Jsoup.parse(html);

	    //�ڶ���������������Ҫ�õ��ı�ǩ��ѡ����ȡ��Ӧ��ǩ������
	    Elements elements = doc.select("div[id=blog_list]").select("div[class=blog_title]");
	    for( Element element : elements ){
	      String title = element.text();
	      titles.add(title);
	      urls.add(element.select("a").attr("href"));
	    }
	    //�������
	    for( String title : titles ){
	      System.out.println(title);
	    }

	    for( String url : urls ){
	      System.out.println(url);
	    }


	}
//	https://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/index.html
}
