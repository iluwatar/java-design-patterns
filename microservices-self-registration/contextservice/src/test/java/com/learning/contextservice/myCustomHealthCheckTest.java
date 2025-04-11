package com.learning.contextservice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.boot.actuate.health.Status;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class MyCustomHealthCheckTest {

  MyCustomHealthCheck healthCheck = new MyCustomHealthCheck();

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
  void testUpdateHealthStatusSetsIsHealthyWithMocking() throws Exception {
    MyCustomHealthCheck healthCheck = Mockito.spy(new MyCustomHealthCheck());

    // Force performHealthCheck to return true
    doReturn(true).when(healthCheck).performHealthCheck();
    healthCheck.updateHealthStatus();
    assertTrue((Boolean)ReflectionTestUtils.getField(healthCheck, "isHealthy"), "Health should be true");

    // Force performHealthCheck to return false
    doReturn(false).when(healthCheck).performHealthCheck();
    healthCheck.updateHealthStatus();
    assertFalse((Boolean)ReflectionTestUtils.getField(healthCheck, "isHealthy"), "Health should be false");
  }
}