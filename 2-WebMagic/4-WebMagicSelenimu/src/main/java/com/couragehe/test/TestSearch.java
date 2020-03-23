package com.couragehe.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couragehe.entity.JobInfo;
import com.couragehe.service.JobService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-elasticsearch.xml")
public class TestSearch {
	@Autowired
	private JobService jobService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Before
	public void init() {
		
	}
	//创建索引和映射
	@Test
	public void createIndex() {
		this.elasticsearchTemplate.createIndex(JobInfo.class);
		this.elasticsearchTemplate.putMapping(JobInfo.class);
	}
	//新增
	@Test
	public void TestSave() {
		JobInfo job = new JobInfo();
		job.setId("123456");
		job.setJobname("Java工程师");
		job.setDetail("房租减半，水电全免");
		this.jobService.save(job);
	}
	//修改
	
	//删除
	
	//批量保存
	

}
