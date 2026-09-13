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
package com.iluwatar.loadshedding;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Admission controller that decides whether a request may enter the service. The decision is based
 * on how many requests are already in flight and on the priority of the new request:
 *
 * <ul>
 *   <li>{@link Priority#LOW} requests are admitted only while the in-flight count is below the low
 *       priority limit, so they are the first to be shed when load builds up.
 *   <li>{@link Priority#NORMAL} requests are admitted while the in-flight count is below the hard
 *       capacity minus the reserve kept for critical work.
 *   <li>{@link Priority#CRITICAL} requests may use the full capacity, including the reserve.
 * </ul>
 *
 * <p>Requests that cannot be admitted are rejected immediately with a {@link LoadShedException}
 * rather than queued. Rejecting quickly costs almost nothing, whereas letting an unbounded queue
 * grow would increase latency for every request and eventually exhaust memory or threads.
 *
 * <p>The class is thread-safe. Admission is lock-free: the in-flight count is updated with an
 * atomic accumulator whose function refuses to increment past the limit, so concurrent callers can
 * never push the in-flight count above the configured capacity.
 */
public class LoadShedder {

  private final int maxInFlight;
  private final Map<Priority, Integer> limits = new EnumMap<>(Priority.class);
  private final AtomicInteger inFlight = new AtomicInteger();
  private final LongAdder accepted = new LongAdder();
  private final Map<Priority, LongAdder> shed = new EnumMap<>(Priority.class);

  /**
   * Creates a load shedder.
   *
   * @param maxInFlight hard capacity: the maximum number of requests processed concurrently
   * @param lowPriorityLimit in-flight count at which low priority requests start being shed
   * @param criticalReserve part of the capacity that only critical requests may use
   */
  public LoadShedder(int maxInFlight, int lowPriorityLimit, int criticalReserve) {
    if (maxInFlight <= 0) {
      throw new IllegalArgumentException("maxInFlight must be positive");
    }
    if (criticalReserve < 0 || criticalReserve >= maxInFlight) {
      throw new IllegalArgumentException("criticalReserve must be between 0 and maxInFlight - 1");
    }
    var normalLimit = maxInFlight - criticalReserve;
    if (lowPriorityLimit <= 0 || lowPriorityLimit > normalLimit) {
      throw new IllegalArgumentException(
          "lowPriorityLimit must be between 1 and maxInFlight - criticalReserve");
    }
    this.maxInFlight = maxInFlight;
    limits.put(Priority.CRITICAL, maxInFlight);
    limits.put(Priority.NORMAL, normalLimit);
    limits.put(Priority.LOW, lowPriorityLimit);
    for (var priority : Priority.values()) {
      shed.put(priority, new LongAdder());
    }
  }

  /**
   * Tries to admit the request. On success the in-flight count is incremented and the caller must
   * invoke {@link #release()} once the work is done.
   *
   * @param request the request asking for admission
   * @return the in-flight count including this request, as observed at the moment of admission
   * @throws LoadShedException if the service has no spare capacity for this priority
   */
  public int acquire(Request request) {
    var priority = request.priority();
    var limit = limits.get(priority);
    // The accumulator is a pure function, so it is safe for the atomic to re-apply it under
    // contention: the count is only incremented while it is below the limit for this priority.
    var previous =
        inFlight.getAndAccumulate(
            1, (current, increment) -> current >= limit ? current : current + increment);
    if (previous >= limit) {
      // Fail fast: the caller gets an immediate rejection instead of waiting in a queue.
      shed.get(priority).increment();
      throw new LoadShedException(request, previous, limit);
    }
    accepted.increment();
    return previous + 1;
  }

  /**
   * Signals that a previously admitted request has finished, freeing one slot of capacity. The
   * count is clamped at zero so that an unmatched release cannot make it negative and hand out more
   * capacity than the service has.
   */
  public void release() {
    inFlight.updateAndGet(current -> Math.max(0, current - 1));
  }

  /** Hard capacity of the service. */
  public int getMaxInFlight() {
    return maxInFlight;
  }

  /** Number of requests currently being processed. */
  public int getInFlight() {
    return inFlight.get();
  }

  /** Total number of requests admitted since creation. */
  public long getAccepted() {
    return accepted.sum();
  }

  /** Number of requests of the given priority that were shed since creation. */
  public long getShed(Priority priority) {
    return shed.get(priority).sum();
  }

  /** Total number of requests shed since creation, across all priorities. */
  public long getTotalShed() {
    return shed.values().stream().mapToLong(LongAdder::sum).sum();
  }
}
