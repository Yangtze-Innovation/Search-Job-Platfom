package com.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.JobInfoDao;
import com.pojo.JobInfo;
import com.service.JobInfoService;

@Service
public class JobInfoServiceImpl implements JobInfoService{
	@Autowired
	private JobInfoDao jobInfoDao;

	@Override
	@Transactional
	public void save(JobInfo jobinfo) {
		//根据url和发布时间查询数据
		JobInfo param = new JobInfo();
		param.setUrl(jobinfo.getUrl());
		param.setCreatetime(jobinfo.getCreatetime());
		
		List<JobInfo> list = this.findJobinfo(param);
		
		if(list.size()==0) {
			
			this.jobInfoDao.saveAndFlush(jobinfo);
		}
	}
	@Override
	public List<JobInfo> findJobinfo(JobInfo jobInfo){
		Example<JobInfo> example = Example.of(jobInfo);
		List<JobInfo> list = this.jobInfoDao.findAll(example);
		return list;
	}
}
