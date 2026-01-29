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

// ABOUTME: MongoDB implementation of DbManager for persistent storage.
// ABOUTME: Handles CRUD operations against a MongoDB database using the legacy driver.
package com.iluwatar.caching.database

import com.iluwatar.caching.UserAccount
import com.iluwatar.caching.constants.CachingConstants.ADD_INFO
import com.iluwatar.caching.constants.CachingConstants.USER_ACCOUNT
import com.iluwatar.caching.constants.CachingConstants.USER_ID
import com.iluwatar.caching.constants.CachingConstants.USER_NAME
import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bson.Document

private val logger = KotlinLogging.logger {}

/**
 * Implementation of DatabaseManager. implements base methods to work with MongoDb.
 */
class MongoDb : DbManager {
    private var client: MongoClient? = null
    internal var db: MongoDatabase? = null

    /** Connect to Db. Check th connection */
    override fun connect() {
        val mongoCredential =
            MongoCredential.createCredential(
                MONGO_USER,
                DATABASE_NAME,
                MONGO_PASSWORD.toCharArray(),
            )
        val options = MongoClientOptions.builder().build()
        client = MongoClient(ServerAddress(), mongoCredential, options)
        db = client?.getDatabase(DATABASE_NAME)
    }

    override fun disconnect() {
        client?.close()
    }

    /**
     * Read data from DB.
     *
     * @param userId user ID
     * @return UserAccount or null if not found
     */
    override fun readFromDb(userId: String): UserAccount? {
        val iterable = db?.getCollection(USER_ACCOUNT)?.find(Document(USER_ID, userId))
        val doc = iterable?.first() ?: return null
        val userName = doc.getString(USER_NAME)
        val appInfo = doc.getString(ADD_INFO)
        return UserAccount(userId, userName, appInfo)
    }

    /**
     * Write data to DB.
     *
     * @param userAccount user account to write
     * @return the written UserAccount
     */
    override fun writeToDb(userAccount: UserAccount): UserAccount {
        db?.getCollection(USER_ACCOUNT)?.insertOne(
            Document(USER_ID, userAccount.userId)
                .append(USER_NAME, userAccount.userName)
                .append(ADD_INFO, userAccount.additionalInfo),
        )
        return userAccount
    }

    /**
     * Update DB.
     *
     * @param userAccount user account to update
     * @return the updated UserAccount
     */
    override fun updateDb(userAccount: UserAccount): UserAccount {
        val id = Document(USER_ID, userAccount.userId)
        val dataSet =
            Document(USER_NAME, userAccount.userName)
                .append(ADD_INFO, userAccount.additionalInfo)
        db?.getCollection(USER_ACCOUNT)?.updateOne(id, Document("\$set", dataSet))
        return userAccount
    }

    /**
     * Update data if exists.
     *
     * @param userAccount user account to upsert
     * @return the upserted UserAccount
     */
    override fun upsertDb(userAccount: UserAccount): UserAccount {
        val userId = userAccount.userId
        val userName = userAccount.userName
        val additionalInfo = userAccount.additionalInfo
        db?.getCollection(USER_ACCOUNT)?.updateOne(
            Document(USER_ID, userId),
            Document(
                "\$set",
                Document(USER_ID, userId)
                    .append(USER_NAME, userName)
                    .append(ADD_INFO, additionalInfo),
            ),
            UpdateOptions().upsert(true),
        )
        return userAccount
    }

    companion object {
        private const val DATABASE_NAME = "admin"
        private const val MONGO_USER = "root"
        private const val MONGO_PASSWORD = "rootpassword"
    }
}