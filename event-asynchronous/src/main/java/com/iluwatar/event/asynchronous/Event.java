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

package com.iluwatar.event.asynchronous;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Each Event runs as a separate/individual thread.
 */
@Slf4j
@RequiredArgsConstructor
public class Event implements IEvent, Runnable {

  private final int eventId;
  private final int eventTime;
  @Getter
  private final boolean synchronous;
  private Thread thread;
  private boolean isComplete = false;
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
    if (!isComplete) {
      LOGGER.info("[{}] is not done.", eventId);
    } else {
      LOGGER.info("[{}] is done.", eventId);
    }
  }

  @Override
  public void run() {
    var currentTime = System.currentTimeMillis();
    var endTime = currentTime + (eventTime * 1000);
    while (System.currentTimeMillis() < endTime) {
      try {
        Thread.sleep(1000); // Sleep for 1 second.
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }
    isComplete = true;
    completed();
  }

  public final void addListener(final ThreadCompleteListener listener) {
    this.eventListener = listener;
  }

  public final void removeListener(final ThreadCompleteListener listener) {
    this.eventListener = null;
  }

  private void completed() {
    if (eventListener != null) {
      eventListener.completedEventHandler(eventId);
    }
  }

}
