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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CacheStoreTest {

  private static final Duration TTL = Duration.ofSeconds(10);
  private static final Instant START = Instant.parse("2026-01-01T00:00:00Z");
  private static final Request PRODUCT = Request.get("/products/1");
  private static final Response KETTLE = new Response(Response.OK, "Kettle");

  private final CacheStore store = new CacheStore(TTL);

  @Test
  void returnsEmptyForUnknownRequest() {
    assertTrue(store.get(PRODUCT, START).isEmpty());
  }

  @Test
  void returnsStoredResponseBeforeExpiry() {
    store.put(PRODUCT, KETTLE, START);

    assertEquals(Optional.of(KETTLE), store.get(PRODUCT, START.plus(TTL).minusMillis(1)));
  }

  @Test
  void dropsEntryFoundExpired() {
    store.put(PRODUCT, KETTLE, START);

    assertTrue(store.get(PRODUCT, START.plus(TTL)).isEmpty());
    assertEquals(0, store.size());
  }

  @Test
  void removePathDropsEveryEntryForThatPath() {
    store.put(PRODUCT, KETTLE, START);
    store.put(new Request("HEAD", "/products/1"), KETTLE, START);
    store.put(Request.get("/products/2"), KETTLE, START);

    store.removePath("/products/1");

    assertEquals(1, store.size());
  }

  @Test
  void rejectsNonPositiveTimeToLive() {
    assertThrows(IllegalArgumentException.class, () -> new CacheStore(Duration.ZERO));
    assertThrows(IllegalArgumentException.class, () -> new CacheStore(Duration.ofSeconds(-1)));
  }
}
