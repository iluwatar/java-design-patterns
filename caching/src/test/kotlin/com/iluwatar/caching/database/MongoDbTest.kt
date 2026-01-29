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

// ABOUTME: Unit tests for MongoDb implementation using MockK for mocking MongoDB interactions.
// ABOUTME: Verifies CRUD operations work correctly with mocked database collections.
package com.iluwatar.caching.database

import com.iluwatar.caching.UserAccount
import com.iluwatar.caching.constants.CachingConstants
import com.iluwatar.caching.constants.CachingConstants.ADD_INFO
import com.iluwatar.caching.constants.CachingConstants.USER_ID
import com.iluwatar.caching.constants.CachingConstants.USER_NAME
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.mockk.every
import io.mockk.mockk
import org.bson.Document
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MongoDbTest {
    private lateinit var db: MongoDatabase
    private val mongoDb = MongoDb()
    private lateinit var userAccount: UserAccount

    @BeforeEach
    fun init() {
        db = mockk(relaxed = true)
        mongoDb.db = db
        userAccount = UserAccount(ID, NAME, ADDITIONAL_INFO)
    }

    @Test
    fun connect() {
        assertDoesNotThrow { mongoDb.connect() }
    }

    @Test
    fun readFromDb() {
        val document =
            Document(USER_ID, ID)
                .append(USER_NAME, NAME)
                .append(ADD_INFO, ADDITIONAL_INFO)
        val mongoCollection = mockk<MongoCollection<Document>>()
        every { db.getCollection(CachingConstants.USER_ACCOUNT) } returns mongoCollection

        val findIterable = mockk<FindIterable<Document>>()
        every { mongoCollection.find(any<Document>()) } returns findIterable

        every { findIterable.first() } returns document

        assertEquals(mongoDb.readFromDb(ID), userAccount)
    }

    @Test
    fun writeToDb() {
        val mongoCollection = mockk<MongoCollection<Document>>(relaxed = true)
        every { db.getCollection(CachingConstants.USER_ACCOUNT) } returns mongoCollection
        assertDoesNotThrow {
            mongoDb.writeToDb(userAccount)
        }
    }

    @Test
    fun updateDb() {
        val mongoCollection = mockk<MongoCollection<Document>>(relaxed = true)
        every { db.getCollection(CachingConstants.USER_ACCOUNT) } returns mongoCollection
        assertDoesNotThrow {
            mongoDb.updateDb(userAccount)
        }
    }

    @Test
    fun upsertDb() {
        val mongoCollection = mockk<MongoCollection<Document>>(relaxed = true)
        every { db.getCollection(CachingConstants.USER_ACCOUNT) } returns mongoCollection
        assertDoesNotThrow {
            mongoDb.upsertDb(userAccount)
        }
    }

    companion object {
        private const val ID = "123"
        private const val NAME = "Some user"
        private const val ADDITIONAL_INFO = "Some app Info"
    }
}