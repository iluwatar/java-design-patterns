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
package com.iluwatar.actormodel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import lombok.Setter;

public abstract class Actor implements Runnable {

  @Setter @Getter private String actorId;
  private final BlockingQueue<Message> mailbox = new LinkedBlockingQueue<>();
  private volatile boolean active =
      true; // always read from main memory and written back to main memory,

  // rather than being cached in a thread's local memory. To make it consistent to all Actors

  public void send(Message message) {
    mailbox.add(message); // Add message to queue
  }

  public void stop() {
    active = false; // Stop the actor loop
  }

  @Override
  public void run() {
    while (active) {
      try {
        Message message = mailbox.take(); // Wait for a message
        onReceive(message); // Process it
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  // Child classes must define what to do with a message
  protected abstract void onReceive(Message message);
}
