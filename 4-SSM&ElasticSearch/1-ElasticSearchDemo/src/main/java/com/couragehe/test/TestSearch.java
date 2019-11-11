package com.couragehe.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	//查询所有数据
	@Test
	public void testFindAll() {
		Iterable<Item> items = this.jobService.findAll();
		for(Item item:items) {
			System.out.println(item);
		}
	}
	//分页查询数据
	@Test 
	public void testFindByPage() {
		Page<Item> items = this.jobService.findByPage(1,10);
		for(Item item:items) {
			System.out.println(item);
		}
	}
	
	//复杂查询
	//根据title和Content进行查询，交集
	@Test
	public void TestFindByJobnameAndContent() {
		List<Item> list = this.jobService.findByJobnameAndContent("11122","11122");
		for(Item item:list) {
			System.out.println(item);
		}
	}
	//根据title和Content进行查询，并集
	@Test
	public void TestFindByJobnameOrContent() {
		List<Item> list = this.jobService.findByJobnameOrContent("11122","11123");
		for(Item item:list) {
			System.out.println(item);
		}		
	}
	//根据title或者content和id的范围 进行分页查询
	@Test
	public void TestFindByJobnameAndContentAndIdBetween() {
		Page<Item> items = this.jobService.findByJobnameAndContentAndIdBetween("Java工程师111","房租减半，水电全免111",12345610, 12345616,1,3);
		for(Item item:items) {
			System.out.println(item);
		}
	}
}
