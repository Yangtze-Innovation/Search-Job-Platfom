package com.couragehe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
