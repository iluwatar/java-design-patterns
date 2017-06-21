package com.iluwatar.cqrs.dto;

public class AuthorDTO {

  private String name;
  private String email;
  private String username;

  public AuthorDTO(String name, String email, String username) {
    super();
    this.name = name;
    this.email = email;
    this.username = username;
  }

  public AuthorDTO() {
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
