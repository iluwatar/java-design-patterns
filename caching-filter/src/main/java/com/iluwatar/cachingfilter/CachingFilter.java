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

import java.time.InstantSource;
import lombok.extern.slf4j.Slf4j;

/**
 * A {@link Filter} that answers repeated requests from a {@link CacheStore}. On a hit that has not
 * expired it returns the stored response without calling the rest of the chain. On a miss, or once
 * the entry has expired, it passes the request on and stores the new response.
 *
 * <p>Only successful {@code GET} responses are cached, since error responses should not hide a
 * resource that appears later. Any other method may change state, so it bypasses the cache and then
 * evicts the cached entries for its path, and the next {@code GET} reaches the handler again.
 *
 * <p>The current time comes from an {@link InstantSource}, so a {@link java.time.Clock} works in
 * production and a test can move time forward without sleeping.
 */
@Slf4j
public class CachingFilter implements Filter {

  private final InstantSource time;
  private final CacheStore store;

  /**
   * Create a caching filter.
   *
   * @param time the source of the current time
   * @param store where responses are kept, with their expiration policy
   */
  public CachingFilter(InstantSource time, CacheStore store) {
    this.time = time;
    this.store = store;
  }

  @Override
  public Response filter(Request request, FilterChain chain) {
    if (!"GET".equals(request.method())) {
      var response = chain.proceed(request);
      store.removePath(request.path());
      return response;
    }
    var now = time.instant();
    var cached = store.get(request, now);
    if (cached.isPresent()) {
      LOGGER.info("Cache hit for {}", request.path());
      return cached.get();
    }
    LOGGER.info("Cache miss for {}", request.path());
    var response = chain.proceed(request);
    if (response.status() == Response.OK) {
      store.put(request, response, now);
    }
    return response;
  }
}
