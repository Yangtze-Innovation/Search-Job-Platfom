package com.couragehe.entity;

import java.sql.Date;

public class JobInfo {
	private String id; 			 
	private String jobname; 					 
	private String salary;					 
	private String city;						 
	private String experience;			 	 	  
	private String education;					 
	private String companylablelist; 	  		  
	private String companyname;  			     
	private String companyaddress; 		  	 
	private String createtime;
	private String url;                   		
	private String detail;
	private String website;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCompanylablelist() {
		return companylablelist;
	}
	public void setCompanylablelist(String companylablelist) {
		this.companylablelist = companylablelist;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanyaddress() {
		return companyaddress;
	}
	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String  createtime) {
		this.createtime = createtime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	@Override
	public String toString() {
		return "JobInfo [id=" + id + ", jobname=" + jobname + ", salary=" + salary + ", city=" + city + ", experience="
				+ experience + ", education=" + education + ", companylablelist=" + companylablelist + ", companyname="
				+ companyname + ", companyaddress=" + companyaddress + ", createtime=" + createtime + ", url=" + url
				+ ", detail=" + detail + ", website=" + website + "]";
	}
	
	
}
