/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.hexagonal.banking;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import java.util.ArrayList;
import org.bson.Document;

/**
 * Mongo based banking adapter.
 */
public class MongoBank implements WireTransfers {

  private static final String DEFAULT_DB = "lotteryDB";
  private static final String DEFAULT_ACCOUNTS_COLLECTION = "accounts";

  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<Document> accountsCollection;

  /**
   * Constructor.
   */
  public MongoBank() {
    connect();
  }

  /**
   * Constructor accepting parameters.
   */
  public MongoBank(String dbName, String accountsCollectionName) {
    connect(dbName, accountsCollectionName);
  }

  /**
   * Connect to database with default parameters.
   */
  public void connect() {
    connect(DEFAULT_DB, DEFAULT_ACCOUNTS_COLLECTION);
  }

  /**
   * Connect to database with given parameters.
   */
  public void connect(String dbName, String accountsCollectionName) {
    if (mongoClient != null) {
      mongoClient.close();
    }
    mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    database = mongoClient.getDatabase(dbName);
    accountsCollection = database.getCollection(accountsCollectionName);
  }

  /**
   * Get mongo client.
   *
   * @return mongo client
   */
  public MongoClient getMongoClient() {
    return mongoClient;
  }

  /**
   * Get mongo database.
   *
   * @return mongo database
   */
  public MongoDatabase getMongoDatabase() {
    return database;
  }

  /**
   * Get accounts collection.
   *
   * @return accounts collection
   */
  public MongoCollection<Document> getAccountsCollection() {
    return accountsCollection;
  }


  @Override
  public void setFunds(String bankAccount, int amount) {
    var search = new Document("_id", bankAccount);
    var update = new Document("_id", bankAccount).append("funds", amount);
    var updateOptions = new UpdateOptions().upsert(true);
    accountsCollection.updateOne(search, new Document("$set", update), updateOptions);
  }

  @Override
  public int getFunds(String bankAccount) {
    return accountsCollection
        .find(new Document("_id", bankAccount))
        .limit(1)
        .into(new ArrayList<>())
        .stream()
        .findFirst()
        .map(x -> x.getInteger("funds"))
        .orElse(0);
  }

  @Override
  public boolean transferFunds(int amount, String sourceAccount, String destinationAccount) {
    var sourceFunds = getFunds(sourceAccount);
    if (sourceFunds < amount) {
      return false;
    } else {
      var destFunds = getFunds(destinationAccount);
      setFunds(sourceAccount, sourceFunds - amount);
      setFunds(destinationAccount, destFunds + amount);
      return true;
    }
  }
}
