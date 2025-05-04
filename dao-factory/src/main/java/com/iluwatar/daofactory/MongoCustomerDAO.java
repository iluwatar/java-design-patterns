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
      throw new RuntimeException("Delete failed: No document found with id: " + objectId);
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
