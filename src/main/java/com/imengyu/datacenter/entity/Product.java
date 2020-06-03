package com.imengyu.datacenter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert(true)
@Table(name = "t_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String productKey;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String secretKey;

  private Date createAt;
  private Integer userId;

  private String type;

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

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

  public String getProductKey() {
    return productKey;
  }
  public void setProductKey(String productKey) {
    this.productKey = productKey;
  }

  public String getSecretKey() {
    return secretKey;
  }
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
  public Date getCreateAt() {
    return createAt;
  }
  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }
  public Integer getUserId() {
    return userId;
  }
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}
