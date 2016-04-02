package com.iluwatar.price.microservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the Price microservice's endpoints
 */
@RestController
public class PriceController {

  /**
   * An endpoint for a user to retrieve a product's price
   * @return A product's price
   */
  @RequestMapping(value = "/price", method = RequestMethod.GET)
  public String getPrice() {
    return "20";
  }
}
