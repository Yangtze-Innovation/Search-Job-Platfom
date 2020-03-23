package com.couragehe.spider;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.openqa.selenium.WebDriver;

import com.couragehe.dao.JobPositionDao;
import com.couragehe.entity.JobInfo;
import com.couragehe.util.ConnPoolUtils;
import com.couragehe.util.MyConn;
import com.couragehe.util.UUIDUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class JobDetailPageProcessor implements PageProcessor {
	private Site site = Site.me()
			//设置重试的次数
			.setRetryTimes(3)
			//设置休眠时间
			.setSleepTime(800)
			//设置超时时间
			.setTimeOut(600)
			//设置请求头
			.addHeader("Accept-Encoding", "gzip, deflate, br")
			.addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
			.addHeader("Connection", "keep-alive")
			.addHeader("Upgrade-Insecure-Requests","1")
			.addHeader("Host", "www.lagou.com")
			.addHeader("Referer", "https://www.lagou.com/zhaopin/Java/?labelWords=label")
			.addHeader("Accept","text/html, application/xhtml+xml, application/xml; q=0.9, */*; q=0.8")
			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
	public void process(Page page) {
		Html html = page.getHtml();
//		if(html.toString().indexOf("页面加载中") != -1) {
//			System.out.println("加载中");
//			//在连接池种取出空闲的连接对象
//			MyConn conn = ConnPoolUtils.getInstance().getMyConn();
//			WebDriver driver  = conn.getDriver();
//			driver.get(page.getRequest().getUrl());
//			ConnPoolUtils.getInstance().setFree(conn);
//			html = Html.create(driver.getPageSource());
//			//将连接对象 释放至连接池
//			ConnPoolUtils.getInstance().setFree(conn);
//			System.out.println(ConnPoolUtils.getInstance());
//		}
//		MyConn conn = ConnPoolUtils.getInstance().getMyConn();
//		WebDriver driver  = conn.getDriver();
//		if(Pattern.matches("https://passport.lagou.com/.*", driver.getCurrentUrl())){
//			System.out.println("重定向了");
//			page.addTargetRequest(page.getRequest().getUrl()+" ");
//			ConnPoolUtils.getInstance().closeErrorDriver(conn);
//			return ;
//		}else {
//			html = Html.create(driver.getPageSource());
//			//将连接对象 释放至连接池
//			ConnPoolUtils.getInstance().setFree(conn);
//		}
		//达到第三层 详情页
		JobInfo job = new JobInfo();
		job.setId(UUIDUtils.getUUID64());
//		CSS选择器
		job.setJobname(html.$("h1.name","text").get());
		job.setSalary(html.$("span.ceil-salary","text").toString());
		
		//地址解析，去除所有标签
		String address = Jsoup.clean(html.$("div[class=work_addr]").toString(), Whitelist.none());
		job.setCompanyaddress(address.substring(0,address.indexOf("查看")));
		
		//JSoup解析器
		Document doc = Jsoup.parse(html.toString());
	
		job.setCity(doc.select("dd[class=job_request] h3 span").get(1).text().replaceAll("/",""));
		job.setExperience(doc.select("dd[class=job_request] h3 span").get(2).text().replaceAll("/",""));
		job.setEducation(doc.select("dd[class=job_request] h3 span").get(3).text().replaceAll("/",""));
		
		//xpath解析器
		job.setCompanylablelist(html.xpath("//dd[@class='job-advantage']/p/text()").toString());
		job.setCompanyname(html.xpath("//h3[@class=\"fl\"]/em/text()").toString());
		
		//时间进行截取
		String time = html.xpath("//p[@class=\"publish_time\"]/text()").toString();
		job.setCreatetime(time.substring(0,time.indexOf(" ")-1));
		job.setUrl(page.getRequest().getUrl());
		
		//职位详细信息进行截取
		String detail = html.xpath("div[@class=\"job-detail\"]").toString();
		job.setDetail(detail.substring(detail.indexOf("<p>1"),detail.lastIndexOf("<p>1")-16));
		job.setWebsite("拉勾");
		
		System.out.println(job);
		if(job.getJobname()!=null) {
			JobPositionDao.addJobData(job);
		}
	}

	public Site getSite() {
		// TODO 自动生成的方法存根
		return site;
	}

}
