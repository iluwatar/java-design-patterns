package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link App}.
 *
 */
class AppTest {

  private RateLimiter mockLimiter;

  @BeforeEach
  void setUp() {
    mockLimiter = mock(RateLimiter.class);
    AppTestUtils.resetCounters(); // Ensures counters are clean before every test
  }

  @Test
  void shouldAllowRequest() {
    AppTestUtils.invokeMakeRequest(1, mockLimiter, "s3", "GetObject");
    assertEquals(1, AppTestUtils.getSuccessfulRequests().get(), "Successful count should be 1");
    assertEquals(0, AppTestUtils.getThrottledRequests().get(), "Throttled count should be 0");
    assertEquals(0, AppTestUtils.getFailedRequests().get(), "Failed count should be 0");
  }

  @Test
  void shouldHandleThrottlingException() throws Exception {
    doThrow(new ThrottlingException("s3", "PutObject", 1000)).when(mockLimiter).check(any(), any());
    AppTestUtils.invokeMakeRequest(2, mockLimiter, "s3", "PutObject");
    assertEquals(0, AppTestUtils.getSuccessfulRequests().get());
    assertEquals(1, AppTestUtils.getThrottledRequests().get());
    assertEquals(0, AppTestUtils.getFailedRequests().get());
  }

  @Test
  void shouldHandleServiceUnavailableException() throws Exception {
    doThrow(new ServiceUnavailableException("lambda", 500)).when(mockLimiter).check(any(), any());
    AppTestUtils.invokeMakeRequest(3, mockLimiter, "lambda", "Invoke");
    assertEquals(0, AppTestUtils.getSuccessfulRequests().get());
    assertEquals(0, AppTestUtils.getThrottledRequests().get());
    assertEquals(1, AppTestUtils.getFailedRequests().get());
  }

  @Test
  void shouldHandleGenericException() throws Exception {
    doThrow(new RuntimeException("Unexpected")).when(mockLimiter).check(any(), any());
    AppTestUtils.invokeMakeRequest(4, mockLimiter, "dynamodb", "Query");
    assertEquals(0, AppTestUtils.getSuccessfulRequests().get());
    assertEquals(0, AppTestUtils.getThrottledRequests().get());
    assertEquals(1, AppTestUtils.getFailedRequests().get());
  }

  @Test
  void shouldRunMainMethodWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }
}
