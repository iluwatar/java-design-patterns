package com.iluwatar.queryobject;

import java.util.function.Predicate;

/**
 * {@link CustomerSalesWithPurchaseMoreThan} implements {@link QueryObject} interface directly,
 * representing a certain type of query. In this case, the query's criterion is whether one's
 * total spending according to all its orders is larger than a given amount.
 */
public class CustomerSalesWithPurchaseMoreThan implements QueryObject<Customer> {
  public final double amount;

  public CustomerSalesWithPurchaseMoreThan(double amount) {
    this.amount = amount;
  }

  @Override
  public Predicate<Customer> query() {
    return customer -> customer.orders.stream().mapToDouble(
        customerOrder -> customerOrder.orderDetails.stream().mapToDouble(
            orderDetail -> orderDetail.unitPrice * orderDetail.quantity
        ).sum()
    ).sum() > amount;
  }
}