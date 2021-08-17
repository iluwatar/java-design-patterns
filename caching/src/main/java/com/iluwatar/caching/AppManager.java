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

import java.util.Optional;

import com.iluwatar.caching.database.DbManager;
import lombok.extern.slf4j.Slf4j;

/**
 * AppManager helps to bridge the gap in communication between the main class and the application's
 * back-end. DB connection is initialized through this class. The chosen caching strategy/policy is
 * also initialized here. Before the cache can be used, the size of the cache has to be set.
 * Depending on the chosen caching policy, AppManager will call the appropriate function in the
 * CacheStore class.
 */
@Slf4j
public class AppManager {

  private static CachingPolicy cachingPolicy;
  private final DbManager dbManager;
  private final CacheStore cacheStore;

  public AppManager(DbManager dbManager) {
    this.dbManager = dbManager;
    this.cacheStore = new CacheStore(dbManager);
  }

  /**
   * Developer/Tester is able to choose whether the application should use MongoDB as its underlying
   * data storage or a simple Java data structure to (temporarily) store the data/objects during
   * runtime.
   */
  public void initDb() {
     dbManager.connect();
  }

  /**
   * Initialize caching policy.
   */
  public void initCachingPolicy(CachingPolicy policy) {
    cachingPolicy = policy;
    if (cachingPolicy == CachingPolicy.BEHIND) {
      Runtime.getRuntime().addShutdownHook(new Thread(cacheStore::flushCache));
    }
    cacheStore.clearCache();
  }

  /**
   * Find user account.
   */
  public UserAccount find(String userId) {
    if (cachingPolicy == CachingPolicy.THROUGH || cachingPolicy == CachingPolicy.AROUND) {
      return cacheStore.readThrough(userId);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      return cacheStore.readThroughWithWriteBackPolicy(userId);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      return findAside(userId);
    }
    return null;
  }

  /**
   * Save user account.
   */
  public void save(UserAccount userAccount) {
    if (cachingPolicy == CachingPolicy.THROUGH) {
      cacheStore.writeThrough(userAccount);
    } else if (cachingPolicy == CachingPolicy.AROUND) {
      cacheStore.writeAround(userAccount);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      cacheStore.writeBehind(userAccount);
    } else if (cachingPolicy == CachingPolicy.ASIDE) {
      saveAside(userAccount);
    }
  }

  public String printCacheContent() {
    return cacheStore.print();
  }

  /**
   * Cache-Aside save user account helper.
   */
  private void saveAside(UserAccount userAccount) {
    dbManager.updateDb(userAccount);
    cacheStore.invalidate(userAccount.getUserId());
  }

  /**
   * Cache-Aside find user account helper.
   */
  private UserAccount findAside(String userId) {
    return Optional.ofNullable(cacheStore.get(userId))
        .or(() -> {
          Optional<UserAccount> userAccount = Optional.ofNullable(dbManager.readFromDb(userId));
          userAccount.ifPresent(account -> cacheStore.set(userId, account));
          return userAccount;
        })
        .orElse(null);
  }
}
