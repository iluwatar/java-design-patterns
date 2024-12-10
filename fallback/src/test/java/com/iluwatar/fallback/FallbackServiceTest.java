package com.iluwatar.fallback;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link FallbackService}.
 * Tests the fallback mechanism, circuit breaker integration, and service stability.
 */
class FallbackServiceTest {
    @Mock private Service primaryService;
    @Mock private Service fallbackService;
    @Mock private CircuitBreaker circuitBreaker;
    private FallbackService service;

    private static final int CONCURRENT_THREADS = 5;
    private static final int REQUEST_COUNT = 100;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new FallbackService(primaryService, fallbackService, circuitBreaker);
    }

    @Nested
    @DisplayName("Primary Service Tests")
    class PrimaryServiceTests {
        @Test
        @DisplayName("Should use primary service when healthy")
        void shouldUsePrimaryServiceWhenHealthy() throws Exception {
            // Given
            when(circuitBreaker.isOpen()).thenReturn(false);
            when(circuitBreaker.allowRequest()).thenReturn(true);  // Add this line
            when(primaryService.getData()).thenReturn("success");
            doNothing().when(circuitBreaker).recordSuccess();      // Add this line

            // When
            String result = service.getData();

            // Then
            assertEquals("success", result);
            verify(primaryService).getData();
            verify(fallbackService, never()).getData();
            verify(circuitBreaker).recordSuccess();
        }

        @Test
        @DisplayName("Should retry primary service on failure")
        void shouldRetryPrimaryServiceOnFailure() throws Exception {
            // Given
            when(circuitBreaker.isOpen()).thenReturn(false);
            when(circuitBreaker.allowRequest()).thenReturn(true);
            when(primaryService.getData())
                .thenThrow(new TimeoutException())
                .thenThrow(new TimeoutException())
                .thenReturn("success");

            // Make sure circuit breaker allows the retry attempts
            doNothing().when(circuitBreaker).recordFailure();
            doNothing().when(circuitBreaker).recordSuccess();

            // When
            String result = service.getData();

            // Then
            assertEquals("success", result, "Should return success after retries");
            verify(primaryService, times(3)).getData();
            verify(circuitBreaker, times(2)).recordFailure();
            verify(circuitBreaker).recordSuccess();
            verify(fallbackService, never()).getData();
        }
    }

    @Nested
    @DisplayName("Fallback Service Tests")
    class FallbackServiceTests {
        @Test
        @DisplayName("Should use fallback when circuit breaker is open")
        void shouldUseFallbackWhenCircuitBreakerOpen() throws Exception {
            // Given
            when(circuitBreaker.isOpen()).thenReturn(true);
            when(fallbackService.getData()).thenReturn("fallback");

            // When
            String result = service.getData();

            // Then
            assertEquals("fallback", result);
            verify(primaryService, never()).getData();
            verify(fallbackService).getData();
        }

        @Test
        @DisplayName("Should handle fallback service failure")
        void shouldHandleFallbackServiceFailure() throws Exception {
            // Given
            when(circuitBreaker.isOpen()).thenReturn(false);
            when(circuitBreaker.allowRequest()).thenReturn(true);  // Add this line
            when(primaryService.getData())
                .thenThrow(new TimeoutException());
            when(fallbackService.getData())
                .thenThrow(new Exception("Fallback failed"));

            // When
            String result = service.getData();

            // Then
            assertEquals("Service temporarily unavailable", result);
            verify(primaryService, atLeast(1)).getData();  // Changed verification
            verify(fallbackService).getData();
            verify(circuitBreaker, atLeast(1)).recordFailure();  // Add verification
        }
    }

    @Nested
    @DisplayName("Service Stability Tests")
    class ServiceStabilityTests {
        @Test
        @DisplayName("Should handle concurrent requests")
        void shouldHandleConcurrentRequests() throws Exception {
            // Given
            when(circuitBreaker.isOpen()).thenReturn(false);
            when(primaryService.getData()).thenReturn("success");

            // When
            ExecutorService executor = Executors.newFixedThreadPool(CONCURRENT_THREADS);
            CountDownLatch latch = new CountDownLatch(REQUEST_COUNT);
            AtomicInteger successCount = new AtomicInteger();

            // Submit concurrent requests
            for (int i = 0; i < REQUEST_COUNT; i++) {
                executor.execute(() -> {
                    try {
                        service.getData();
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        // Count as failure
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Then
            assertTrue(latch.await(30, TimeUnit.SECONDS));
            assertTrue(successCount.get() >= 85, "Success rate should be at least 85%");
            
            executor.shutdown();
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
        }

        @Test
        @DisplayName("Should maintain monitoring metrics")
        void shouldMaintainMonitoringMetrics() throws Exception {
            // Given
            when(circuitBreaker.isOpen()).thenReturn(false);
            when(circuitBreaker.allowRequest()).thenReturn(true);
            
            // Set up primary service to succeed, then fail, then succeed
            when(primaryService.getData())
                .thenReturn("success")
                .thenThrow(new TimeoutException("Simulated timeout"))
                .thenReturn("success");
                
            // Set up fallback service
            when(fallbackService.getData()).thenReturn("fallback");
            
            // Configure circuit breaker behavior
            doNothing().when(circuitBreaker).recordSuccess();
            doNothing().when(circuitBreaker).recordFailure();
            
            // When - First call: success from primary
            String result1 = service.getData();
            assertEquals("success", result1);
            
            // Second call: primary fails, use fallback
            when(circuitBreaker.allowRequest()).thenReturn(false);  // Force fallback
            String result2 = service.getData();
            assertEquals("fallback", result2);
            
            // Third call: back to primary
            when(circuitBreaker.allowRequest()).thenReturn(true);
            String result3 = service.getData();
            assertEquals("success", result3);

            // Then
            ServiceMonitor monitor = service.getMonitor();
            assertEquals(2, monitor.getSuccessCount());
            assertEquals(1, monitor.getErrorCount());
            assertTrue(monitor.getSuccessRate() > 0.5);
            
            // Verify interactions
            verify(primaryService, times(3)).getData();
            verify(fallbackService, times(1)).getData();
        }
    }

    @Test
    @DisplayName("Should close resources properly")
    void shouldCloseResourcesProperly() throws Exception {
        // When
        service.close();

        // Then
        assertThrows(ServiceException.class, () -> service.getData());
    }
}
