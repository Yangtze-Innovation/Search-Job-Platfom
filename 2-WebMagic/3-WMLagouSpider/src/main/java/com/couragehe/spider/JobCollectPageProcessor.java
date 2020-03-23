package com.couragehe.spider;



import javax.swing.text.html.HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import com.couragehe.entity.JobInfo;
import com.couragehe.util.UUIDUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

public class JobCollectPageProcessor implements PageProcessor {
	private Site site = Site.me()
						//设置重试的次数
						.setRetryTimes(3)
						//设置休眠时间
						.setSleepTime(3000)
						//设置超时时间
						.setTimeOut(3000);
						//设置请求头
	

	public void process(Page page) {
		Html html = page.getHtml();
		System.out.println(page.getRequest().getUrl().toString());
		
		//请求各类职业方向的链接
		String regex = "https://www.lagou.com/zhaopin/.*";
		page.addTargetRequest(html.links().regex(regex).toString());
		if(page.getRequest().getUrl().toString().matches(".*zhaopin.*")){
			System.out.println(html);
			page.addTargetRequests(html.$("div.pager_container").links().all());
			//获取每页数据详情的url列表
//			page.addTargetRequests(html.$("li.con_list_item div.p_top").links().all());
		}else if(page.getRequest().getUrl().toString().matches(".*job.*")){
			System.out.println(html);
			processDetailPage(page);
		}
	}
	public Site getSite() {
		return site;
	}
	public void processDetailPage(Page page) {
		Html html = page.getHtml();
		//达到第三层 详情页
		JobInfo job = new JobInfo();
		job.setId(UUIDUtils.getUUID64());
//		CSS选择器
		job.setJobname(html.$("h1.name","text").get());
		job.setSalary(html.$("span.ceil-salary","text").toString());
		
		//地址解析，去除所有标签
		String address = Jsoup.clean(html.$("div[class=work_addr]").toString(), Whitelist.none());
		job.setCompanyaddress(address);
		
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
		job.setCreatetime(time.substring(0,5));
		job.setUrl(page.getRequest().getUrl());
		job.setDetail(html.xpath("dd[@class=\"job_bt\"]").toString());
		job.setWebsite("拉勾");
		page.putField("JobInfo", job);
	
	}
	public static void main(String[] args) {	
		String url = "https://www.lagou.com/";
		Spider.create(new JobCollectPageProcessor())
			 //从该网页开始抓取
		  	 .addUrl(url)
		  	 .setDownloader(new JobCollectDownloader())
			 //自定义结果处理、计算、持久化
			 .addPipeline(new JobCollectSavePipeline())
			 //开启线程抓取
			 .thread(2)
			 //启动爬虫
			 .run();
		}

}
