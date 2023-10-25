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
