package com.couragehe.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.couragehe.pojo.Item;
@Repository
public interface JobRepository extends ElasticsearchRepository<Item,Integer>{
	List<Item> findByJobnameAndContent(String Jobname, String Content);
	
	List<Item> findByJobnameOrContent(String Jobname, String Content);
	
	Page<Item> findByJobnameAndContentAndIdBetween(String Jobname, String Content, int min, int max, Pageable pageable);
}
