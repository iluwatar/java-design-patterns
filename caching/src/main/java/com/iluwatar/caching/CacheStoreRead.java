package com.iluwatar.caching;

import com.iluwatar.caching.database.DbManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheStoreRead extends CacheStore {

    /**
     * Cache Store.
     *
     * @param dataBaseManager {@link DbManager}
     */
    public CacheStoreRead(DbManager dataBaseManager) {
        super(dataBaseManager);
    }

    /**
     * Get user account using read-through cache.
     * @param userId {@link String}
     * @return {@link UserAccount}
     */
    public UserAccount readThrough(final String userId) {
        if (cache.contains(userId)) {
            LOGGER.info("# Found in Cache!");
            return cache.get(userId);
        }
        LOGGER.info("# Not found in cache! Go to DB!!");
        UserAccount userAccount = dbManager.readFromDb(userId);
        cache.set(userId, userAccount);
        return userAccount;
    }

    public UserAccount readThroughWithWriteBackPolicy(final String userId) {
        if (cache.contains(userId)) {
            LOGGER.info("# Found in cache!");
            return cache.get(userId);
        }
        LOGGER.info("# Not found in Cache!");
        UserAccount userAccount = dbManager.readFromDb(userId);
        if (cache.isFull()) {
            LOGGER.info("# Cache is FULL! Writing LRU data to DB...");
            UserAccount toBeWrittenToDb = cache.getLruData();
            dbManager.upsertDb(toBeWrittenToDb);
        }
        cache.set(userId, userAccount);
        return userAccount;
    }

}
