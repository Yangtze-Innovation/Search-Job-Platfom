package com.couragehe.webmagic;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class LagouSavePipeline implements Pipeline {

	public void process(ResultItems resultItems, Task task) {
		List<String> userIds = resultItems.get("userIds");
		if (CollectionUtils.isNotEmpty(userIds)) {
	         for (String userId: userIds) {
	        	 System.out.println(userId);
	         }
		}
	}

}
