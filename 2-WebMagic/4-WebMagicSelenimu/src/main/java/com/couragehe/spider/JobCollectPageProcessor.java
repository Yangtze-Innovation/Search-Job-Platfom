package com.couragehe.spider;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import com.couragehe.util.ConnPoolUtils;
import com.couragehe.util.LinkListUtils;
import com.couragehe.util.MyConn;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class JobCollectPageProcessor implements PageProcessor {
	private Site site = Site.me()
				.setRetryTimes(3)
				.setRetrySleepTime(1000)
				.setTimeOut(3000)
				.setSleepTime(600)
				.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
	private static Set<Cookie> cookies;
	
	public void process(Page page) {
//		//在连接池种取出空闲的连接对象
		MyConn conn = ConnPoolUtils.getInstance().getMyConn();
		WebDriver driver  = conn.getDriver();
		driver.get(page.getRequest().getUrl());
		Html html = Html.create(driver.getPageSource());
		 //访问页面过多的话会自动重定向到登陆页
		 if(Pattern.matches("https://passport.lagou.com/.*", driver.getCurrentUrl())){
			//将该页面再次请求，并加上空格，避免webmagic的去重机制去除
			page.addTargetRequest(page.getRequest().getUrl()+" ");
			//关闭该浏览器对象
			ConnPoolUtils.getInstance().closeErrorDriver(conn);
			return ;
		}else {
			//正常得到所需html
			html = Html.create(driver.getPageSource());
			//将连接对象 释放至连接池
			ConnPoolUtils.getInstance().setFree(conn);
		}
		//查看连接池使用情况
		System.out.println(ConnPoolUtils.getInstance());
		 //从首页进入 采集首页的招聘方向的连接
		if(page.getRequest().getUrl().equals("https://www.lagou.com/")) {
			//由于没有代理，爬一段时间就会被封，因此将连接存储，每次爬取30到40个种类的所有连接
			//	LinkListUtils.DuplicateHandle(html.links().regex(regex).all());
			//	List<String>list = LinkListUtils.getLinkList(6, 15);
			//	page.addTargetRequests(list);
			String regex = "https://www.lagou.com/zhaopin/.*";
			page.addTargetRequests(html.links().regex(regex).all());
		}
		//每个招聘收索页的其他页面的连接继续请求
		page.addTargetRequests(html.$("div.pager_container").links().all());
		//将得到的每个职业的连接单独用另一个spider对象处理，因为driver加载太慢了
		List<String>jobList = html.$("li.con_list_item div.p_top").links().all();
		page.putField("joblist", jobList);
	}
	
	public Site getSite() {
//		for(Cookie cookie :cookies) {
//			site.addCookie(cookie.getName(), cookie.getValue());
//		}
		System.out.println(site.getUserAgent());
		return site;
	}



}
