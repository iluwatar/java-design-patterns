/**
 * The MIT License Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.event.asynchronous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Each Event runs as a separate/individual thread.
 *
 */
public class Event implements IEvent, Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(Event.class);

  private int eventId;
  private int eventTime;
  private boolean isSynchronous;
  private Thread thread;
  private boolean isComplete = false;
  private ThreadCompleteListener eventListener;

  /**
   *
   * @param eventId event ID
   * @param eventTime event time
   * @param isSynchronous is of synchronous type
   */
  public Event(final int eventId, final int eventTime, final boolean isSynchronous) {
    this.eventId = eventId;
    this.eventTime = eventTime;
    this.isSynchronous = isSynchronous;
  }

  public boolean isSynchronous() {
    return isSynchronous;
  }

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
    long currentTime = System.currentTimeMillis();
    long endTime = currentTime + (eventTime * 1000);
    while (System.currentTimeMillis() < endTime) {
      try {
        Thread.sleep(1000); // Sleep for 1 second.
      } catch (InterruptedException e) {
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

  private final void completed() {
    if (eventListener != null) {
      eventListener.completedEventHandler(eventId);
    }
  }

}
