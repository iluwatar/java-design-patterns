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

// ABOUTME: Integration tests for the MongoDB banking adapter.
// ABOUTME: Uses embedded MongoDB for testing fund transfers and account management.
package com.iluwatar.hexagonal.banking

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import de.flapdoodle.embed.mongo.commands.ServerAddress
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.transitions.Mongod
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess
import de.flapdoodle.reverse.TransitionWalker
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests for Mongo banking adapter
 */
class MongoBankTest {

    private lateinit var mongoBank: MongoBank

    companion object {
        private const val TEST_DB = "lotteryDBTest"
        private const val TEST_ACCOUNTS_COLLECTION = "testAccounts"

        private lateinit var mongoClient: MongoClient
        private lateinit var mongoDatabase: MongoDatabase
        private lateinit var mongodProcess: TransitionWalker.ReachedState<RunningMongodProcess>
        private lateinit var serverAddress: ServerAddress

        @BeforeAll
        @JvmStatic
        fun setUp() {
            mongodProcess = Mongod.instance().start(Version.Main.V7_0)
            serverAddress = mongodProcess.current().serverAddress
            mongoClient = MongoClients.create("mongodb://$serverAddress")
            mongoClient.startSession()
            mongoDatabase = mongoClient.getDatabase(TEST_DB)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            mongoClient.close()
            mongodProcess.close()
        }
    }

    @BeforeEach
    fun init() {
        System.setProperty("mongo-host", serverAddress.host)
        System.setProperty("mongo-port", serverAddress.port.toString())
        mongoDatabase.drop()
        mongoBank = MongoBank(mongoDatabase.name, TEST_ACCOUNTS_COLLECTION)
    }

    @Test
    fun testSetup() {
        assertEquals(0, mongoBank.accountsCollection!!.countDocuments())
    }

    @Test
    fun testFundTransfers() {
        assertEquals(0, mongoBank.getFunds("000-000"))
        mongoBank.setFunds("000-000", 10)
        assertEquals(10, mongoBank.getFunds("000-000"))
        assertEquals(0, mongoBank.getFunds("111-111"))
        mongoBank.transferFunds(9, "000-000", "111-111")
        assertEquals(1, mongoBank.getFunds("000-000"))
        assertEquals(9, mongoBank.getFunds("111-111"))
    }
}
