package com.couragehe.dao;

import java.awt.print.Pageable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.couragehe.entity.JobInfo;
import com.couragehe.utils.DBUtils;
import com.couragehe.utils.UUIDUtils;

@Component
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

	public List<JobInfo> findAll(int page,int rows){
		List<JobInfo> list = new ArrayList<JobInfo>();
		
		Connection conn  = DBUtils.getConnection();
		String sql = "select * from job_data2 limit ?,?";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.setInt(2, rows);
			ResultSet  rs = pstmt.executeQuery();
			while(rs.next()) {
				JobInfo jobInfo = new JobInfo();
				jobInfo.setId(rs.getString("id") );
				jobInfo.setJobname(rs.getString("jobname") );
				jobInfo.setSalary(rs.getString("salary") );
				jobInfo.setCity(rs.getString("city") );
				jobInfo.setExperience(rs.getString("experience") );
				jobInfo.setEducation(rs.getString("education") );
				jobInfo.setCompanylablelist(rs.getString("companylablelist") );
				jobInfo.setCompanyname(rs.getString("companyname") );
				jobInfo.setCreatetime(rs.getString("createtime") );
				jobInfo.setCompanyaddress(rs.getString("companyaddress") );
				jobInfo.setUrl(rs.getString("url"));
				jobInfo.setDetail(rs.getString("detail"));
				jobInfo.setWebsite(rs.getString("website"));
				list.add(jobInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(conn);	
		}
		return list;
	}
	/**
	 * 半夜清除数据库，进行数据更新爬取
	 */
	public void clearTable() {
		String sql = "truncate table job_data2";
		Connection conn = DBUtils.getConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection(conn);			
		}
	}
	public static void main(String[]args) {
		new JobInfoDao().clearTable();
	}
}
