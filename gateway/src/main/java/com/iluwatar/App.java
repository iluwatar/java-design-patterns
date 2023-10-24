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
package com.iluwatar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * The "App" class serves as the main entry point for the application implementing the Gateway design pattern.
 * It demonstrates the use of the GatewayFactory to manage different gateway implementations, allowing for the
 * execution of various services asynchronously.
 *
 * <p>In this example, there are three virtual service. GateFactory is the factory class and it
 * provides a method to create different kinds of external services.
 */
public class App {
    public static void main(String[] args) {
        GatewayFactory gatewayFactory = new GatewayFactory();

        // Register different gateways
        gatewayFactory.registerGateway("ServiceA", new ExternalServiceA());
        gatewayFactory.registerGateway("ServiceB", new ExternalServiceB());
        gatewayFactory.registerGateway("ServiceC", new ExternalServiceC());

        // Use an executor service for asynchronous execution
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try {
            // Execute Service A asynchronously
            Future<?> serviceAFuture = executorService.submit(() -> {
                try {
                    Gateway serviceA = gatewayFactory.getGateway("ServiceA");
                    serviceA.execute();
                } catch (Exception e) {
                    System.err.println("Error executing Service A: " + e.getMessage());
                }
            });

            // Execute Service B asynchronously
            Future<?> serviceBFuture = executorService.submit(() -> {
                try {
                    Gateway serviceB = gatewayFactory.getGateway("ServiceB");
                    serviceB.execute();
                } catch (Exception e) {
                    System.err.println("Error executing Service B: " + e.getMessage());
                }
            });

            // Execute Service C asynchronously
            Future<?> servicecFuture = executorService.submit(() -> {
                try {
                    Gateway serviceC = gatewayFactory.getGateway("ServiceC");
                    serviceC.execute();
                } catch (Exception e) {
                    System.err.println("Error executing Service C: " + e.getMessage());
                }
            });

            // Wait for both tasks to complete
            serviceAFuture.get();
            serviceBFuture.get();
            servicecFuture.get();
        } catch (Exception e) {
            System.err.println("Error in the main client: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }
}

/**
 * ExternalServiceA is one of external services.
 */
class ExternalServiceA implements Gateway {
    @Override
    public void execute() throws Exception {
        System.out.println("Executing Service A");
        // Simulate a time-consuming task
        Thread.sleep(1000);
    }
}

/**
 * ExternalServiceB is one of external services.
 */
class ExternalServiceB implements Gateway {
    @Override
    public void execute() throws Exception {
        System.out.println("Executing Service B");
        // Simulate a time-consuming task
        Thread.sleep(1000);
    }

    public void error() throws Exception{
        // Simulate an exception
        throw new RuntimeException("Service B encountered an error");
    }
}

/**
 * ExternalServiceC is one of external services.
 */
class ExternalServiceC implements Gateway {
    @Override
    public void execute() throws Exception {
        System.out.println("Executing Service C");
        // Simulate a time-consuming task
        Thread.sleep(1000);
    }

    public void error() throws Exception{
        // Simulate an exception
        throw new RuntimeException("Service C encountered an error");
    }
}
