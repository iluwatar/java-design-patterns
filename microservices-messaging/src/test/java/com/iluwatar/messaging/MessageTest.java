/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link Message}.
 * Tests follow FIRST principles: Fast, Isolated, Repeatable, Self-validating, Timely.
 */
class MessageTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Test
  void testMessageCreation() {
    // Arrange & Act
    var message = new Message("Test content");

    // Assert
    assertNotNull(message.getId(), "Message ID should not be null");
    assertEquals("Test content", message.getContent(), "Content should match");
    assertNotNull(message.getTimestamp(), "Timestamp should not be null");
  }

  @Test
  void testMessageIdIsUnique() {
    // Arrange & Act
    var message1 = new Message("Content 1");
    var message2 = new Message("Content 2");

    // Assert
    assertNotEquals(message1.getId(), message2.getId(), "Each message should have unique ID");
  }

  @Test
  void testMessageTimestamp() {
    // Arrange
    var beforeCreation = LocalDateTime.now();

    // Act
    var message = new Message("Test");
    var afterCreation = LocalDateTime.now();

    // Assert
    assertTrue(message.getTimestamp().isAfter(beforeCreation.minusSeconds(1))
            && message.getTimestamp().isBefore(afterCreation.plusSeconds(1)),
        "Timestamp should be close to creation time");
  }

  @Test
  void testJsonSerialization() throws Exception {
    // Arrange
    var originalMessage = new Message("Test content");

    // Act
    var json = objectMapper.writeValueAsString(originalMessage);

    // Assert
    assertNotNull(json, "JSON should not be null");
    assertTrue(json.contains("Test content"), "JSON should contain content");
    assertTrue(json.contains(originalMessage.getId()), "JSON should contain ID");
  }

  @Test
  void testJsonDeserialization() throws Exception {
    // Arrange
    var originalMessage = new Message("Test content");
    var json = objectMapper.writeValueAsString(originalMessage);

    // Act
    var deserializedMessage = objectMapper.readValue(json, Message.class);

    // Assert
    assertNotNull(deserializedMessage, "Deserialized message should not be null");
    assertEquals(originalMessage.getId(), deserializedMessage.getId(), "IDs should match");
    assertEquals(originalMessage.getContent(), deserializedMessage.getContent(),
        "Content should match");
  }

  @Test
  void testToString() {
    // Arrange
    var message = new Message("Test content");

    // Act
    var result = message.toString();

    // Assert
    assertNotNull(result, "ToString should not return null");
    assertTrue(result.contains("Message{"), "ToString should contain class name");
    assertTrue(result.contains("Test content"), "ToString should contain content");
    assertTrue(result.contains(message.getId()), "ToString should contain ID");
  }

  @Test
  void testMessageWithEmptyContent() {
    // Arrange & Act
    var message = new Message("");

    // Assert
    assertNotNull(message.getId(), "ID should be generated even for empty content");
    assertEquals("", message.getContent(), "Empty content should be preserved");
  }

  @Test
  void testMessageWithNullContent() {
    // Arrange & Act
    var message = new Message(null);

    // Assert
    assertNotNull(message.getId(), "ID should be generated even for null content");
    assertEquals(null, message.getContent(), "Null content should be preserved");
  }

  @Test
  void testMessageWithSpecialCharacters() {
    // Arrange
    var specialContent = "Test with special chars: @#$%^&*()";

    // Act
    var message = new Message(specialContent);

    // Assert
    assertEquals(specialContent, message.getContent(),
        "Special characters should be preserved");
  }
}
