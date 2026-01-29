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

// ABOUTME: MongoDB-based implementation of the lottery ticket repository.
// ABOUTME: Persists lottery tickets in MongoDB for production use.
package com.iluwatar.hexagonal.database

import com.iluwatar.hexagonal.domain.LotteryNumbers
import com.iluwatar.hexagonal.domain.LotteryTicket
import com.iluwatar.hexagonal.domain.LotteryTicketId
import com.iluwatar.hexagonal.domain.PlayerDetails
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import java.util.Optional

/**
 * Mongo lottery ticket database.
 */
class MongoTicketRepository : LotteryTicketRepository {

    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null
    var ticketsCollection: MongoCollection<Document>? = null
        private set
    var countersCollection: MongoCollection<Document>? = null
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
    constructor(dbName: String, ticketsCollectionName: String, countersCollectionName: String) {
        connect(dbName, ticketsCollectionName, countersCollectionName)
    }

    /**
     * Connect to database with default parameters.
     */
    fun connect() {
        connect(DEFAULT_DB, DEFAULT_TICKETS_COLLECTION, DEFAULT_COUNTERS_COLLECTION)
    }

    /**
     * Connect to database with given parameters.
     */
    fun connect(dbName: String, ticketsCollectionName: String, countersCollectionName: String) {
        mongoClient?.close()
        mongoClient = MongoClient(
            System.getProperty("mongo-host"),
            System.getProperty("mongo-port").toInt()
        )
        database = mongoClient!!.getDatabase(dbName)
        ticketsCollection = database!!.getCollection(ticketsCollectionName)
        countersCollection = database!!.getCollection(countersCollectionName)
        if (countersCollection!!.countDocuments() <= 0) {
            initCounters()
        }
    }

    private fun initCounters() {
        val doc = Document("_id", TICKET_ID).append("seq", 1)
        countersCollection!!.insertOne(doc)
    }

    /**
     * Get next ticket id.
     *
     * @return next ticket id
     */
    fun getNextId(): Int {
        val find = Document("_id", TICKET_ID)
        val increase = Document("seq", 1)
        val update = Document("\$inc", increase)
        val result = countersCollection!!.findOneAndUpdate(find, update)
        return result!!.getInteger("seq")
    }

    override fun findById(id: LotteryTicketId): Optional<LotteryTicket> {
        return Optional.ofNullable(
            ticketsCollection!!
                .find(Document(TICKET_ID, id.id))
                .limit(1)
                .into(ArrayList())
                .firstOrNull()
                ?.let { docToTicket(it) }
        )
    }

    override fun save(ticket: LotteryTicket): Optional<LotteryTicketId> {
        val ticketId = getNextId()
        val doc = Document(TICKET_ID, ticketId)
        doc["email"] = ticket.playerDetails.email
        doc["bank"] = ticket.playerDetails.bankAccount
        doc["phone"] = ticket.playerDetails.phoneNumber
        doc["numbers"] = ticket.lotteryNumbers.getNumbersAsString()
        ticketsCollection!!.insertOne(doc)
        return Optional.of(LotteryTicketId(ticketId))
    }

    override fun findAll(): Map<LotteryTicketId, LotteryTicket> {
        return ticketsCollection!!.find(Document()).into(ArrayList())
            .map { docToTicket(it) }
            .associateBy { it.id }
    }

    override fun deleteAll() {
        ticketsCollection!!.deleteMany(Document())
    }

    private fun docToTicket(doc: Document): LotteryTicket {
        val playerDetails = PlayerDetails(
            doc.getString("email"),
            doc.getString("bank"),
            doc.getString("phone")
        )
        val numbers = doc.getString("numbers")
            .split(",")
            .map { it.toInt() }
            .toSet()
        val lotteryNumbers = LotteryNumbers.create(numbers)
        val ticketId = LotteryTicketId(doc.getInteger(TICKET_ID))
        return LotteryTicket(ticketId, playerDetails, lotteryNumbers)
    }

    companion object {
        private const val DEFAULT_DB = "lotteryDB"
        private const val DEFAULT_TICKETS_COLLECTION = "lotteryTickets"
        private const val DEFAULT_COUNTERS_COLLECTION = "counters"
        private const val TICKET_ID = "ticketId"
    }
}
