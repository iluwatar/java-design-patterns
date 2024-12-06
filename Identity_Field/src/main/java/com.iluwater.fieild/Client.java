package com.iluwater.fieild;

public class Client extends DomainObject{
  private String name;
  private String Email;


  public Client(String name,String Email) {
    this.name = name;
    this.Email=Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return Email;
  }

}
