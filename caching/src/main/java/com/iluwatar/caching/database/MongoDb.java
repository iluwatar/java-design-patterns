package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.constants.CachingConstants;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

/**
 * Implementation of DatabaseManager.
 * implements base methods to work with MongoDb.
 */
public class MongoDb implements DbManager {
    private MongoDatabase db;

    @Override
    public void connect() {
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase("test");
    }

    @Override
    public UserAccount readFromDb(String userId) {
        if (db == null) {
            connect();
        }
        var iterable = db
                .getCollection(CachingConstants.USER_ACCOUNT)
                .find(new Document(CachingConstants.USER_ID, userId));
        if (iterable == null) {
            return null;
        }
        Document doc = iterable.first();
        String userName = doc.getString(CachingConstants.USER_NAME);
        String appInfo = doc.getString(CachingConstants.ADD_INFO);
        return new UserAccount(userId, userName, appInfo);
    }

    @Override
    public UserAccount writeToDb(UserAccount userAccount) {
        if (db == null) {
            connect();
        }
        db.getCollection(CachingConstants.USER_ACCOUNT).insertOne(
                new Document(CachingConstants.USER_ID, userAccount.getUserId())
                        .append(CachingConstants.USER_NAME, userAccount.getUserName())
                        .append(CachingConstants.ADD_INFO, userAccount.getAdditionalInfo())
        );
        return userAccount;
    }

    @Override
    public UserAccount updateDb(UserAccount userAccount) {
        if (db == null) {
            connect();
        }
        db.getCollection(CachingConstants.USER_ACCOUNT).updateOne(
                new Document(CachingConstants.USER_ID, userAccount.getUserId()),
                new Document("$set", new Document(CachingConstants.USER_NAME, userAccount.getUserName())
                        .append(CachingConstants.ADD_INFO, userAccount.getAdditionalInfo())));
        return userAccount;
    }

    @Override
    public UserAccount upsertDb(UserAccount userAccount) {
        if (db == null) {
            connect();
        }
        db.getCollection(CachingConstants.USER_ACCOUNT).updateOne(
                new Document(CachingConstants.USER_ID, userAccount.getUserId()),
                new Document("$set",
                        new Document(CachingConstants.USER_ID, userAccount.getUserId())
                                .append(CachingConstants.USER_NAME, userAccount.getUserName())
                                .append(CachingConstants.ADD_INFO, userAccount.getAdditionalInfo())
                ),
                new UpdateOptions().upsert(true)
        );
        return userAccount;
    }
}
