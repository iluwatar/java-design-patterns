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
package com.iluwatar.caching;

import com.iluwatar.caching.database.DbManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * The caching strategies are implemented in this class.
 */
@Slf4j
public class CacheStore {
  /**
   * Cache capacity.
   */
  protected static final int CAPACITY = 3;

  /**
   * Lru cache see {@link LruCache}.
   */
  protected LruCache cache;
  /**
   * DbManager.
   */
  protected final DbManager dbManager;

  /**
   * Cache Store.
   * @param dataBaseManager {@link DbManager}
   */
  public CacheStore(final DbManager dataBaseManager) {
    this.dbManager = dataBaseManager;
    initCapacity(CAPACITY);
  }

  /**
   * Init cache capacity.
   * @param capacity int
   */
  public void initCapacity(final int capacity) {
    if (cache == null) {
      cache = new LruCache(capacity);
    } else {
      cache.setCapacity(capacity);
    }
  }

  /**
   * Clears cache.
   */
  public void clearCache() {
    if (cache != null) {
      cache.clear();
    }
  }

  /**
   * Writes remaining content in the cache into the DB.
   */
  public void flushCache() {
    LOGGER.info("# flushCache...");
    Optional.ofNullable(cache)
        .map(LruCache::getCacheDataInListForm)
        .orElse(List.of())
        .forEach(dbManager::updateDb);
    dbManager.disconnect();
  }

  /**
   * Print user accounts.
   * @return {@link String}
   */
  public String print() {
    return Optional.ofNullable(cache)
        .map(LruCache::getCacheDataInListForm)
        .orElse(List.of())
        .stream()
        .map(userAccount -> userAccount.toString() + "\n")
        .collect(Collectors.joining("", "\n--CACHE CONTENT--\n", "----"));
  }

  /**
   * Delegate to backing cache store.
   * @param userId {@link String}
   * @return {@link UserAccount}
   */
  public UserAccount get(final String userId) {
    return cache.get(userId);
  }

  /**
   * Delegate to backing cache store.
   * @param userId {@link String}
   * @param userAccount {@link UserAccount}
   */
  public void set(final String userId, final UserAccount userAccount) {
    cache.set(userId, userAccount);
  }

  /**
   * Delegate to backing cache store.
   * @param userId {@link String}
   */
  public void invalidate(final String userId) {
    cache.invalidate(userId);
  }
}
