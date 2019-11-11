package com.couragehe.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

//index name 必须小写
@Document(indexName = "jobinfo",type="JobInfoField")
public class JobInfoField {
	@Id
	@Field(index = true,store = true,type=FieldType.text)
	private String id; 			
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String jobname; 					 
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String salary;					 
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String city;						 
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String experience;			 	 	  
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String education;					 
	private String companylablelist; 	  		  
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String companyname;  			     
	@Field(index = true,store = true,type=FieldType.text) 
	private String companyaddress; 		  	 
	@Field(index = true,store = true,type=FieldType.text)
	private String createtime;
	@Field(index = true,store = true,type=FieldType.text)
	private String url;                   		
	@Field(index = true,store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart", type=FieldType.text)
	private String detail;
	@Field(index = true,store = true,type=FieldType.text)
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
