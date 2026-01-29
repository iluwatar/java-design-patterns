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

// ABOUTME: Integration tests for the MongoDB ticket repository.
// ABOUTME: Disabled by default as it requires MongoDB instance.
package com.iluwatar.hexagonal.database

import com.iluwatar.hexagonal.domain.LotteryNumbers
import com.iluwatar.hexagonal.domain.LotteryTicket
import com.iluwatar.hexagonal.domain.LotteryTicketId
import com.iluwatar.hexagonal.domain.PlayerDetails
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader
import com.mongodb.MongoClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * Tests for Mongo based ticket repository
 */
@Disabled
class MongoTicketRepositoryTest {

    private lateinit var repository: MongoTicketRepository

    companion object {
        private const val TEST_DB = "lotteryTestDB"
        private const val TEST_TICKETS_COLLECTION = "lotteryTestTickets"
        private const val TEST_COUNTERS_COLLECTION = "testCounters"
    }

    @BeforeEach
    fun init() {
        MongoConnectionPropertiesLoader.load()
        val mongoClient = MongoClient(
            System.getProperty("mongo-host"),
            System.getProperty("mongo-port").toInt()
        )
        mongoClient.dropDatabase(TEST_DB)
        mongoClient.close()
        repository = MongoTicketRepository(TEST_DB, TEST_TICKETS_COLLECTION, TEST_COUNTERS_COLLECTION)
    }

    @Test
    fun testSetup() {
        assertEquals(1, repository.countersCollection!!.countDocuments())
        assertEquals(0, repository.ticketsCollection!!.countDocuments())
    }

    @Test
    fun testNextId() {
        assertEquals(1, repository.getNextId())
        assertEquals(2, repository.getNextId())
        assertEquals(3, repository.getNextId())
    }

    @Test
    fun testCrudOperations() {
        // create new lottery ticket and save it
        val details = PlayerDetails("foo@bar.com", "123-123", "07001234")
        val random = LotteryNumbers.createRandom()
        val original = LotteryTicket(LotteryTicketId(), details, random)
        val saved = repository.save(original)
        assertEquals(1, repository.ticketsCollection!!.countDocuments())
        assertTrue(saved.isPresent)
        // fetch the saved lottery ticket from database and check its contents
        val found = repository.findById(saved.get())
        assertTrue(found.isPresent)
        val ticket = found.get()
        assertEquals("foo@bar.com", ticket.playerDetails.email)
        assertEquals("123-123", ticket.playerDetails.bankAccount)
        assertEquals("07001234", ticket.playerDetails.phoneNumber)
        assertEquals(original.lotteryNumbers, ticket.lotteryNumbers)
        // clear the collection
        repository.deleteAll()
        assertEquals(0, repository.ticketsCollection!!.countDocuments())
    }
}
