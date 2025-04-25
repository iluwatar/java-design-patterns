package com.iluwatar.daofactory;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:26
 * Filename  : NitriteCustomerDAO
 */
@Slf4j
public class MongoCustomerDAO implements CustomerDAO<ObjectId> {
  static String connString = "mongodb://localhost:27017/";
  static MongoClient mongoClient = null;
  static MongoDatabase database = null;
  static MongoCollection<Document> customerCollection = null;

  static {
    try {
      mongoClient = MongoClients.create(connString);
      database = mongoClient.getDatabase("dao_factory");
      customerCollection = database.getCollection("customer");
    } catch (RuntimeException e) {
      LOGGER.error("Error: %s", e);
    }

  }


  @Override
  public void save(Customer<ObjectId> customer) {
    Document customerDocument = new Document("_id", customer.getId());
    customerDocument.append("name", customer.getName());
    customerCollection.insertOne(customerDocument);
  }

  @Override
  public void update(Customer<ObjectId> customer) {
    Document updateQuery = new Document("_id", customer.getId());
    Bson update = Updates.set("name", customer.getName());
    customerCollection.updateOne(updateQuery, update);
  }

  @Override
  public void delete(ObjectId objectId) {
    Bson deleteQuery = Filters.eq("_id", objectId);
    customerCollection.deleteOne(deleteQuery);
  }

  @Override
  public List<Customer<ObjectId>> findAll() {
    List<Customer<ObjectId>> customers = new LinkedList<>();
    FindIterable<Document> customerDocuments = customerCollection.find();
    for (Document customerDocument : customerDocuments) {
      Customer<ObjectId> customer = new Customer<>((ObjectId) customerDocument.get("_id"),
          customerDocument.getString("name"));
      customers.add(customer);
    }
    return customers;
  }

  @Override
  public Optional<Customer<ObjectId>> findById(ObjectId objectId) {
    Bson filter = Filters.eq("_id", objectId);
    Document customerDocument = customerCollection.find(filter)
        .first();
    Customer<ObjectId> customerResult = null;
    if (customerDocument != null) {
      customerResult =
          new Customer<>((ObjectId) customerDocument.get("_id"),
              customerDocument.getString("name"));
    }
    return Optional.ofNullable(customerResult);
  }

  @Override
  public void deleteSchema() {
    database.drop();
  }
}
