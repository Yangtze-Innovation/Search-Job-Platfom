package com.task;




import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.UUIDutils;
import com.pojo.JobInfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

@Component
public class JobWork implements PageProcessor{
	private String url = "https://search.51job.com/list/000000,000000,0000,00,9,99,java,2,1.html?"
									+ "lang=c&stype=&postchannel=0000&workyear=99&cotype=99&"
									+ "degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&"
									+ "ord_field=0&confirmdate=9&fromType=&dibiaoid="
									+ "0&address=&line=&specialarea=00&from=&welfare=";
	
	
	private Site site = Site.me()
						.setCharset("gbk")  //设置解析编码格式
						.setTimeOut(10000) //设置超时时间
						.setRetrySleepTime(3000) //设置重试时间
						.setRetryTimes(3);//设置重置次数
	@Override
	public Site getSite() {
	
		return site;
	}

	@Override
	public void process(Page page) {
		List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
		
		if(list.size() == 0) {
			//为空为详情页 解析数据.
			this.saveJobInfo(page);
		}else {
			//不为空 为列表页
			for(Selectable selectable:list) {
				String jobInfourl = selectable.links().toString();
				page.addTargetRequest(jobInfourl);
			}
			//获取下一页的url
			String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
			page.addTargetRequest(bkUrl);
		}

	}
	//保存数据信息
	private synchronized void saveJobInfo(Page page) {
		String[] s1 =new String[5];
		String[] s2 =new String[5];
		//招聘详情对象
		JobInfo jobinfo = new JobInfo();
		
		//解析页面
		Html html = page.getHtml();
		
		
		//获取数据，封装到对象中
		
				//用正则获取学历，经验，城市，发布时间
		String s = html.css("div.cn p.msg","text").toString();
		Pattern p = Pattern.compile("([\\u4e00-\\u9fa5_a-zA-Z0-9-]+)");	
		Matcher m = p.matcher(s);		
		//加入线程锁，否则会出现学历为召几人的情况
		synchronized (this) {
			for(int i = 0; i<5;i++) {
				if(m.find()) {
					s1[i] = m.group(1);
					s2[i] = s1[i];
					
				}
			}
			//如果学历要求的内容没有，则显示学历不限的字样
			if(s1[4]==null) {
				s2[4] = s1[3];
				s2[2] = "学历不限";
		}	
		}
		 	
		//获取公司标签
		String sc = html.css("div.jtag span","text").all().toString();
		//获取公司地址
		String sca = Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text();
		//格式化时间
		String settime = s2[4].substring(0, s2[4].length()-2);
		
		jobinfo.setCity(s2[0]);
		jobinfo.setUrl(page.getUrl().toString());
		jobinfo.setSalary(html.css("div.cn strong","text").toString());
		jobinfo.setWebsite("51job");
		jobinfo.setDetail(Jsoup.parse(html.css("div.job_msg").toString()).text());
		jobinfo.setJobname(html.css("div.cn h1","text").toString());
		jobinfo.setCompanyname(html.css("div.cn p.cname a","text").toString());
		//格式公司地址  去掉“上班地址  地图”的字
		jobinfo.setCompanyaddress(sca.substring(5, sca.length()-3));
		//格式公司标签  去掉“[]”
		jobinfo.setCompanylablelist( sc.substring(1, sc.length()-1));
		jobinfo.setCreatetime(settime);
		jobinfo.setEducation(s2[2]);
		jobinfo.setExperience(s2[1]);
		//使用uuid工具类生成64位的uuid随机数作为id
		jobinfo.setId(UUIDutils.getUUID64());
	
		//保存结果
		page.putField("jobInfo",jobinfo);
	}
	
	@Autowired
	private DataOutput dataoutput;
	
	@Scheduled(initialDelay = 1000,fixedDelay = 100000)
	public void process() {
		Spider.create(new JobWork())
			   .addUrl(url)	//执行url地址
			   .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000))) //使用bloom过滤器 过滤网页
			   .thread(50) //添加线程数
			   .addPipeline(this.dataoutput) //保存结果
			   .run();  //执行
		
	}
}
