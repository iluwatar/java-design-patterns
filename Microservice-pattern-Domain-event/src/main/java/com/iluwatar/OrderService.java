package com.iluwatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderCreatedListener orderCreatedListener;

  @Autowired
  public OrderService(OrderCreatedListener orderCreatedListener) {
    this.orderCreatedListener = orderCreatedListener;
  }

  public void createOrder(String orderId) {
    OrderCreatedEvent event = new OrderCreatedEvent(orderId);
    orderCreatedListener.handleOrderCreatedEvent(event);
  }
}
