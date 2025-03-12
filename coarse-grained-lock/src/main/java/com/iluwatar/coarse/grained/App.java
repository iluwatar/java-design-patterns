package com.iluwatar.coarse.grained;
/**
 * The coarse grained lock is a pattern designed to handle locking multiple objects at the same time;
 * for instance, a customer can have multiple addresses and wants to change an information regarding them.
 * It makes more buissenes logic to lock both the customer and the addresses in order to avoid any confuison.
 * This reduces concurrent programming but ensures a more simple code that can work accurately.
 */

public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */

  public static void main(String[] args) {
    Lock lock = new Lock();
    Customer customer = new Customer(55, "john");
    Address address1 = new Address(customer.getCustomerId(), 1, "chicago");
    Address address2 = new Address(customer.getCustomerId(), 2, "houston");

    lock.synchronizedMethod(() -> {
      customer.setName("smith");
      address1.setCity("dallas");
      address2.setCity("phoenix");
    });
  }

}