package com.iluwatar.cleanArchitecture;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
  private final List<Order> orders = new ArrayList<>();

  @Override
  public void saveOrder(Order order) {
    orders.add(order);
  }
}
