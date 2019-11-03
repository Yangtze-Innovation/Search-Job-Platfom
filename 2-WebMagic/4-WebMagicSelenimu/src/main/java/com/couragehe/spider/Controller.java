package com.couragehe.spider;


import com.couragehe.util.ConnPoolUtils;

import us.codecraft.webmagic.Spider;

public class Controller {
	public static void main(String[] args) {
		String url = "https://www.lagou.com/";

		Spider.create(new JobCollectPageProcessor())
			.addUrl(url)
//			.setDownloader(ConnPoolUtils.getHttpClientDownloader())
			.addPipeline(new JobCollectSavePipeline() )
			.thread(3)
			.run();
		//执行完毕 关闭所有的浏览器对象
		ConnPoolUtils.getInstance().closeAllDriver();
	}
		
}
