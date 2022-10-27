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
   * @param nm name
   * @param phn phone
   * @param adrs address
   */
  public Customer(String nm, String phn, String adrs) {
    name = nm;
    phone = phn;
    address = adrs;
  }
}
