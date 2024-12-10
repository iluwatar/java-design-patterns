package com.iluwatar.fallback;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class ServiceMonitorTest {
    private ServiceMonitor monitor;

    @BeforeEach
    void setUp() {
        monitor = new ServiceMonitor();
    }

    @Test
    void testInitialState() {
        assertEquals(0, monitor.getSuccessCount());
        assertEquals(0, monitor.getFallbackCount());
        assertEquals(0, monitor.getErrorCount());
        assertEquals(0.0, monitor.getSuccessRate());
        assertNotNull(monitor.getLastSuccessTime());
        assertNotNull(monitor.getLastFailureTime());
        assertEquals(Duration.ZERO, monitor.getLastResponseTime());
    }

    @Test
    void testResponseTimeTracking() {
        Duration responseTime = Duration.ofMillis(150);
        monitor.recordSuccess(responseTime);
        assertEquals(responseTime, monitor.getLastResponseTime());
    }

    @Test
    void testLastSuccessTime() throws InterruptedException {
        Instant beforeTest = Instant.now();
        Thread.sleep(1); // Add small delay
        monitor.recordSuccess(Duration.ofMillis(100));
        assertTrue(monitor.getLastSuccessTime().isAfter(beforeTest));
    }

    @Test
    void testLastFailureTime() throws InterruptedException {
        Instant beforeTest = Instant.now();
        Thread.sleep(1); // Add small delay
        monitor.recordError();
        assertTrue(monitor.getLastFailureTime().isAfter(beforeTest));
    }

    @Test
    void testSuccessRateWithOnlyErrors() {
        monitor.recordError();
        monitor.recordError();
        monitor.recordError();
        assertEquals(0.0, monitor.getSuccessRate());
    }

    @Test
    void testSuccessRateWithOnlySuccesses() {
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordSuccess(Duration.ofMillis(100));
        assertEquals(1.0, monitor.getSuccessRate());
    }

    @Test
    void testReset() {
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordFallback();
        monitor.recordError();
        monitor.reset();

        assertEquals(0, monitor.getSuccessCount());
        assertEquals(0, monitor.getFallbackCount());
        assertEquals(0, monitor.getErrorCount());
        assertEquals(0.0, monitor.getSuccessRate());
        assertEquals(Duration.ZERO, monitor.getLastResponseTime());
        assertTrue(monitor.getLastSuccessTime().isAfter(Instant.now().minusSeconds(1)));
        assertTrue(monitor.getLastFailureTime().isAfter(Instant.now().minusSeconds(1)));
    }

    @Test
    void testConcurrentOperations() throws Exception {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    monitor.recordSuccess(Duration.ofMillis(100));
                } finally {
                    latch.countDown();
                }
            });
        }
        
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(threadCount, monitor.getSuccessCount());
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void testMixedOperationsSuccessRate() {
        // 60% success rate scenario
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordError();
        monitor.recordError();

        assertEquals(0.6, monitor.getSuccessRate(), 0.01);
        assertEquals(3, monitor.getSuccessCount());
        assertEquals(2, monitor.getErrorCount());
    }

    @Test
    void testBasicMetrics() {
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordError();
        monitor.recordFallback();

        assertEquals(1, monitor.getSuccessCount());
        assertEquals(1, monitor.getErrorCount());
        assertEquals(1, monitor.getFallbackCount());
        assertEquals(Duration.ofMillis(100), monitor.getLastResponseTime());
    }

    @Test
    void testConsecutiveFailures() {
        int consecutiveFailures = 5;
        for (int i = 0; i < consecutiveFailures; i++) {
            monitor.recordError();
        }

        assertEquals(consecutiveFailures, monitor.getErrorCount());
        monitor.recordSuccess(Duration.ofMillis(100));
        assertEquals(consecutiveFailures, monitor.getErrorCount());
        assertEquals(1, monitor.getSuccessCount());
    }

    @Test
    void testResetBehavior() {
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordError();
        monitor.recordFallback();
        
        monitor.reset();
        
        assertEquals(0, monitor.getSuccessCount());
        assertEquals(0, monitor.getErrorCount());
        assertEquals(0, monitor.getFallbackCount());
        assertEquals(Duration.ZERO, monitor.getLastResponseTime());
    }
}
