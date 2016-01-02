package com.iluwatar.intercepting.filter;

/**
 * Concrete implementation of filter This filter is responsible for checking/filtering the input in
 * the address field.
 * 
 * @author joshzambales
 *
 */
public class AddressFilter extends AbstractFilter {

  @Override
  public String execute(Order order) {
    String result = super.execute(order);
    if (order.getAddress() == null || order.getAddress().isEmpty()) {
      return result + "Invalid address! ";
    } else {
      return result;
    }
  }
}
