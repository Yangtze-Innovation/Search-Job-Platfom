package com.service;

import java.util.List;

import com.pojo.JobInfo;


public interface JobInfoService {
		public void save(JobInfo jobInfo);
		public List<JobInfo> findJobinfo(JobInfo jobInfo);
}
