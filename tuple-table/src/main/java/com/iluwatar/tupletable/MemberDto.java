package com.iluwatar.tupletable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

  private long memberNumber;
  private String firstName;
  private String lastName;
  private String address1;
  private String address2;
  private String city;
  private String zip;
  private String state;
  private long freePasses;
}
