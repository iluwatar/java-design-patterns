package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefaultCircuitBreakerTest {
    private DefaultCircuitBreaker circuitBreaker;
    private static final int FAILURE_THRESHOLD = 3;

    @BeforeEach
    void setUp() {
        circuitBreaker = new DefaultCircuitBreaker(FAILURE_THRESHOLD);
    }

    @Test
    void testInitialState() {
        assertFalse(circuitBreaker.isOpen());
    }

    @Test
    void testOpenStateAfterFailures() {
        for (int i = 0; i < FAILURE_THRESHOLD; i++) {
            circuitBreaker.recordFailure();
        }
        assertTrue(circuitBreaker.isOpen());
    }

    @Test
    void testHalfOpenStateAfterTimeout() throws InterruptedException {
        for (int i = 0; i < FAILURE_THRESHOLD; i++) {
            circuitBreaker.recordFailure();
        }
        assertTrue(circuitBreaker.isOpen());

        // Wait for reset timeout
        Thread.sleep(5100);
        assertFalse(circuitBreaker.isOpen());
    }

    @Test
    void testSuccessResetsFailureCount() {
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();
        circuitBreaker.recordSuccess();
        
        for (int i = 0; i < FAILURE_THRESHOLD; i++) {
            circuitBreaker.recordFailure();
        }
        assertTrue(circuitBreaker.isOpen());
    }

    @Test
    void testReset() {
        for (int i = 0; i < FAILURE_THRESHOLD; i++) {
            circuitBreaker.recordFailure();
        }
        assertTrue(circuitBreaker.isOpen());

        circuitBreaker.reset();
        assertFalse(circuitBreaker.isOpen());
    }

    @Test
    void testFailureThresholdBoundary() {
        for (int i = 0; i < FAILURE_THRESHOLD - 1; i++) {
            circuitBreaker.recordFailure();
            assertFalse(circuitBreaker.isOpen());
        }
        
        circuitBreaker.recordFailure();
        assertTrue(circuitBreaker.isOpen());
    }
}
