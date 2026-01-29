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

// ABOUTME: In-memory database simulator implementation using ArrayList.
// ABOUTME: Demonstrates CRUD operations with personNationalId as primary key.
package com.iluwatar.identitymap

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * This is a sample database implementation. The database is in the form of an arraylist which
 * stores records of different persons. The personNationalId acts as the primary key for a record.
 * Operations : -> find (look for object with a particular ID) -> insert (insert record for a new
 * person into the database) -> update (update the record of a person). To do this, create a new
 * person instance with the same ID as the record you want to update. Then call this method with
 * that person as an argument. -> delete (delete the record for a particular ID)
 */
class PersonDbSimulatorImplementation : PersonDbSimulator {

    // This simulates a table in the database. To extend logic to multiple tables just add more lists
    // to the implementation.
    private val personList = mutableListOf<Person>()

    override fun find(personNationalId: Int): Person {
        val elem = personList.find { it.personNationalId == personNationalId }
        if (elem == null) {
            throw IdNotFoundException("$ID_STR$personNationalId$NOT_IN_DATA_BASE")
        }
        logger.info { elem.toString() }
        return elem
    }

    override fun insert(person: Person) {
        val elem = personList.find { it.personNationalId == person.personNationalId }
        if (elem != null) {
            logger.info { "Record already exists." }
            return
        }
        personList.add(person)
    }

    override fun update(person: Person) {
        val elem = personList.find { it.personNationalId == person.personNationalId }
        if (elem != null) {
            elem.name = person.name
            elem.phoneNum = person.phoneNum
            logger.info { "Record updated successfully" }
            return
        }
        throw IdNotFoundException("$ID_STR${person.personNationalId}$NOT_IN_DATA_BASE")
    }

    /**
     * Delete the record corresponding to given ID from the DB.
     *
     * @param personNationalId personNationalId for person whose record is to be deleted.
     */
    override fun delete(personNationalId: Int) {
        val elem = personList.find { it.personNationalId == personNationalId }
        if (elem != null) {
            personList.remove(elem)
            logger.info { "Record deleted successfully." }
            return
        }
        throw IdNotFoundException("$ID_STR$personNationalId$NOT_IN_DATA_BASE")
    }

    /**
     * Return the size of the database.
     */
    fun size(): Int = personList.size

    companion object {
        internal const val NOT_IN_DATA_BASE = " not in DataBase"
        internal const val ID_STR = "ID : "
    }
}
