package com.couragehe.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.couragehe.entity.JobInfoField;

@Component
public interface JobRepository extends ElasticsearchRepository<JobInfoField,String> {
	public Page<JobInfoField> findByCityAndJobnameAndDetail(String city,String keyword,String keyword1,Pageable pageable);

}
