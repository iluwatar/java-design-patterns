package iluwater.com.daofactory;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import de.bwaldvogel.mongo.MongoServer;
//import de.bwaldvogel.mongo.bson.ObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class MongoUserDAO implements UserDAO {
    MongoClient client;
    MongoServer server;
    MongoCollection<Document> collection;
    public MongoUserDAO() {
        Object[] clientAndServer = MongoDAOFactory.create();
        client = (MongoClient) clientAndServer[0];
        server = (MongoServer) clientAndServer[1];
        collection = client.getDatabase("mongo").getCollection("coll");
    }
    @Override
    public int insertUser(User user) {
        Document insUser = new Document("_id", new ObjectId());
        insUser.append("name", user.getName())
                .append("userid", user.getUserId())
                .append("city", user.getCity())
                .append("streetAddress", user.getStreetAddress());
        collection.insertOne(insUser);
        return user.getUserId();
    }
    @Override
    public boolean deleteUser(User user) {
        Bson filter = eq("userid", user.getUserId());
        collection.deleteOne(filter);
        return true;
    }
    @Override
    public User findUser(int userID) {
        User user = new User();
        Document dbuser = collection.find(new Document("userid", userID)).first();
        user.setUserId((Integer) dbuser.get("userid"));
        user.setName((String) dbuser.get("name"));
        user.setCity((String) dbuser.get("city"));
        user.setStreetAddress((String) dbuser.get("streetAddress"));
        return user;
    }
    @Override
    public boolean updateUser(User user) {
        Bson filter = eq("userid", user.getUserId());
        Bson updateUserId = set("userid", user.getUserId());
        Bson updateName = set("name", user.getName());
        Bson updateCity = set("city", user.getCity());
        Bson updateStreetAddress = set("streetAddress", user.getStreetAddress());
        Bson updates = combine(updateUserId, updateName, updateCity, updateStreetAddress);
        collection.updateOne(filter, updates);
        return true;
    }
    @Override
    public String selectUserRS(String criteriaCol, String criteria) {
        FindIterable<Document> iterable = collection.find(eq(criteriaCol, criteria)).projection(
                new Document("_id", 0).append("userid", 1).append("name", 1).append("city", 1).append("streetAddress", 1)
        );
        MongoCursor<Document> cursor = iterable.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while(cursor.hasNext()) {
            sb.append(cursor.next().toJson());
            if (cursor.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("]");
        cursor.close();
        return sb.toString();
    }
    @Override
    public Collection selectUsersTO(String criteriaCol, String criteria) {
        FindIterable<Document> iterable = collection.find(eq(criteriaCol, criteria));
        MongoCursor<Document> cursor = iterable.iterator();
        ArrayList<User> selectedUsers = new ArrayList<>();
        while(cursor.hasNext()) {
            User u = new User();
            Document d = cursor.next();
            u.setUserId((Integer) d.get("userid"));
            u.setName((String) d.get("name"));
            u.setCity((String) d.get("city"));
            u.setStreetAddress((String) d.get("streetAddress"));
            selectedUsers.add(u);
        }
        cursor.close();
        return selectedUsers;
    }
}