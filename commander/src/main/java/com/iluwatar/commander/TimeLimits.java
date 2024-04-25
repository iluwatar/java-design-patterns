package com.iluwatar.commander;

public record TimeLimits(long queueTime, long queueTaskTime, long paymentTime,
                         long messageTime, long employeeTime) {

  public static final TimeLimits DEFAULT = new TimeLimits(240000L, 60000L, 120000L, 150000L, 240000L);
}