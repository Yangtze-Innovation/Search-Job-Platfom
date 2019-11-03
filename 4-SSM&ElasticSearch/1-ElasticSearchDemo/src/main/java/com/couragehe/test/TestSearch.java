package com.couragehe.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couragehe.pojo.Item;
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
		this.elasticsearchTemplate.createIndex(Item.class);
		this.elasticsearchTemplate.putMapping(Item.class);
	}
	//新增
	@Test
	public void TestSave() {
		Item item = new Item();
		item.setId(1234561);
		item.setJobname("Java工程师1");
		item.setContent("房租减半，水电全免1");
		this.jobService.save(item);
	}
	//修改,和新增的代码一样，如果id存在则是修改，不存在就是新增
	@Test
	public void TestUpdata() {
		Item item = new Item();
		item.setId(1234561);
		item.setJobname("Java工程师111");
		item.setContent("房租减半，水电全免111");
		this.jobService.save(item);
	}
	//删除
	@Test
	public void TestDelete() {
		Item item = new Item();
		item.setId(1234561);
		this.jobService.delete(item);
	}
	//批量保存
	
	@Test
	public void TestSaveAll() {
		//创建集合
		List<Item> list = new ArrayList<Item>();
		//封装数据
		for(int i = 0;i<100;i++) {
			Item item = new Item();
			item.setId(1234561+i);
			item.setJobname("Java工程师111"+i);
			item.setContent("房租减半，水电全免111"+i);
			list.add(item);
		}
		//批量保存
		this.jobService.saveAll(list);
	}
	

}
