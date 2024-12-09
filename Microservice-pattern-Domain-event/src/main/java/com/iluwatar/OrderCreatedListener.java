package com.iluwatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

  private final EventPublisher eventPublisher;

  @Autowired
  public OrderCreatedListener(EventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public void handleOrderCreatedEvent(OrderCreatedEvent event) {
    // Process the event
    System.out.println("Processing order: " + event.getOrderId());
    eventPublisher.publishEvent(event);
  }
}
