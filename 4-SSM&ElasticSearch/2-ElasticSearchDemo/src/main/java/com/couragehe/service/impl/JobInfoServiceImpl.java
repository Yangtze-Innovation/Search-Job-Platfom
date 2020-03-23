package com.couragehe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.couragehe.dao.JobInfoDao;
import com.couragehe.entity.JobInfo;
import com.couragehe.service.JobInfoService;

@Service
public class JobInfoServiceImpl implements JobInfoService{
	@Autowired
	private JobInfoDao jobInfoDao;

	public void save(JobInfo jobInfo) {
		
	}

	public List<JobInfo> findJobInfo(JobInfo jobInfo) {
		return null;
	}
	public List<JobInfo> findJobInfoByPage(int page, int rows) {
		List<JobInfo> jobInfos = this.jobInfoDao.findAll(page,rows);
		
		return jobInfos;
	}

}
