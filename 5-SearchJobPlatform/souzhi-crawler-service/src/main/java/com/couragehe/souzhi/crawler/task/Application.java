package com.couragehe.souzhi.crawler.task;

import com.couragehe.souzhi.crawler.mapper.CompanyMapper;
import com.couragehe.souzhi.crawler.mapper.PositionMapper;
import com.couragehe.souzhi.crawler.task.website.Job51Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import us.codecraft.webmagic.Spider;

/**
 * WebMagic启动类
 */
@Controller
@EnableScheduling
public class Application {
	@Autowired
	WebsitePipeline websitePipeline;
	@Autowired
	WebsiteProcessor websiteProcessor;

	@Scheduled(cron = "0 05 05 * * *")
	@RequestMapping("test")
	public void startTask() {	
		//清空表单并进行爬取

		String lagouUrl = "https://www.lagou.com";
		String jobsUrl = "https://jobs.51job.com";
		Spider.create(websiteProcessor)
				//从该网页开始抓取
				.addUrl(lagouUrl,jobsUrl)
				//自定义下载器
				.setDownloader(new WebsiteDownloader())
				//自定义结果处理、计算、持久化
				.addPipeline(websitePipeline)
				//开启线程抓取
				.thread(50)
				//启动爬虫
				.run();		
	}

}
