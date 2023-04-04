package com.iluwatar.caching;

import com.iluwatar.caching.database.DbManager;
import lombok.extern.slf4j.Slf4j;

/**
 * CacheStoreRead extending CacheStore to divide the methods.
 */
@Slf4j
public class CacheStoreWrite extends CacheStore {

  /**
   * Cache Store.
   *
   * @param dataBaseManager {@link DbManager}
   */
  public CacheStoreWrite(DbManager dataBaseManager) {
    super(dataBaseManager);
  }

  /**
   * Write through method.
   *
   * @param userAccount object.
   */

  public void writeThrough(final UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      dbManager.updateDb(userAccount);
    } else {
      dbManager.writeToDb(userAccount);
    }
    cache.set(userAccount.getUserId(), userAccount);
  }

  /**
   * write around method.
   *
   * @param userAccount object.
   */
  public void writeAround(final UserAccount userAccount) {
    if (cache.contains(userAccount.getUserId())) {
      dbManager.updateDb(userAccount);
      // Cache data has been updated -- remove older
      cache.invalidate(userAccount.getUserId());
      // version from cache.
    } else {
      dbManager.writeToDb(userAccount);
    }
  }

  /**
   * write behind method.
   *
   * @param userAccount object.
   */
  public void writeBehind(final UserAccount userAccount) {
    if (cache.isFull() && !cache.contains(userAccount.getUserId())) {
      LOGGER.info("# Cache is FULL! Writing LRU data to DB...");
      UserAccount toBeWrittenToDb = cache.getLruData();
      dbManager.upsertDb(toBeWrittenToDb);
    }
    cache.set(userAccount.getUserId(), userAccount);
  }
}
