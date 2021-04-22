package com.iluwatar.queryobject;

import java.util.Collection;

/**
 * This class represents the model of Customer rows stored in a table in DBMS. It has 2 attributes:
 * customer name and a list of its orders. In real database scenarios, instead of holding a list of
 * one's orders in customer table, such one-to-many relationship might most probably implemented
 * in the table of orders, in which each row contains order information and an unique key of
 * its owner customer.
 */
public class Customer {
  public String name;
  public final Collection<CustomerOrder> orders;

  public Customer(String name, Collection<CustomerOrder> orders) {
    this.name = name;
    this.orders = orders;
  }
}
