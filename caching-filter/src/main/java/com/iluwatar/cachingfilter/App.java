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
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

/**
 * The Caching Filter pattern puts a cache in a request-processing chain. The {@link CachingFilter}
 * answers a repeated request from the cache until the stored response expires, and only then lets
 * the request reach the {@link ProductCatalog} at the end of the {@link FilterChain}.
 *
 * <p>The example uses a time source it moves by hand, so the expiry shows up without waiting.
 */
@Slf4j
public class App {

  private static final Duration TIME_TO_LIVE = Duration.ofSeconds(30);

  /** Program entry point. */
  public static void main(String[] args) {
    var now = new AtomicReference<>(Instant.parse("2026-01-01T09:00:00Z"));
    var catalog =
        new ProductCatalog(
            Map.of(
                "/products/1", "Espresso machine, 199 EUR",
                "/products/2", "Coffee grinder, 89 EUR"));
    var chain =
        new FilterChain(
            List.of(new CachingFilter(now::get, new CacheStore(TIME_TO_LIVE))), catalog);

    send(chain, Request.get("/products/1"));
    send(chain, Request.get("/products/1"));
    send(chain, Request.get("/products/2"));

    now.set(now.get().plus(TIME_TO_LIVE));
    LOGGER.info("{} later", TIME_TO_LIVE);
    send(chain, Request.get("/products/1"));
    send(chain, Request.get("/products/1"));

    LOGGER.info("Catalog served {} of 5 requests", catalog.getLookups());
  }

  private static void send(FilterChain chain, Request request) {
    var response = chain.proceed(request);
    LOGGER.info(
        "{} {} -> {} {}", request.method(), request.path(), response.status(), response.body());
  }
}
