package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.database.exceptions.DatabaseConnectionException;

/**
 * <p>DBManager handles the communication with the underlying data store i.e.
 * Database. It contains the implemented methods for querying, inserting,
 * and updating data. MongoDB was used as the database for the application.</p>
 */
public interface DbManager {
  /**
   * Connect to DB.
   */
  void connect() throws DatabaseConnectionException;

  /**
   * Read from DB.
   * @param userId {@link String}
   * @return {@link UserAccount}
   */
  UserAccount readFromDb(String userId);

  /**
   * Write to DB.
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  UserAccount writeToDb(UserAccount userAccount);

  /**
   * Update record.
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  UserAccount updateDb(UserAccount userAccount);

  /**
   * Update record or Insert if not exists.
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  UserAccount upsertDb(UserAccount userAccount);
}
