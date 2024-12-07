package com.iluwatar.coarse.grained;


public class Customer {


  private String name;


  private int customerId;


  public Customer(int customerId, String name) {
    this.customerId = customerId;
    this.name = name;
  }

  /**
   * Returns the name of the customer.
   *
   * @return the customer's name
   */
  public String getName() {
    return name;
  }

  /**
   * Updates the name of the customer.
   *
   * @param name the new name of the customer
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the unique ID of the customer.
   *
   * @return the customer ID
   */
  public int getCustomerId() {
    return customerId;
  }

  /**
   * Updates the unique ID of the customer.
   *
   * @param customerId the new customer ID
   */
  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }
}
