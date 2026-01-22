package com.iluwatar.threadspecificstorage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Tests for UserContext class */
class UserContextTest {

  @Test
  void testConstructorAndGetUserId() {
    Long userId = 123L;
    UserContext context = new UserContext(userId);

    assertEquals(
        userId, context.getUserId(), "UserId should match the one provided in constructor");
  }

  @Test
  void testSetUserId() {
    UserContext context = new UserContext(123L);
    Long newUserId = 456L;

    context.setUserId(newUserId);

    assertEquals(newUserId, context.getUserId(), "UserId should be updated");
  }

  @Test
  void testToString() {
    Long userId = 123L;
    UserContext context = new UserContext(userId);

    String expected = "UserContext(userId=" + userId + ")";
    assertEquals(expected, context.toString(), "toString should return expected format");
  }

  @Test
  void testEqualsAndHashCode() {
    Long userId = 123L;
    UserContext context1 = new UserContext(userId);
    UserContext context2 = new UserContext(userId);
    UserContext context3 = new UserContext(456L);

    assertEquals(context1, context2, "Objects with same userId should be equal");
    assertEquals(
        context1.hashCode(),
        context2.hashCode(),
        "Objects with same userId should have same hashCode");

    assertNotEquals(context1, context3, "Objects with different userId should not be equal");
    assertNotEquals(
        context1.hashCode(),
        context3.hashCode(),
        "Objects with different userId should have different hashCode");
  }
}
