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
package com.iluwatar.hexagonal.eventlog;

import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

/**
 * Mongo based event log.
 */
public class MongoEventLog implements LotteryEventLog {

  private static final String DEFAULT_DB = "lotteryDB";
  private static final String DEFAULT_EVENTS_COLLECTION = "events";
  private static final String EMAIL = "email";
  private static final String PHONE = "phone";
  public static final String MESSAGE = "message";

  @Getter
  private MongoClient mongoClient;
  @Getter
  private MongoDatabase database;
  @Getter
  private MongoCollection<Document> eventsCollection;

  private final StdOutEventLog stdOutEventLog = new StdOutEventLog();

  /**
   * Constructor.
   */
  public MongoEventLog() {
    connect();
  }

  /**
   * Constructor accepting parameters.
   */
  public MongoEventLog(String dbName, String eventsCollectionName) {
    connect(dbName, eventsCollectionName);
  }

  /**
   * Connect to database with default parameters.
   */
  public void connect() {
    connect(DEFAULT_DB, DEFAULT_EVENTS_COLLECTION);
  }

  /**
   * Connect to database with given parameters.
   */
  public void connect(String dbName, String eventsCollectionName) {
    if (mongoClient != null) {
      mongoClient.close();
    }
    mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    database = mongoClient.getDatabase(dbName);
    eventsCollection = database.getCollection(eventsCollectionName);
  }

  @Override
  public void ticketSubmitted(PlayerDetails details) {
    var document = new Document(EMAIL, details.email());
    document.put(PHONE, details.phoneNumber());
    document.put("bank", details.bankAccount());
    document
        .put(MESSAGE, "Lottery ticket was submitted and bank account was charged for 3 credits.");
    eventsCollection.insertOne(document);
    stdOutEventLog.ticketSubmitted(details);
  }

  @Override
  public void ticketSubmitError(PlayerDetails details) {
    var document = new Document(EMAIL, details.email());
    document.put(PHONE, details.phoneNumber());
    document.put("bank", details.bankAccount());
    document.put(MESSAGE, "Lottery ticket could not be submitted because lack of funds.");
    eventsCollection.insertOne(document);
    stdOutEventLog.ticketSubmitError(details);
  }

  @Override
  public void ticketDidNotWin(PlayerDetails details) {
    var document = new Document(EMAIL, details.email());
    document.put(PHONE, details.phoneNumber());
    document.put("bank", details.bankAccount());
    document.put(MESSAGE, "Lottery ticket was checked and unfortunately did not win this time.");
    eventsCollection.insertOne(document);
    stdOutEventLog.ticketDidNotWin(details);
  }

  @Override
  public void ticketWon(PlayerDetails details, int prizeAmount) {
    var document = new Document(EMAIL, details.email());
    document.put(PHONE, details.phoneNumber());
    document.put("bank", details.bankAccount());
    document.put(MESSAGE, String
        .format("Lottery ticket won! The bank account was deposited with %d credits.",
            prizeAmount));
    eventsCollection.insertOne(document);
    stdOutEventLog.ticketWon(details, prizeAmount);
  }

  @Override
  public void prizeError(PlayerDetails details, int prizeAmount) {
    var document = new Document(EMAIL, details.email());
    document.put(PHONE, details.phoneNumber());
    document.put("bank", details.bankAccount());
    document.put(MESSAGE, String
        .format("Lottery ticket won! Unfortunately the bank credit transfer of %d failed.",
            prizeAmount));
    eventsCollection.insertOne(document);
    stdOutEventLog.prizeError(details, prizeAmount);
  }
}
