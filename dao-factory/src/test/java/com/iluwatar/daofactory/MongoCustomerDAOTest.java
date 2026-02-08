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
package com.iluwatar.daofactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import java.util.Optional;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

/** Tests {@link MongoCustomerDAO} */
class MongoCustomerDAOTest {
  MongoCollection<Document> customerCollection = mock(MongoCollection.class);
  MongoCustomerDAO mongoCustomerDAO = new MongoCustomerDAO(customerCollection);

  @Test
  void givenValidCustomer_whenSaveCustomer_thenSaveSucceed() {
    Customer<ObjectId> customer = new Customer<>(new ObjectId(), "John");
    mongoCustomerDAO.save(customer);
    verify(customerCollection)
        .insertOne(
            argThat(
                document ->
                    document.get("_id").equals(customer.getId())
                        && document.get("name").equals(customer.getName())));
  }

  @Test
  void givenValidCustomer_whenUpdateCustomer_thenUpdateSucceed() {
    ObjectId customerId = new ObjectId();
    Customer<ObjectId> customerUpdated = new Customer<>(customerId, "John");
    when(customerCollection.updateOne(any(Bson.class), any(Bson.class)))
        .thenReturn(UpdateResult.acknowledged(1L, 1L, null));
    mongoCustomerDAO.update(customerUpdated);
    verify(customerCollection)
        .updateOne(
            argThat(
                (Bson filter) -> {
                  Document filterDoc = (Document) filter;
                  return filterDoc.getObjectId("_id").equals(customerId);
                }),
            argThat(
                (Bson update) -> {
                  BsonDocument bsonDoc = update.toBsonDocument();
                  BsonDocument setDoc = bsonDoc.getDocument("$set");
                  return setDoc.getString("name").getValue().equals(customerUpdated.getName());
                }));
  }

  @Test
  void givenValidObjectId_whenDeleteCustomer_thenDeleteSucceed() {
    ObjectId customerId = new ObjectId();
    when(customerCollection.deleteOne(any(Bson.class))).thenReturn(DeleteResult.acknowledged(1));
    mongoCustomerDAO.delete(customerId);
    verify(customerCollection)
        .deleteOne(
            argThat(
                (Bson filter) -> {
                  BsonDocument filterDoc = filter.toBsonDocument();
                  return filterDoc.getObjectId("_id").getValue().equals(customerId);
                }));
  }

  @Test
  void givenIdNotExist_whenDeleteCustomer_thenThrowException() {
    ObjectId customerId = new ObjectId();
    when(customerCollection.deleteOne(any(Bson.class))).thenReturn(DeleteResult.acknowledged(0));
    assertThrows(CustomException.class, () -> mongoCustomerDAO.delete(customerId));
    verify(customerCollection)
        .deleteOne(
            argThat(
                (Bson filter) -> {
                  BsonDocument filterDoc = filter.toBsonDocument();
                  return filterDoc.getObjectId("_id").getValue().equals(customerId);
                }));
  }

  @Test
  void findAll_thenReturnAllCustomers() {
    FindIterable<Document> findIterable = mock(FindIterable.class);
    MongoCursor<Document> cursor = mock(MongoCursor.class);
    Document customerDoc1 = new Document("_id", new ObjectId()).append("name", "Duc");
    Document customerDoc2 = new Document("_id", new ObjectId()).append("name", "Thanh");
    when(customerCollection.find()).thenReturn(findIterable);
    when(findIterable.iterator()).thenReturn(cursor);
    when(cursor.hasNext()).thenReturn(true, true, false);
    when(cursor.next()).thenReturn(customerDoc1, customerDoc2);
    List<Customer<ObjectId>> customerList = mongoCustomerDAO.findAll();
    assertEquals(2, customerList.size());
    verify(customerCollection).find();
  }

  @Test
  void givenValidId_whenFindById_thenReturnCustomer() {
    FindIterable<Document> findIterable = mock(FindIterable.class);
    ObjectId customerId = new ObjectId();
    String customerName = "Duc";
    Document customerDoc = new Document("_id", customerId).append("name", customerName);
    when(customerCollection.find(Filters.eq("_id", customerId))).thenReturn(findIterable);
    when(findIterable.first()).thenReturn(customerDoc);

    Optional<Customer<ObjectId>> customer = mongoCustomerDAO.findById(customerId);
    assertTrue(customer.isPresent());
    assertEquals(customerId, customer.get().getId());
    assertEquals(customerName, customer.get().getName());
  }

  @Test
  void givenNotExistingId_whenFindById_thenReturnEmpty() {
    FindIterable<Document> findIterable = mock(FindIterable.class);
    ObjectId customerId = new ObjectId();
    when(customerCollection.find(Filters.eq("_id", customerId))).thenReturn(findIterable);
    when(findIterable.first()).thenReturn(null);
    Optional<Customer<ObjectId>> customer = mongoCustomerDAO.findById(customerId);
    assertTrue(customer.isEmpty());
    verify(customerCollection).find(Filters.eq("_id", customerId));
  }

  @Test
  void whenDeleteSchema_thenDeleteCollection() {
    mongoCustomerDAO.deleteSchema();
    verify(customerCollection).drop();
  }
}
