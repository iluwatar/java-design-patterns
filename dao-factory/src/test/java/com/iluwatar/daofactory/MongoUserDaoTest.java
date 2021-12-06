package com.iluwatar.daofactory;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
public class MongoUserDaoTest {
  MongoUserDao dao;
  User u;
  @BeforeEach
  public void init() {
    AbstractDaoFactory mongoFactory = AbstractDaoFactory.getDaoFactory(AbstractDaoFactory.MONGO);
    dao = (MongoUserDao) mongoFactory.getUserDao();
    u = new User();
    u.setUserId(1);
    u.setName("John Smith");
    u.setCity("Somewhere");
    u.setStreetAddress("123 Anywhere Street");
  }
  // Test basic CRUD operations
  @Nested
  class BasicTests {
    @BeforeEach
    public void basicinit() {
            dao.insertUser(u);
        }
      @Test
      public void insert() {
        try (MongoClient client = new MongoClient(new ServerAddress(dao.serverAddress))) {
          final MongoCollection<Document> collection =
              client.getDatabase("mongo").getCollection("coll");
          assertEquals(1, collection.count());
          assertEquals(u.getUserId(), collection.find().first().get("userid"));
          assertEquals(u.getName(), collection.find().first().get("name"));
          assertEquals(u.getCity(), collection.find().first().get("city"));
          assertEquals(u.getStreetAddress(), collection.find().first().get("streetAddress"));
        }
    }
    @Test
    public void find() {
      User found = dao.findUser(u.getUserId());
      assertEquals(u.getUserId(), found.getUserId());
      assertEquals(u.getName(), found.getName());
      assertEquals(u.getCity(), found.getCity());
      assertEquals(u.getStreetAddress(), found.getStreetAddress());
    }
    @Test
    public void update() {
      User updated = new User();
      updated.setUserId(1);
      updated.setName("John Jones");
      updated.setCity("Atlantis");
      updated.setStreetAddress("2000 Coral Drive");
      dao.updateUser(updated);
      try (MongoClient client = new MongoClient(new ServerAddress(dao.serverAddress))) {
        final MongoCollection<Document> collection =
            client.getDatabase("mongo").getCollection("coll");
        assertEquals(updated.getUserId(), collection.find().first().get("userid"));
        assertEquals(updated.getName(), collection.find().first().get("name"));
        assertEquals(updated.getCity(), collection.find().first().get("city"));
        assertEquals(updated.getStreetAddress(), collection.find().first().get("streetAddress"));
      }
    }
    @Test
    public void delete() {
      try (MongoClient client = new MongoClient(new ServerAddress(dao.serverAddress))) {
        final MongoCollection<Document> collection =
            client.getDatabase("mongo").getCollection("coll");
        assertEquals(1, collection.count());
      }
      dao.deleteUser(u);
      try (MongoClient client = new MongoClient(new ServerAddress(dao.serverAddress))) {
        final MongoCollection<Document> collection =
            client.getDatabase("mongo").getCollection("coll");
        assertEquals(0, collection.count());
      }
    }
  }
  @Nested
  class MultipleEntryTests {
    @BeforeEach
    void setup() {
      User u2 = new User();
      u2.setUserId(2);
      u2.setName("Sam Smith");
      u2.setCity("Somewhere");
      u2.setStreetAddress("456 Nowhere Street");
      User u3 = new User();
      u3.setUserId(3);
      u3.setName("John Jones");
      u3.setCity("Atlantis");
      u3.setStreetAddress("2000 Coral Drive");
      dao.insertUser(u);
      dao.insertUser(u2);
      dao.insertUser(u3);
    }

    @Test
    void selectUsersTOTest() {
      ArrayList<User> selectedUsers = (ArrayList<User>) dao.selectUsersTO("city", "Somewhere");
      assertEquals(selectedUsers.size(), 2);
      assertEquals(1, selectedUsers.get(0).getUserId());
      assertEquals("Sam Smith", selectedUsers.get(1).getName());
    }
  }
  @AfterEach
  void cleanup() {
    dao.server.shutdown();
  }
}
