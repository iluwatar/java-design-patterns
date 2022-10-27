package com.iluwatar.remotefacade.domain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
/**
 * {@link Customer } is an object with customer details.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Customer {
  private String name;
  private String phone;
  private String address;

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
