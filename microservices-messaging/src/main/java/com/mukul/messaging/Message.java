package com.mukul.messaging;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a message exchanged between services.
 */
@Getter
public class Message {
  private final String id;
  private final String content;
  private final LocalDateTime timestamp;

  public Message(String content) {
    this.id = UUID.randomUUID().toString();
    this.content = content;
    this.timestamp = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Message{" +
        "id='" + id + '\'' +
        ", content='" + content + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }
}
