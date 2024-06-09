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
package com.iluwatar.health.check;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * An asynchronous health checker component that executes health checks in a separate thread.
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AsynchronousHealthChecker {

  /** A scheduled executor service used to execute health checks in a separate thread. */
  private final ScheduledExecutorService healthCheckExecutor =
      Executors.newSingleThreadScheduledExecutor();

  private static final String HEALTH_CHECK_TIMEOUT_MESSAGE = "Health check timed out";
  private static final String HEALTH_CHECK_FAILED_MESSAGE = "Health check failed";

  /**
   * Performs a health check asynchronously using the provided health check logic with a specified
   * timeout.
   *
   * @param healthCheck the health check logic supplied as a {@code Supplier<Health>}
   * @param timeoutInSeconds the maximum time to wait for the health check to complete, in seconds
   * @return a {@code CompletableFuture<Health>} object that represents the result of the health
   *     check
   */
  public CompletableFuture<Health> performCheck(
      Supplier<Health> healthCheck, long timeoutInSeconds) {
    CompletableFuture<Health> future =
        CompletableFuture.supplyAsync(healthCheck, healthCheckExecutor);

    // Schedule a task to enforce the timeout
    healthCheckExecutor.schedule(
        () -> {
          if (!future.isDone()) {
            LOGGER.error(HEALTH_CHECK_TIMEOUT_MESSAGE);
            future.completeExceptionally(new TimeoutException(HEALTH_CHECK_TIMEOUT_MESSAGE));
          }
        },
        timeoutInSeconds,
        TimeUnit.SECONDS);

    return future.handle(
        (result, throwable) -> {
          if (throwable != null) {
            LOGGER.error(HEALTH_CHECK_FAILED_MESSAGE, throwable);
            // Check if the throwable is a TimeoutException or caused by a TimeoutException
            Throwable rootCause =
                throwable instanceof CompletionException ? throwable.getCause() : throwable;
            if (!(rootCause instanceof TimeoutException)) {
              LOGGER.error(HEALTH_CHECK_FAILED_MESSAGE, rootCause);
              return Health.down().withException(rootCause).build();
            } else {
              LOGGER.error(HEALTH_CHECK_TIMEOUT_MESSAGE, rootCause);
              // If it is a TimeoutException, rethrow it wrapped in a CompletionException
              throw new CompletionException(rootCause);
            }
          } else {
            return result;
          }
        });
  }

  /**
   * Checks whether the health check executor service has terminated completely. This method waits
   * for the executor service to finish all its tasks within a specified timeout. If the timeout is
   * reached before all tasks are completed, the method returns `true`; otherwise, it returns
   * `false`.
   *
   * @throws InterruptedException if the current thread is interrupted while waiting for the
   *     executor service to terminate
   */
  private boolean awaitTerminationWithTimeout() throws InterruptedException {
    boolean isTerminationIncomplete = !healthCheckExecutor.awaitTermination(5, TimeUnit.SECONDS);
    LOGGER.info("Termination status: {}", isTerminationIncomplete);
    // Await termination and return true if termination is incomplete (timeout elapsed)
    return isTerminationIncomplete;
  }

  /** Shuts down the executor service, allowing in-flight tasks to complete. */
  @PreDestroy
  public void shutdown() {
    try {
      // Wait a while for existing tasks to terminate
      if (awaitTerminationWithTimeout()) {
        LOGGER.info("Health check executor did not terminate in time");
        // Attempt to cancel currently executing tasks
        healthCheckExecutor.shutdownNow();
        // Wait again for tasks to respond to being cancelled
        if (awaitTerminationWithTimeout()) {
          LOGGER.error("Health check executor did not terminate");
        }
      }
    } catch (InterruptedException ie) {
      // Preserve interrupt status
      Thread.currentThread().interrupt();
      // (Re-)Cancel if current thread also interrupted
      healthCheckExecutor.shutdownNow();
      // Log the stack trace for interrupted exception
      LOGGER.error("Shutdown of the health check executor was interrupted", ie);
    }
  }
}
