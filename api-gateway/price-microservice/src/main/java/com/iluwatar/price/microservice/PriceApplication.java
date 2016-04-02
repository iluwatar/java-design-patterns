package com.iluwatar.price.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PriceApplication starts up Spring Boot, exposing endpoints for the Price microservice through
 * the {@link PriceController}.
 */
@SpringBootApplication
public class PriceApplication {

  /**
   * Microservice entry point
   * @param args
   *          command line args
   */
  public static void main(String[] args) {
    SpringApplication.run(PriceApplication.class, args);
  }
}
