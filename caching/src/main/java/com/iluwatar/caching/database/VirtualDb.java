package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DatabaseManager.
 * implements base methods to work with hashMap as database.
 */
public class VirtualDb implements DbManager {
    private Map<String, UserAccount> virtualDB;

    @Override
    public void connect() {
        virtualDB = new HashMap<>();
    }

    @Override
    public UserAccount readFromDb(String userId) {
        if (virtualDB.containsKey(userId)) {
            return virtualDB.get(userId);
        }
        return null;
    }

    @Override
    public UserAccount writeToDb(UserAccount userAccount) {
        virtualDB.put(userAccount.getUserId(), userAccount);
        return userAccount;
    }

    @Override
    public UserAccount updateDb(UserAccount userAccount) {
        virtualDB.put(userAccount.getUserId(), userAccount);
        return userAccount;
    }

    @Override
    public UserAccount upsertDb(UserAccount userAccount) {
        return updateDb(userAccount);
    }
}