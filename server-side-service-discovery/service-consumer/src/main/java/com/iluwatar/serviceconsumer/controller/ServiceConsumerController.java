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

package com.iluwatar.serviceconsumer.controller;

import com.iluwatar.serviceconsumer.service.ServiceDiscoveryService;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller that demonstrates service discovery and consumption.
 * This controller shows how to discover services and make calls to them
 * using the Server-Side Service Discovery pattern.
 */
@RestController
@RequestMapping("/api")
public class ServiceConsumerController {

  private final ServiceDiscoveryService serviceDiscoveryService;

  @Autowired
  public ServiceConsumerController(ServiceDiscoveryService serviceDiscoveryService) {
    this.serviceDiscoveryService = serviceDiscoveryService;
  }

  /**
   * Discover all available services.
   *
   * @return list of all registered services
   */
  @GetMapping("/services")
  public ResponseEntity<List<String>> discoverAllServices() {
    List<String> services = serviceDiscoveryService.getAllServices();
    return ResponseEntity.ok(services);
  }

  /**
   * Discover instances of a specific service.
   *
   * @param serviceName the name of the service
   * @return list of service instances
   */
  @GetMapping("/services/{serviceName}/instances")
  public ResponseEntity<List<InstanceInfo>> discoverServiceInstances(@PathVariable("serviceName") String serviceName) {
    List<InstanceInfo> instances = serviceDiscoveryService.discoverService(serviceName);
    return ResponseEntity.ok(instances);
  }

  /**
   * Check if a service is available.
   *
   * @param serviceName the name of the service
   * @return service availability status
   */
  @GetMapping("/services/{serviceName}/availability")
  public ResponseEntity<Map<String, Object>> checkServiceAvailability(@PathVariable("serviceName") String serviceName) {
    boolean available = serviceDiscoveryService.isServiceAvailable(serviceName);
    Map<String, Object> response = new HashMap<>();
    response.put("serviceName", serviceName);
    response.put("available", available);
    response.put("timestamp", System.currentTimeMillis());
    return ResponseEntity.ok(response);
  }

  /**
   * Get health status of a service.
   *
   * @param serviceName the name of the service
   * @return health status response
   */
  @GetMapping("/services/{serviceName}/health")
  public ResponseEntity<String> getServiceHealth(@PathVariable("serviceName") String serviceName) {
    String health = serviceDiscoveryService.getServiceHealth(serviceName);
    return ResponseEntity.ok(health);
  }

  /**
   * Call a specific endpoint on a service through service discovery.
   *
   * @param serviceName the name of the service
   * @param endpoint    the endpoint to call
   * @return response from the service
   */
  @GetMapping("/call/{serviceName}")
  public ResponseEntity<String> callService(@PathVariable("serviceName") String serviceName, @RequestParam String endpoint) {
    String response = serviceDiscoveryService.callService(serviceName, endpoint);
    return ResponseEntity.ok(response);
  }

  /**
   * Get all products from product service using service discovery.
   *
   * @return products from product service
   */
  @GetMapping("/products")
  public ResponseEntity<String> getProducts() {
    String response = serviceDiscoveryService.callService("product-service", "/products");
    return ResponseEntity.ok(response);
  }

  /**
   * Get all orders from order service using service discovery.
   *
   * @return orders from order service
   */
  @GetMapping("/orders")
  public ResponseEntity<String> getOrders() {
    String response = serviceDiscoveryService.callService("order-service", "/orders");
    return ResponseEntity.ok(response);
  }

  /**
   * Health check endpoint for the service consumer itself.
   *
   * @return service status
   */
  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Service Consumer is running!");
  }
}
