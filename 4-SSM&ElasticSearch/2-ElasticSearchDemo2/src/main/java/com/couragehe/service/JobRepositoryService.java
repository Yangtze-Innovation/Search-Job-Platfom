package com.couragehe.service;

import java.util.List;

import com.couragehe.entity.JobInfoField;

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
}
