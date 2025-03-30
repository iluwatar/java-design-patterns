package com.iluwatar.publish.subscribe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

public class MessageTest {

  @Test
  public void testMessage() {
    final String content = "some content";
    Message message = new Message(content);
    assertInstanceOf(String.class, message.content());
    assertEquals(content, String.valueOf(message.content()));
  }
}
