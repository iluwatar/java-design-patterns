package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Fallback pattern implementation.
 * Tests the interaction between components and system behavior under various conditions.
 */
class IntegrationTest {
    private FallbackService fallbackService;
    private LocalCacheService cacheService;
    private CircuitBreaker circuitBreaker;
    
    // Test configuration constants
    private static final int CONCURRENT_REQUESTS = 50;
    private static final int THREAD_POOL_SIZE = 10;
    private static final Duration TEST_TIMEOUT = Duration.ofSeconds(30);
    private static final String INVALID_SERVICE_URL = "http://localhost:0"; // Force failures

    @BeforeEach
    void setUp() {
        Service remoteService = new RemoteService(INVALID_SERVICE_URL, HttpClient.newHttpClient());
        cacheService = new LocalCacheService();
        circuitBreaker = new DefaultCircuitBreaker(3);
        fallbackService = new FallbackService(remoteService, cacheService, circuitBreaker);
    }

    @Nested
    @DisplayName("Basic Fallback Flow Tests")
    class BasicFallbackTests {
        @Test
        @DisplayName("Should follow complete fallback flow when primary service fails")
        void testCompleteFailoverFlow() throws Exception {
            // First call should try remote service, fail, and fall back to cache
            String result1 = fallbackService.getData();
            assertNotNull(result1);
            assertTrue(result1.contains("fallback"), "Should return fallback response");

            // Update cache with new data
            String updatedData = "updated cache data";
            cacheService.updateCache("default", updatedData);

            // Second call should use updated cache data
            String result2 = fallbackService.getData();
            assertEquals(updatedData, result2, "Should return updated cache data");

            // Verify metrics
            ServiceMonitor monitor = fallbackService.getMonitor();
            assertEquals(0, monitor.getSuccessCount(), "Should have no successful primary calls");
            assertTrue(monitor.getFallbackCount() > 0, "Should have recorded fallbacks");
            assertTrue(monitor.getErrorCount() > 0, "Should have recorded errors");
        }
    }

    @Nested
    @DisplayName("Circuit Breaker Integration Tests")
    class CircuitBreakerTests {
        @Test
        @DisplayName("Should properly transition circuit breaker states")
        void testCircuitBreakerIntegration() throws Exception {
            // Make calls to trigger circuit breaker
            for (int i = 0; i < 5; i++) {
                fallbackService.getData();
            }

            assertTrue(circuitBreaker.isOpen(), "Circuit breaker should be open after failures");

            // Verify fallback behavior when circuit is open
            String result = fallbackService.getData();
            assertNotNull(result);
            assertTrue(result.contains("fallback"), "Should use fallback when circuit is open");

            // Wait for circuit reset timeout
            Thread.sleep(5100);
            assertFalse(circuitBreaker.isOpen(), "Circuit breaker should reset after timeout");
        }
    }

    @Nested
    @DisplayName("Performance and Reliability Tests")
    class PerformanceTests {
        @Test
        @DisplayName("Should maintain performance metrics")
        void testPerformanceMetrics() throws Exception {
            Instant startTime = Instant.now();
            
            // Make sequential service calls
            for (int i = 0; i < 3; i++) {
                fallbackService.getData();
            }

            Duration totalDuration = Duration.between(startTime, Instant.now());
            ServiceMonitor monitor = fallbackService.getMonitor();
            
            assertTrue(monitor.getLastResponseTime().compareTo(totalDuration) <= 0,
                "Response time should be tracked accurately");
            assertTrue(monitor.getLastFailureTime().isAfter(monitor.getLastSuccessTime()),
                "Timing of failures should be tracked");
        }

        @Test
        @DisplayName("Should handle concurrent requests reliably")
        void testSystemReliability() throws Exception {
            AtomicInteger successCount = new AtomicInteger();
            AtomicInteger fallbackCount = new AtomicInteger();
            CountDownLatch latch = new CountDownLatch(CONCURRENT_REQUESTS);
            
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            try {
                // Submit concurrent requests
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
                            fail("Request should not fail: " + e.getMessage());
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                assertTrue(latch.await(TEST_TIMEOUT.toSeconds(), TimeUnit.SECONDS),
                    "Concurrent requests should complete within timeout");
                
                // Verify system reliability
                ServiceMonitor monitor = fallbackService.getMonitor();
                double reliabilityRate = (monitor.getSuccessRate() + 
                    (double)fallbackCount.get() / CONCURRENT_REQUESTS);
                assertTrue(reliabilityRate > 0.95,
                    String.format("Reliability rate should be > 95%%, actual: %.2f%%",
                        reliabilityRate * 100));
            } finally {
                executor.shutdown();
                assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS),
                    "Executor should shutdown cleanly");
            }
        }
    }

    @Nested
    @DisplayName("Resource Management Tests")
    class ResourceTests {
        @Test
        @DisplayName("Should properly clean up resources")
        void testResourceCleanup() throws Exception {
            // Verify service works before closing
            String result = fallbackService.getData();
            assertNotNull(result, "Service should work before closing");

            // Close service and verify it's unusable
            fallbackService.close();
            assertThrows(ServiceException.class, () -> fallbackService.getData(),
                "Service should throw exception after closing");
        }
    }
}
