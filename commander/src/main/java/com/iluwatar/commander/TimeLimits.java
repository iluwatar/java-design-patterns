package com.iluwatar.commander;

/**
 * Record to hold parameters related to time limit
 * for various tasks.
 * @param queueTime time limit for queue
 * @param queueTaskTime time limit for queuing task
 * @param paymentTime time limit for payment error message
 * @param messageTime time limit for message time order
 * @param employeeTime time limit for employee handle time
 */
public record TimeLimits(long queueTime, long queueTaskTime, long paymentTime,
                         long messageTime, long employeeTime) {

  public static final TimeLimits DEFAULT = new TimeLimits(240000L, 60000L, 120000L, 150000L, 240000L);
}