package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

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
}
