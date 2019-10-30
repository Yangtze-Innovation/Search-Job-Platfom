package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.pojo.JobInfo;
@Component
public interface JobInfoDao extends JpaRepository<JobInfo,String> {

}
