package com.couragehe.dao;

import java.awt.print.Pageable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.couragehe.entity.JobInfo;
import com.couragehe.utils.DBUtils;
import com.couragehe.utils.UUIDUtils;

@Repository
public class JobInfoDao {

	public static void addJobData(JobInfo job) {
		Connection conn = DBUtils.getConnection();
		String sql = "INSERT INTO job_data2 VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job.getId());
			pstmt.setString(2, job.getJobname());
			pstmt.setString(3, job.getSalary());
			pstmt.setString(4, job.getCity());
			pstmt.setString(5, job.getExperience());
			pstmt.setString(6, job.getEducation());
			pstmt.setString(7, job.getCompanylablelist());
			pstmt.setString(8, job.getCompanyname());
			pstmt.setString(9, job.getCompanyaddress());
			pstmt.setString(10, job.getCreatetime());
			pstmt.setString(11, job.getUrl());
			pstmt.setString(12, job.getDetail());
			pstmt.setString(13, job.getWebsite());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(conn);			
		}
	}
	public static void main(String[]args) {
		JobInfo job = new JobInfo();
		job.setId(UUIDUtils.getUUID64());
		job.setJobname("Java工程师");
		System.out.println("执行完毕");
		addJobData(job);
	}
	public List<JobInfo> findAll(int page,int rows){
		List<JobInfo> list = new ArrayList<JobInfo>();
		
		Connection conn  = DBUtils.getConnection();
		String sql = "select jobname from job_data2 limit ?,?";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.setInt(2, rows);
			ResultSet  rs = pstmt.executeQuery();
			while(rs.next()) {
				JobInfo jobInfo = new JobInfo();
				job.setId(UUIDUtils.getUUID64());
				job.setJobname(jobname.get(i));
				job.setSalary(salary.get(i));
				job.setCity(city.get(i));
				job.setExperience(experience.get(i));
				job.setEducation(education.get(i));
				job.setCompanylablelist(companylablelist.get(i));
				job.setCompanyname(companyname.get(i));
				job.setCreatetime(createtime.get(i));
				job.setCompanyaddress(companyaddress.get(i));
				job.setUrl(url);
				job.setDetail(detail);
				job.setWebsite("拉勾");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
}
