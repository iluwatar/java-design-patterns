package com.iluwatar.cqrs.dto;

/**
 * 
 * This is a DTO (Data Transfer Object) author, contains only useful information to be returned
 *
 */
public class Author {

  private String name;
  private String email;
  private String username;

  /**
   * 
   * @param name
   *          name of the author
   * @param email
   *          email of the author
   * @param username
   *          username of the author
   */
  public Author(String name, String email, String username) {
    super();
    this.name = name;
    this.email = email;
    this.username = username;
  }

  public Author() {
    super();
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return "AuthorDTO [name=" + name + ", email=" + email + ", username=" + username + "]";
  }

}
