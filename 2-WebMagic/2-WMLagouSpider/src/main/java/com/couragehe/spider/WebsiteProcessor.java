package com.couragehe.spider;

import java.util.List;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.couragehe.util.IOUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
/**
 * 页面处理
 */
public class WebsiteProcessor implements PageProcessor{
	private static int count = 0;
	
	private Site site = Site.me()
						.setRetryTimes(3)
						.setSleepTime(200)
						.setTimeOut(5000)
						.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
	
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		page.putField("url", url);
		System.out.println(++count);
		System.out.println(page.getRawText());
		if(Pattern.matches(".*lagou.*", page.getRequest().getUrl())) {
			this.handleLagouPage(page);
		}	
		//处理拉勾请求得到的数据
		 if(url.indexOf("positionAjax") != -1) {
			 this.parseLagouJob(page);
		 }
	
	}
	public void parseLagouJob(Page page) {
		//解析
		List<String> jobname = new JsonPathSelector("$.content.positionResult.result[*].positionName").selectList(page.getRawText());			 
		List<String> salary = new JsonPathSelector("$.content.positionResult.result[*].salary").selectList(page.getRawText());	
		List<String> city = new JsonPathSelector("$.content.positionResult.result[*].city").selectList(page.getRawText());	
		List<String> experience = new JsonPathSelector("$.content.positionResult.result[*].workYear").selectList(page.getRawText());	
		List<String> education = new JsonPathSelector("$.content.positionResult.result[*].education").selectList(page.getRawText());	
		List<String> companylablelist = new JsonPathSelector("$.content.positionResult.result[*].companyLabelList").selectList(page.getRawText());	
		List<String> companyname = new JsonPathSelector("$.content.positionResult.result[*].companyFullName").selectList(page.getRawText());	
		List<String> companyaddress = new JsonPathSelector("$.content.positionResult.result[*].district").selectList(page.getRawText());	
		List<String> createtime = new JsonPathSelector("$.content.positionResult.result[*].createTime").selectList(page.getRawText());	
		
		//组合URL链接
		List<String> positionId = new JsonPathSelector("$.content.positionResult.result[*].positionId").selectList(page.getRawText());	
		String showId = page.getJson().jsonPath("$.content.showId").get();
		
		//组合职位详情
		List<String> positionAdvantage = new JsonPathSelector("$.content.positionResult.result[*].positionAdvantage").selectList(page.getRawText());	
		List<String> skillLables = new JsonPathSelector("$.content.positionResult.result[*].skillLables").selectList(page.getRawText());	
		
		
		page.putField("jobname", jobname);
		page.putField("salary", salary);
		page.putField("city",city );
		page.putField("experience", experience);
		page.putField("education",education );
		page.putField("companylablelist", companylablelist);
		page.putField("companyname",companyname );
		page.putField("companyaddress",companyaddress );
		page.putField("createtime",createtime );
		page.putField("positionId", positionId);
		page.putField("showId",showId );
		page.putField("positionAdvantage", positionAdvantage);
		page.putField("skillLables",  skillLables);
	}
	//拉勾首页获取关键字，并发送所有关键字的第一的链接
	public void handleLagouPage(Page page){
		 String url = page.getRequest().getUrl();
		 
		//确保是职位ajax请求而且是第一次请求返回的数据
		 if((url.indexOf("positionAjax") != -1)&&url.substring(url.indexOf("num=")+4).equals("1")){
			//第一次ajax后返回json数据获得总数，对所有页面进行请求
			 int num = Integer.parseInt(new JsonPathSelector("$.content.positionResult.totalCount").select(page.getRawText()));
			 //截取关键字
			 String keyword = url.substring(url.indexOf("keyword=")+8,url.indexOf("&num="));
			 //获取页数
			 num = num > 450 ?300 : num/15;
			 for(int i=2;i <= num; i++) {
				 page.addTargetRequest("https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&keyword="+keyword+"&num="+i);
			 }
			 System.out.println("总个数："+num);
		}else if("https://www.lagou.com".equals(page.getRequest().getUrl())) {
			//解析首页关键字、并发起请求
			Document doc = Jsoup.parse(page.getRawText());
			List<String> list = doc.getElementsByClass("menu_sub").select("a").eachText();
			int num = 0;
			IOUtils.writeLinkList(list);
			list = IOUtils.getLinkList();
			//			String keyword = "JAVA";
//			对每个关键字发起第一个请求
			for(String keyword : list) {
//				if(num++ == 10)return ;
				page.addTargetRequest("https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&keyword="+keyword+"&num="+1);
			}
		}
	
	}
	

	public Site getSite() {
		return site;
	}

}
