package com.couragehe.spider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.couragehe.dao.JobPositionDao;
import com.couragehe.entity.JobInfo;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class JobCollectSavePipeline implements Pipeline {
	

	public void process(ResultItems resultItems, Task task) {
		JobInfo job= resultItems.get("JobInfo");
		System.out.println(job);
		if(job.getJobname()!=null) {
			JobPositionDao.addJobData(job);
		}
	}
	
}
