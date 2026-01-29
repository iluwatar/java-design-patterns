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

// ABOUTME: MongoDB-based implementation of the event log adapter.
// ABOUTME: Persists lottery events in MongoDB for production audit logging.
package com.iluwatar.hexagonal.eventlog

import com.iluwatar.hexagonal.domain.PlayerDetails
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

/**
 * Mongo based event log.
 */
class MongoEventLog : LotteryEventLog {

    var mongoClient: MongoClient? = null
        private set
    var database: MongoDatabase? = null
        private set
    var eventsCollection: MongoCollection<Document>? = null
        private set

    private val stdOutEventLog = StdOutEventLog()

    /**
     * Constructor.
     */
    constructor() {
        connect()
    }

    /**
     * Constructor accepting parameters.
     */
    constructor(dbName: String, eventsCollectionName: String) {
        connect(dbName, eventsCollectionName)
    }

    /**
     * Connect to database with default parameters.
     */
    fun connect() {
        connect(DEFAULT_DB, DEFAULT_EVENTS_COLLECTION)
    }

    /**
     * Connect to database with given parameters.
     */
    fun connect(dbName: String, eventsCollectionName: String) {
        mongoClient?.close()
        mongoClient = MongoClient(
            System.getProperty("mongo-host"),
            System.getProperty("mongo-port").toInt()
        )
        database = mongoClient!!.getDatabase(dbName)
        eventsCollection = database!!.getCollection(eventsCollectionName)
    }

    override fun ticketSubmitted(details: PlayerDetails) {
        val document = Document(EMAIL, details.email)
        document[PHONE] = details.phoneNumber
        document["bank"] = details.bankAccount
        document[MESSAGE] = "Lottery ticket was submitted and bank account was charged for 3 credits."
        eventsCollection!!.insertOne(document)
        stdOutEventLog.ticketSubmitted(details)
    }

    override fun ticketSubmitError(details: PlayerDetails) {
        val document = Document(EMAIL, details.email)
        document[PHONE] = details.phoneNumber
        document["bank"] = details.bankAccount
        document[MESSAGE] = "Lottery ticket could not be submitted because lack of funds."
        eventsCollection!!.insertOne(document)
        stdOutEventLog.ticketSubmitError(details)
    }

    override fun ticketDidNotWin(details: PlayerDetails) {
        val document = Document(EMAIL, details.email)
        document[PHONE] = details.phoneNumber
        document["bank"] = details.bankAccount
        document[MESSAGE] = "Lottery ticket was checked and unfortunately did not win this time."
        eventsCollection!!.insertOne(document)
        stdOutEventLog.ticketDidNotWin(details)
    }

    override fun ticketWon(details: PlayerDetails, prizeAmount: Int) {
        val document = Document(EMAIL, details.email)
        document[PHONE] = details.phoneNumber
        document["bank"] = details.bankAccount
        document[MESSAGE] = "Lottery ticket won! The bank account was deposited with $prizeAmount credits."
        eventsCollection!!.insertOne(document)
        stdOutEventLog.ticketWon(details, prizeAmount)
    }

    override fun prizeError(details: PlayerDetails, prizeAmount: Int) {
        val document = Document(EMAIL, details.email)
        document[PHONE] = details.phoneNumber
        document["bank"] = details.bankAccount
        document[MESSAGE] = "Lottery ticket won! Unfortunately the bank credit transfer of $prizeAmount failed."
        eventsCollection!!.insertOne(document)
        stdOutEventLog.prizeError(details, prizeAmount)
    }

    companion object {
        private const val DEFAULT_DB = "lotteryDB"
        private const val DEFAULT_EVENTS_COLLECTION = "events"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        const val MESSAGE = "message"
    }
}
