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

// ABOUTME: MongoDB-based implementation of the banking adapter.
// ABOUTME: Persists account balances in MongoDB for production use.
package com.iluwatar.hexagonal.banking

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import org.bson.Document

/**
 * Mongo based banking adapter.
 */
class MongoBank : WireTransfers {

    var mongoClient: MongoClient? = null
        private set
    var database: MongoDatabase? = null
        private set
    var accountsCollection: MongoCollection<Document>? = null
        private set

    /**
     * Constructor.
     */
    constructor() {
        connect()
    }

    /**
     * Constructor accepting parameters.
     */
    constructor(dbName: String, accountsCollectionName: String) {
        connect(dbName, accountsCollectionName)
    }

    /**
     * Connect to database with default parameters.
     */
    fun connect() {
        connect(DEFAULT_DB, DEFAULT_ACCOUNTS_COLLECTION)
    }

    /**
     * Connect to database with given parameters.
     */
    fun connect(dbName: String, accountsCollectionName: String) {
        mongoClient?.close()
        mongoClient = MongoClient(
            System.getProperty("mongo-host"),
            System.getProperty("mongo-port").toInt()
        )
        database = mongoClient!!.getDatabase(dbName)
        accountsCollection = database!!.getCollection(accountsCollectionName)
    }

    override fun setFunds(bankAccount: String, amount: Int) {
        val search = Document("_id", bankAccount)
        val update = Document("_id", bankAccount).append("funds", amount)
        val updateOptions = UpdateOptions().upsert(true)
        accountsCollection!!.updateOne(search, Document("\$set", update), updateOptions)
    }

    override fun getFunds(bankAccount: String): Int {
        return accountsCollection!!
            .find(Document("_id", bankAccount))
            .limit(1)
            .into(ArrayList())
            .firstOrNull()
            ?.getInteger("funds")
            ?: 0
    }

    override fun transferFunds(amount: Int, sourceBackAccount: String, destinationBankAccount: String): Boolean {
        val sourceFunds = getFunds(sourceBackAccount)
        return if (sourceFunds < amount) {
            false
        } else {
            val destFunds = getFunds(destinationBankAccount)
            setFunds(sourceBackAccount, sourceFunds - amount)
            setFunds(destinationBankAccount, destFunds + amount)
            true
        }
    }

    companion object {
        private const val DEFAULT_DB = "lotteryDB"
        private const val DEFAULT_ACCOUNTS_COLLECTION = "accounts"
    }
}
