package com.iluwater.fieild;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Client extends DomainObject{
  private String name;
  private String Email;

  public Client(String name,String Email) {
    this.name = name;
    this.Email = Email;
  }

}
