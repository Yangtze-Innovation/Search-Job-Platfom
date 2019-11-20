package com.couragehe.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.couragehe.dao.JobInfoDao;

import us.codecraft.webmagic.Spider;

/**
 * 应用启动类
 */
@Component
@EnableScheduling
public class Application {
	@Autowired
	private JobInfoDao jobInfoDao;

	@Scheduled(cron = "0 21 1 * * *")
	public void startTask() {	
		//清空表单并进行爬取
		System.out.println("定时更新数据库数据!!!");
		jobInfoDao.clearTable();
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
