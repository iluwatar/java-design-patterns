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

// ABOUTME: Test class for PersonDbSimulatorImplementation database operations.
// ABOUTME: Verifies CRUD operations and exception handling for the simulated database.
package com.iluwatar.identitymap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class PersonDbSimulatorImplementationTest {
    @Test
    fun testInsert() {
        // DataBase initialization.
        val db = PersonDbSimulatorImplementation()
        assertEquals(0, db.size(), "Size of null database should be 0")
        // Dummy persons.
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        val person3 = Person(3, "Arthur", 27489171)
        db.insert(person1)
        db.insert(person2)
        db.insert(person3)
        // Test size after insertion.
        assertEquals(3, db.size(), "Incorrect size for database.")
        val person4 = Person(4, "Finn", 20499078)
        val person5 = Person(5, "Michael", 40599078)
        db.insert(person4)
        db.insert(person5)
        // Test size after more insertions.
        assertEquals(5, db.size(), "Incorrect size for database.")
        val person5duplicate = Person(5, "Kevin", 89589122)
        db.insert(person5duplicate)
        // Test size after attempt to insert record with duplicate key.
        assertEquals(5, db.size(), "Incorrect size for data base")
    }

    @Test
    fun findNotInDb() {
        val db = PersonDbSimulatorImplementation()
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        db.insert(person1)
        db.insert(person2)
        // Test if IdNotFoundException is thrown where expected.
        assertThrows(IdNotFoundException::class.java) { db.find(3) }
    }

    @Test
    fun findInDb() {
        val db = PersonDbSimulatorImplementation()
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        db.insert(person1)
        db.insert(person2)
        assertEquals(person2, db.find(2), "Record that was found was incorrect.")
    }

    @Test
    fun updateNotInDb() {
        val db = PersonDbSimulatorImplementation()
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        db.insert(person1)
        db.insert(person2)
        val person3 = Person(3, "Micheal", 25671234)
        // Test if IdNotFoundException is thrown when person with ID 3 is not in DB.
        assertThrows(IdNotFoundException::class.java) { db.update(person3) }
    }

    @Test
    fun updateInDb() {
        val db = PersonDbSimulatorImplementation()
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        db.insert(person1)
        db.insert(person2)
        val person = Person(2, "Thomas", 42273690)
        db.update(person)
        assertEquals(person, db.find(2), "Incorrect update.")
    }

    @Test
    fun deleteNotInDb() {
        val db = PersonDbSimulatorImplementation()
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        db.insert(person1)
        db.insert(person2)
        // Test if IdNotFoundException is thrown when person with this ID not in DB.
        assertThrows(IdNotFoundException::class.java) { db.delete(3) }
    }

    @Test
    fun deleteInDb() {
        val db = PersonDbSimulatorImplementation()
        val person1 = Person(1, "Thomas", 27304159)
        val person2 = Person(2, "John", 42273631)
        db.insert(person1)
        db.insert(person2)
        // delete the record.
        db.delete(1)
        // test size of database after deletion.
        assertEquals(1, db.size(), "Size after deletion is incorrect.")
        // try to find deleted record in db.
        assertThrows(IdNotFoundException::class.java) { db.find(1) }
    }
}
