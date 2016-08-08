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

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * EventManager handles and maintains a pool of event threads. {@link Event} threads are created upon user request. Thre
 * are two types of events; Asynchronous and Synchronous. There can be multiple Asynchronous events running at once but
 * only one Synchronous event running at a time. Currently supported event operations are: start, stop, and getStatus.
 * Once an event is complete, it then notifies EventManager through a listener. The EventManager then takes the event
 * out of the pool.
 *
 */
public class EventManager implements ThreadCompleteListener {

  private int minID = 1;
  private int maxID = Integer.MAX_VALUE - 1; // Be cautious of overflows.
  private int maxRunningEvents = 1000; // no particular reason. Just don't wanna have too many running events. :)
  private int maxEventTime = 1800; // in seconds / 30 minutes.
  private int currentlyRunningSyncEvent = -1;
  private Random rand;
  private Map<Integer, Event> eventPool;

  public EventManager() {
    rand = new Random(1);
    eventPool = new ConcurrentHashMap<Integer, Event>(maxRunningEvents);

  }

  // Create a Synchronous event.
  public int createSyncEvent(int eventTime)
      throws MaxNumOfEventsAllowedException, InvalidOperationException, LongRunningEventException {
    int eventID = createEvent(eventTime);
    if (currentlyRunningSyncEvent != -1) {
      throw new InvalidOperationException(
          "Event [" + currentlyRunningSyncEvent + "] is still running. Please wait until it finishes and try again.");
    }
    currentlyRunningSyncEvent = eventID;

    return eventID;
  }

  // Create an Asynchronous event.
  public int createAsyncEvent(int eventTime) throws MaxNumOfEventsAllowedException, LongRunningEventException {
    return createEvent(eventTime);
  }

  private int createEvent(int eventTime) throws MaxNumOfEventsAllowedException, LongRunningEventException {
    if (eventPool.size() == maxRunningEvents) {
      throw new MaxNumOfEventsAllowedException("Too many events are running at the moment. Please try again later.");
    }

    if (eventTime >= maxEventTime) {
      throw new LongRunningEventException(
          "Maximum event time allowed is " + maxEventTime + " seconds. Please try again.");
    }

    int newEventID = generateID();

    Event newEvent = new Event(newEventID, eventTime);
    newEvent.addListener(this);
    eventPool.put(newEventID, newEvent);

    return newEventID;
  }

  public void startEvent(int eventID) throws EventDoesNotExistException {
    if (!eventPool.containsKey(eventID)) {
      throw new EventDoesNotExistException(eventID + " does not exist.");
    }

    eventPool.get(eventID).start();
  }

  public void stopEvent(int eventID) throws EventDoesNotExistException {
    if (!eventPool.containsKey(eventID)) {
      throw new EventDoesNotExistException(eventID + " does not exist.");
    }

    if (eventID == currentlyRunningSyncEvent) {
      currentlyRunningSyncEvent = -1;
    }

    eventPool.get(eventID).stop();
    eventPool.remove(eventID);
  }

  public void getStatus(int eventID) throws EventDoesNotExistException {
    if (!eventPool.containsKey(eventID)) {
      throw new EventDoesNotExistException(eventID + " does not exist.");
    }

    eventPool.get(eventID).status();
  }

  @SuppressWarnings("rawtypes")
  public void getStatusOfAllEvents() {
    Iterator it = eventPool.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      ((Event) pair.getValue()).status();
    }
  }

  /**
   * Returns a pseudo-random number between min and max, inclusive. The difference between min and max can be at most
   * <code>Integer.MAX_VALUE - 1</code>.
   */
  private int generateID() {
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((maxID - minID) + 1) + minID;
    while (eventPool.containsKey(randomNum)) {
      randomNum = rand.nextInt((maxID - minID) + 1) + minID;
    }

    return randomNum;
  }

  /**
   * Callback from an {@link Event} (once it is complete). The Event is then removed from the pool.
   */
  @Override
  public void notifyOfThreadComplete(int eventID) {
    eventPool.get(eventID).status();
    eventPool.remove(eventID);
  }

}
