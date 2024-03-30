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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AppTest {
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
    public void testServiceAExecution() throws InterruptedException, ExecutionException {
        // Test Service A execution
        Future<?> serviceAFuture = executorService.submit(() -> {
            try {
                Gateway serviceA = gatewayFactory.getGateway("ServiceA");
                serviceA.execute();
            } catch (Exception e) {
                fail("Service A should not throw an exception.");
            }
        });

        // Wait for Service A to complete
        serviceAFuture.get();
    }

    @Test
    public void testServiceCExecutionWithException() throws InterruptedException, ExecutionException {
        // Test Service B execution with an exception
        Future<?> serviceBFuture = executorService.submit(() -> {
            try {
                Gateway serviceB = gatewayFactory.getGateway("ServiceB");
                serviceB.execute();
            } catch (Exception e) {
                fail("Service B should not throw an exception.");
            }
        });

        // Wait for Service B to complete
        serviceBFuture.get();
    }

    @Test
    public void testServiceCExecution() throws InterruptedException, ExecutionException {
        // Test Service C execution
        Future<?> serviceCFuture = executorService.submit(() -> {
            try {
                Gateway serviceC = gatewayFactory.getGateway("ServiceC");
                serviceC.execute();
            } catch (Exception e) {
                fail("Service C should not throw an exception.");
            }
        });

        // Wait for Service C to complete
        serviceCFuture.get();
    }

    @Test
    public void testServiceCError() {
        try {
            ExternalServiceC serviceC = (ExternalServiceC) gatewayFactory.getGateway("ServiceC");
            serviceC.error();
            fail("Service C should throw an exception.");
        } catch (Exception e) {
            assertEquals("Service C encountered an error", e.getMessage());
        }
    }
}
