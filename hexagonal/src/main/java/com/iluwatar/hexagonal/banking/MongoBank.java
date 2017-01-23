/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Mongo based banking adapter
 */
public class MongoBank implements WireTransfers {

  private static final String DEFAULT_DB = "lotteryDB";
  private static final String DEFAULT_ACCOUNTS_COLLECTION = "accounts";

  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<Document> accountsCollection;

  /**
   * Constructor
   */
  public MongoBank() {
    connect();
  }

  /**
   * Constructor accepting parameters
   */
  public MongoBank(String dbName, String accountsCollectionName) {
    connect(dbName, accountsCollectionName);
  }

  /**
   * Connect to database with default parameters
   */
  public void connect() {
    connect(DEFAULT_DB, DEFAULT_ACCOUNTS_COLLECTION);
  }

  /**
   * Connect to database with given parameters
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
   * @return mongo client
   */
  public MongoClient getMongoClient() {
    return mongoClient;
  }

  /**
   *
   * @return mongo database
   */
  public MongoDatabase getMongoDatabase() {
    return database;
  }

  /**
   *
   * @return accounts collection
   */
  public MongoCollection<Document> getAccountsCollection() {
    return accountsCollection;
  }


  @Override
  public void setFunds(String bankAccount, int amount) {
    Document search = new Document("_id", bankAccount);
    Document update = new Document("_id", bankAccount).append("funds", amount);
    accountsCollection.updateOne(search, new Document("$set", update), new UpdateOptions().upsert(true));
  }

  @Override
  public int getFunds(String bankAccount) {
    Document search = new Document("_id", bankAccount);
    List<Document> results = accountsCollection.find(search).limit(1).into(new ArrayList<Document>());
    if (results.size() > 0) {
      return results.get(0).getInteger("funds");
    } else {
      return 0;
    }
  }

  @Override
  public boolean transferFunds(int amount, String sourceBackAccount, String destinationBankAccount) {
    int sourceFunds = getFunds(sourceBackAccount);
    if (sourceFunds < amount) {
      return false;
    } else {
      int destFunds = getFunds(destinationBankAccount);
      setFunds(sourceBackAccount, sourceFunds - amount);
      setFunds(destinationBankAccount, destFunds + amount);
      return true;
    }
  }
}
