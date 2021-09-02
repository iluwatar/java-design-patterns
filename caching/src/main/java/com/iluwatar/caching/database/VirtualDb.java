package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DatabaseManager.
 * implements base methods to work with hashMap as database.
 */
public class VirtualDb implements DbManager {
  /**
   * Virtual DataBase.
   */
  private Map<String, UserAccount> db;

  /**
   * Creates new HashMap.
   */
  @Override
  public void connect() {
    db = new HashMap<>();
  }

  @Override
  public void disconnect() {
    db = null;
  }

  /**
   * Read from Db.
   *
   * @param userId {@link String}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount readFromDb(final String userId) {
    if (db.containsKey(userId)) {
      return db.get(userId);
    }
    return null;
  }

  /**
   * Write to DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount writeToDb(final UserAccount userAccount) {
    db.put(userAccount.getUserId(), userAccount);
    return userAccount;
  }

  /**
   * Update reecord in DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount updateDb(final UserAccount userAccount) {
    return writeToDb(userAccount);
  }

  /**
   * Update.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount upsertDb(final UserAccount userAccount) {
    return updateDb(userAccount);
  }
}
