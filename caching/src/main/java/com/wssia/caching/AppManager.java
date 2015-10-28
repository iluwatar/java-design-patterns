package main.java.com.wssia.caching;

import java.text.ParseException;

/**
 *
 * AppManager helps to bridge the gap in communication between the main class and the application's
 * back-end. DB connection is initialized through this class. The chosen caching strategy/policy is
 * also initialized here. Before the cache can be used, the size of the cache has to be set.
 * Depending on the chosen caching policy, AppManager will call the appropriate function in the
 * CacheStore class.
 *
 */
public class AppManager {

  private static CachingPolicy cachingPolicy;

  public static void init() {
    try {
      DBManager.connect();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public static void initCachingPolicy(CachingPolicy policy) {
    cachingPolicy = policy;
    if (cachingPolicy == CachingPolicy.BEHIND) {
      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        @Override
        public void run() {
          CacheStore.flushCache();
        }
      }));
    }
    CacheStore.clearCache();
  }

  public static void initCacheCapacity(int capacity) {
    CacheStore.initCapacity(capacity);
  }

  public static UserAccount find(String userID) {
    if (cachingPolicy == CachingPolicy.THROUGH || cachingPolicy == CachingPolicy.AROUND) {
      return CacheStore.readThrough(userID);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      return CacheStore.readThroughWithWriteBackPolicy(userID);
    }
    return null;
  }

  public static void save(UserAccount userAccount) {
    if (cachingPolicy == CachingPolicy.THROUGH) {
      CacheStore.writeThrough(userAccount);
    } else if (cachingPolicy == CachingPolicy.AROUND) {
      CacheStore.writeAround(userAccount);
    } else if (cachingPolicy == CachingPolicy.BEHIND) {
      CacheStore.writeBehind(userAccount);
    }
  }

  public static String printCacheContent() {
    return CacheStore.print();
  }
}
