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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Tests for Mongo event log
 */
class MongoEventLogTest {

  private static final String TEST_DB = "lotteryDBTest";
  private static final String TEST_EVENTS_COLLECTION = "testEvents";

  private MongoEventLog mongoEventLog;

  private static MongoDBContainer mongoDBContainer;

  @BeforeAll
  static void init() {
    mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
    mongoDBContainer.start();

    final var mongoClient = new MongoClient(mongoDBContainer.getHost(), mongoDBContainer.getExposedPorts().stream().findFirst().get());
    System.setProperty("mongo-host", mongoDBContainer.getHost());
    System.setProperty("mongo-port", String.valueOf(mongoDBContainer.getExposedPorts().stream().findFirst().get()));

    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
  }

  @AfterAll
  static void tearDown() {
    mongoDBContainer.stop();
  }

  @BeforeEach
  void createEventLog() {
    mongoEventLog = new MongoEventLog(TEST_DB, TEST_EVENTS_COLLECTION);
  }

  @Test
  void testSetup() {
    assertEquals(0, mongoEventLog.getEventsCollection().countDocuments());
  }

  @Test
  void testFundTransfers() {
    var playerDetails = new PlayerDetails("john@wayne.com", "000-000", "03432534543");
    mongoEventLog.prizeError(playerDetails, 1000);
    assertEquals(1, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.prizeError(playerDetails, 1000);
    assertEquals(2, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketDidNotWin(playerDetails);
    assertEquals(3, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketDidNotWin(playerDetails);
    assertEquals(4, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketSubmitError(playerDetails);
    assertEquals(5, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketSubmitError(playerDetails);
    assertEquals(6, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketSubmitted(playerDetails);
    assertEquals(7, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketSubmitted(playerDetails);
    assertEquals(8, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketWon(playerDetails, 1000);
    assertEquals(9, mongoEventLog.getEventsCollection().countDocuments());
    mongoEventLog.ticketWon(playerDetails, 1000);
    assertEquals(10, mongoEventLog.getEventsCollection().countDocuments());
  }
}