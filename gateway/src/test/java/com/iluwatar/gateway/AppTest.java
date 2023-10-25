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
