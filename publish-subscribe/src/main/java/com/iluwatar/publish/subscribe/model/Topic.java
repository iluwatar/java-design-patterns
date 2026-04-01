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
package com.iluwatar.publish.subscribe.model;

import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/** This class represents a Topic that topic name and subscribers. */
@Getter
@Setter
@RequiredArgsConstructor
public class Topic {

  private final String topicName;
  private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();

  /**
   * Add a subscriber to the list of subscribers.
   *
   * @param subscriber subscriber to add
   */
  public void addSubscriber(Subscriber subscriber) {
    subscribers.add(subscriber);
  }

  /**
   * Remove a subscriber from the list of subscribers.
   *
   * @param subscriber subscriber to remove
   */
  public void removeSubscriber(Subscriber subscriber) {
    subscribers.remove(subscriber);
  }

  /**
   * Publish a message to subscribers.
   *
   * @param message message with content to publish
   */
  public void publish(Message message) {
    for (Subscriber subscriber : subscribers) {
      CompletableFuture.runAsync(() -> subscriber.onMessage(message));
    }
  }
}
