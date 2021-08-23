package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.constants.CachingConstants;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import static com.iluwatar.caching.constants.CachingConstants.USER_NAME;
import static com.iluwatar.caching.constants.CachingConstants.ADD_INFO;
import static com.iluwatar.caching.constants.CachingConstants.USER_ID;
import static com.iluwatar.caching.constants.CachingConstants.USER_ACCOUNT;

/**
 * Implementation of DatabaseManager.
 * implements base methods to work with MongoDb.
 */
public class MongoDb implements DbManager {
    /**
     * Mongo db.
     */
    private MongoDatabase db;

    /**
     * Connect to Db.
     */
    @Override
    public void connect() {
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase("test");
    }

    /**
     * Read data from DB.
     *
     * @param userId {@link String}
     * @return {@link UserAccount}
     */
    @Override
    public UserAccount readFromDb(final String userId) {
        if (db == null) {
            connect();
        }
        var iterable = db
                .getCollection(CachingConstants.USER_ACCOUNT)
                .find(new Document(USER_ID, userId));
        if (iterable == null) {
            return null;
        }
        Document doc = iterable.first();
        String userName = doc.getString(USER_NAME);
        String appInfo = doc.getString(ADD_INFO);
        return new UserAccount(userId, userName, appInfo);
    }

    /**
     * Write data to DB.
     *
     * @param userAccount {@link UserAccount}
     * @return {@link UserAccount}
     */
    @Override
    public UserAccount writeToDb(final UserAccount userAccount) {
        if (db == null) {
            connect();
        }
        db.getCollection(USER_ACCOUNT).insertOne(
                new Document(USER_ID, userAccount.getUserId())
                        .append(USER_NAME, userAccount.getUserName())
                        .append(ADD_INFO, userAccount.getAdditionalInfo())
        );
        return userAccount;
    }

    /**
     * Update DB.
     *
     * @param userAccount {@link UserAccount}
     * @return {@link UserAccount}
     */
    @Override
    public UserAccount updateDb(final UserAccount userAccount) {
        if (db == null) {
            connect();
        }
        Document id = new Document(USER_ID, userAccount.getUserId());
        Document dataSet = new Document(USER_NAME, userAccount.getUserName())
                .append(ADD_INFO, userAccount.getAdditionalInfo());
        db.getCollection(CachingConstants.USER_ACCOUNT)
                .updateOne(id, new Document("$set", dataSet));
        return userAccount;
    }

    /**
     * Update data if exists.
     *
     * @param userAccount {@link UserAccount}
     * @return {@link UserAccount}
     */
    @Override
    public UserAccount upsertDb(final UserAccount userAccount) {
        if (db == null) {
            connect();
        }
        String userId = userAccount.getUserId();
        String userName = userAccount.getUserName();
        String additionalInfo = userAccount.getAdditionalInfo();
        db.getCollection(CachingConstants.USER_ACCOUNT).updateOne(
                new Document(USER_ID, userId),
                new Document("$set",
                        new Document(USER_ID, userId)
                                .append(USER_NAME, userName)
                                .append(ADD_INFO, additionalInfo)
                ),
                new UpdateOptions().upsert(true)
        );
        return userAccount;
    }
}
