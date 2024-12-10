package com.iluwatar.fallback;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit tests for {@link DefaultCircuitBreaker}.
 * Tests state transitions and behaviors of the circuit breaker pattern implementation.
 */
class DefaultCircuitBreakerTest {
    private DefaultCircuitBreaker circuitBreaker;
    
    private static final int FAILURE_THRESHOLD = 3;
    private static final long RESET_TIMEOUT_MS = 5000;
    private static final long WAIT_BUFFER = 100;

    @BeforeEach
    void setUp() {
        circuitBreaker = new DefaultCircuitBreaker(FAILURE_THRESHOLD);
    }

    @Nested
    @DisplayName("State Transition Tests")
    class StateTransitionTests {
        @Test
        @DisplayName("Should start in CLOSED state")
        void initialStateShouldBeClosed() {
            assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
            assertTrue(circuitBreaker.allowRequest());
            assertFalse(circuitBreaker.isOpen());
        }

        @Test
        @DisplayName("Should transition to OPEN after threshold failures")
        void shouldTransitionToOpenAfterFailures() {
            // When
            for (int i = 0; i < FAILURE_THRESHOLD; i++) {
                circuitBreaker.recordFailure();
            }

            // Then
            assertEquals(CircuitBreaker.CircuitState.OPEN, circuitBreaker.getState());
            assertFalse(circuitBreaker.allowRequest());
            assertTrue(circuitBreaker.isOpen());
        }

        @Test
        @DisplayName("Should transition to HALF-OPEN after timeout")
        void shouldTransitionToHalfOpenAfterTimeout() throws InterruptedException {
            // Given
            for (int i = 0; i < FAILURE_THRESHOLD; i++) {
                circuitBreaker.recordFailure();
            }
            assertTrue(circuitBreaker.isOpen());

            // When
            Thread.sleep(RESET_TIMEOUT_MS + WAIT_BUFFER);

            // Then
            assertEquals(CircuitBreaker.CircuitState.HALF_OPEN, circuitBreaker.getState());
            assertTrue(circuitBreaker.allowRequest());
            assertFalse(circuitBreaker.isOpen());
        }
    }

    @Nested
    @DisplayName("Behavior Tests")
    class BehaviorTests {
        @Test
        @DisplayName("Success should reset failure count")
        void successShouldResetFailureCount() {
            // Given
            circuitBreaker.recordFailure();
            circuitBreaker.recordFailure();
            
            // When
            circuitBreaker.recordSuccess();
            
            // Then
            assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
            assertTrue(circuitBreaker.allowRequest());
            
            // Additional verification
            circuitBreaker.recordFailure();
            assertFalse(circuitBreaker.isOpen());
        }

        @Test
        @DisplayName("Reset should return to initial state")
        void resetShouldReturnToInitialState() {
            // Given
            for (int i = 0; i < FAILURE_THRESHOLD; i++) {
                circuitBreaker.recordFailure();
            }
            assertTrue(circuitBreaker.isOpen());

            // When
            circuitBreaker.reset();

            // Then
            assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
            assertTrue(circuitBreaker.allowRequest());
            assertFalse(circuitBreaker.isOpen());
        }

        @Test
        @DisplayName("Should respect failure threshold boundary")
        void shouldRespectFailureThresholdBoundary() {
            // When/Then
            for (int i = 0; i < FAILURE_THRESHOLD - 1; i++) {
                circuitBreaker.recordFailure();
                assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
                assertFalse(circuitBreaker.isOpen());
            }
            
            circuitBreaker.recordFailure();
            assertEquals(CircuitBreaker.CircuitState.OPEN, circuitBreaker.getState());
            assertTrue(circuitBreaker.isOpen());
        }

        @Test
        @DisplayName("Should allow request in HALF-OPEN state")
        void shouldAllowRequestInHalfOpenState() throws InterruptedException {
            // Given
            for (int i = 0; i < FAILURE_THRESHOLD; i++) {
                circuitBreaker.recordFailure();
            }
            
            // When
            Thread.sleep(RESET_TIMEOUT_MS + WAIT_BUFFER);
            
            // Then
            assertTrue(circuitBreaker.allowRequest());
            assertEquals(CircuitBreaker.CircuitState.HALF_OPEN, circuitBreaker.getState());
        }
    }

    @Nested
    @DisplayName("Recovery Tests")
    class RecoveryTests {
        @Test
        @DisplayName("Should close circuit after sustained success in half-open state")
        void shouldCloseAfterSustainedSuccess() throws InterruptedException {
            // Given
            for (int i = 0; i < FAILURE_THRESHOLD; i++) {
                circuitBreaker.recordFailure();
            }
            Thread.sleep(RESET_TIMEOUT_MS + WAIT_BUFFER);
            assertEquals(CircuitBreaker.CircuitState.HALF_OPEN, circuitBreaker.getState());
            
            // When
            Thread.sleep(30000); // Wait for MIN_HALF_OPEN_DURATION
            circuitBreaker.recordSuccess();
            
            // Then
            assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
            assertTrue(circuitBreaker.allowRequest());
        }

        @Test
        @DisplayName("Should remain half-open if success duration not met")
        void shouldRemainHalfOpenBeforeSuccessDuration() throws InterruptedException {
            // Given
            for (int i = 0; i < FAILURE_THRESHOLD; i++) {
                circuitBreaker.recordFailure();
            }
            Thread.sleep(RESET_TIMEOUT_MS + WAIT_BUFFER);
            
            // When
            circuitBreaker.recordSuccess(); // Success before MIN_HALF_OPEN_DURATION
            
            // Then
            assertEquals(CircuitBreaker.CircuitState.HALF_OPEN, circuitBreaker.getState());
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        @Test
        @DisplayName("Should handle rapid state transitions")
        void shouldHandleRapidStateTransitions() throws InterruptedException {
            // Multiple rapid transitions
            for (int i = 0; i < 3; i++) {
                // To OPEN
                for (int j = 0; j < FAILURE_THRESHOLD; j++) {
                    circuitBreaker.recordFailure();
                }
                assertEquals(CircuitBreaker.CircuitState.OPEN, circuitBreaker.getState());
                
                // To HALF_OPEN
                Thread.sleep(RESET_TIMEOUT_MS + WAIT_BUFFER);
                assertTrue(circuitBreaker.allowRequest());
                assertEquals(CircuitBreaker.CircuitState.HALF_OPEN, circuitBreaker.getState());
                
                // Back to CLOSED
                circuitBreaker.reset();
                assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
            }
        }

        @Test
        @DisplayName("Should handle boundary conditions for failure threshold")
        void shouldHandleFailureThresholdBoundaries() {
            // Given/When
            for (int i = 0; i < FAILURE_THRESHOLD - 1; i++) {
                circuitBreaker.recordFailure();
                // Then - Should still be closed
                assertEquals(CircuitBreaker.CircuitState.CLOSED, circuitBreaker.getState());
            }
            
            // One more failure - Should open
            circuitBreaker.recordFailure();
            assertEquals(CircuitBreaker.CircuitState.OPEN, circuitBreaker.getState());
            
            // Additional failures should keep it open
            circuitBreaker.recordFailure();
            assertEquals(CircuitBreaker.CircuitState.OPEN, circuitBreaker.getState());
        }
    }

    @Nested
    @DisplayName("Concurrency Tests") 
    class ConcurrencyTests {
        private static final int THREAD_COUNT = 10;
        private static final int OPERATIONS_PER_THREAD = 1000;

        @Test
        @DisplayName("Should handle concurrent state transitions safely")
        void shouldHandleConcurrentTransitions() throws InterruptedException {
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
            AtomicInteger errors = new AtomicInteger(0);
            
            // Create threads that will perform operations concurrently
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread t = new Thread(() -> {
                    try {
                        startLatch.await(); // Wait for start signal
                        for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                            try {
                                if (j % 2 == 0) {
                                    circuitBreaker.recordFailure();
                                } else {
                                    circuitBreaker.recordSuccess();
                                }
                                circuitBreaker.allowRequest();
                                circuitBreaker.getState();
                            } catch (Exception e) {
                                errors.incrementAndGet();
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        endLatch.countDown();
                    }
                });
                threads.add(t);
                t.start();
            }
            
            // Start all threads simultaneously
            startLatch.countDown();
            
            // Wait for all threads to complete
            assertTrue(endLatch.await(30, TimeUnit.SECONDS), 
                "Concurrent operations should complete within timeout");
            assertEquals(0, errors.get(), "Should handle concurrent operations without errors");
            
            // Verify circuit breaker is in a valid state
            CircuitBreaker.CircuitState finalState = circuitBreaker.getState();
            assertTrue(EnumSet.allOf(CircuitBreaker.CircuitState.class).contains(finalState),
                "Circuit breaker should be in a valid state");
        }
    }
}
