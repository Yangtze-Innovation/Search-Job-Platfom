package com.couragehe.task;

import java.util.List;
import java.util.regex.Pattern;


import com.couragehe.dao.JobInfoDao;
import com.couragehe.entity.JobInfo;
import com.couragehe.task.website.LagouSpider;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
/**
 * 页面持久化处理
 */
public class WebsitePipeline implements Pipeline {

	public void process(ResultItems resultItems, Task task) {
		String url = resultItems.get("url");
		//村塾拉勾数据
		 if(Pattern.matches(".*positionAjax.*", url)) {
			 LagouSpider.lagouSave(resultItems);
		 }

	}


}
