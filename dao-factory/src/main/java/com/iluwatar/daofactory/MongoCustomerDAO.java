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

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/** An implementation of {@link CustomerDAO} that uses MongoDB (https://www.mongodb.com/) */
@Slf4j
@RequiredArgsConstructor
public class MongoCustomerDAO implements CustomerDAO<ObjectId> {
  private final MongoCollection<Document> customerCollection;

  /** {@inheritDoc} */
  @Override
  public void save(Customer<ObjectId> customer) {
    Document customerDocument = new Document("_id", customer.getId());
    customerDocument.append("name", customer.getName());
    customerCollection.insertOne(customerDocument);
  }

  /** {@inheritDoc} */
  @Override
  public void update(Customer<ObjectId> customer) {
    Document updateQuery = new Document("_id", customer.getId());
    Bson update = Updates.set("name", customer.getName());
    customerCollection.updateOne(updateQuery, update);
  }

  /** {@inheritDoc} */
  @Override
  public void delete(ObjectId objectId) {
    Bson deleteQuery = Filters.eq("_id", objectId);
    DeleteResult deleteResult = customerCollection.deleteOne(deleteQuery);
    if (deleteResult.getDeletedCount() == 0) {
      throw new CustomException("Delete failed: No document found with id: " + objectId);
    }
  }

  /** {@inheritDoc} */
  @Override
  public List<Customer<ObjectId>> findAll() {
    List<Customer<ObjectId>> customers = new LinkedList<>();
    FindIterable<Document> customerDocuments = customerCollection.find();
    for (Document customerDocument : customerDocuments) {
      Customer<ObjectId> customer =
          new Customer<>(
              (ObjectId) customerDocument.get("_id"), customerDocument.getString("name"));
      customers.add(customer);
    }
    return customers;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Customer<ObjectId>> findById(ObjectId objectId) {
    Bson filter = Filters.eq("_id", objectId);
    Document customerDocument = customerCollection.find(filter).first();
    Customer<ObjectId> customerResult = null;
    if (customerDocument != null) {
      customerResult =
          new Customer<>(
              (ObjectId) customerDocument.get("_id"), customerDocument.getString("name"));
    }
    return Optional.ofNullable(customerResult);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteSchema() {
    customerCollection.drop();
  }
}
