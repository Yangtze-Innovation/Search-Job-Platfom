package com.couragehe.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.couragehe.entity.JobInfoField;

@Component
public interface JobRepository extends ElasticsearchRepository<JobInfoField,String> {

}
