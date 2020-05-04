package com.couragehe.souzhi.bean;


import javax.persistence.Id;
import java.io.Serializable;

public class PositionDetail implements Serializable {
  @Id
  private String id;
  private String positionId;
  private String positionDesc;
  private String positionAddress;

  public  PositionDetail (){}
  public PositionDetail(String id, String positionId, String positionDesc, String positionAddress) {
    this.id = id;
    this.positionId = positionId;
    this.positionDesc = positionDesc;
    this.positionAddress = positionAddress;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getPositionId() {
    return positionId;
  }

  public void setPositionId(String positionId) {
    this.positionId = positionId;
  }


  public String getPositionDesc() {
    return positionDesc;
  }

  public void setPositionDesc(String positionDesc) {
    this.positionDesc = positionDesc;
  }


  public String getPositionAddress() {
    return positionAddress;
  }

  public void setPositionAddress(String positionAddress) {
    this.positionAddress = positionAddress;
  }

}
