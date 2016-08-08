/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
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

/**
 * 
 * Each Event runs as a separate/individual thread.
 *
 */
public class Event implements IEvent, Runnable {

  private int eventID;
  private int eventTime;
  private Thread thread;
  private long counter = 0;
  private boolean isComplete = false;
  private ThreadCompleteListener eventListener;

  public Event(int eventID, int eventTime) {
    this.eventID = eventID;
    this.eventTime = eventTime;
  }

  @Override
  public void start() {
    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void stop() {
    thread.interrupt();
  }

  @Override
  public void status() {
    if (!isComplete) {
      System.out.println("[" + eventID + "] I am at not done. [" + counter + "%]");
    } else {
      System.out.println("[" + eventID + "] I am done.");
    }
  }

  @Override
  public void run() {
    long currentTime = System.currentTimeMillis();
    long endTime = currentTime + (eventTime * 1000);
    while (System.currentTimeMillis() < endTime) {
      try {
        counter += 1;
        Thread.sleep(5000); // Sleep for 5 seconds.
      } catch (InterruptedException e) {
        return;
      }
    }
    isComplete = true;
    notifyListener();
  }

  public final void addListener(final ThreadCompleteListener listener) {
    this.eventListener = listener;
  }

  public final void removeListener(final ThreadCompleteListener listener) {
    this.eventListener = null;
  }

  private final void notifyListener() {
    if (eventListener != null) {
      eventListener.notifyOfThreadComplete(eventID);
    }
  }

}
