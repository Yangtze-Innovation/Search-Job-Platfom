package com.couragehe.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.couragehe.pojo.Item;
@Repository
public interface JobRepository extends ElasticsearchRepository<Item,Integer>{
	
}
