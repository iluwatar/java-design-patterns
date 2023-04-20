/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.caching.database;

import static com.iluwatar.caching.constants.CachingConstants.ADD_INFO;
import static com.iluwatar.caching.constants.CachingConstants.USER_ACCOUNT;
import static com.iluwatar.caching.constants.CachingConstants.USER_ID;
import static com.iluwatar.caching.constants.CachingConstants.USER_NAME;

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.constants.CachingConstants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
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
  private static final String DATABASE_NAME = "admin";
  private static final String MONGO_USER = "root";
  private static final String MONGO_PASSWORD = "rootpassword";
  private MongoClient client;
  private MongoDatabase db;

  void setDb(MongoDatabase db) {
    this.db = db;
  }

  /**
   * Connect to Db. Check th connection
   */
  @Override
  public void connect() {
    MongoCredential mongoCredential = MongoCredential.createCredential(MONGO_USER,
                    DATABASE_NAME,
                    MONGO_PASSWORD.toCharArray());
    MongoClientOptions options = MongoClientOptions.builder().build();
    client = new MongoClient(new ServerAddress(), mongoCredential, options);
    db = client.getDatabase(DATABASE_NAME);
  }

  @Override
  public void disconnect() {
    client.close();
  }

  /**
   * Read data from DB.
   *
   * @param userId {@link String}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount readFromDb(final String userId) {
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

  /**
   * Write data to DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount writeToDb(final UserAccount userAccount) {
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
