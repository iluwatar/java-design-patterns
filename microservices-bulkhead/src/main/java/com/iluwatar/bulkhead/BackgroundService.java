package com.iluwatar.bulkhead;

/**
 * Service for handling background processing tasks with dedicated resources.
 *
 * <p>This service handles non-critical, asynchronous operations that can tolerate
 * higher latency. By isolating its resources from user-facing services, failures
 * or slowdowns in background processing don't impact critical user operations.
 *
 * <p>Example use cases:
 * <ul>
 *   <li>Email sending</li>
 *   <li>Report generation</li>
 *   <li>Data synchronization</li>
 *   <li>Scheduled batch jobs</li>
 * </ul>
 */
public class BackgroundService extends BulkheadService {

  private static final int DEFAULT_QUEUE_CAPACITY = 20;

  /**
   * Creates a BackgroundService with specified thread pool size.
   *
   * @param maxThreads maximum number of threads for background processing
   */
  public BackgroundService(int maxThreads) {
    super("BackgroundService", maxThreads, DEFAULT_QUEUE_CAPACITY);
  }

  @Override
  protected void handleRejectedTask(Task task) {
    // For background tasks, we might want to retry later or persist to a queue
    super.handleRejectedTask(task);
    // Additional background-specific rejection logic could go here
    // e.g., persist to database for later retry
  }
}
