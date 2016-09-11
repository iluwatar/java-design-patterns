/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Mongo based ticket repository
 */
public class MongoTicketRepositoryTest {

  private static final String TEST_HOST = "localhost";
  private static final int TEST_PORT = 27017;
  private static final String TEST_DB = "lotteryDB";
  private static final String TEST_TICKETS_COLLECTION = "lotteryTickets";
  private static final String TEST_COUNTERS_COLLECTION = "counters";

  private MongoTicketRepository repository;

  @Before
  public void init() {
    MongoClient mongoClient = new MongoClient(TEST_HOST, TEST_PORT);
    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
    repository = new MongoTicketRepository(TEST_HOST, TEST_PORT, TEST_DB, TEST_TICKETS_COLLECTION,
        TEST_COUNTERS_COLLECTION);
  }

  @Test
  public void testSetup() {
    assertTrue(repository.getCountersCollection().count() == 1);
    assertTrue(repository.getTicketsCollection().count() == 0);
  }

  @Test
  public void testNextId() {
    assertEquals(1, repository.getNextId());
    assertEquals(2, repository.getNextId());
    assertEquals(3, repository.getNextId());
  }

  @Test
  public void testCrudOperations() {
    // create new lottery ticket and save it
    PlayerDetails details = PlayerDetails.create("foo@bar.com", "123-123", "07001234");
    LotteryNumbers random = LotteryNumbers.createRandom();
    LotteryTicket original = LotteryTicket.create(new LotteryTicketId(), details, random);
    Optional<LotteryTicketId> saved = repository.save(original);
    assertEquals(1, repository.getTicketsCollection().count());
    assertTrue(saved.isPresent());
    // fetch the saved lottery ticket from database and check its contents
    Optional<LotteryTicket> found = repository.findById(saved.get());
    assertTrue(found.isPresent());
    LotteryTicket ticket = found.get();
    assertEquals("foo@bar.com", ticket.getPlayerDetails().getEmail());
    assertEquals("123-123", ticket.getPlayerDetails().getBankAccount());
    assertEquals("07001234", ticket.getPlayerDetails().getPhoneNumber());
    assertEquals(original.getNumbers(), ticket.getNumbers());
    // clear the collection
    repository.deleteAll();
    assertEquals(0, repository.getTicketsCollection().count());
  }
}
