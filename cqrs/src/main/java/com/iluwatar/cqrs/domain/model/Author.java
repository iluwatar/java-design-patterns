package com.iluwatar.cqrs.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This is an Author entity. It is used by Hibernate for persistence.
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
   *          username of the author
   * @param name
   *          name of the author
   * @param email
   *          email of the author
   */
  public Author(String username, String name, String email) {
    super();
    this.username = username;
    this.name = name;
    this.email = email;
  }

  protected Author() {
    super();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Author [name=" + name + ", email=" + email + "]";
  }

}
