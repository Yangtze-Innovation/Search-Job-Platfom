package com.couragehe.souzhi.crawler.task;

import java.util.regex.Pattern;


import com.couragehe.souzhi.crawler.task.website.Job51Spider;
import com.couragehe.souzhi.crawler.task.website.LagouSpider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
/**
 * 页面持久化处理
 */
@Component("WebsitePipeline")
public class WebsitePipeline implements Pipeline {
	//拉勾爬虫封装类
	@Autowired
	private LagouSpider lagouSpider;
	//51Job爬虫封装类
	@Autowired
	Job51Spider job51Spider;
	public void process(ResultItems resultItems, Task task) {
		String url = resultItems.get("url");
		//村塾拉勾数据
		 if(Pattern.matches(".*positionAjax.*", url)) {
			 lagouSpider.jobInfoSave(resultItems);
		 }else if(url.indexOf(".html") != -1){
			 job51Spider.jobInfoSave(resultItems);
		 }

	}


}
