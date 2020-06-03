package com.imengyu.datacenter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert(true)
@Table(name = "t_device")
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String markName;

  private Integer productId;
  private Integer groupId;

  private Integer enableState;
  private Integer nowState;

  private Date lastUpTime;
  private Date createAt;

  private Integer authType;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String authSecretKey;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMarkName() {
    return markName;
  }

  public void setMarkName(String markName) {
    this.markName = markName;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public Integer getEnableState() {
    return enableState;
  }

  public void setEnableState(Integer enableState) {
    this.enableState = enableState;
  }

  public Integer getNowState() {
    return nowState;
  }

  public void setNowState(Integer nowState) {
    this.nowState = nowState;
  }

  public Date getLastUpTime() {
    return lastUpTime;
  }

  public void setLastUpTime(Date lastUpTime) {
    this.lastUpTime = lastUpTime;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Integer getAuthType() {
    return authType;
  }

  public void setAuthType(Integer authType) {
    this.authType = authType;
  }

  public String getAuthSecretKey() {
    return authSecretKey;
  }

  public void setAuthSecretKey(String authSecretKey) {
    this.authSecretKey = authSecretKey;
  }
}
