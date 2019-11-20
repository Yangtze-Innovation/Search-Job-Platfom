package com.couragehe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couragehe.entity.JobResult;
import com.couragehe.service.JobRepositoryService;

/**
 * 注解RestController 包含了二个注解
 * @ Controller （定义控件）
 * @ ResponseBody（定义返回值格式）
 * 
 * salary: 
	page:1
	jobaddr:北京
	keyword: java
	Request url：http://127.0.0.1:8000/search.do
	Request Method POST
 * @author CourageHe
 *
 */
@RestController
public class SearchController {
	
	@Autowired
	private JobRepositoryService jobRepositoryService;
	
	/**
	 * 根据条件分页查询招聘信息
	 * @param salary
	 * @param jobaddr
	 * @param keyword
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "search.do",method = RequestMethod.POST)
	public JobResult search(String salary,String jobaddr,String keyword,Integer page) {

		 JobResult jobResult = this.jobRepositoryService.search("","*",keyword,1);
		 return jobResult;
	}
}
