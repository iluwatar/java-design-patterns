package com.iluwatar.tupletable;

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

  public long getMemberNumber() {
    return memberNumber;
  }

  public void setMemberNumber(long newMembernumber) {
    this.memberNumber = newMembernumber;
  }

  public String getFirstname() {
    return firstName;
  }

  public void setFirstname(String newFirstname) {
    this.firstName = newFirstname;
  }

  public String getLastname() {
    return lastName;
  }

  public void setLastname(String newLastname) {
    this.lastName = newLastname;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String newAddress1) {
    this.address1 = newAddress1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String newAddress2) {
    this.address2 = newAddress2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String newCity) {
    this.city = newCity;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String newZip) {
    this.zip = newZip;
  }

  public String getState() {
    return state;
  }

  public void setState(String newState) {
    this.state = newState;
  }

  public Long getFreePasses() {
    return freePasses;
  }

  public void setFreePasses(Long passes) {
    this.freePasses = passes;
  }
}
