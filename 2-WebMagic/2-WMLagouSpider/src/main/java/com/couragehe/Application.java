package com.couragehe;

import com.couragehe.spider.WebsiteDownloader;
import com.couragehe.spider.WebsitePipeline;
import com.couragehe.spider.WebsiteProcessor;

import us.codecraft.webmagic.Spider;

/**
 * 应用启动类
 */
public class Application {

	public static void main(String[] args) {	
		String url = "https://www.lagou.com";
		Spider.create(new WebsiteProcessor())
				//从该网页开始抓取
				.addUrl(url)
				//自定义下载器
				.setDownloader(new WebsiteDownloader())
				//自定义结果处理、计算、持久化
				.addPipeline(new WebsitePipeline())
				//开启线程抓取
				.thread(50)
				//启动爬虫
				.run();		
	}

}
