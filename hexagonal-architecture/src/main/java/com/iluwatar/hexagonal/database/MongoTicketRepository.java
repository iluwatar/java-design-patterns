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
import lombok.Getter;
import org.bson.Document;

/**
 * Mongo lottery ticket database.
 */
public class MongoTicketRepository implements LotteryTicketRepository {

  private static final String DEFAULT_DB = "lotteryDB";
  private static final String DEFAULT_TICKETS_COLLECTION = "lotteryTickets";
  private static final String DEFAULT_COUNTERS_COLLECTION = "counters";
  private static final String TICKET_ID = "ticketId";

  private MongoClient mongoClient;
  private MongoDatabase database;
  @Getter
  private MongoCollection<Document> ticketsCollection;
  @Getter
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
    if (countersCollection.countDocuments() <= 0) {
      initCounters();
    }
  }

  private void initCounters() {
    var doc = new Document("_id", TICKET_ID).append("seq", 1);
    countersCollection.insertOne(doc);
  }

  /**
   * Get next ticket id.
   *
   * @return next ticket id
   */
  public int getNextId() {
    var find = new Document("_id", TICKET_ID);
    var increase = new Document("seq", 1);
    var update = new Document("$inc", increase);
    var result = countersCollection.findOneAndUpdate(find, update);
    return result.getInteger("seq");
  }

  @Override
  public Optional<LotteryTicket> findById(LotteryTicketId id) {
    return ticketsCollection
        .find(new Document(TICKET_ID, id.getId()))
        .limit(1)
        .into(new ArrayList<>())
        .stream()
        .findFirst()
        .map(this::docToTicket);
  }

  @Override
  public Optional<LotteryTicketId> save(LotteryTicket ticket) {
    var ticketId = getNextId();
    var doc = new Document(TICKET_ID, ticketId);
    doc.put("email", ticket.playerDetails().email());
    doc.put("bank", ticket.playerDetails().bankAccount());
    doc.put("phone", ticket.playerDetails().phoneNumber());
    doc.put("numbers", ticket.lotteryNumbers().getNumbersAsString());
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
        .collect(Collectors.toMap(LotteryTicket::id, Function.identity()));
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
    var ticketId = new LotteryTicketId(doc.getInteger(TICKET_ID));
    return new LotteryTicket(ticketId, playerDetails, lotteryNumbers);
  }
}
