package com.couragehe.service.impl;

import java.awt.ItemSelectable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.couragehe.dao.JobRepository;
import com.couragehe.pojo.Item;
import com.couragehe.service.JobService;

@Service("jobService")
public class JobServiceImpl implements JobService{
	@Autowired
	private JobRepository jobRepository;
	public void save(Item item) {
		this.jobRepository.save(item);
		
	}
	public void delete(Item item) {
		this.jobRepository.delete(item);
		
	}
	public void saveAll(List<Item> list) {
		this.jobRepository.saveAll(list);
	}
	public Iterable<Item> findAll() {
		Iterable<Item> items = this.jobRepository.findAll();
		return items;
	}

	public Page<Item> findByPage(int page, int rows) {
		Page<Item>  items =this.jobRepository.findAll(PageRequest.of(page, rows));
		return items;
	}
	public List<Item> findByJobnameAndContent(String Jobname, String Content) {
		List<Item> list = this.jobRepository.findByJobnameAndContent(Jobname, Content);
		return list;
	}
	public List<Item> findByJobnameOrContent(String Jobname, String Content) {
		List<Item> list = this.jobRepository.findByJobnameOrContent(Jobname, Content);
		return list;
	}
	public Page<Item> findByJobnameAndContentAndIdBetween(String Jobname, String Content, int min, int max, int page,int rows) {
		Page<Item> items = this.jobRepository.findByJobnameAndContentAndIdBetween(Jobname,Content,  min, max, PageRequest.of(page-1, rows)) ;
		return items;
	}
}
