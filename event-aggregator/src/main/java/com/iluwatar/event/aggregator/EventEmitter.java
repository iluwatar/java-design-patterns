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

package com.iluwatar.event.aggregator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * EventEmitter is the base class for event producers that can be observed.
 */
public abstract class EventEmitter {

  private final HashMap<Event, ArrayList<EventObserver>> observers;

  public EventEmitter() {
    observers = new HashMap<>();
  }

  public EventEmitter(EventObserver obs, Event e) {
    this();
    registerObserver(obs, e);
  }

  public EventEmitter(EventObserver obs, ArrayList<Event> e) {
    this();
    registerObserver(obs, e);
  }

  /**
   * method to register one event.
   */
  public final void registerObserver(EventObserver obs, Event e) {
    if (!observers.containsKey(e)) {
      observers.put(e, new ArrayList<>());
    }

    observers.get(e).add(obs);
  }

  /**
   * method to register multiple events at once.
   */
  public final void registerObserver(EventObserver obs, ArrayList<Event> e) {
    e.forEach((Event x) -> {
      if (!observers.containsKey(x)) {
        observers.put(x, new ArrayList<>());
      }

      observers.get(x).add(obs);
    });

  }

  /**
   * unregistering event method for observers.
   */
  public final void unregisterObserver(EventObserver obs, Event e) {

    if (observers.containsKey(e)) {
      observers.get(e).remove(obs);
    }
  }

  /**
   * unregistering multiple event method for observers.
   */
  public final void unregisterObserver(EventObserver obs, ArrayList<Event> e) {
    e.forEach((Event x) -> {
      if (observers.containsKey(x)) {
        observers.get(x).remove(obs);
      }
    });
  }

  protected void notifyObservers(Event e) {
    if (observers.containsKey(e)) {
      observers.get(e).forEach(obs -> obs.onEvent(e));
    }
  }

  public abstract void timePasses(Weekday day);
}
