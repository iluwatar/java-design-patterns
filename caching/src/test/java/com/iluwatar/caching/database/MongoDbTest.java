package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.constants.CachingConstants;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

import static com.iluwatar.caching.constants.CachingConstants.ADD_INFO;
import static com.iluwatar.caching.constants.CachingConstants.USER_ID;
import static com.iluwatar.caching.constants.CachingConstants.USER_NAME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MongoDbTest {
  private static final String ID = "123";
  private static final String NAME = "Some user";
  private static final String ADDITIONAL_INFO = "Some app Info";

  @Mock
  MongoDatabase db;
  private MongoDb mongoDb = new MongoDb();

  private UserAccount userAccount;

  @BeforeEach
  void init() {
    db = mock(MongoDatabase.class);
    Whitebox.setInternalState(mongoDb, "db", db);
    userAccount = new UserAccount(ID, NAME, ADDITIONAL_INFO);


  }

  @Test
  void connect() {
    assertDoesNotThrow(() -> mongoDb.connect());
  }

  @Test
  void readFromDb() {
    Document document = new Document(USER_ID, ID)
            .append(USER_NAME, NAME)
            .append(ADD_INFO, ADDITIONAL_INFO);
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(db.getCollection(CachingConstants.USER_ACCOUNT)).thenReturn(mongoCollection);

    FindIterable<Document> findIterable = mock(FindIterable.class);
    when(mongoCollection.find(any(Document.class))).thenReturn(findIterable);

    when(findIterable.first()).thenReturn(document);

    assertEquals(mongoDb.readFromDb(ID),userAccount);
  }

  @Test
  void writeToDb() {
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(db.getCollection(CachingConstants.USER_ACCOUNT)).thenReturn(mongoCollection);
    doNothing().when(mongoCollection).insertOne(any(Document.class));
    assertDoesNotThrow(()-> {mongoDb.writeToDb(userAccount);});
  }

  @Test
  void updateDb() {
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(db.getCollection(CachingConstants.USER_ACCOUNT)).thenReturn(mongoCollection);
    assertDoesNotThrow(()-> {mongoDb.updateDb(userAccount);});
  }

  @Test
  void upsertDb() {
    MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
    when(db.getCollection(CachingConstants.USER_ACCOUNT)).thenReturn(mongoCollection);
    assertDoesNotThrow(()-> {mongoDb.upsertDb(userAccount);});
  }
}