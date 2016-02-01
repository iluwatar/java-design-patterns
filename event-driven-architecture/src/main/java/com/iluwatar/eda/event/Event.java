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
package com.iluwatar.eda.event;

import com.iluwatar.eda.framework.EventDispatcher;
import com.iluwatar.eda.framework.Message;

/**
 * The {@link Event} class serves as a base class for defining custom events happening with your
 * system. In this example we have two types of events defined.
 * <ul>
 *   <li>{@link UserCreatedEvent} - used when a user is created</li>
 *   <li>{@link UserUpdatedEvent} - used when a user is updated</li>
 * </ul>
 * Events can be distinguished using the {@link #getType() getType} method.
 */
public class Event implements Message {

  /**
   * Returns the event type as a {@link Class} object
   * In this example, this method is used by the {@link EventDispatcher} to
   * dispatch events depending on their type.
   *
   * @return the Event type as a {@link Class}.
   */
  public Class<? extends Message> getType() {
    return getClass();
  }
}