package com.couragehe.spider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.couragehe.util.ConnPoolUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class JobCollectSavePipeline implements Pipeline {
	

	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("joblist");
		Spider spider = Spider.create(new JobDetailPageProcessor())
//				.setDownloader(ConnPoolUtils.getHttpClientDownloader())
		 		//开启线程抓取
		 		.thread(5);
			
	    for (String url: list ) {
	    	spider.addUrl(url);
		}

		 //启动爬虫
	    spider.run();
	}
	public static void main(String[]args) {
		String url = "https://www.lagou.com/jobs/6403487.html?show=81f1607c93684c7c86d647e3fc0cc7c4";
		Spider.create(new JobDetailPageProcessor())
//				.setDownloader(ConnPoolUtils.getHttpClientDownloader())
				.addUrl(url)
				.thread(1)
				.run();
	}

}
