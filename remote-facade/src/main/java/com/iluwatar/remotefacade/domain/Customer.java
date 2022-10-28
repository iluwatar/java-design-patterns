package com.iluwatar.remotefacade.domain;
import lombok.Getter;
/**
 * {@link Customer } is an object with customer details.
 */
@Getter
public class Customer {
  private final String name;
  private final String phone;
  private final String address;

  /**
  * Customer details.
  *
   * @param customername .
   * @param customerphone .
   * @param customeradress .
   */
  public Customer(String customername, String customerphone, String customeradress) {
    name = customername;
    phone = customerphone;
    address = customeradress;
  }
}
