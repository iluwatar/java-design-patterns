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

// ABOUTME: JSON file-based implementation of EventJournal for persisting domain events.
// ABOUTME: Serializes events to JSON and stores them line-by-line in a journal file.
package com.iluwatar.event.sourcing.processor

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.iluwatar.event.sourcing.event.AccountCreateEvent
import com.iluwatar.event.sourcing.event.DomainEvent
import com.iluwatar.event.sourcing.event.MoneyDepositEvent
import com.iluwatar.event.sourcing.event.MoneyTransferEvent
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

/**
 * This is the implementation of event journal. This implementation serialize/deserialize the events
 * with JSON and writes/reads them on a Journal.json file at the working directory.
 */
class JsonFileJournal : EventJournal() {

    private val events: MutableList<String> = mutableListOf()
    private var index = 0

    /** Instantiates a new Json file journal. */
    init {
        file = File("Journal.json")
        if (file.exists()) {
            try {
                BufferedReader(
                    InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8)
                ).use { input ->
                    var line: String?
                    while (input.readLine().also { line = it } != null) {
                        events.add(line!!)
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        } else {
            reset()
        }
    }

    /**
     * Write.
     *
     * @param domainEvent the domain event
     */
    override fun write(domainEvent: DomainEvent) {
        val mapper = ObjectMapper()
        try {
            BufferedWriter(
                OutputStreamWriter(FileOutputStream(file, true), StandardCharsets.UTF_8)
            ).use { output ->
                val eventString = mapper.writeValueAsString(domainEvent)
                output.write(eventString + "\r\n")
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Read the next domain event.
     *
     * @return the domain event
     */
    override fun readNext(): DomainEvent? {
        if (index >= events.size) {
            return null
        }
        val event = events[index]
        index++

        val mapper = ObjectMapper()
        val domainEvent: DomainEvent
        try {
            val jsonElement = mapper.readTree(event)
            val eventClassName = jsonElement.get("eventClassName").asText()
            domainEvent = when (eventClassName) {
                "AccountCreateEvent" -> mapper.treeToValue(jsonElement, AccountCreateEvent::class.java)
                "MoneyDepositEvent" -> mapper.treeToValue(jsonElement, MoneyDepositEvent::class.java)
                "MoneyTransferEvent" -> mapper.treeToValue(jsonElement, MoneyTransferEvent::class.java)
                else -> throw RuntimeException("Journal Event not recognized")
            }
        } catch (jsonProcessingException: JsonProcessingException) {
            throw RuntimeException("Failed to convert JSON")
        }

        domainEvent.realTime = false
        return domainEvent
    }
}
