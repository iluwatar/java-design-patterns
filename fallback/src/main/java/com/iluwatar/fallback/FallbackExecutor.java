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
package com.iluwatar.fallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Orchestrates primary service execution with timeouts, circuit breaker health checks, and fallback
 * execution logic. Implements AutoCloseable to ensure thread pools are closed cleanly.
 */
public class FallbackExecutor implements AutoCloseable {
  private static final Logger LOGGER = LoggerFactory.getLogger(FallbackExecutor.class);
  private final ExecutorService executorService;

  /** Constructor for FallbackExecutor. Initializes a virtual thread per task executor. */
  public FallbackExecutor() {
    this.executorService = Executors.newVirtualThreadPerTaskExecutor();
  }

  /**
   * Executes the primary service call. If it fails, times out, or the circuit breaker is open, it
   * falls back to the fallback service.
   *
   * @param primary the primary service call to execute
   * @param fallback the fallback service to call when primary fails or is bypassed
   * @param circuitBreaker the circuit breaker monitoring service health
   * @param timeoutMs timeout limit for the primary service in milliseconds
   * @return response string
   */
  public String execute(
      RemoteService primary,
      RemoteService fallback,
      SimpleCircuitBreaker circuitBreaker,
      long timeoutMs) {

    // 1. Check Circuit Breaker
    if (circuitBreaker.getState() == SimpleCircuitBreaker.State.OPEN) {
      LOGGER.warn("Circuit is OPEN. Fast-failing and calling fallback service.");
      try {
        return fallback.execute();
      } catch (Exception ex) {
        LOGGER.error("Fallback service execution failed: {}", ex.getMessage());
        return "Fallback Error";
      }
    }

    // 2. Attempt service call with timeout
    Callable<String> task = primary::execute;
    Future<String> future = executorService.submit(task);

    try {
      String result = future.get(timeoutMs, TimeUnit.MILLISECONDS);
      circuitBreaker.recordSuccess();
      return result;
    } catch (TimeoutException e) {
      LOGGER.error("Service call timed out. Triggering fallback.");
      future.cancel(true); // Interrupt / cancel the task
      circuitBreaker.recordFailure();
      try {
        return fallback.execute();
      } catch (Exception ex) {
        LOGGER.error("Fallback service execution failed: {}", ex.getMessage());
        return "Fallback Error";
      }
    } catch (Exception e) {
      LOGGER.error("Service call failed with exception: {}. Triggering fallback.", e.getMessage());
      circuitBreaker.recordFailure();
      try {
        return fallback.execute();
      } catch (Exception ex) {
        LOGGER.error("Fallback service execution failed: {}", ex.getMessage());
        return "Fallback Error";
      }
    }
  }

  @Override
  public void close() {
    executorService.shutdown();
  }
}
