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