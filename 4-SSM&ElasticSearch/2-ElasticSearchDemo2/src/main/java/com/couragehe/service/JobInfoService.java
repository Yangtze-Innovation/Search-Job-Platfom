package com.couragehe.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.couragehe.entity.JobInfo;

public interface JobInfoService {
	/**
	 * 保存工作信息
	 * 
	 * @param jobInfo
	 */
	public void save(JobInfo jobInfo);
	
	/**
	 * 根据条件查询工作信息
	 * @param jobInfo
	 * @return
	 */
	public List<JobInfo> findJobInfo(JobInfo jobInfo);
	
	/**
	 * 分页查询数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<JobInfo> findJobInfoByPage(int page,int rows);

}
