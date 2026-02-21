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

package com.iluwatar.serviceconsumer.service;

import com.netflix.discovery.EurekaClient;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service Discovery Service that demonstrates how to discover and interact with services
 * registered in the Service Registry (Eureka).
 */
@Service
public class ServiceDiscoveryService {

  private final RestTemplate restTemplate;
  private final EurekaClient eurekaClient;

  @Autowired
  public ServiceDiscoveryService(RestTemplate restTemplate, EurekaClient eurekaClient) {
    this.restTemplate = restTemplate;
    this.eurekaClient = eurekaClient;
  }

  /**
   * Discover available instances of a service by service name.
   *
   * @param serviceName the name of the service
   * @return list of service instances
   */
  public List<InstanceInfo> discoverService(String serviceName) {
    return eurekaClient.getInstancesByVipAddress(serviceName, false);
  }

  /**
   * Get service health status by calling the health endpoint.
   *
   * @param serviceName the name of the service
   * @return health status response
   */
  public String getServiceHealth(String serviceName) {
    try {
      String healthUrl = "http://" + serviceName + "/actuator/health";
      return restTemplate.getForObject(healthUrl, String.class);
    } catch (Exception e) {
      return "Service unavailable: " + e.getMessage();
    }
  }

  /**
   * Call a service endpoint using service discovery and load balancing.
   *
   * @param serviceName the name of the service
   * @param endpoint    the endpoint path
   * @return response from the service
   */
  public String callService(String serviceName, String endpoint) {
    try {
      String serviceUrl = "http://" + serviceName + endpoint;
      return restTemplate.getForObject(serviceUrl, String.class);
    } catch (Exception e) {
      return "Error calling service: " + e.getMessage();
    }
  }

  /**
   * Get all registered services from Eureka.
   *
   * @return list of all service names
   */
  public List<String> getAllServices() {
    return eurekaClient.getApplications().getRegisteredApplications().stream()
        .map(app -> app.getName().toLowerCase())
        .toList();
  }

  /**
   * Check if a service is available and healthy.
   *
   * @param serviceName the name of the service
   * @return true if service is available, false otherwise
   */
  public boolean isServiceAvailable(String serviceName) {
    List<InstanceInfo> instances = discoverService(serviceName);
    return !instances.isEmpty() && instances.stream()
        .anyMatch(instance -> instance.getStatus() == InstanceInfo.InstanceStatus.UP);
  }
}
