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
package com.iluwatar.event.asynchronous;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Each Event runs as a separate/individual thread.
 */
@Slf4j
@RequiredArgsConstructor
public class AsyncEvent implements Event, Runnable {

  private final int eventId;
  private final Duration eventTime;
  @Getter
  private final boolean synchronous;
  private Thread thread;
  private final AtomicBoolean isComplete = new AtomicBoolean(false);
  private ThreadCompleteListener eventListener;

  @Override
  public void start() {
    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void stop() {
    if (null == thread) {
      return;
    }
    thread.interrupt();
  }

  @Override
  public void status() {
    if (isComplete.get()) {
      LOGGER.info("[{}] is not done.", eventId);
    } else {
      LOGGER.info("[{}] is done.", eventId);
    }
  }

  @Override
  public void run() {

    var currentTime = Instant.now();
    var endTime = currentTime.plusSeconds(eventTime.getSeconds());
    while (Instant.now().compareTo(endTime) < 0) {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        LOGGER.error("Thread was interrupted: ", e);
        Thread.currentThread().interrupt();
        return;
      }
    }
    isComplete.set(true);
    completed();
  }

  public final void addListener(final ThreadCompleteListener listener) {
    this.eventListener = listener;
  }

  private void completed() {
    if (eventListener != null) {
      eventListener.completedEventHandler(eventId);
    }
  }
}