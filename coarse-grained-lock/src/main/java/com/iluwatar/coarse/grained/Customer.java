package com.iluwatar.coarse.grained;

/**
 * Represents a customer with a unique ID and a name.
 * Provides methods to access and modify customer attributes.
 */
public class Customer {

  /** The name of the customer. */
  private String name;

  /** The unique identifier for the customer. */
  private int customerId;

  /**
   * Constructs a {@code Customer} object with the specified ID and name.
   *
   * @param customerId the unique ID of the customer
   * @param name       the name of the customer
   */
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
