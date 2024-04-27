package com.iluwatar.commander;

/**
 * Record to hold parameters related to time limit
 * for various tasks
 * @param queueTime
 * @param queueTaskTime
 * @param paymentTime
 * @param messageTime
 * @param employeeTime
 */
public record TimeLimits(long queueTime, long queueTaskTime, long paymentTime,
                         long messageTime, long employeeTime) {

  public static final TimeLimits DEFAULT = new TimeLimits(240000L, 60000L, 120000L, 150000L, 240000L);
}