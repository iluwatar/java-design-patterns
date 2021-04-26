package com.iluwatar.queryobject;

import java.util.Collection;

/**
 * This class represents the model of customer's order rows stored in a table in DBMS. It has
 * a list of its details. The one-to-many relationship between this class might be most probably
 * implemented in the table of order details, as how the similar situation is illustrated in
 * {@link Customer} class.
 */
public class CustomerOrder {
  public final Collection<CustomerOrderDetail> orderDetails;

  public CustomerOrder(Collection<CustomerOrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }
}
