package com.iluwatar.metamapping.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User Entity
 */
@Setter
@Getter
@ToString
public class User{
  private Integer id;
  private String username;
  private String password;

  public User() {}

  /**
   * get a user
   * @param username
   * @param password
   */
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}