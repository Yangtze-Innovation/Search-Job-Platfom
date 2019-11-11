package com.couragehe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couragehe.dao.JobRepository;
import com.couragehe.entity.JobInfoField;
import com.couragehe.service.JobRepositoryService;

@Service
public class JobRepositoryServiceImpl implements JobRepositoryService {
	@Autowired
	private  JobRepository jobRepository;
	
	
	public void save(JobInfoField jobInfoField) {
		this.jobRepository.save(jobInfoField);
	}

	public void saveAll(List<JobInfoField> list) {
		this.jobRepository.saveAll(list);
	}

}
