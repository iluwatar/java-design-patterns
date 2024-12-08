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
package com.iluwatar;

import java.util.concurrent.ConcurrentHashMap;  // Add this import

/**
 * LocalCacheService implementation that provides cached data as a fallback mechanism.
 * This service stores and retrieves data from a local cache with expiration support.
 */
public class LocalCacheService implements Service {
  private final Cache<String, String> cache;
  private static final long CACHE_EXPIRY_MS = 300000; // 5 minutes
  
  private static final String[] DEFAULT_KEYS = {"default", "backup1", "backup2"};
  private static final String[] DEFAULT_VALUES = {
    "Default fallback response",
    "Secondary fallback response",
    "Tertiary fallback response"
  };

  public LocalCacheService() {
    this.cache = new Cache<>(CACHE_EXPIRY_MS);
    initializeDefaultCache();
  }

  private void initializeDefaultCache() {
    for (int i = 0; i < DEFAULT_KEYS.length; i++) {
      cache.put(DEFAULT_KEYS[i], DEFAULT_VALUES[i]);
    }
  }

  @Override
  public String getData() throws Exception {
    // Try all cache entries in order until a valid one is found
    for (String key : DEFAULT_KEYS) {
      String value = cache.get(key);
      if (value != null) {
        return value;
      }
    }
    throw new Exception("No valid cache entry found");
  }

  public void updateCache(String key, String value) {
    cache.put(key, value);
  }

  private static class Cache<K, V> {
    private final ConcurrentHashMap<K, CacheEntry<V>> map = new ConcurrentHashMap<>();
    private final long expiryMs;
    
    Cache(long expiryMs) {
      this.expiryMs = expiryMs;
    }
    
    V get(K key) {
      CacheEntry<V> entry = map.get(key);
      if (entry != null && !entry.isExpired()) {
        return entry.value;
      }
      return null;
    }
    
    void put(K key, V value) {
      map.put(key, new CacheEntry<>(value, System.currentTimeMillis() + expiryMs));
    }

    private record CacheEntry<V>(V value, long expiryTime) {
      boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
      }
    }
  }
}