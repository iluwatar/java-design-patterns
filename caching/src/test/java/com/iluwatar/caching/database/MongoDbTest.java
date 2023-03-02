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

import com.iluwatar.caching.UserAccount;
import com.iluwatar.caching.constants.CachingConstants;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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
    mongoDb.setDb(db);
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