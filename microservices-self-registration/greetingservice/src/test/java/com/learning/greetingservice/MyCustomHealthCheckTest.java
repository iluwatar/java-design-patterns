package com.learning.greetingservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.boot.actuate.health.Status;

import java.util.function.BooleanSupplier;

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

  @Test
  void testUpdateHealthStatusSetsIsHealthy() throws Exception {
    MyCustomHealthCheck healthCheck = new MyCustomHealthCheck();

    // Force performHealthCheck to return true (by time simulation - might be flaky)
    long currentTimeForTrue = System.currentTimeMillis();
    while (currentTimeForTrue % 10000 >= 5000) {
      Thread.sleep(10); // Wait until time is in the "true" range
      currentTimeForTrue = System.currentTimeMillis();
    }
    ReflectionTestUtils.invokeMethod(healthCheck, "performHealthCheck");
    healthCheck.updateHealthStatus();
    assertTrue((Boolean) ReflectionTestUtils.getField(healthCheck, "isHealthy"), "Health should be true");

    // Force performHealthCheck to return false (by time simulation - might be flaky)
    long currentTimeForFalse = System.currentTimeMillis();
    while (currentTimeForFalse % 10000 < 5000) {
      Thread.sleep(10); // Wait until time is in the "false" range
      currentTimeForFalse = System.currentTimeMillis();
    }
    ReflectionTestUtils.invokeMethod(healthCheck, "performHealthCheck");
    healthCheck.updateHealthStatus();
    assertFalse((Boolean) ReflectionTestUtils.getField(healthCheck, "isHealthy"), "Health should be false");
  }

  // Note: Directly testing performHealthCheck (the private method) based on time is inherently
  // difficult and can lead to flaky tests. The testUpdateHealthStatus attempts to indirectly
  // verify its behavior. For more robust testing of performHealthCheck in isolation,
  // you might consider refactoring the class to make the time dependency injectable
  // or making the method protected for testing purposes.
}