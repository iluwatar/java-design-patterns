package com.iluwatar.dao;

/**
 * 
 * Customer
 *
 */
public class Customer {

  private int id;
  private String firstName;
  private String lastName;

  /**
   * Constructor
   */
  public Customer(final int id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Customer{" + "id=" + getId() + ", firstName='" + getFirstName() + '\'' + ", lastName='"
        + getLastName() + '\'' + '}';
  }

  @Override
  public boolean equals(final Object o) {
    boolean isEqual = false;
    if (this == o) {
      isEqual = true;
    } else if (o != null && getClass() == o.getClass()) {
      final Customer customer = (Customer) o;
      if (getId() == customer.getId()) {
        isEqual = true;
      }
    }
    return isEqual;
  }

  @Override
  public int hashCode() {
    return getId();
  }
}
