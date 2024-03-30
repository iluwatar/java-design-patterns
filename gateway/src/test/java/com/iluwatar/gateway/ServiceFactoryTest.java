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


import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceFactoryTest {
    private GatewayFactory gatewayFactory;
    private ExecutorService executorService;

    @Before
    public void setUp() {
        gatewayFactory = new GatewayFactory();
        executorService = Executors.newFixedThreadPool(2);
        gatewayFactory.registerGateway("ServiceA", new ExternalServiceA());
        gatewayFactory.registerGateway("ServiceB", new ExternalServiceB());
        gatewayFactory.registerGateway("ServiceC", new ExternalServiceC());
    }

    @Test
    public void testGatewayFactoryRegistrationAndRetrieval() {
        Gateway serviceA = gatewayFactory.getGateway("ServiceA");
        Gateway serviceB = gatewayFactory.getGateway("ServiceB");
        Gateway serviceC = gatewayFactory.getGateway("ServiceC");

        // Check if the retrieved instances match their expected types
        assertTrue("ServiceA should be an instance of ExternalServiceA", serviceA instanceof ExternalServiceA);
        assertTrue("ServiceB should be an instance of ExternalServiceB", serviceB instanceof ExternalServiceB);
        assertTrue("ServiceC should be an instance of ExternalServiceC", serviceC instanceof ExternalServiceC);
    }

    @Test
    public void testGatewayFactoryRegistrationWithNonExistingKey() {
        Gateway nonExistingService = gatewayFactory.getGateway("NonExistingService");
        assertEquals(null, nonExistingService);
    }

    @Test
    public void testGatewayFactoryConcurrency() throws InterruptedException {
        int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicBoolean failed = new AtomicBoolean(false);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
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
        assertTrue("This should not fail", !failed.get());
    }
}
