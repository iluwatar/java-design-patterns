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

package com.iluwatar.caching;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * The caching strategies are implemented in this class.
 */
@Slf4j
public class CacheStore {

  private static LruCache cache;

  private CacheStore() {
  }

  /**
   * Init cache capacity.
   */
  public static void initCapacity(int capacity) {
    if (cache == null) {
      cache = new LruCache(capacity);
    } else {
      cache.setCapacity(capacity);
    }
  }

  /**
   * Get user account using read-through cache.
   */
  public static UserAccount readThrough(String userId) {
    if (cache.contains(userId)) {
      LOGGER.info("# Cache Hit!");
      return cache.get(userId);
    }
    LOGGER.info("# Cache Miss!");
    UserAccount userAccount = DbManager.readFromDb(userId);
    cache.set(userId, userAccount);
    return userAccount;
  }

  /**
   * Get user account using write-through cache.
   */
  public static void writeThrough(UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      DbManager.updateDb(userAccount);
    } else {
      DbManager.writeToDb(userAccount);
    }
    cache.set(userAccount.getUserId(), userAccount);
  }

  /**
   * Get user account using write-around cache.
   */
  public static void writeAround(UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      DbManager.updateDb(userAccount);
      cache.invalidate(userAccount.getUserId()); // Cache data has been updated -- remove older
      // version from cache.
    } else {
      DbManager.writeToDb(userAccount);
    }
  }

  /**
   * Get user account using read-through cache with write-back policy.
   */
  public static UserAccount readThroughWithWriteBackPolicy(String userId) {
    if (cache.contains(userId)) {
      LOGGER.info("# Cache Hit!");
      return cache.get(userId);
    }
    LOGGER.info("# Cache Miss!");
    UserAccount userAccount = DbManager.readFromDb(userId);
    if (cache.isFull()) {
      LOGGER.info("# Cache is FULL! Writing LRU data to DB...");
      UserAccount toBeWrittenToDb = cache.getLruData();
      DbManager.upsertDb(toBeWrittenToDb);
    }
    cache.set(userId, userAccount);
    return userAccount;
  }

  /**
   * Set user account.
   */
  public static void writeBehind(UserAccount userAccount) {
    if (cache.isFull() && !cache.contains(userAccount.getUserId())) {
      LOGGER.info("# Cache is FULL! Writing LRU data to DB...");
      UserAccount toBeWrittenToDb = cache.getLruData();
      DbManager.upsertDb(toBeWrittenToDb);
    }
    cache.set(userAccount.getUserId(), userAccount);
  }

  /**
   * Clears cache.
   */
  public static void clearCache() {
    if (cache != null) {
      cache.clear();
    }
  }

  /**
   * Writes remaining content in the cache into the DB.
   */
  public static void flushCache() {
    LOGGER.info("# flushCache...");
    Optional.ofNullable(cache)
        .map(LruCache::getCacheDataInListForm)
        .orElse(List.of())
        .forEach(DbManager::updateDb);
  }

  /**
   * Print user accounts.
   */
  public static String print() {
    return Optional.ofNullable(cache)
        .map(LruCache::getCacheDataInListForm)
        .orElse(List.of())
        .stream()
        .map(userAccount -> userAccount.toString() + "\n")
        .collect(Collectors.joining("", "\n--CACHE CONTENT--\n", "----\n"));
  }

  /**
   * Delegate to backing cache store.
   */
  public static UserAccount get(String userId) {
    return cache.get(userId);
  }

  /**
   * Delegate to backing cache store.
   */
  public static void set(String userId, UserAccount userAccount) {
    cache.set(userId, userAccount);
  }

  /**
   * Delegate to backing cache store.
   */
  public static void invalidate(String userId) {
    cache.invalidate(userId);
  }
}
