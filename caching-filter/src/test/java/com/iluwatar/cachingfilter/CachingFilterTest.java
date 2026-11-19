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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CachingFilterTest {

  private static final Duration TTL = Duration.ofSeconds(10);
  private static final Request PRODUCT = Request.get("/products/1");

  private final AtomicReference<Instant> now =
      new AtomicReference<>(Instant.parse("2026-01-01T00:00:00Z"));
  private ProductCatalog catalog;
  private FilterChain chain;

  @BeforeEach
  void setUp() {
    catalog = new ProductCatalog(Map.of("/products/1", "Kettle"));
    chain = new FilterChain(List.of(new CachingFilter(now::get, new CacheStore(TTL))), catalog);
  }

  private void advance(Duration duration) {
    now.set(now.get().plus(duration));
  }

  @Test
  void firstRequestReachesHandler() {
    var response = chain.proceed(PRODUCT);

    assertEquals(new Response(Response.OK, "Kettle"), response);
    assertEquals(1, catalog.getLookups());
  }

  @Test
  void repeatedRequestIsServedFromCacheBeforeExpiry() {
    var first = chain.proceed(PRODUCT);
    advance(TTL.minusMillis(1));
    var second = chain.proceed(PRODUCT);

    assertSame(first, second);
    assertEquals(1, catalog.getLookups());
  }

  @Test
  void requestReachesHandlerAgainOnceEntryExpires() {
    chain.proceed(PRODUCT);
    advance(TTL);
    chain.proceed(PRODUCT);

    assertEquals(2, catalog.getLookups());
  }

  @Test
  void refreshedEntryIsCachedForAnotherTimeToLive() {
    chain.proceed(PRODUCT);
    advance(TTL);
    chain.proceed(PRODUCT);
    advance(TTL.minusMillis(1));
    chain.proceed(PRODUCT);

    assertEquals(2, catalog.getLookups());
  }

  @Test
  void differentPathsAreCachedSeparately() {
    chain.proceed(PRODUCT);
    chain.proceed(Request.get("/products/2"));

    assertEquals(2, catalog.getLookups());
  }

  @Test
  void errorResponsesAreNotCached() {
    var missing = Request.get("/products/2");
    var response = chain.proceed(missing);
    chain.proceed(missing);

    assertEquals(Response.NOT_FOUND, response.status());
    assertEquals(2, catalog.getLookups());
  }

  @Test
  void nonGetRequestsBypassTheCache() {
    var post = new Request("POST", "/products/1");
    chain.proceed(post);
    chain.proceed(post);

    assertEquals(2, catalog.getLookups());
  }

  @Test
  void writeEvictsCachedResponseForSamePath() {
    chain.proceed(PRODUCT);
    chain.proceed(new Request("POST", "/products/1"));
    chain.proceed(PRODUCT);

    assertEquals(3, catalog.getLookups());
  }

  @Test
  void errorAfterExpiryDropsOldEntry() {
    var found = new AtomicBoolean(true);
    var handled = new AtomicInteger();
    Handler handler =
        request -> {
          handled.incrementAndGet();
          return found.get()
              ? new Response(Response.OK, "Kettle")
              : new Response(Response.NOT_FOUND, "Gone");
        };
    var store = new CacheStore(TTL);
    var errorChain = new FilterChain(List.of(new CachingFilter(now::get, store)), handler);

    errorChain.proceed(PRODUCT);
    advance(TTL);
    found.set(false);
    var gone = errorChain.proceed(PRODUCT);
    var again = errorChain.proceed(PRODUCT);

    assertEquals(Response.NOT_FOUND, gone.status());
    assertEquals(Response.NOT_FOUND, again.status());
    assertEquals(3, handled.get());
    assertEquals(0, store.size());
  }
}
