/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.order.microservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

/**
 * Service to handle order processing logic.
 */
@Slf4j
@Service
public class OrderService {

  private final RestTemplateBuilder restTemplateBuilder;

  /**
   * Constructor to inject RestTemplateBuilder.
   *
   * @param restTemplateBuilder the RestTemplateBuilder to build RestTemplate instances
   */
  public OrderService(final RestTemplateBuilder restTemplateBuilder) {
    this.restTemplateBuilder = restTemplateBuilder;
  }

  /**
   * Processes an order by calling
   * {@link OrderService#validateProduct()} and
   * {@link OrderService#processPayment()}.
   *
   * @return A string indicating whether the order was processed successfully or failed.
   */
  public String processOrder() {
    if (validateProduct() && processPayment()) {
      return "Order processed successfully";
    }
    return "Order processing failed";
  }

  /**
   * Validates the product by calling the respective microservice.
   *
   * @return true if the product is valid, false otherwise.
   */
  Boolean validateProduct() {
    try {
      ResponseEntity<Boolean> productValidationResult = restTemplateBuilder
          .build()
          .postForEntity("http://localhost:30302/product/validate", "validating product",
              Boolean.class);
      LOGGER.info("Product validation result: {}", productValidationResult.getBody());
      return productValidationResult.getBody();
    } catch (ResourceAccessException | HttpClientErrorException e) {
      LOGGER.error("Error communicating with product service: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Validates the product by calling the respective microservice.
   *
   * @return true if the product is valid, false otherwise.
   */
  Boolean processPayment() {
    try {
      ResponseEntity<Boolean> paymentProcessResult = restTemplateBuilder
          .build()
          .postForEntity("http://localhost:30301/payment/process", "processing payment",
              Boolean.class);
      LOGGER.info("Payment processing result: {}", paymentProcessResult.getBody());
      return paymentProcessResult.getBody();
    } catch (ResourceAccessException | HttpClientErrorException e) {
      LOGGER.error("Error communicating with payment service: {}", e.getMessage());
      return false;
    }
  }
}
