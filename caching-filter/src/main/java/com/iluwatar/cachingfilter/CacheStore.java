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
package com.iluwatar.cachingfilter;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Holds cached responses keyed by request, together with the expiration policy: each entry stays
 * valid for a fixed time to live after it is stored. An entry found expired on lookup is dropped.
 *
 * <p>The caller passes the current time into every call, so the store itself has no clock.
 */
@Slf4j
public class CacheStore {

  private record Entry(Response response, Instant expiresAt) {}

  private final Duration timeToLive;
  private final Map<Request, Entry> entries = new ConcurrentHashMap<>();

  /**
   * Create a cache store.
   *
   * @param timeToLive how long a stored response stays valid; must be positive
   */
  public CacheStore(Duration timeToLive) {
    if (timeToLive.isNegative() || timeToLive.isZero()) {
      throw new IllegalArgumentException("Time to live must be positive: " + timeToLive);
    }
    this.timeToLive = timeToLive;
  }

  /**
   * Look up the response stored for a request.
   *
   * @param request the cache key
   * @param now the current time
   * @return the stored response, or empty if there is none or it has expired
   */
  public Optional<Response> get(Request request, Instant now) {
    var entry = entries.get(request);
    if (entry == null) {
      return Optional.empty();
    }
    if (!now.isBefore(entry.expiresAt())) {
      LOGGER.info("Dropping expired entry for {}", request.path());
      entries.remove(request, entry);
      return Optional.empty();
    }
    return Optional.of(entry.response());
  }

  /**
   * Store a response, valid for the time to live from {@code now}.
   *
   * @param request the cache key
   * @param response the response to store
   * @param now the current time
   */
  public void put(Request request, Response response, Instant now) {
    entries.put(request, new Entry(response, now.plus(timeToLive)));
  }

  /** Remove every entry whose request has the given path. */
  public void removePath(String path) {
    entries.keySet().removeIf(request -> request.path().equals(path));
  }

  /** Number of entries currently stored, including expired ones not dropped yet. */
  public int size() {
    return entries.size();
  }
}
