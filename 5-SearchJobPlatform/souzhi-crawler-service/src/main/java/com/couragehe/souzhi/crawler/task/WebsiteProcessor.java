package com.couragehe.souzhi.crawler.task;

import java.util.regex.Pattern;


import com.couragehe.souzhi.crawler.task.website.Job51Spider;
import com.couragehe.souzhi.crawler.task.website.LagouSpider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 页面处理
 */
@Component("WebsiteProcessor")
public class WebsiteProcessor implements PageProcessor{
	//拉勾爬虫封装类
	@Autowired
	private LagouSpider lagouSpider;
	//51Job爬虫封装类
	@Autowired
	Job51Spider job51Spider;

	private Site site = Site.me()
						.setRetryTimes(3)
						.setSleepTime(200)
						.setTimeOut(2000)
						.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
	
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		page.putField("url", url);
//		System.out.println(page.getRawText());
		if(Pattern.matches(".*lagou.*", page.getRequest().getUrl())) {
			lagouSpider.addPageUrl(page);
		}else if(Pattern.matches(".*51job.*", page.getRequest().getUrl())) {
			job51Spider.addPageUrl(page);
		}
		//处理拉勾请求得到的数据
		 if(url.indexOf("positionAjax") != -1) {
			 lagouSpider.parseJobInfo(page);

		 }else if(url.indexOf(".html") != -1) {
			//51Job详情页以html结尾的特征 如：https://jobs.51job.com/nanning/121231609.html?s=02
			 job51Spider.parseJobInfo(page);
		 }
	
	}


	

	public Site getSite() {
		return site;
	}

}
