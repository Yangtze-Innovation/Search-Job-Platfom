package com.couragehe.souzhi.beans;


import javax.persistence.Id;

public class Company {
  @Id
  private String id;
  private String companyName;
  private String companyLogo;
  private String companySize;
  private String industryField;
  private String financeStage;
  private String companyLabelList;

  public Company() {
  }

  public Company(String id, String companyName, String companyLogo, String companySize, String industryField, String financeStage, String companyLabelList) {
    this.id = id;
    this.companyName = companyName;
    this.companyLogo = companyLogo;
    this.companySize = companySize;
    this.industryField = industryField;
    this.financeStage = financeStage;
    this.companyLabelList = companyLabelList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyLogo() {
    return companyLogo;
  }

  public void setCompanyLogo(String companyLogo) {
    this.companyLogo = companyLogo;
  }

  public String getCompanySize() {
    return companySize;
  }

  public void setCompanySize(String companySize) {
    this.companySize = companySize;
  }

  public String getIndustryField() {
    return industryField;
  }

  public void setIndustryField(String industryField) {
    this.industryField = industryField;
  }

  public String getFinanceStage() {
    return financeStage;
  }

  public void setFinanceStage(String financeStage) {
    this.financeStage = financeStage;
  }

  public String getCompanyLabelList() {
    return companyLabelList;
  }

  public void setCompanyLabelList(String companyLabelList) {
    this.companyLabelList = companyLabelList;
  }
}
