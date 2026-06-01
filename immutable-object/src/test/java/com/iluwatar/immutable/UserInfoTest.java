package com.iluwatar.immutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Immutable Object pattern.
 */
class UserInfoTest {

  @Test
  void testCreateImmutableUser() {
    var metadata = Map.of("role", "admin");
    var user = new UserInfo("Alice", "alice@example.com", metadata);

    assertEquals("Alice", user.getName());
    assertEquals("alice@example.com", user.getEmail());
    assertEquals("admin", user.getMetadata().get("role"));
  }

  @Test
  void testMetadataIsUnmodifiable() {
    var user = new UserInfo("Alice", "alice@example.com", Map.of("key", "value"));

    assertThrows(UnsupportedOperationException.class, () -> user.getMetadata().put("new", "val"));
  }

  @Test
  void testWithNameReturnsNewInstance() {
    var original = new UserInfo("Alice", "alice@example.com", Map.of());
    var modified = original.withName("Bob");

    assertNotSame(original, modified);
    assertEquals("Alice", original.getName());
    assertEquals("Bob", modified.getName());
  }

  @Test
  void testWithEmailReturnsNewInstance() {
    var original = new UserInfo("Alice", "alice@example.com", Map.of());
    var modified = original.withEmail("bob@example.com");

    assertNotSame(original, modified);
    assertEquals("alice@example.com", original.getEmail());
    assertEquals("bob@example.com", modified.getEmail());
  }

  @Test
  void testWithMetadataReturnsNewInstance() {
    var original = new UserInfo("Alice", "alice@example.com", Map.of("role", "admin"));
    var modified = original.withMetadata("location", "Beijing");

    assertNotSame(original, modified);
    assertEquals(1, original.getMetadata().size());
    assertEquals(2, modified.getMetadata().size());
    assertEquals("Beijing", modified.getMetadata().get("location"));
  }

  @Test
  void testDefensiveCopy() {
    var mutableMap = new HashMap<String, String>();
    mutableMap.put("key", "value");

    var user = new UserInfo("Alice", "alice@example.com", mutableMap);

    // Mutating the original map does not affect the immutable object
    mutableMap.put("key", "changed");
    assertEquals("value", user.getMetadata().get("key"));
  }

  @Test
  void testChainingWithMethods() {
    var user = new UserInfo("Alice", "alice@example.com", Map.of());
    var result = user.withName("Bob").withEmail("bob@example.com");

    assertEquals("Bob", result.getName());
    assertEquals("bob@example.com", result.getEmail());
  }
}
