package com.iluwatar.caching;

import java.util.ArrayList;

/**
 *
 * The caching strategies are implemented in this class.
 *
 */
public class CacheStore {

  static LRUCache cache = null;

  public static void initCapacity(int capacity) {
    if (null == cache)
      cache = new LRUCache(capacity);
    else
      cache.setCapacity(capacity);
  }

  public static UserAccount readThrough(String userID) {
    if (cache.contains(userID)) {
      System.out.println("# Cache Hit!");
      return cache.get(userID);
    }
    System.out.println("# Cache Miss!");
    UserAccount userAccount = DBManager.readFromDB(userID);
    cache.set(userID, userAccount);
    return userAccount;
  }

  public static void writeThrough(UserAccount userAccount) {
    if (cache.contains(userAccount.getUserID())) {
      DBManager.updateDB(userAccount);
    } else {
      DBManager.writeToDB(userAccount);
    }
    cache.set(userAccount.getUserID(), userAccount);
  }

  public static void writeAround(UserAccount userAccount) {
    if (cache.contains(userAccount.getUserID())) {
      DBManager.updateDB(userAccount);
      cache.invalidate(userAccount.getUserID()); // Cache data has been updated -- remove older
                                                 // version from cache.
    } else {
      DBManager.writeToDB(userAccount);
    }
  }

  public static UserAccount readThroughWithWriteBackPolicy(String userID) {
    if (cache.contains(userID)) {
      System.out.println("# Cache Hit!");
      return cache.get(userID);
    }
    System.out.println("# Cache Miss!");
    UserAccount userAccount = DBManager.readFromDB(userID);
    if (cache.isFull()) {
      System.out.println("# Cache is FULL! Writing LRU data to DB...");
      UserAccount toBeWrittenToDB = cache.getLRUData();
      DBManager.upsertDB(toBeWrittenToDB);
    }
    cache.set(userID, userAccount);
    return userAccount;
  }

  public static void writeBehind(UserAccount userAccount) {
    if (cache.isFull() && !cache.contains(userAccount.getUserID())) {
      System.out.println("# Cache is FULL! Writing LRU data to DB...");
      UserAccount toBeWrittenToDB = cache.getLRUData();
      DBManager.upsertDB(toBeWrittenToDB);
    }
    cache.set(userAccount.getUserID(), userAccount);
  }

  public static void clearCache() {
    if (null != cache)
      cache.clear();
  }

  /**
   * Writes remaining content in the cache into the DB.
   */
  public static void flushCache() {
    System.out.println("# flushCache...");
    if (null == cache)
      return;
    ArrayList<UserAccount> listOfUserAccounts = cache.getCacheDataInListForm();
    for (UserAccount userAccount : listOfUserAccounts) {
      DBManager.upsertDB(userAccount);
    }
  }

  public static String print() {
    ArrayList<UserAccount> listOfUserAccounts = cache.getCacheDataInListForm();
    StringBuilder sb = new StringBuilder();
    sb.append("\n--CACHE CONTENT--\n");
    for (UserAccount userAccount : listOfUserAccounts) {
      sb.append(userAccount.toString() + "\n");
    }
    sb.append("----\n");
    return sb.toString();
  }
}
