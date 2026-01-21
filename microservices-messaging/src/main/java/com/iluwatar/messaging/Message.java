package com.iluwatar.messaging;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  /**
   * Creates a new message with the given content.
   *
   * @param content the message content
   */
  public Message(String content) {
    this.id = UUID.randomUUID().toString();
    this.content = content;
    this.timestamp = LocalDateTime.now();
  }

  /**
   * JSON constructor for deserialization.
   */
  @JsonCreator
  public Message(
      @JsonProperty("id") String id,
      @JsonProperty("content") String content,
      @JsonProperty("timestamp") LocalDateTime timestamp) {
    this.id = id;
    this.content = content;
    this.timestamp = timestamp;
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