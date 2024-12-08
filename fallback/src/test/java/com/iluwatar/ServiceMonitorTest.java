package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;
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
        assertEquals(1.0, monitor.getSuccessRate());
        assertNotNull(monitor.getLastSuccessTime());
        assertNotNull(monitor.getLastFailureTime());
        assertEquals(Duration.ZERO, monitor.getLastResponseTime());
    }

    @Test
    void testSuccessRate() {
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordSuccess(Duration.ofMillis(100));
        monitor.recordFallback();
        monitor.recordError();

        assertEquals(2, monitor.getSuccessCount());
        assertEquals(1, monitor.getFallbackCount());
        assertEquals(1, monitor.getErrorCount());
        assertEquals(0.5, monitor.getSuccessRate(), 0.01);
    }

    @Test
    void testResponseTimeTracking() {
        Duration responseTime = Duration.ofMillis(150);
        monitor.recordSuccess(responseTime);
        assertEquals(responseTime, monitor.getLastResponseTime());
    }

    @Test
    void testLastSuccessTime() {
        Instant before = Instant.now();
        monitor.recordSuccess(Duration.ofMillis(100));
        Instant after = Instant.now();
        
        Instant lastSuccess = monitor.getLastSuccessTime();
        assertTrue(lastSuccess.isAfter(before) || lastSuccess.equals(before));
        assertTrue(lastSuccess.isBefore(after) || lastSuccess.equals(after));
    }

    @Test
    void testLastFailureTime() {
        Instant before = Instant.now();
        monitor.recordError();
        Instant after = Instant.now();
        
        Instant lastFailure = monitor.getLastFailureTime();
        assertTrue(lastFailure.isAfter(before) || lastFailure.equals(before));
        assertTrue(lastFailure.isBefore(after) || lastFailure.equals(after));
    }

    @Test
    void testSuccessRateWithOnlyErrors() {
        monitor.recordError();
        monitor.recordError();
        monitor.recordError();
        assertEquals(0.0, monitor.getSuccessRate());
    }

    @Test
    void testSuccessRateWithOnlyFallbacks() {
        monitor.recordFallback();
        monitor.recordFallback();
        monitor.recordFallback();
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
        assertEquals(1.0, monitor.getSuccessRate());
        assertEquals(Duration.ZERO, monitor.getLastResponseTime());
        assertTrue(monitor.getLastSuccessTime().isAfter(Instant.now().minusSeconds(1)));
        assertTrue(monitor.getLastFailureTime().isAfter(Instant.now().minusSeconds(1)));
    }
}
