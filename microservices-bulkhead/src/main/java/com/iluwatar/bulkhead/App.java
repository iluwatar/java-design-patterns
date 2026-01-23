package com.iluwatar.bulkhead;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Bulkhead pattern isolates critical system resources for each service or component
 * to prevent failures or heavy load in one part from cascading and degrading the entire system.
 *
 * <p>In this example, we demonstrate resource isolation using separate thread pools for
 * different services. The {@link UserService} handles user-facing requests while
 * {@link BackgroundService} handles background tasks. Each service has its own dedicated
 * thread pool (bulkhead), ensuring that if one service becomes overloaded, the other
 * continues to function normally.
 *
 * <p>Key concepts demonstrated:
 * <ul>
 *   <li>Resource Isolation: Separate thread pools for different services</li>
 *   <li>Fail-Fast: Quick rejection when resources are exhausted</li>
 *   <li>Resilience: Services remain operational even when others fail</li>
 * </ul>
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    LOGGER.info("Starting Bulkhead Pattern demonstration");

    // Create bulkhead services with different thread pool sizes
    BulkheadService userService = new UserService(3); // 3 threads for user requests
    BulkheadService backgroundService = new BackgroundService(2); // 2 threads for background tasks

    try {
      // Scenario 1: Normal operation - both services handle requests successfully
      LOGGER.info("\n=== Scenario 1: Normal Operation ===");
      demonstrateNormalOperation(userService, backgroundService);

      Thread.sleep(2000); // Wait for tasks to complete

      // Scenario 2: Overload background service - user service should remain unaffected
      LOGGER.info("\n=== Scenario 2: Background Service Overload ===");
      demonstrateServiceOverload(userService, backgroundService);

      Thread.sleep(3000); // Wait for demonstration to complete

      // Scenario 3: Show resource isolation
      LOGGER.info("\n=== Scenario 3: Resource Isolation ===");
      demonstrateResourceIsolation(userService, backgroundService);

    } catch (InterruptedException e) {
      LOGGER.error("Demonstration interrupted", e);
      Thread.currentThread().interrupt();
    } finally {
      // Cleanup
      LOGGER.info("\nShutting down services");
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
      BulkheadService userService,
      BulkheadService backgroundService) {

    LOGGER.info("Submitting normal workload to both services");

    // Submit tasks to user service
    for (int i = 1; i <= 3; i++) {
      Task task = new Task("User-Request-" + i, TaskType.USER_REQUEST, 500);
      userService.submitTask(task);
    }

    // Submit tasks to background service
    for (int i = 1; i <= 2; i++) {
      Task task = new Task("Background-Job-" + i, TaskType.BACKGROUND_PROCESSING, 700);
      backgroundService.submitTask(task);
    }
  }

  /**
   * Demonstrates overloading the background service while user service remains operational.
   * This shows how the bulkhead pattern prevents cascade failures.
   *
   * @param userService the user-facing service
   * @param backgroundService the background processing service
   */
  private static void demonstrateServiceOverload(
      BulkheadService userService,
      BulkheadService backgroundService) {

    LOGGER.info("Overloading background service - user service should remain responsive");

    // Flood background service with tasks (more than its capacity)
    for (int i = 1; i <= 10; i++) {
      Task task = new Task("Heavy-Background-Job-" + i, TaskType.BACKGROUND_PROCESSING, 2000);
      backgroundService.submitTask(task);
    }

    // User service should still accept requests
    for (int i = 1; i <= 3; i++) {
      Task task = new Task("Critical-User-Request-" + i, TaskType.USER_REQUEST, 300);
      boolean accepted = userService.submitTask(task);
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
      BulkheadService userService,
      BulkheadService backgroundService) {

    LOGGER.info("Demonstrating resource isolation between services");
    LOGGER.info("User Service - Active threads: {}, Queue size: {}",
        userService.getActiveThreads(), userService.getQueueSize());
    LOGGER.info("Background Service - Active threads: {}, Queue size: {}",
        backgroundService.getActiveThreads(), backgroundService.getQueueSize());
  }
}