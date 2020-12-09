package com.iluwatar.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    CustomerRegistry customerRegistry = CustomerRegistry.getInstance();
    var john = new Customer("1", "John");
    customerRegistry.addCustomer(john);

    var julia = new Customer("2", "Julia");
    customerRegistry.addCustomer(julia);

    LOGGER.info("John {}", customerRegistry.getCustomer("1"));
    LOGGER.info("Julia {}", customerRegistry.getCustomer("2"));
  }

}
