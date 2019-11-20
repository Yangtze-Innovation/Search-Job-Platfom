package com.couragehe.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couragehe.controller.SearchController;
import com.couragehe.entity.JobInfo;
import com.couragehe.entity.JobInfoField;
import com.couragehe.entity.JobResult;
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
	@Autowired
	private SearchController searchController;
	//创建索引和映射
	@Test
	public void CreateIndex() {
		this.elasticsearchTemplate.createIndex(JobInfoField.class);
		this.elasticsearchTemplate.putMapping(JobInfoField.class);
	}

	@Test
	public void jobInfoData() {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		//声明页码数，从1开始
		int p = 0;
		//声明查询的数据条数
		int pageSize = 0;
		
		do {			
			//从数据库种查询数据
			List<JobInfo> jobInfos = this.jobInfoService.findJobInfoByPage(p,2000);
			
			//声明器存放jobinfoField
			List<JobInfoField> list = new ArrayList<JobInfoField>();
			
			//把查询到的数据封装为jobInfoField
			for(JobInfo jobInfo : jobInfos) {
				//声明一个jobInfoField对象
				JobInfoField jobInfoField = new JobInfoField();
				//封装数据(Bean工具复制)
				BeanUtils.copyProperties(jobInfo,jobInfoField);
				//把准备好数据的对象放到list容器中
				list.add(jobInfoField);
				//输出测试
//				System.out.println(jobInfo);
			}
			//把封装好的是护具保存到索引库中
			this.jobRepositoryService.saveAll(list);
			
 			//获取查询结果集的数据条数(不满500即代表到最后一页)
			pageSize = list.size();
			//起点向后递增
			p+= pageSize;
			System.out.println("查询起点："+p);
		}while(pageSize == 2000);
		
	//	https://blog.csdn.net/derlinchen/article/details/85692525
	}
	@Test
	public void TestController() {
		JobResult list= this.searchController.search("", "*", "java", 1);
		for(JobInfoField job: list.getRows()) {
			System.out.println(job);
			
		}
	}
}
