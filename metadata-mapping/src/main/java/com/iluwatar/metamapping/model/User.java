package com.iluwatar.metamapping.model;

import java.io.Serializable;

public class User implements Serializable {
  private Integer id;
  private String username;
  private String password;

  private static final long serialVersionUID = 1L;

  public User() {}

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", username=").append(username);
    sb.append(", password=").append(password);
    sb.append("]");
    return sb.toString();
  }
}