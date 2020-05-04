package com.couragehe.souzhi.bean;


import java.io.Serializable;

public class Position implements Serializable {

  private String id;
  private String positionName;
  private String companyId;
  private String companyName;
  private String skillLables;
  private String createTime;
  private String formatCreateTime;
  private String workCity;
  private String workDistrict;
  private String workSalary;
  private String workYear;
  private String workNature;
  private String educationRequire;
  private String positionAdvantage;
  private int isSchoolJob;
  private String detailUrl;
  private String originWebsite;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }


  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getSkillLables() {
    return skillLables;
  }

  public void setSkillLables(String skillLables) {
    this.skillLables = skillLables;
  }


  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }


  public String getFormatCreateTime() {
    return formatCreateTime;
  }

  public void setFormatCreateTime(String formatCreateTime) {
    this.formatCreateTime = formatCreateTime;
  }


  public String getWorkCity() {
    return workCity;
  }

  public void setWorkCity(String workCity) {
    this.workCity = workCity;
  }


  public String getWorkDistrict() {
    return workDistrict;
  }

  public void setWorkDistrict(String workDistrict) {
    this.workDistrict = workDistrict;
  }


  public String getWorkSalary() {
    return workSalary;
  }

  public void setWorkSalary(String workSalary) {
    this.workSalary = workSalary;
  }


  public String getWorkYear() {
    return workYear;
  }

  public void setWorkYear(String workYear) {
    this.workYear = workYear;
  }


  public String getWorkNature() {
    return workNature;
  }

  public void setWorkNature(String workNature) {
    this.workNature = workNature;
  }


  public String getEducationRequire() {
    return educationRequire;
  }

  public void setEducationRequire(String educationRequire) {
    this.educationRequire = educationRequire;
  }


  public String getPositionAdvantage() {
    return positionAdvantage;
  }

  public void setPositionAdvantage(String positionAdvantage) {
    this.positionAdvantage = positionAdvantage;
  }

  public int getIsSchoolJob() {
    return isSchoolJob;
  }

  public void setIsSchoolJob(int isSchoolJob) {
    this.isSchoolJob = isSchoolJob;
  }

  public String getDetailUrl() {
    return detailUrl;
  }

  public void setDetailUrl(String detailUrl) {
    this.detailUrl = detailUrl;
  }


  public String getOriginWebsite() {
    return originWebsite;
  }

  public void setOriginWebsite(String originWebsite) {
    this.originWebsite = originWebsite;
  }

}
