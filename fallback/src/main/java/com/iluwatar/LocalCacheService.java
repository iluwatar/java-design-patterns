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

import ch.qos.logback.classic.Logger;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;

/**
 * LocalCacheService implementation that provides cached data with fallback mechanism.
 * This service maintains a local cache with multiple fallback levels and automatic
 * expiration of cached entries. If the primary data source fails, the service
 * falls back to cached data in order of priority.
 */
public class LocalCacheService implements Service, AutoCloseable {
  
  /** Cache instance for storing key-value pairs. */
  private final Cache<String, String> cache;
  
  /** Default cache entry expiration time in milliseconds. */
  private static final long CACHE_EXPIRY_MS = 300000;
  
  /** Logger instance for this class. */
  private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LocalCacheService.class);
  
  /** Interval for periodic cache refresh operations. */
  private static final Duration CACHE_REFRESH_INTERVAL = Duration.ofMinutes(5);
  
  /** Executor service for scheduling cache maintenance tasks. */
  private final ScheduledExecutorService refreshExecutor;

  /**
   * Defines the fallback chain priority levels.
   * Entries are tried in order from PRIMARY to TERTIARY until valid data is found.
   */
  private enum FallbackLevel {
    PRIMARY("default"),
    SECONDARY("backup1"),
    TERTIARY("backup2");

    private final String key;
    
    FallbackLevel(String key) {
      this.key = key;
    }
  }

  /**
   * Constructs a new LocalCacheService with initialized cache and scheduled maintenance.
   */
  public LocalCacheService() {
    this.cache = new Cache<>(CACHE_EXPIRY_MS);
    this.refreshExecutor = Executors.newSingleThreadScheduledExecutor();
    initializeDefaultCache();
    scheduleMaintenanceTasks();
  }

  /**
   * Initializes the cache with default fallback values.
   */
  private void initializeDefaultCache() {
    cache.put(FallbackLevel.PRIMARY.key, "Default fallback response");
    cache.put(FallbackLevel.SECONDARY.key, "Secondary fallback response");
    cache.put(FallbackLevel.TERTIARY.key, "Tertiary fallback response");
  }

  /**
   * Schedules periodic cache maintenance tasks.
   */
  private void scheduleMaintenanceTasks() {
    refreshExecutor.scheduleAtFixedRate(
        this::cleanupExpiredEntries,
        CACHE_REFRESH_INTERVAL.toMinutes(),
        CACHE_REFRESH_INTERVAL.toMinutes(),
        TimeUnit.MINUTES
    );
  }

  /**
   * Removes expired entries from the cache.
   */
  private void cleanupExpiredEntries() {
    try {
      cache.cleanup();
      LOGGER.debug("Completed cache cleanup");
    } catch (Exception e) {
      LOGGER.error("Error during cache cleanup", e);
    }
  }

  @Override
  public void close() throws Exception {
    if (refreshExecutor != null) {
      refreshExecutor.shutdown();
      try {
        if (!refreshExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
          refreshExecutor.shutdownNow();
        }
      } catch (InterruptedException e) {
        refreshExecutor.shutdownNow();
        Thread.currentThread().interrupt();
        throw new Exception("Failed to shutdown refresh executor", e);
      }
    }
  }

  /**
   * Retrieves data using the fallback chain mechanism.
   * @return The cached data from the highest priority available fallback level.
   * @throws Exception if no valid data is available at any fallback level.
   */
  @Override
  public String getData() throws Exception {
    // Try each fallback level in order of priority
    for (FallbackLevel level : FallbackLevel.values()) {
      String value = cache.get(level.key);
      if (value != null) {
        LOGGER.debug("Retrieved value from {} fallback level", level);
        return value;
      }
      LOGGER.debug("Cache miss at {} fallback level", level);
    }
    throw new Exception("All fallback levels exhausted");
  }

  /**
   * Updates the cached data for a specific key.
   * @param key The cache key to update.
   * @param value The new value to cache.
   */
  public void updateCache(String key, String value) {
    cache.put(key, value);
  }

  /**
   * Thread-safe cache implementation with entry expiration.
   */
  private static class Cache<K, V> {
    private final ConcurrentHashMap<K, CacheEntry<V>> map;
    private final long expiryMs;
    
    Cache(long expiryMs) {
      this.map = new ConcurrentHashMap<>();
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

    /**
     * Removes all expired entries from the cache.
     */
    void cleanup() {
      map.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    private record CacheEntry<V>(V value, long expiryTime) {
      boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
      }
    }
  }
}