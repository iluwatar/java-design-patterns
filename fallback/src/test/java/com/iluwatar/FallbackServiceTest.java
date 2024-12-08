package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FallbackServiceTest {
    @Mock private Service primaryService;
    @Mock private Service fallbackService;
    @Mock private CircuitBreaker circuitBreaker;
    private FallbackService fallbackServiceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fallbackServiceUnderTest = new FallbackService(primaryService, fallbackService, circuitBreaker);
    }

    @Test
    void testSuccessfulPrimaryService() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenReturn("success");

        String result = fallbackServiceUnderTest.getData();

        assertEquals("success", result);
        verify(primaryService, times(1)).getData();
        verify(fallbackService, never()).getData();
        verify(circuitBreaker).recordSuccess();
    }

    @Test
    void testFallbackWhenPrimaryFails() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenThrow(new TimeoutException());
        when(fallbackService.getData()).thenReturn("fallback");

        String result = fallbackServiceUnderTest.getData();

        assertEquals("fallback", result);
        verify(primaryService, times(3)).getData();
        verify(fallbackService, times(1)).getData();
        verify(circuitBreaker, times(3)).recordFailure();
    }

    @Test
    void testCircuitBreakerOpen() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(true);
        when(fallbackService.getData()).thenReturn("fallback");

        String result = fallbackServiceUnderTest.getData();

        assertEquals("fallback", result);
        verify(primaryService, never()).getData();
        verify(fallbackService, times(1)).getData();
    }

    @Test
    void testFallbackServiceFailure() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenThrow(new TimeoutException());
        when(fallbackService.getData()).thenThrow(new Exception("Fallback failed"));

        String result = fallbackServiceUnderTest.getData();

        assertEquals("Service temporarily unavailable", result);
        verify(primaryService, times(3)).getData();
        verify(fallbackService, times(1)).getData();
    }

    @Test
    void testServiceClosedState() throws Exception {
        fallbackServiceUnderTest.close();
        assertThrows(ServiceException.class, () -> fallbackServiceUnderTest.getData());
    }

    @Test
    void testLocalCacheUpdateOnSuccess() throws Exception {
        LocalCacheService localCache = new LocalCacheService();
        FallbackService service = new FallbackService(primaryService, localCache, circuitBreaker);
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenReturn("new data");

        String result = service.getData();

        assertEquals("new data", result);
        assertEquals("new data", localCache.getData());
    }

    @Test
    void testMonitoringMetricsAfterMultipleOperations() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData())
            .thenReturn("success1")
            .thenThrow(new TimeoutException())
            .thenThrow(new TimeoutException())
            .thenThrow(new TimeoutException())
            .thenReturn("success2");
        when(fallbackService.getData()).thenReturn("fallback");

        // First call - success
        String result1 = fallbackServiceUnderTest.getData();
        assertEquals("success1", result1);
        
        // Second call - fallback after retries
        String result2 = fallbackServiceUnderTest.getData();
        assertEquals("fallback", result2);
        
        // Third call - success
        String result3 = fallbackServiceUnderTest.getData();
        assertEquals("success2", result3);

        ServiceMonitor monitor = fallbackServiceUnderTest.getMonitor();
        assertEquals(2, monitor.getSuccessCount());
        assertEquals(1, monitor.getFallbackCount());
        assertTrue(monitor.getSuccessRate() > 0.6);
    }

    @Test
    void testHealthCheck() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenThrow(new TimeoutException());
        when(fallbackService.getData()).thenReturn("fallback");

        // Generate some failing requests
        for (int i = 0; i < 10; i++) {
            fallbackServiceUnderTest.getData();
        }

        // Wait for health check to run
        Thread.sleep(2000);

        ServiceMonitor monitor = fallbackServiceUnderTest.getMonitor();
        assertTrue(monitor.getSuccessRate() < 0.6);
    }

    @Test
    void testRetryBehavior() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData())
            .thenThrow(new TimeoutException())
            .thenThrow(new TimeoutException())
            .thenReturn("success");

        String result = fallbackServiceUnderTest.getData();

        assertEquals("success", result);
        verify(primaryService, times(3)).getData();
        verify(fallbackService, never()).getData();
    }

    @Test
    void testCircuitBreakerTripping() throws Exception {
        when(circuitBreaker.isOpen())
            .thenReturn(false) // First call
            .thenReturn(false) // Second call
            .thenReturn(true); // Third call
        when(primaryService.getData()).thenThrow(new TimeoutException());
        when(fallbackService.getData()).thenReturn("fallback");

        String result1 = fallbackServiceUnderTest.getData();
        String result2 = fallbackServiceUnderTest.getData();
        String result3 = fallbackServiceUnderTest.getData();

        assertEquals("fallback", result1);
        assertEquals("fallback", result2);
        assertEquals("fallback", result3);
        
        // Verify primary service was called only during first two attempts
        verify(primaryService, times(6)).getData(); // 3 retries * 2 attempts
        verify(fallbackService, times(3)).getData(); // Called for each attempt
    }

    @Test
    void testConcurrentRequests() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenReturn("success");

        // Simulate concurrent requests
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                try {
                    fallbackServiceUnderTest.getData();
                } catch (Exception e) {
                    fail("Concurrent request failed: " + e.getMessage());
                }
            });
            threads[i].start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        verify(primaryService, times(5)).getData();
    }

    @Test
    void testServiceMetricsAfterMultipleFailures() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenThrow(new TimeoutException());
        when(fallbackService.getData()).thenReturn("fallback");

        // Generate multiple failures
        for (int i = 0; i < 5; i++) {
            fallbackServiceUnderTest.getData();
        }

        ServiceMonitor monitor = fallbackServiceUnderTest.getMonitor();
        assertEquals(0, monitor.getSuccessCount());
        assertEquals(5, monitor.getFallbackCount());
        assertEquals(15, monitor.getErrorCount()); // 3 retries per attempt
    }

    @Test
    void testGracefulDegradation() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData())
            .thenReturn("success")
            .thenThrow(new TimeoutException())
            .thenThrow(new RuntimeException())
            .thenThrow(new OutOfMemoryError());
        when(fallbackService.getData()).thenReturn("fallback");

        // First call succeeds
        assertEquals("success", fallbackServiceUnderTest.getData());

        // Subsequent calls should gracefully degrade to fallback
        for (int i = 0; i < 3; i++) {
            String result = fallbackServiceUnderTest.getData();
            assertEquals("fallback", result);
        }
    }

    @Test
    void testSystemStability() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenReturn("success");
        when(fallbackService.getData()).thenReturn("fallback");

        AtomicInteger requestCount = new AtomicInteger();
        AtomicInteger errorCount = new AtomicInteger();
        
        long endTime = System.currentTimeMillis() + 5000; // 5 second test
        while (System.currentTimeMillis() < endTime) {
            try {
                fallbackServiceUnderTest.getData();
                requestCount.incrementAndGet();
            } catch (Exception e) {
                errorCount.incrementAndGet();
            }
            Thread.sleep(50); // Prevent tight loop
        }

        assertTrue(requestCount.get() > 0);
        assertEquals(0, errorCount.get());
        assertTrue(fallbackServiceUnderTest.getMonitor().getSuccessRate() > 0.95);
    }

    @Test
    void testResourceExhaustion() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenAnswer(inv -> {
            Thread.sleep(100); // Simulate processing time
            return "success";
        });

        // Create many concurrent requests
        ExecutorService executor = Executors.newFixedThreadPool(50);
        List<Future<String>> futures = new ArrayList<>();
        
        for (int i = 0; i < 100; i++) {
            futures.add(executor.submit(() -> fallbackServiceUnderTest.getData()));
        }



        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
    }

    @Test
    void testRecoveryAfterFailure() throws Exception {
        // Setup circuit breaker state transitions
        when(circuitBreaker.isOpen())
            .thenReturn(false) // Initial attempt
            .thenReturn(true)  // Circuit open
            .thenReturn(false); // Circuit reset
        
        // Setup primary service behavior
        when(primaryService.getData())
            .thenThrow(new TimeoutException()) // Initial failure
            .thenThrow(new TimeoutException()) // When circuit opens
            .thenThrow(new TimeoutException()) // Third retry
            .thenReturn("recovered"); // After reset
            
        // Setup fallback service behavior
        when(fallbackService.getData())
            .thenReturn("fallback")
            .thenReturn("fallback")
            .thenReturn("fallback");

        // Initial failure should trigger fallback
        String result1 = fallbackServiceUnderTest.getData();
        assertEquals("fallback", result1);

        // Circuit is open, should use fallback without trying primary
        String result2 = fallbackServiceUnderTest.getData();
        assertEquals("fallback", result2);

        // Circuit resets, primary service should be tried again
        String result3 = fallbackServiceUnderTest.getData();
        assertEquals("recovered", result3);

        // Verify timing of success/failure
        ServiceMonitor monitor = fallbackServiceUnderTest.getMonitor();
        assertTrue(monitor.getLastSuccessTime().isAfter(monitor.getLastFailureTime()));
        
        // Verify correct number of calls
        verify(primaryService, atLeast(3)).getData();
        verify(fallbackService, times(2)).getData();
    }

    @Test
    void testReliabilityUnderLoad() throws Exception {
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData()).thenReturn("success");
        when(fallbackService.getData()).thenReturn("fallback");

        int totalRequests = 100;
        CountDownLatch latch = new CountDownLatch(totalRequests);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failureCount = new AtomicInteger();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            for (int i = 0; i < totalRequests; i++) {
                executor.execute(() -> {
                    try {
                        fallbackServiceUnderTest.getData();
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        failureCount.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            assertTrue(latch.await(30, TimeUnit.SECONDS));
            assertTrue(successCount.get() >= 85, "Success count should be at least 85%");
            assertTrue(failureCount.get() <= 15, "Failure count should be no more than 15%");
        } finally {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
