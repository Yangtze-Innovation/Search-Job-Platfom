package com.couragehe.service;

import java.util.List;

import com.couragehe.entity.JobInfoField;
import com.couragehe.entity.JobResult;

public interface JobRepositoryService {
	/**
	 * 保存一条数据
	 * @param jobInfoField
	 */
	public void save(JobInfoField jobInfoField);
	
	/** 
	 * 批量保存数据
	 * @param list
	 */
	public void saveAll(List<JobInfoField> list);
	/**
	 * 根据条件分页查询招聘信息
	 * @param salary
	 * @param jobaddr
	 * @param keyword
	 * @param page
	 * @return
	 */
	public JobResult search(String salary,String jobaddr,String keyword,Integer page);
}
