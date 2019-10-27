package com.couragehe.spider;

import java.awt.font.NumericShaper.Range;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.assertj.core.util.ToString;

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
	protected static HttpClientDownloader downloader;
	private Site site = Site.me()
						//设置重试的次数
						.setRetryTimes(3)
						//设置休眠时间
						.setSleepTime(3000)
						//设置超时时间
						.setTimeOut(3000)
						//设置请求头
						.addHeader("Accept-Encoding", "gzip, deflate, br")
						.addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
						.addHeader("Connection", "keep-alive")
						.addHeader("Upgrade-Insecure-Requests","1")
						.addHeader("Host", "www.lagou.com")
						.addHeader("Cookie", "user_trace_token=20191018210812-69542a99-c4c1-4213-9935-86ebc3517d9b; _ga=GA1.2.581480575.1571404096; LGUID=20191018210814-57d3cddd-f1a8-11e9-a5fb-5254005c3644; index_location_city=%E5%85%A8%E5%9B%BD; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216dee7b2996220-054c10aeb32ba2-b363e65-1327104-16dee7b299a395%22%2C%22%24device_id%22%3A%2216dee7b2996220-054c10aeb32ba2-b363e65-1327104-16dee7b299a395%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D; JSESSIONID=ABAAABAAADEAAFI83A1958E58D80806233C43727DF7EA82; WEBTJ-ID=20191025145527-16e01b2d42e11a-0cb5ed79ec1166-b363e65-1296000-16e01b2d42f524; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1571969759,1571973653,1571979962,1571986528; LGSID=20191025153933-9613ac20-f6fa-11e9-a083-525400f775ce; TG-TRACK-CODE=index_navigation; SEARCH_ID=3e2c3336972f4f87a4be526935dc2994; _gat=1; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1571991302; LGRID=20191025161501-8a7ed17d-f6ff-11e9-a084-525400f775ce; X_HTTP_TOKEN=e9d06f90977f552d613199175117e6d34776edec79")						
						.addHeader("Referer", "https://www.lagou.com/zhaopin/Java/?labelWords=label")
						.addHeader("Accept","text/html, application/xhtml+xml, application/xml; q=0.9, */*; q=0.8")
						.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
	

	public void process(Page page) {
		Html html = page.getHtml();
		//请求各类职业方向的链接
		String regex = "https://www.lagou.com/zhaopin/.*";
		page.addTargetRequests(html.links().regex(regex).all());
		
		if(page.getRequest().getUrl().toString().matches(".*zhaopin.*")){
//			System.out.println(page.getHtml().toString());
			String url = page.getRequest().getUrl().toString();
			
			//
			List<String> list = html.$("div.pager_container").links().all();
//			int i = 0;
//			while(i<30) {
//				list.add(url.substring(0,url.lastIndexOf('/')-1).concat(++i+""));
//			}
			page.addTargetRequests(list);
			
			//获取每页数据详情的url列表
			List<String> jobDetailList = html.$("li.con_list_item div.p_top").links().all();
			page.putField("JobList", jobDetailList);
		   	System.out.println(jobDetailList);
		}
	}
	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {	
//		getCookies("Java");
		String url = "https://www.lagou.com/";
	    downloader = new HttpClientDownloader(){
				@Override
				protected void onError(Request request) {
					setProxyProvider(SimpleProxyProvider.from(
							new Proxy("202.109.157.47",9000),
							new Proxy("183.166.110.36",9999),
							new Proxy("1.199.30.144",9999),
							new Proxy("123.163.97.89",9999)
							));
				}
	    };

		Spider.create(new JobCollectPageProcessor())
			 //从该网页开始抓取
		  	 .addUrl(url)
		  	 .setDownloader(downloader)
			 //自定义结果处理、计算、持久化
			 .addPipeline(new JobCollectSavePipeline())
			 //开启线程抓取
			 .thread(3)
			 //启动爬虫
			 .run();
		}

}
