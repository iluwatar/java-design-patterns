package com.iluwatar.bulkhead;

/**
 * Enumeration of task types in the system.
 *
 * <p>Different task types may have different priorities, resource requirements,
 * and SLA expectations. The bulkhead pattern allows us to isolate resources
 * based on these types.
 */
public enum TaskType {

  /**
   * User-facing requests that require low latency and high availability.
   */
  USER_REQUEST,

  /**
   * Background processing tasks that can tolerate higher latency.
   */
  BACKGROUND_PROCESSING
}
