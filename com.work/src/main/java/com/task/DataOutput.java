package com.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pojo.JobInfo;
import com.service.JobInfoService;


import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class DataOutput implements Pipeline{

	@Autowired
	private JobInfoService jobInfoservice;
	
	@Override
	public void process(ResultItems resultTtems, Task task) {
		//获取封装好的数据
		JobInfo jobinfo = resultTtems.get("jobInfo");
		
		if(jobinfo !=null) {
			this.jobInfoservice.save(jobinfo);
			
		}
	}

}
