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

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServiceFactoryTest {

  private GatewayFactory gatewayFactory;
  private ExecutorService executorService;

  @BeforeEach
  void setUp() {
    gatewayFactory = new GatewayFactory();
    executorService = Executors.newFixedThreadPool(2);
    gatewayFactory.registerGateway("ServiceA", new ExternalServiceA());
    gatewayFactory.registerGateway("ServiceB", new ExternalServiceB());
    gatewayFactory.registerGateway("ServiceC", new ExternalServiceC());
  }

  @Test
  void testGatewayFactoryRegistrationAndRetrieval() {
    Gateway serviceA = gatewayFactory.getGateway("ServiceA");
    Gateway serviceB = gatewayFactory.getGateway("ServiceB");
    Gateway serviceC = gatewayFactory.getGateway("ServiceC");

    // Check if the retrieved instances match their expected types
    assertTrue(
        serviceA instanceof ExternalServiceA, "ServiceA should be an instance of ExternalServiceA");
    assertTrue(
        serviceB instanceof ExternalServiceB, "ServiceB should be an instance of ExternalServiceB");
    assertTrue(
        serviceC instanceof ExternalServiceC, "ServiceC should be an instance of ExternalServiceC");
  }

  @Test
  void testGatewayFactoryRegistrationWithNonExistingKey() {
    Gateway nonExistingService = gatewayFactory.getGateway("NonExistingService");
    assertNull(nonExistingService);
  }

  @Test
  void testGatewayFactoryConcurrency() throws InterruptedException {
    int numThreads = 10;
    CountDownLatch latch = new CountDownLatch(numThreads);
    AtomicBoolean failed = new AtomicBoolean(false);

    for (int i = 0; i < numThreads; i++) {
      executorService.submit(
          () -> {
            try {
              Gateway serviceA = gatewayFactory.getGateway("ServiceA");
              serviceA.execute();
            } catch (Exception e) {
              failed.set(true);
            } finally {
              latch.countDown();
            }
          });
    }

    latch.await();
    assertFalse(failed.get(), "This should not fail");
  }
}
