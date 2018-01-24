/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.event.sourcing.event;

import java.io.Serializable;

/**
 * This is the base class for domain events. All events must extend this class.
 *
 * Created by Serdar Hamzaogullari on 06.08.2017.
 */
public abstract class DomainEvent implements Serializable {

  private final long sequenceId;
  private final long createdTime;
  private final String eventClassName;
  private boolean realTime = true;

  /**
   * Instantiates a new Domain event.
   *
   * @param sequenceId the sequence id
   * @param createdTime the created time
   * @param eventClassName the event class name
   */
  public DomainEvent(long sequenceId, long createdTime, String eventClassName) {
    this.sequenceId = sequenceId;
    this.createdTime = createdTime;
    this.eventClassName = eventClassName;
  }

  /**
   * Gets sequence id.
   *
   * @return the sequence id
   */
  public long getSequenceId() {
    return sequenceId;
  }

  /**
   * Gets created time.
   *
   * @return the created time
   */
  public long getCreatedTime() {
    return createdTime;
  }

  /**
   * Is real time boolean.
   *
   * @return the boolean
   */
  public boolean isRealTime() {
    return realTime;
  }

  /**
   * Sets real time.
   *
   * @param realTime the real time
   */
  public void setRealTime(boolean realTime) {
    this.realTime = realTime;
  }

  /**
   * Process.
   */
  public abstract void process();

  /**
   * Gets event class name.
   *
   * @return the event class name
   */
  public String getEventClassName() {
    return eventClassName;
  }
}
