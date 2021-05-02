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

package com.iluwatar.hexagonal.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for Mongo based ticket repository
 */
@Disabled
class MongoTicketRepositoryTest {

  private static final String TEST_DB = "lotteryTestDB";
  private static final String TEST_TICKETS_COLLECTION = "lotteryTestTickets";
  private static final String TEST_COUNTERS_COLLECTION = "testCounters";

  private MongoTicketRepository repository;

  @BeforeEach
  void init() {
    MongoConnectionPropertiesLoader.load();
    var mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
    repository = new MongoTicketRepository(TEST_DB, TEST_TICKETS_COLLECTION,
        TEST_COUNTERS_COLLECTION);
  }

  @Test
  void testSetup() {
    assertEquals(1, repository.getCountersCollection().count());
    assertEquals(0, repository.getTicketsCollection().count());
  }

  @Test
  void testNextId() {
    assertEquals(1, repository.getNextId());
    assertEquals(2, repository.getNextId());
    assertEquals(3, repository.getNextId());
  }

  @Test
  void testCrudOperations() {
    // create new lottery ticket and save it
    var details = new PlayerDetails("foo@bar.com", "123-123", "07001234");
    var random = LotteryNumbers.createRandom();
    var original = new LotteryTicket(new LotteryTicketId(), details, random);
    var saved = repository.save(original);
    assertEquals(1, repository.getTicketsCollection().count());
    assertTrue(saved.isPresent());
    // fetch the saved lottery ticket from database and check its contents
    var found = repository.findById(saved.get());
    assertTrue(found.isPresent());
    var ticket = found.get();
    assertEquals("foo@bar.com", ticket.getPlayerDetails().getEmail());
    assertEquals("123-123", ticket.getPlayerDetails().getBankAccount());
    assertEquals("07001234", ticket.getPlayerDetails().getPhoneNumber());
    assertEquals(original.getLotteryNumbers(), ticket.getLotteryNumbers());
    // clear the collection
    repository.deleteAll();
    assertEquals(0, repository.getTicketsCollection().count());
  }
}
