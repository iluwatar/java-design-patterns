package com.learning.greetingservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.test.util.ReflectionTestUtils;

class MyCustomHealthCheckTest {

  @Test
  void testHealthUp() {
    MyCustomHealthCheck healthCheck = new MyCustomHealthCheck();
    // Simulate a healthy state
    ReflectionTestUtils.setField(healthCheck, "isHealthy", true);
    Health health = healthCheck.health();
    assertEquals(Status.UP, health.getStatus());
    assertTrue(health.getDetails().containsKey("message"));
    assertEquals(
        "Service is running and scheduled checks are OK", health.getDetails().get("message"));
  }
}
