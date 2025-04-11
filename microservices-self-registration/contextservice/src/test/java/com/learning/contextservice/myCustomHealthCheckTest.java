package com.learning.contextservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.boot.actuate.health.Status;
import static org.junit.jupiter.api.Assertions.*;

class MyCustomHealthCheckTest {

  @Test
  void testHealthUp() {
    MyCustomHealthCheck healthCheck = new MyCustomHealthCheck();
    // Simulate a healthy state
    ReflectionTestUtils.setField(healthCheck, "isHealthy", true);
    Health health = healthCheck.health();
    assertEquals(Status.UP, health.getStatus());
    assertTrue(health.getDetails().containsKey("message"));
    assertEquals("Service is running and scheduled checks are OK", health.getDetails().get("message"));
  }

  @Test
  void testHealthDown() {
    MyCustomHealthCheck healthCheck = new MyCustomHealthCheck();
    // Simulate an unhealthy state
    ReflectionTestUtils.setField(healthCheck, "isHealthy", false);
    Health health = healthCheck.health();
    assertEquals(Status.DOWN, health.getStatus());
    assertTrue(health.getDetails().containsKey("error"));
    assertEquals("Scheduled health checks failed", health.getDetails().get("error"));
  }

}