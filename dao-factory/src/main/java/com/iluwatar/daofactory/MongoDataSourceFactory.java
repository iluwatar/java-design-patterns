package com.iluwatar.daofactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/** MongoDataSourceFactory concrete factory. */
public class MongoDataSourceFactory extends DAOFactory {
  private final String CONN_STR = "mongodb://localhost:27017/";
  private final String DB_NAME = "dao_factory";
  private final String COLLECTION_NAME = "customer";

  @Override
  public CustomerDAO createCustomerDAO() {
    try {
      MongoClient mongoClient = MongoClients.create(CONN_STR);
      MongoDatabase database = mongoClient.getDatabase(DB_NAME);
      MongoCollection<Document> customerCollection = database.getCollection(COLLECTION_NAME);
      return new MongoCustomerDAO(customerCollection);
    } catch (RuntimeException e) {
      throw new RuntimeException("Error: " + e);
    }
  }
}
