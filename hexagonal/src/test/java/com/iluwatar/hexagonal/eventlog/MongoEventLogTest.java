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

package com.iluwatar.hexagonal.eventlog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for Mongo event log
 */
@Disabled
class MongoEventLogTest {

  private static final String TEST_DB = "lotteryDBTest";
  private static final String TEST_EVENTS_COLLECTION = "testEvents";

  private MongoEventLog mongoEventLog;

  @BeforeEach
  void init() {
    MongoConnectionPropertiesLoader.load();
    var mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
    mongoEventLog = new MongoEventLog(TEST_DB, TEST_EVENTS_COLLECTION);
  }

  @Test
  void testSetup() {
    assertEquals(0, mongoEventLog.getEventsCollection().count());
  }

  @Test
  void testFundTransfers() {
    var playerDetails = new PlayerDetails("john@wayne.com", "000-000", "03432534543");
    mongoEventLog.prizeError(playerDetails, 1000);
    assertEquals(1, mongoEventLog.getEventsCollection().count());
    mongoEventLog.prizeError(playerDetails, 1000);
    assertEquals(2, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketDidNotWin(playerDetails);
    assertEquals(3, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketDidNotWin(playerDetails);
    assertEquals(4, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitError(playerDetails);
    assertEquals(5, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitError(playerDetails);
    assertEquals(6, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitted(playerDetails);
    assertEquals(7, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitted(playerDetails);
    assertEquals(8, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketWon(playerDetails, 1000);
    assertEquals(9, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketWon(playerDetails, 1000);
    assertEquals(10, mongoEventLog.getEventsCollection().count());
  }
}
