package com.couragehe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.couragehe.entity.JobInfo;
import com.couragehe.util.DBUtils;
import com.couragehe.util.UUIDUtils;

public class JobPositionDao {
	public static long count=0;
	public static void addJobData(JobInfo job) {
		Connection conn = DBUtils.getConnection();
		String sql = "INSERT INTO job_data VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			System.out.println("存入第"+(count++)+"条数据！");
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
}
