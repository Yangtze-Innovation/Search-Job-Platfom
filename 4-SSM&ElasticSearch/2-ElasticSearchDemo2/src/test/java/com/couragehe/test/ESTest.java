package com.couragehe.test;

import org.elasticsearch.client.ElasticsearchClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couragehe.entity.JobInfo;
import com.couragehe.entity.JobInfoField;
import com.couragehe.service.JobInfoService;
import com.couragehe.service.JobRepositoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ESTest {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private JobRepositoryService jobRepositoryService;
	@Autowired
	private JobInfoService jobInfoService;
	//创建索引和映射
	@Test
	public void CreateIndex() {
		this.elasticsearchTemplate.createIndex(JobInfoField.class);
		this.elasticsearchTemplate.putMapping(JobInfoField.class);
	}
	//
	@Test
	public void jobInfoData() {
		//从数据库种查询数据
		Page<JobInfo> page = this.jobInfoService.findJobInfoByPage(1,500);
		//把查询到的数据封装为jobInfoField
		
		//把封装好的是护具保存到索引库中
	}
	
//	https://blog.csdn.net/derlinchen/article/details/85692525
}
