package com.iluwatar.auditlog;

/**
 * Class used to represent a customer with a unique id, a name, and an address.
 */
public class Customer {
  private String address;
  private String name;
  private final int id;

  public Customer(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public void setAddress(String address, SimpleDate changeDate) {
    AuditLog.log(changeDate, this, "change of address", this.address, address);
    this.address = address;
  }

  public void setName(String name, SimpleDate changeDate) {
    AuditLog.log(changeDate, this, "change of name", this.name, name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }
}
