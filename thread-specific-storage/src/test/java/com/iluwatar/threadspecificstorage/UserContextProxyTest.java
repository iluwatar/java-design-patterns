package com.iluwatar.threadspecificstorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UserContextProxy class
 */
class UserContextProxyTest {

  private UserContext userContext;

  @BeforeEach
  void setUp() {
    userContext = new UserContext(123L);
  }

  @AfterEach
  void tearDown() {
    UserContextProxy.clear();
  }

  @Test
  void testSetAndGetContext() {
    UserContextProxy.set(userContext);
    UserContext retrievedContext = UserContextProxy.get();
    assertNotNull(retrievedContext, "Retrieved context should not be null");
    assertEquals(userContext.getUserId(), retrievedContext.getUserId(),
        "Retrieved context should have the same userId");
  }

  @Test
  void testGetContextWhenNotSet() {
    UserContext retrievedContext = UserContextProxy.get();
    assertNull(retrievedContext, "Context should be null when not set");
  }

  @Test
  void testClearContext() {
    UserContextProxy.set(userContext);
    UserContextProxy.clear();
    UserContext retrievedContext = UserContextProxy.get();
    assertNull(retrievedContext, "Context should be null after clearing");
  }

  @Test
  void testThreadIsolation() throws InterruptedException {
    UserContext context1 = new UserContext(123L);
    UserContext context2 = new UserContext(456L);
    UserContextProxy.set(context1);
    // Create another thread to set different context
    Thread thread = new Thread(() -> {
      UserContextProxy.set(context2);
      UserContext threadContext = UserContextProxy.get();
      assertNotNull(threadContext);
      assertEquals(456L, threadContext.getUserId());
    });
    thread.start();
    thread.join();
    // Main thread context should remain unchanged
    UserContext mainThreadContext = UserContextProxy.get();
    assertNotNull(mainThreadContext);
    assertEquals(123L, mainThreadContext.getUserId());
  }
}