package com.iluwatar.metamapping.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User Entity.
 */
@Setter
@Getter
@ToString
public class User {
  private Integer id;
  private String username;
  private String password;

  public User() {}

  /**
   * Get a user.
   * @param username user name
   * @param password user password
   */
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}