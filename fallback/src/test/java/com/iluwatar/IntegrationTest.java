package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {
    private FallbackService fallbackService;
    private LocalCacheService cacheService;
    private CircuitBreaker circuitBreaker;
    private static final int CONCURRENT_REQUESTS = 50;
    private static final int THREAD_POOL_SIZE = 10;

    @BeforeEach
    void setUp() {
        Service remoteService = new RemoteService(
            "http://localhost:0", // Invalid port to force failure
            HttpClient.newHttpClient()
        );
        
        cacheService = new LocalCacheService();
        circuitBreaker = new DefaultCircuitBreaker(3);
        fallbackService = new FallbackService(remoteService, cacheService, circuitBreaker);
    }

    @Test
    void testCompleteFailoverFlow() throws Exception {
        // First call should try remote service, fail, and fall back to cache
        String result1 = fallbackService.getData();
        assertNotNull(result1);
        assertTrue(result1.contains("fallback"));

        // Update cache with new data
        cacheService.updateCache("default", "updated cache data");

        // Second call should use updated cache data
        String result2 = fallbackService.getData();
        assertEquals("updated cache data", result2);

        // Verify metrics
        ServiceMonitor monitor = fallbackService.getMonitor();
        assertEquals(0, monitor.getSuccessCount());
        assertTrue(monitor.getFallbackCount() > 0);
        assertTrue(monitor.getErrorCount() > 0);
    }

    @Test
    void testCircuitBreakerIntegration() throws Exception {
        // Make multiple calls to trigger circuit breaker
        for (int i = 0; i < 5; i++) {
            fallbackService.getData();
        }

        // Verify circuit breaker is open
        assertTrue(circuitBreaker.isOpen());

        // Verify all subsequent calls use fallback without trying primary
        String result = fallbackService.getData();
        assertNotNull(result);
        assertTrue(result.contains("fallback"));

        // Wait for circuit breaker timeout
        Thread.sleep(5100);

        // Verify circuit breaker is reset
        assertFalse(circuitBreaker.isOpen());
    }

    @Test
    void testPerformanceMetrics() throws Exception {
        long startTime = System.currentTimeMillis();
        
        // Make multiple service calls
        for (int i = 0; i < 3; i++) {
            fallbackService.getData();
        }

        long endTime = System.currentTimeMillis();
        Duration totalDuration = Duration.ofMillis(endTime - startTime);

        // Verify performance metrics
        ServiceMonitor monitor = fallbackService.getMonitor();
        assertTrue(monitor.getLastResponseTime().compareTo(totalDuration) <= 0);
        assertTrue(monitor.getLastFailureTime().isAfter(monitor.getLastSuccessTime()));
    }

    @Test
    void testResourceCleanup() throws Exception {
        // Verify service works before closing
        String result = fallbackService.getData();
        assertNotNull(result);

        // Close service
        fallbackService.close();

        // Verify service is closed
        assertThrows(ServiceException.class, () -> fallbackService.getData());
    }

    @Test
    void testSystemReliability() throws Exception {
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger fallbackCount = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(CONCURRENT_REQUESTS);
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try {
            for (int i = 0; i < CONCURRENT_REQUESTS; i++) {
                executor.execute(() -> {
                    try {
                        String result = fallbackService.getData();
                        if (result.contains("fallback")) {
                            fallbackCount.incrementAndGet();
                        } else {
                            successCount.incrementAndGet();
                        }
                    } catch (Exception e) {
                        fail("Request failed: " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            assertTrue(latch.await(30, TimeUnit.SECONDS), "Timed out waiting for requests");
            
            // Verify system reliability
            ServiceMonitor monitor = fallbackService.getMonitor();
            double reliabilityRate = (monitor.getSuccessRate() + 
                (double)fallbackCount.get() / CONCURRENT_REQUESTS);
            assertTrue(reliabilityRate > 0.95, 
                "Reliability rate should be > 95%, actual: " + (reliabilityRate * 100) + "%");
        } finally {
            executor.shutdown();
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
        }
    }

    @Test
    void testLongTermStability() throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        AtomicBoolean stable = new AtomicBoolean(true);
        AtomicInteger requestCount = new AtomicInteger();
        
        try {
            ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
                try {
                    fallbackService.getData();
                    requestCount.incrementAndGet();
                } catch (Exception e) {
                    stable.set(false);
                }
            }, 0, 100, TimeUnit.MILLISECONDS);

            // Run for 5 seconds instead of 30 for faster tests
            Thread.sleep(5_000);
            future.cancel(false);

            assertTrue(stable.get(), "System should remain stable");
            assertTrue(requestCount.get() >= 45, 
                "Should handle at least 45 requests in 5 seconds");
            assertTrue(fallbackService.getMonitor().getSuccessRate() + 
                fallbackService.getMonitor().getFallbackCount() / 
                (double)requestCount.get() > 0.9,
                "Combined success and fallback rate should be > 90%");
        } finally {
            scheduler.shutdown();
            assertTrue(scheduler.awaitTermination(5, TimeUnit.SECONDS));
        }
    }
    @Test
    void testConcurrentRequestsUnderLoad() throws Exception {
        int numThreads = 100;
        int requestsPerThread = 100;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(numThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);
        
        when(circuitBreaker.isOpen()).thenReturn(false);
        when(primaryService.getData())
            .thenAnswer(inv -> {
                Thread.sleep(5); // Simulate processing time
                return "success";
            });
    
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> futures = new ArrayList<>();
    
        try {
            // Create worker threads
            for (int i = 0; i < numThreads; i++) {
                futures.add(executor.submit(() -> {
                    try {
                        startLatch.await(); // Wait for all threads to be ready
                        for (int j = 0; j < requestsPerThread; j++) {
                            try {
                                String result = fallbackServiceUnderTest.getData();
                                if (result != null && !result.isEmpty()) {
                                    successCount.incrementAndGet();
                                }
                            } catch (Exception e) {
                                failureCount.incrementAndGet();
                            }
                        }
                    } finally {
                        completionLatch.countDown();
                    }
                }));
            }
    
            // Start all threads simultaneously
            startLatch.countDown();
            
            // Wait for completion with timeout
            assertTrue(completionLatch.await(30, TimeUnit.SECONDS), 
                "Test timed out waiting for completion");
    
            // Verify results
            assertTrue(successCount.get() > numThreads * requestsPerThread * 0.95,
                "Success rate should be > 95%");
            assertTrue(failureCount.get() < numThreads * requestsPerThread * 0.05,
                "Failure rate should be < 5%");
            
            // Check service monitor metrics
            ServiceMonitor monitor = fallbackServiceUnderTest.getMonitor();
            assertTrue(monitor.getSuccessRate() > 0.95, 
                "Service monitor success rate should be > 95%");
            assertTrue(monitor.getLastResponseTime().toMillis() < 1000,
                "Response time should be under 1 second");
            
        } finally {
            executor.shutdown();
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
        }
    }
    
    @Test
    void testCircuitBreakerBehaviorUnderStress() throws Exception {
        AtomicInteger openStateCount = new AtomicInteger();
        AtomicInteger closedStateCount = new AtomicInteger();
        CountDownLatch completionLatch = new CountDownLatch(1);
        
        when(circuitBreaker.isOpen()).thenAnswer(inv -> {
            if (openStateCount.get() > 100) { // Switch to closed after 100 open calls
                closedStateCount.incrementAndGet();
                return false;
            }
            openStateCount.incrementAndGet();
            return true;
        });
    
        when(primaryService.getData())
            .thenThrow(new TimeoutException())
            .thenThrow(new TimeoutException())
            .thenReturn("recovered");
        when(fallbackService.getData()).thenReturn("fallback");
    
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            // Generate load
            for (int i = 0; i < 200; i++) {
                executor.submit(() -> {
                    try {
                        fallbackServiceUnderTest.getData();
                    } catch (Exception ignored) {
                    }
                });
            }
    
            Thread.sleep(2000); // Allow time for requests to process
            
            assertTrue(openStateCount.get() >= 100, 
                "Circuit breaker should have been open at least 100 times");
            assertTrue(closedStateCount.get() > 0,
                "Circuit breaker should have transitioned to closed state");
            
            verify(fallbackService, atLeast(50)).getData();
            verify(primaryService, atLeast(10)).getData();
            
        } finally {
            executor.shutdown();
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
        }
    }
    
    @Test
    void testErrorBoundaries() throws Exception {
        // Test with null responses
        when(primaryService.getData()).thenReturn(null);
        when(fallbackService.getData()).thenReturn(null);
        
        String result = fallbackServiceUnderTest.getData();
        assertEquals("Service temporarily unavailable", result);
        
        // Test with empty responses
        when(primaryService.getData()).thenReturn("");
        when(fallbackService.getData()).thenReturn("");
        
        result = fallbackServiceUnderTest.getData();
        assertNotNull(result);
        
        // Test with very large responses
        StringBuilder largeResponse = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            largeResponse.append("x");
        }
        when(primaryService.getData()).thenReturn(largeResponse.toString());
        
        result = fallbackServiceUnderTest.getData();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
}
