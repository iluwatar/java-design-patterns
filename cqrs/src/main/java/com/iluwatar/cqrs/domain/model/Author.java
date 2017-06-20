package com.iluwatar.cqrs.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Sabiq Ihab
 *
 */
@Entity
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String username;
  private String name;
  private String email;

  /**
   * 
   * @param username
   * @param name
   * @param email
   */
  public Author(String username, String name, String email) {
    super();
    this.username = username;
    this.name = name;
    this.email = email;
  }

  public Author() {
    super();
  }

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "Author [name=" + name + ", email=" + email + "]";
  }

}
