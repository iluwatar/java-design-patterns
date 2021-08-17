package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;

/**
 * <p>DBManager handles the communication with the underlying data store i.e. Database. It contains
 * the implemented methods for querying, inserting, and updating data. MongoDB was used as the
 * database for the application.</p>
 */
public interface DbManager {
    void connect();
    UserAccount readFromDb(String userId);
    UserAccount writeToDb(UserAccount userAccount);
    UserAccount updateDb(UserAccount userAccount);
    UserAccount upsertDb(UserAccount userAccount);
}
