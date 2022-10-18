package com.iluwatar.temporalproperty;

/**
 * Class used to represent a customer with a unique id, a name, and a history of address.
 */
public class Customer {
  private final int id;
  private String name;

  AddressHistory addressHistory;

  /**
   * Create new customer.
   *
   * @param id Id of the customer
   * @param name name of the customer
   */
  public Customer(int id, String name) {
    this.id = id;
    this.name = name;
    addressHistory = new AddressHistory();
  }

  /**
   * Set the customers address on the given date to the given address.
   *
   * @param date Date to set
   * @param address address to set
   */
  public void putAddress(String address, SimpleDate date) {
    addressHistory.put(date, address);
  }

  /**
   * Set the customers address today to be the given address.
   *
   * @param address Address to set for today
   */
  public void putAddress(String address) {
    putAddress(address, SimpleDate.getToday());
  }

  /**
   * Returns the address of the customer on given date.
   *
   * @param date Date to check the Customer's address on
   * @return The address of the customer at given date
   */
  public String getAddress(SimpleDate date) throws IllegalStateException {
    return addressHistory.get(date);
  }

  /**
   * Gets the customer's current address.
   *
   * @return The address of the customer today
   */
  public String getAddress() throws IllegalStateException {
    return getAddress(SimpleDate.getToday());
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
