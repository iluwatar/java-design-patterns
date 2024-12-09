package com.iluwatar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);

    // Simulate creating an order
    OrderService orderService = SpringContext.getBean(OrderService.class);
    orderService.createOrder("12345");
  }
}
