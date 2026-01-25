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

package com.iluwatar.serviceconsumer.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Demo class to showcase the Server-Side Service Discovery pattern.
 * This class runs automatically when the Service Consumer application starts
 * and demonstrates various aspects of service discovery.
 */
@Component
public class ServerSideServiceDiscoveryDemo implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(ServerSideServiceDiscoveryDemo.class);
  private final RestTemplate restTemplate;

  @Autowired
  public ServerSideServiceDiscoveryDemo(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    // Wait a bit for services to start up and register
    Thread.sleep(5000);

    log.info("=== Server-Side Service Discovery Pattern Demo ===");
    
    demonstrateServiceDiscovery();
    demonstrateLoadBalancing();
    demonstrateHealthChecks();
    
    log.info("=== Demo completed ===");
  }

  /**
   * Demonstrates basic service discovery functionality.
   */
  private void demonstrateServiceDiscovery() {
    log.info("\n1. Demonstrating Service Discovery:");
    
    try {
      // Discover all available services
      String services = restTemplate.getForObject("http://localhost:8080/api/services", String.class);
      log.info("Available services: {}", services);
      
      // Check specific service availability
      String productAvailability = restTemplate.getForObject("http://localhost:8080/api/services/product-service/availability", String.class);
      log.info("Product service availability: {}", productAvailability);
      
      String orderAvailability = restTemplate.getForObject("http://localhost:8080/api/services/order-service/availability", String.class);
      log.info("Order service availability: {}", orderAvailability);
      
    } catch (Exception e) {
      log.error("Error during service discovery demonstration: {}", e.getMessage());
    }
  }

  /**
   * Demonstrates load balancing by making multiple calls to services.
   */
  private void demonstrateLoadBalancing() {
    log.info("\n2. Demonstrating Load Balancing:");
    
    try {
      // Make multiple calls to demonstrate load balancing
      for (int i = 0; i < 3; i++) {
        String products = restTemplate.getForObject("http://localhost:8080/api/products", String.class);
        log.info("Call {} - Products response length: {} characters", i + 1, 
                 products != null ? products.length() : 0);
        
        String orders = restTemplate.getForObject("http://localhost:8080/api/orders", String.class);
        log.info("Call {} - Orders response length: {} characters", i + 1, 
                 orders != null ? orders.length() : 0);
        
        Thread.sleep(1000); // Small delay between calls
      }
    } catch (Exception e) {
      log.error("Error during load balancing demonstration: {}", e.getMessage());
    }
  }

  /**
   * Demonstrates health check functionality.
   */
  private void demonstrateHealthChecks() {
    log.info("\n3. Demonstrating Health Checks:");
    
    try {
      // Check health of individual services
      String productHealth = restTemplate.getForObject("http://localhost:8080/api/services/product-service/health", String.class);
      log.info("Product service health: {}", productHealth);
      
      String orderHealth = restTemplate.getForObject("http://localhost:8080/api/services/order-service/health", String.class);
      log.info("Order service health: {}", orderHealth);
      
      // Check health of the consumer service itself
      String consumerHealth = restTemplate.getForObject("http://localhost:8080/api/health", String.class);
      log.info("Consumer service health: {}", consumerHealth);
      
    } catch (Exception e) {
      log.error("Error during health check demonstration: {}", e.getMessage());
    }
  }
}
