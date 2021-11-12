package com.iluwatar.tupletable;

import lombok.Data;

@Data
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
