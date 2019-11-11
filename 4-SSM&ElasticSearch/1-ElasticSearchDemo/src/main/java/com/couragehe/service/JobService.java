package com.couragehe.service;


import java.util.List;

import javax.swing.border.TitledBorder;

import org.springframework.data.domain.Page;

import com.couragehe.pojo.Item;


public interface JobService {
	//新增
	public void save(Item item);
	//删除
	public void delete(Item item);
	//批量保存
	public void saveAll(List<Item> list);
	//查找所有
	public Iterable<Item> findAll();
	//分页查询
	public Page<Item> findByPage(int page,int rows);
	//根据title和Content进行查询
	//自定义方法 复杂查询
	public List<Item>findByJobnameAndContent(String Jobname,String Content);
	
	public List<Item>findByJobnameOrContent(String Jobname,String Content);
	
	public Page<Item> findByJobnameAndContentAndIdBetween(String Jobname,String Content,int min,int max,int page,int rows);
}
