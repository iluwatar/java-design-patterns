/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import java.text.ParseException;
import java.util.Optional;

/**
 * AppManager helps to bridge the gap in communication between the main class and the application's
 * back-end. DB connection is initialized through this class. The chosen caching strategy/policy is
 * also initialized here. Before the cache can be used, the size of the cache has to be set.
 * Depending on the chosen caching policy, AppManager will call the appropriate function in the
 * CacheStore class.
 */
public final class AppManager {

  private static CachingPolicy cachingPolicy;

  private AppManager() {
  }

  /**
   * Developer/Tester is able to choose whether the application should use MongoDB as its underlying
   * data storage or a simple Java data structure to (temporarily) store the data/objects during
   * runtime.
   */
  public static void initDb(boolean useMongoDb) {
    if (useMongoDb) {
      try {
        DbManager.connect();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } else {
      DbManager.createVirtualDb();
    }
  }

  /**
   * Initialize caching policy.
   */
  public static void initCachingPolicy(CachingPolicy policy) {
    cachingPolicy = policy;
    if (cachingPolicy == CachingPolicy.BEHIND) {
      Runtime.getRuntime().addShutdownHook(new Thread(CacheStore::flushCache));
    }
    CacheStore.clearCache();
  }

  public static void initCacheCapacity(int capacity) {
    CacheStore.initCapacity(capacity);
  }

  /**
   * Find user account.
   */
  public static UserAccount find(String userId) {
    if (cachingPolicy == CachingPolicy.THROUGH || cachingPolicy == CachingPolicy.AROUND) {
      return CacheStore.readThrough(userId);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      return CacheStore.readThroughWithWriteBackPolicy(userId);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      return findAside(userId);
    }
    return null;
  }

  /**
   * Save user account.
   */
  public static void save(UserAccount userAccount) {
    if (cachingPolicy == CachingPolicy.THROUGH) {
      CacheStore.writeThrough(userAccount);
    } else if (cachingPolicy == CachingPolicy.AROUND) {
      CacheStore.writeAround(userAccount);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      CacheStore.writeBehind(userAccount);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      saveAside(userAccount);
    }
  }

  public static String printCacheContent() {
    return CacheStore.print();
  }

  /**
   * Cache-Aside save user account helper.
   */
  private static void saveAside(UserAccount userAccount) {
    DbManager.updateDb(userAccount);
    CacheStore.invalidate(userAccount.getUserId());
  }

  /**
   * Cache-Aside find user account helper.
   */
  private static UserAccount findAside(String userId) {
    return Optional.ofNullable(CacheStore.get(userId))
        .or(() -> {
          Optional<UserAccount> userAccount = Optional.ofNullable(DbManager.readFromDb(userId));
          userAccount.ifPresent(account -> CacheStore.set(userId, account));
          return userAccount;
        })
        .orElse(null);
  }
}
