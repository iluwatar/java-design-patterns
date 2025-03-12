package com.iluwatar.coarse.grained;

/**
 * Represents an address with attributes customerId, addressId, and city.
 * This class associates a customer's address with a unique ID and provides
 * getter and setter methods to manage its attributes.
 */
public class Address {

  /** Unique identifier for the address. */
  private int addressId;

  /** Identifier of the customer associated with the address. */
  private int customerId;

  /** The city where the address is located. */
  private String city;

  /**
   * Constructs an {@code Address} object with the specified customer ID, address ID, and city.
   *
   * @param customerId the ID of the customer
   * @param addressId  the unique ID of the address
   * @param city       the city of the address
   */
  public Address(int customerId, int addressId, String city) {
    this.customerId = customerId;
    this.addressId = addressId;
    this.city = city;
  }

  /**
   * Returns the unique address ID.
   *
   * @return the address ID
   */
  public int getAddressId() {
    return addressId;
  }

  /**
   * Updates the address ID with the specified value.
   *
   * @param addressId the new address ID
   */
  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  /**
   * Returns the ID of the customer associated with the address.
   *
   * @return the customer ID
   */
  public int getCustomerId() {
    return customerId;
  }

  /**
   * Updates the customer ID with the specified value.
   *
   * @param customerId the new customer ID
   */
  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  /**
   * Returns the city where the address is located.
   *
   * @return the city name
   */
  public String getCity() {
    return city;
  }

  /**
   * Updates the city name with the specified value.
   *
   * @param city the new city name
   */
  public void setCity(String city) {
    this.city = city;
  }
}
