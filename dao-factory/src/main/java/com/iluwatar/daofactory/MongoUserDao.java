package com.iluwatar.daofactory;


import de.bwaldvogel.mongo.MongoServer;

import java.util.ArrayList;
import java.util.Collection;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


/**
 * MongoUserDAO implementation of the
 * UserDAO interface. This class can contain all
 * Derby specific code and SQL statements.
 * The client is thus shielded from knowing
 * these implementation details.
 *
 */
public class MongoUserDao implements UserDao {
  /**
   * MongoClient for connecting to the database.
   */
  public transient MongoClient client;

  /**
   * MongoServer for connecting to the database.
   */
  public transient MongoServer server;

  /**
   * Collection of documents.
   */
  public transient MongoCollection<Document> collection;

  /**
   * Variable for name literal.
   */
  private static final String NAME = "name";

  /**
   * Variable for userid literal.
   */
  private static final String USERID = "userid";

  /**
   * Variable for city literal.
   */
  private static final String CITY = "city";

  /**
   * Variable for streetaddress literal.
   */
  private static final String STREETADDRES = "streetAddress";

  /**
   * Creates and connect to Mongo.
   */
  public MongoUserDao() {
    final Object[] clientAndServer = MongoDaoFactory.create();
    client = (MongoClient) clientAndServer[0];
    server = (MongoServer) clientAndServer[1];
    collection = client.getDatabase("mongo").getCollection("coll");
  }

  /**
   * Insert user to mongo.
   *
   * @param user user to insert
   * @return newly created user number or -1 on error
   */
  @Override
  public int insertUser(final User user) {
    final Document insUser = new Document("_id", new ObjectId());
    insUser.append(NAME, user.getName())
           .append(USERID, user.getUserId())
           .append(CITY, user.getCity())
           .append(STREETADDRES, user.getStreetAddress());
    collection.insertOne(insUser);
    return user.getUserId();
  }

  /**
   * Delete user in mongo.
   *
   * @param user user to delete
   * @return true on success, false on failure
   */
  @Override
  public boolean deleteUser(final User user) {
    final Bson filter = eq(USERID, user.getUserId());
    collection.deleteOne(filter);
    return true;
  }

  /**
   * Find a user in mongo using userId.
   *
   * @param userId userId to find
   * @return a User Object if found, return null on error or if not found
   */
  @Override
  public User findUser(final int userId) {
    final User user = new User();
    final Document dbuser = collection.find(new Document(USERID, userId)).first();
    user.setUserId((Integer) dbuser.get(USERID));
    user.setName((String) dbuser.get(NAME));
    user.setCity((String) dbuser.get(CITY));
    user.setStreetAddress((String) dbuser.get(STREETADDRES));
    return user;
  }

  /**
   * Update record here using data from the User Object.
   *
   * @param user user to update
   * @return true on success, false on failure or error
   */
  @Override
  public boolean updateUser(final User user) {
    final Bson filter = eq(USERID, user.getUserId());
    final Bson updateUserId = set(USERID, user.getUserId());
    final Bson updateName = set(NAME, user.getName());
    final Bson updateCity = set(CITY, user.getCity());
    final Bson updateStreetAddr = set(STREETADDRES, user.getStreetAddress());
    final Bson updates = combine(updateUserId, updateName, updateCity, updateStreetAddr);
    collection.updateOne(filter, updates);
    return true;
  }

  /**
   * Search users here using the supplied criteria.
   *
   * @param criteriaCol criteria column
   * @param criteria criteria
   * @return Collection of users found using the criteria
   */
  @Override
  public Collection selectUsersTO(final String criteriaCol, final String criteria) {
    final FindIterable<Document> iterable = collection.find(eq(criteriaCol, criteria));
    final MongoCursor<Document> cursor = iterable.iterator();
    final ArrayList<User> selectedUsers = new ArrayList<>();
    while (cursor.hasNext()) {
      final User user = new User();
      final Document document = cursor.next();
      user.setUserId((Integer) document.get(USERID));
      user.setName((String) document.get(NAME));
      user.setCity((String) document.get(CITY));
      user.setStreetAddress((String) document.get(STREETADDRES));
      selectedUsers.add(user);
    }
    cursor.close();
    return selectedUsers;
  }
}