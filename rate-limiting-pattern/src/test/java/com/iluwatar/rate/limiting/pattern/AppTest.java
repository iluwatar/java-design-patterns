/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link App}. */
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
