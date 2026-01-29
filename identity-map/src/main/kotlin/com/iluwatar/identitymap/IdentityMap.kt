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

// ABOUTME: Identity map implementation that caches Person objects by their national ID.
// ABOUTME: Prevents duplicate database loads by maintaining an in-memory cache.
package com.iluwatar.identitymap

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * This class stores the map into which we will be caching records after loading them from a
 * DataBase. Stores the records as a Hash Map with the personNationalIDs as keys.
 */
class IdentityMap {
    val personMap: MutableMap<Int, Person> = mutableMapOf()

    /**
     * Add person to the map.
     */
    fun addPerson(person: Person) {
        if (!personMap.containsKey(person.personNationalId)) {
            personMap[person.personNationalId] = person
        } else {
            // Ensure that addPerson does not update a record. This situation will never arise in
            // our implementation. Added only for testing purposes.
            logger.info { "Key already in Map" }
        }
    }

    /**
     * Get Person with given id.
     *
     * @param id personNationalId as requested by user.
     */
    fun getPerson(id: Int): Person? {
        val person = personMap[id]
        if (person == null) {
            logger.info { "ID not in Map." }
            return null
        }
        logger.info { person.toString() }
        return person
    }

    /**
     * Get the size of the map.
     */
    fun size(): Int = personMap.size
}
