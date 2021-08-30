package com.iluwatar.caching.database;

import static com.iluwatar.caching.constants.CachingConstants.ADD_INFO;
import static com.iluwatar.caching.constants.CachingConstants.USER_ACCOUNT;
import static com.iluwatar.caching.constants.CachingConstants.USER_ID;
import static com.iluwatar.caching.constants.CachingConstants.USER_NAME;

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.constants.CachingConstants;
import com.iluwatar.caching.database.exceptions.DatabaseConnectionException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 * Implementation of DatabaseManager.
 * implements base methods to work with MongoDb.
 */
@Slf4j
public class MongoDb implements DbManager {
  private static final String DATABASE_NAME = "test";

  /**
   * Connect to Db. Check th connection
   */
  @Override
  public void connect() throws DatabaseConnectionException {
    try (MongoClient mongoClient = new MongoClient()) {
      mongoClient.getDatabase("test");
    } catch (NoClassDefFoundError e) {
      throw new DatabaseConnectionException("Could not connect to DB.");
    }
  }

  /**
   * Read data from DB.
   *
   * @param userId {@link String}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount readFromDb(final String userId) {
    try (MongoClient mongoClient = new MongoClient()) {
      MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
      var iterable = db
              .getCollection(CachingConstants.USER_ACCOUNT)
              .find(new Document(USER_ID, userId));
      if (iterable.first() == null) {
        return null;
      }
      Document doc = iterable.first();
      if (doc != null) {
        String userName = doc.getString(USER_NAME);
        String appInfo = doc.getString(ADD_INFO);
        return new UserAccount(userId, userName, appInfo);
      } else {
        return null;
      }
    }
  }

  /**
   * Write data to DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount writeToDb(final UserAccount userAccount) {
    try (MongoClient mongoClient = new MongoClient()) {
      MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
      db.getCollection(USER_ACCOUNT).insertOne(
              new Document(USER_ID, userAccount.getUserId())
                      .append(USER_NAME, userAccount.getUserName())
                      .append(ADD_INFO, userAccount.getAdditionalInfo())
      );
      return userAccount;
    }
  }

  /**
   * Update DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount updateDb(final UserAccount userAccount) {
    try (MongoClient mongoClient = new MongoClient()) {
      MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
      Document id = new Document(USER_ID, userAccount.getUserId());
      Document dataSet = new Document(USER_NAME, userAccount.getUserName())
              .append(ADD_INFO, userAccount.getAdditionalInfo());
      db.getCollection(CachingConstants.USER_ACCOUNT)
              .updateOne(id, new Document("$set", dataSet));
      return userAccount;
    }
  }

  /**
   * Update data if exists.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount upsertDb(final UserAccount userAccount) {
    try (MongoClient mongoClient = new MongoClient()) {
      MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
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
}
