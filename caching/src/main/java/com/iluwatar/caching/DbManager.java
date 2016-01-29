package com.iluwatar.caching;

import java.text.ParseException;
import java.util.HashMap;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/**
 *
 * <p>DBManager handles the communication with the underlying data store i.e. Database. It contains the
 * implemented methods for querying, inserting, and updating data. MongoDB was used as the database
 * for the application.</p>
 * 
 * <p>Developer/Tester is able to choose whether the application should use MongoDB as its underlying
 * data storage (connect()) or a simple Java data structure to (temporarily) store the data/objects
 * during runtime (createVirtualDB()).</p>
 * 
 */
public class DbManager {

  private static MongoClient mongoClient;
  private static MongoDatabase db;
  private static boolean useMongoDB;

  private static HashMap<String, UserAccount> virtualDB;

  private DbManager() {
  }

  /**
   * Create DB
   */
  public static void createVirtualDb() {
    useMongoDB = false;
    virtualDB = new HashMap<>();
  }

  /**
   * Connect to DB
   */
  public static void connect() throws ParseException {
    useMongoDB = true;
    mongoClient = new MongoClient();
    db = mongoClient.getDatabase("test");
  }

  /**
   * Read user account from DB
   */
  public static UserAccount readFromDb(String userId) {
    if (!useMongoDB) {
      if (virtualDB.containsKey(userId)) {
        return virtualDB.get(userId);
      }
      return null;
    }
    if (null == db) {
      try {
        connect();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    FindIterable<Document> iterable =
        db.getCollection("user_accounts").find(new Document("userID", userId));
    if (iterable == null) {
      return null;
    }
    Document doc = iterable.first();
    UserAccount userAccount =
        new UserAccount(userId, doc.getString("userName"), doc.getString("additionalInfo"));
    return userAccount;
  }

  /**
   * Write user account to DB
   */
  public static void writeToDb(UserAccount userAccount) {
    if (!useMongoDB) {
      virtualDB.put(userAccount.getUserId(), userAccount);
      return;
    }
    if (null == db) {
      try {
        connect();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    db.getCollection("user_accounts").insertOne(
        new Document("userID", userAccount.getUserId()).append("userName",
            userAccount.getUserName()).append("additionalInfo", userAccount.getAdditionalInfo()));
  }

  /**
   * Update DB
   */
  public static void updateDb(UserAccount userAccount) {
    if (!useMongoDB) {
      virtualDB.put(userAccount.getUserId(), userAccount);
      return;
    }
    if (null == db) {
      try {
        connect();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    db.getCollection("user_accounts").updateOne(
        new Document("userID", userAccount.getUserId()),
        new Document("$set", new Document("userName", userAccount.getUserName()).append(
            "additionalInfo", userAccount.getAdditionalInfo())));
  }

  /**
   *
   * Insert data into DB if it does not exist. Else, update it.
   */
  public static void upsertDb(UserAccount userAccount) {
    if (!useMongoDB) {
      virtualDB.put(userAccount.getUserId(), userAccount);
      return;
    }
    if (null == db) {
      try {
        connect();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    db.getCollection("user_accounts").updateOne(
        new Document("userID", userAccount.getUserId()),
        new Document("$set", new Document("userID", userAccount.getUserId()).append("userName",
            userAccount.getUserName()).append("additionalInfo", userAccount.getAdditionalInfo())),
        new UpdateOptions().upsert(true));
  }
}
