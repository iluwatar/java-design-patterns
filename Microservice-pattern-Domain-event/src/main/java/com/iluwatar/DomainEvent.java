package com.iluwatar;

import java.time.LocalDateTime;

public abstract class DomainEvent {
  private final LocalDateTime timestamp;

  public DomainEvent() {
    this.timestamp = LocalDateTime.now();
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}
