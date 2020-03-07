/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.hexagonal.database;

import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 * Mongo lottery ticket database.
 */
public class MongoTicketRepository implements LotteryTicketRepository {

  private static final String DEFAULT_DB = "lotteryDB";
  private static final String DEFAULT_TICKETS_COLLECTION = "lotteryTickets";
  private static final String DEFAULT_COUNTERS_COLLECTION = "counters";

  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<Document> ticketsCollection;
  private MongoCollection<Document> countersCollection;

  /**
   * Constructor.
   */
  public MongoTicketRepository() {
    connect();
  }

  /**
   * Constructor accepting parameters.
   */
  public MongoTicketRepository(String dbName, String ticketsCollectionName,
                               String countersCollectionName) {
    connect(dbName, ticketsCollectionName, countersCollectionName);
  }

  /**
   * Connect to database with default parameters.
   */
  public void connect() {
    connect(DEFAULT_DB, DEFAULT_TICKETS_COLLECTION, DEFAULT_COUNTERS_COLLECTION);
  }

  /**
   * Connect to database with given parameters.
   */
  public void connect(String dbName, String ticketsCollectionName,
                      String countersCollectionName) {
    if (mongoClient != null) {
      mongoClient.close();
    }
    mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    database = mongoClient.getDatabase(dbName);
    ticketsCollection = database.getCollection(ticketsCollectionName);
    countersCollection = database.getCollection(countersCollectionName);
    if (countersCollection.count() <= 0) {
      initCounters();
    }
  }

  private void initCounters() {
    var doc = new Document("_id", "ticketId").append("seq", 1);
    countersCollection.insertOne(doc);
  }

  /**
   * Get next ticket id.
   *
   * @return next ticket id
   */
  public int getNextId() {
    var find = new Document("_id", "ticketId");
    var increase = new Document("seq", 1);
    var update = new Document("$inc", increase);
    var result = countersCollection.findOneAndUpdate(find, update);
    return result.getInteger("seq");
  }

  /**
   * Get tickets collection.
   *
   * @return tickets collection
   */
  public MongoCollection<Document> getTicketsCollection() {
    return ticketsCollection;
  }

  /**
   * Get counters collection.
   *
   * @return counters collection
   */
  public MongoCollection<Document> getCountersCollection() {
    return countersCollection;
  }

  @Override
  public Optional<LotteryTicket> findById(LotteryTicketId id) {
    return ticketsCollection
        .find(new Document("ticketId", id.getId()))
        .limit(1)
        .into(new ArrayList<>())
        .stream()
        .findFirst()
        .map(this::docToTicket);
  }

  @Override
  public Optional<LotteryTicketId> save(LotteryTicket ticket) {
    var ticketId = getNextId();
    var doc = new Document("ticketId", ticketId);
    doc.put("email", ticket.getPlayerDetails().getEmail());
    doc.put("bank", ticket.getPlayerDetails().getBankAccount());
    doc.put("phone", ticket.getPlayerDetails().getPhoneNumber());
    doc.put("numbers", ticket.getNumbers().getNumbersAsString());
    ticketsCollection.insertOne(doc);
    return Optional.of(new LotteryTicketId(ticketId));
  }

  @Override
  public Map<LotteryTicketId, LotteryTicket> findAll() {
    return ticketsCollection
        .find(new Document())
        .into(new ArrayList<>())
        .stream()
        .map(this::docToTicket)
        .collect(Collectors.toMap(LotteryTicket::getId, Function.identity()));
  }

  @Override
  public void deleteAll() {
    ticketsCollection.deleteMany(new Document());
  }

  private LotteryTicket docToTicket(Document doc) {
    var playerDetails = new PlayerDetails(doc.getString("email"), doc.getString("bank"),
        doc.getString("phone"));
    var numbers = Arrays.stream(doc.getString("numbers").split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toSet());
    var lotteryNumbers = LotteryNumbers.create(numbers);
    var ticketId = new LotteryTicketId(doc.getInteger("ticketId"));
    return new LotteryTicket(ticketId, playerDetails, lotteryNumbers);
  }
}
