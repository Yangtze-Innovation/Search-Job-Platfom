package com.couragehe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couragehe.dao.JobPositionDao;
import com.couragehe.entity.JobInfo;

@Service("jobService ")
public class JobService {
	@Autowired
	private JobPositionDao jobPositionDao;
	public void save(JobInfo jobInfo) {
		this.jobPositionDao.save(jobInfo);
		
	}
}
