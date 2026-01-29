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

// ABOUTME: In-memory database implementation using HashMap for testing and demonstration.
// ABOUTME: Provides a simple alternative to MongoDB for running the caching pattern examples.
package com.iluwatar.caching.database

import com.iluwatar.caching.UserAccount

/**
 * Implementation of DatabaseManager. implements base methods to work with hashMap as database.
 */
class VirtualDb : DbManager {
    /** Virtual DataBase. */
    private var db: MutableMap<String, UserAccount>? = null

    /** Creates new HashMap. */
    override fun connect() {
        db = HashMap()
    }

    override fun disconnect() {
        db = null
    }

    /**
     * Read from Db.
     *
     * @param userId user ID
     * @return UserAccount or null if not found
     */
    override fun readFromDb(userId: String): UserAccount? = db?.get(userId)

    /**
     * Write to DB.
     *
     * @param userAccount user account to write
     * @return the written UserAccount
     */
    override fun writeToDb(userAccount: UserAccount): UserAccount {
        db?.put(userAccount.userId, userAccount)
        return userAccount
    }

    /**
     * Update record in DB.
     *
     * @param userAccount user account to update
     * @return the updated UserAccount
     */
    override fun updateDb(userAccount: UserAccount): UserAccount = writeToDb(userAccount)

    /**
     * Update.
     *
     * @param userAccount user account to upsert
     * @return the upserted UserAccount
     */
    override fun upsertDb(userAccount: UserAccount): UserAccount = updateDb(userAccount)
}