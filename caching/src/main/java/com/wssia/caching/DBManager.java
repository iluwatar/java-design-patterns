package main.java.com.wssia.caching;

import java.text.ParseException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/**
 *
 * DBManager handles the communication with the underlying data store i.e. Database. It contains the
 * implemented methods for querying, inserting, and updating data. MongoDB was used as the database
 * for the application.
 *
 */
public class DBManager {

  private static MongoClient mongoClient;
  private static MongoDatabase db;

  public static void connect() throws ParseException {
    mongoClient = new MongoClient();
    db = mongoClient.getDatabase("test");
  }

  public static UserAccount readFromDB(String userID) {
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
