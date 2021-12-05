package com.iluwatar.daofactory;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import de.bwaldvogel.mongo.MongoServer;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/**
 * MongoUserDAO implementation of the
 * UserDAO interface. This class can contain all
 * Derby specific code and SQL statements.
 * The client is thus shielded from knowing
 * these implementation details.
 *
 */
public class MongoUserDAO implements UserDAO {
    /**
     * MongoClient for connecting to the database.
     */
    public MongoClient client;

    /**
     * MongoServer for connecting to the database.
     */
    public MongoServer server;

    /**
     * Collection of documents.
     */
    public MongoCollection<Document> collection;

    /**
     * Creates and connect to Mongo
     */
    public MongoUserDAO() {
        final Object[] clientAndServer = com.iluwatar.daofactory.MongoDAOFactory.create();
        client = (MongoClient) clientAndServer[0];
        server = (MongoServer) clientAndServer[1];
        collection = client.getDatabase("mongo").getCollection("coll");
    }

    /**
     * Insert user to mongo.
     *
     * @param user
     * @return newly created user number or -1 on error
     */
    @Override
    public int insertUser(final User user) {
        final Document insUser = new Document("_id", new ObjectId());
        insUser.append("name", user.getName())
                .append("userid", user.getUserId())
                .append("city", user.getCity())
                .append("streetAddress", user.getStreetAddress());
        collection.insertOne(insUser);
        return user.getUserId();
    }

    /**
     * Delete user in mongo.
     *
     * @param user
     * @return true on success, false on failure
     */
    @Override
    public boolean deleteUser(final User user) {
        final Bson filter = eq("userid", user.getUserId());
        collection.deleteOne(filter);
        return true;
    }

    /**
     * Find a user in mongo using userId.
     *
     * @param userId
     * @return a User Object if found, return null on error or if not found
     */
    @Override
    public User findUser(final int userId) {
        final User user = new User();
        final Document dbuser = collection.find(new Document("userid", userId)).first();
        user.setUserId((Integer) dbuser.get("userid"));
        user.setName((String) dbuser.get("name"));
        user.setCity((String) dbuser.get("city"));
        user.setStreetAddress((String) dbuser.get("streetAddress"));
        return user;
    }

    /**
     * Update record here using data from the User Object
     *
     * @param user
     * @return true on success, false on failure or error
     */
    @Override
    public boolean updateUser(final User user) {
        final Bson filter = eq("userid", user.getUserId());
        final Bson updateUserId = set("userid", user.getUserId());
        final Bson updateName = set("name", user.getName());
        final Bson updateCity = set("city", user.getCity());
        final Bson updateStreetAddr = set("streetAddress", user.getStreetAddress());
        final Bson updates = combine(updateUserId, updateName, updateCity, updateStreetAddr);
        collection.updateOne(filter, updates);
        return true;
    }

    /**
     * Search users here using the supplied criteria.
     *
     * @param criteriaCol, criteria
     * @return Collection of users found using the criteria
     */
    @Override
    public Collection selectUsersTO(final String criteriaCol, final String criteria) {
        final FindIterable<Document> iterable = collection.find(eq(criteriaCol, criteria));
        final MongoCursor<Document> cursor = iterable.iterator();
        final ArrayList<User> selectedUsers = new ArrayList<>();
        while(cursor.hasNext()) {
            final User user = new User();
            final Document document = cursor.next();
            user.setUserId((Integer) document.get("userid"));
            user.setName((String) document.get("name"));
            user.setCity((String) document.get("city"));
            user.setStreetAddress((String) document.get("streetAddress"));
            selectedUsers.add(user);
        }
        cursor.close();
        return selectedUsers;
    }
}