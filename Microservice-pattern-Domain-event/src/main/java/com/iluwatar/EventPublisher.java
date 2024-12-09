package com.iluwatar;

import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

  public void publishEvent(OrderCreatedEvent event) {
    // Simulate publishing the event
    System.out.println("Event published: " + event.getOrderId());
  }
}
