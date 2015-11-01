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
public class DBManager {

  private static MongoClient mongoClient;
  private static MongoDatabase db;
  private static boolean useMongoDB;

  private static HashMap<String, UserAccount> virtualDB;

  public static void createVirtualDB() {
    useMongoDB = false;
    virtualDB = new HashMap<String, UserAccount>();
  }

  public static void connect() throws ParseException {
    useMongoDB = true;
    mongoClient = new MongoClient();
    db = mongoClient.getDatabase("test");
  }

  public static UserAccount readFromDB(String userID) {
    if (!useMongoDB) {
      if (virtualDB.containsKey(userID))
        return virtualDB.get(userID);
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
        db.getCollection("user_accounts").find(new Document("userID", userID));
    if (iterable == null)
      return null;
    Document doc = iterable.first();
    UserAccount userAccount =
        new UserAccount(userID, doc.getString("userName"), doc.getString("additionalInfo"));
    return userAccount;
  }

  public static void writeToDB(UserAccount userAccount) {
    if (!useMongoDB) {
      virtualDB.put(userAccount.getUserID(), userAccount);
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
        new Document("userID", userAccount.getUserID()).append("userName",
            userAccount.getUserName()).append("additionalInfo", userAccount.getAdditionalInfo()));
  }

  public static void updateDB(UserAccount userAccount) {
    if (!useMongoDB) {
      virtualDB.put(userAccount.getUserID(), userAccount);
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
        new Document("userID", userAccount.getUserID()),
        new Document("$set", new Document("userName", userAccount.getUserName()).append(
            "additionalInfo", userAccount.getAdditionalInfo())));
  }

  /**
   *
   * Insert data into DB if it does not exist. Else, update it.
   */
  public static void upsertDB(UserAccount userAccount) {
    if (!useMongoDB) {
      virtualDB.put(userAccount.getUserID(), userAccount);
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
        new Document("userID", userAccount.getUserID()),
        new Document("$set", new Document("userID", userAccount.getUserID()).append("userName",
            userAccount.getUserName()).append("additionalInfo", userAccount.getAdditionalInfo())),
        new UpdateOptions().upsert(true));
  }
}
