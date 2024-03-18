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
package com.iluwatar.gateway;

import lombok.extern.slf4j.Slf4j;

/**
 * the Gateway design pattern is a structural design pattern that provides a unified interface to a set of
 * interfaces in a subsystem. It involves creating a Gateway interface that serves as a common entry point for
 * interacting with various services, and concrete implementations of this interface for different external services.
 *
 * <p>In this example, GateFactory is the factory class, and it provides a method to create different kinds of external
 * services. ExternalServiceA, B, and C are virtual implementations of the external services. Each service provides its
 * own implementation of the execute() method. The Gateway interface is the common interface for all external services.
 * The App class serves as the main entry point for the application implementing the Gateway design pattern. Through
 * the Gateway interface, the App class could call each service with much less complexity.
 */
@Slf4j
public class App {
  /**
   * Simulate an application calling external services.
   */
  public static void main(String[] args) throws Exception {
    GatewayFactory gatewayFactory = new GatewayFactory();

    // Register different gateways
    gatewayFactory.registerGateway("ServiceA", new ExternalServiceA());
    gatewayFactory.registerGateway("ServiceB", new ExternalServiceB());
    gatewayFactory.registerGateway("ServiceC", new ExternalServiceC());

    // Use an executor service for execution
    Gateway serviceA = gatewayFactory.getGateway("ServiceA");
    Gateway serviceB = gatewayFactory.getGateway("ServiceB");
    Gateway serviceC = gatewayFactory.getGateway("ServiceC");

    // Execute external services
    try {
      serviceA.execute();
      serviceB.execute();
      serviceC.execute();
    } catch (ThreadDeath e) {
      LOGGER.info("Interrupted!" + e);
      throw e;
    }
  }
}
