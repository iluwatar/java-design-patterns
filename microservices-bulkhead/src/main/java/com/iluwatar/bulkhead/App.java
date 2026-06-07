/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK
 * framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License Copyright © 2014-2025 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.bulkhead;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Bulkhead pattern isolates critical system resources for each service or component to prevent
 * failures or heavy load in one part from cascading and degrading the entire system.
 *
 * <p>In this example, we demonstrate resource isolation using separate thread pools for different
 * services. The {@link UserService} handles user-facing requests while {@link BackgroundService}
 * handles background tasks. Each service has its own dedicated thread pool (bulkhead), ensuring
 * that if one service becomes overloaded, the other continues to function normally.
 *
 * <p>Key concepts demonstrated:
 *
 * <ul>
 *   <li>Resource Isolation: Separate thread pools for different services
 *   <li>Fail-Fast: Quick rejection when resources are exhausted
 *   <li>Resilience: Services remain operational even when others fail
 * </ul>
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws InterruptedException {
    LOGGER.info("Starting Bulkhead Pattern demonstration");

    BulkheadService userService = new UserService(3);
    BulkheadService backgroundService = new BackgroundService(2);

    try {
      LOGGER.info("\n=== Scenario 1: Normal Operation ===");
      demonstrateNormalOperation(userService, backgroundService);

      Thread.sleep(2000);

      LOGGER.info("\n=== Scenario 2: Background Service Overload ===");
      demonstrateServiceOverload(userService, backgroundService);

      Thread.sleep(3000);

      LOGGER.info("\n=== Scenario 3: Resource Isolation ===");
      demonstrateResourceIsolation(userService, backgroundService);

    } finally {
      userService.shutdown();
      backgroundService.shutdown();
      LOGGER.info("Bulkhead Pattern demonstration completed");
    }
  }

  /**
   * Demonstrates normal operation where both services handle requests successfully.
   *
   * @param userService the user-facing service
   * @param backgroundService the background processing service
   */
  private static void demonstrateNormalOperation(
      BulkheadService userService, BulkheadService backgroundService) {
    LOGGER.info("Submitting normal workload to both services");
    for (int i = 1; i <= 3; i++) {
      userService.submitTask(new Task("User-Request-" + i, TaskType.USER_REQUEST, 500));
    }
    for (int i = 1; i <= 2; i++) {
      backgroundService.submitTask(
          new Task("Background-Job-" + i, TaskType.BACKGROUND_PROCESSING, 700));
    }
  }

  /**
   * Demonstrates overloading the background service while user service remains operational. This
   * shows how the bulkhead pattern prevents cascade failures.
   *
   * @param userService the user-facing service
   * @param backgroundService the background processing service
   */
  private static void demonstrateServiceOverload(
      BulkheadService userService, BulkheadService backgroundService) {
    LOGGER.info("Overloading background service - user service should remain responsive");
    for (int i = 1; i <= 10; i++) {
      backgroundService.submitTask(
          new Task("Heavy-Background-Job-" + i, TaskType.BACKGROUND_PROCESSING, 2000));
    }
    for (int i = 1; i <= 3; i++) {
      boolean accepted =
          userService.submitTask(
              new Task("Critical-User-Request-" + i, TaskType.USER_REQUEST, 300));
      LOGGER.info("User request {} accepted: {}", i, accepted);
    }
  }

  /**
   * Demonstrates resource isolation by showing independent operation of services.
   *
   * @param userService the user-facing service
   * @param backgroundService the background processing service
   */
  private static void demonstrateResourceIsolation(
      BulkheadService userService, BulkheadService backgroundService) {
    LOGGER.info("Demonstrating resource isolation between services");
    LOGGER.info(
        "User Service - Active threads: {}, Queue size: {}",
        userService.getActiveThreads(),
        userService.getQueueSize());
    LOGGER.info(
        "Background Service - Active threads: {}, Queue size: {}",
        backgroundService.getActiveThreads(),
        backgroundService.getQueueSize());
  }
}
