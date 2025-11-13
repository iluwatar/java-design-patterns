/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ConfigurationManager 테스트.
 */
class ConfigurationManagerTest {

  @BeforeEach
  void setUp() {
    // 각 테스트 전에 설정 초기화
    ConfigurationManager.getInstance().clearAllProperties();
  }

  @Test
  void testSingletonInstance() {
    // Given & When
    ConfigurationManager instance1 = ConfigurationManager.getInstance();
    ConfigurationManager instance2 = ConfigurationManager.getInstance();

    // Then
    assertNotNull(instance1);
    assertNotNull(instance2);
    assertSame(instance1, instance2, "두 인스턴스는 동일해야 함");
  }

  @Test
  void testDefaultProperties() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();

    // When & Then
    assertEquals("Java Design Patterns", config.getProperty("app.name"));
    assertEquals("1.0.0", config.getProperty("app.version"));
    assertEquals("development", config.getProperty("app.environment"));
    assertEquals("100", config.getProperty("db.max.connections"));
    assertEquals("30000", config.getProperty("api.timeout.ms"));
  }

  @Test
  void testSetAndGetProperty() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    String key = "test.property";
    String value = "test.value";

    // When
    config.setProperty(key, value);

    // Then
    assertEquals(value, config.getProperty(key));
  }

  @Test
  void testGetPropertyWithDefault() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    String nonExistentKey = "non.existent.key";
    String defaultValue = "default.value";

    // When
    String result = config.getProperty(nonExistentKey, defaultValue);

    // Then
    assertEquals(defaultValue, result);
  }

  @Test
  void testRemoveProperty() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    String key = "test.property";
    String value = "test.value";
    config.setProperty(key, value);

    // When
    String removedValue = config.removeProperty(key);

    // Then
    assertEquals(value, removedValue);
    assertNull(config.getProperty(key));
  }

  @Test
  void testHasProperty() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    String existingKey = "app.name";
    String nonExistentKey = "non.existent.key";

    // When & Then
    assertTrue(config.hasProperty(existingKey));
    assertFalse(config.hasProperty(nonExistentKey));
  }

  @Test
  void testGetAllProperties() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    config.setProperty("custom.key1", "value1");
    config.setProperty("custom.key2", "value2");

    // When
    Map<String, String> allProperties = config.getAllProperties();

    // Then
    assertNotNull(allProperties);
    assertTrue(allProperties.size() >= 7); // 5 기본 + 2 추가
    assertEquals("value1", allProperties.get("custom.key1"));
    assertEquals("value2", allProperties.get("custom.key2"));
  }

  @Test
  void testClearAllProperties() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    config.setProperty("custom.key", "custom.value");

    // When
    config.clearAllProperties();

    // Then
    assertNull(config.getProperty("custom.key"));
    // 기본 속성은 다시 초기화되어야 함
    assertEquals("Java Design Patterns", config.getProperty("app.name"));
  }

  @Test
  void testSetPropertyWithNullKey() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      config.setProperty(null, "value");
    });
  }

  @Test
  void testSetPropertyWithEmptyKey() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      config.setProperty("", "value");
    });
  }

  @Test
  void testSetPropertyWithWhitespaceKey() {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      config.setProperty("   ", "value");
    });
  }

  @Test
  void testThreadSafety() throws InterruptedException {
    // Given
    ConfigurationManager config = ConfigurationManager.getInstance();
    int threadCount = 10;
    int iterationsPerThread = 100;
    Thread[] threads = new Thread[threadCount];

    // When
    for (int i = 0; i < threadCount; i++) {
      final int threadId = i;
      threads[i] = new Thread(() -> {
        for (int j = 0; j < iterationsPerThread; j++) {
          String key = "thread." + threadId + ".key." + j;
          String value = "value." + j;
          config.setProperty(key, value);
          assertEquals(value, config.getProperty(key));
        }
      });
      threads[i].start();
    }

    // Wait for all threads to complete
    for (Thread thread : threads) {
      thread.join();
    }

    // Then - 모든 속성이 정확히 설정되었는지 확인
    for (int i = 0; i < threadCount; i++) {
      for (int j = 0; j < iterationsPerThread; j++) {
        String key = "thread." + i + ".key." + j;
        assertEquals("value." + j, config.getProperty(key));
      }
    }
  }
}
