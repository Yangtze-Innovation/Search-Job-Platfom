package com.couragehe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.couragehe.dao.JobRepository;
import com.couragehe.entity.JobInfoField;
import com.couragehe.entity.JobResult;
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

	public JobResult search(String salary, String jobaddr, String keyword, Integer page) {
		//解析参数薪资
		String[]salarys = salary.split("-");
		
		//获取最低薪资
		
		//获取最高薪资
		
		
		//判断工作地点是否为空
		if("".equals(jobaddr.trim())) {
			//如果为空则设置为*
			jobaddr="*";
		}
		//判断关键词是否为空
		if("".equals(keyword.trim())) {
			//如果为空则设置为*
			keyword="*";
		}
		
		//调用dao的方法执行查询
		Page<JobInfoField> pages = this.jobRepository.findByCityAndJobnameAndDetail(jobaddr,keyword,keyword,PageRequest.of(page-1, 30));
		//封装结果对象jobResult
		JobResult jobResult = new JobResult();
		//设置结果集
		jobResult.setRows(pages.getContent());
		//设置总页数
		jobResult.setPageTotal(pages.getTotalPages());
		
		return jobResult;
	}

}
