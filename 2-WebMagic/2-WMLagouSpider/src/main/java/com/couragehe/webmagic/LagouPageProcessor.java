package com.couragehe.webmagic;

import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class LagouPageProcessor implements PageProcessor {
	private static int count = 0;
	private Site site = Site.me()
						.setRetryTimes(3)
						.setSleepTime(3000)
						.setTimeOut(5000)
						.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
	
	public void process(Page page) {
		Map<String, List<String>> headers = page.getHeaders();
		Map<String, String> cookies = page.getRequest().getCookies();
		System.out.println(page.getJson().toString());
//		System.out.println(page.getJson().jsonPath("$.content.showId").get());
		for(int i=2;i<30;i++) {
			page.addTargetRequest(Controller.addTargetRequest(Controller.url, i));
		}
		//获取数据
//		List<String> userIds = new JsonPathSelector("$.content.hrInfoMap[*].userId").selectList(page.getRawText());
//		for(String userId  : userIds) {
//			System.out.println(userId);
//			System.out.println(count++);
//		}
		//保存数据
//		page.putField("userIds", userIds);
	}
	public Site getSite() {
		return site;
	}

}
