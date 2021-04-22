package com.iluwatar.queryobject;

import java.util.function.Predicate;

/**
 * {@link CustomersWithOrdersAmountMoreThan} implements {@link QueryObject} interface directly,
 * representing a certain type of query. In this case, the query's criterion is whether a
 * customer has made more than a certain number of orders.
 */
public class CustomersWithOrdersAmountMoreThan implements QueryObject<Customer> {
  public final int orderNumber;

  public CustomersWithOrdersAmountMoreThan(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @Override
  public Predicate<Customer> query() {
    return x -> x.orders.size() > orderNumber;
  }
}
