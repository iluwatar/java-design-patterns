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

// ABOUTME: Test class for PersonFinder identity map pattern implementation.
// ABOUTME: Verifies caching behavior when finding persons in database vs identity map.
package com.iluwatar.identitymap

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PersonFinderTest {
    @Test
    fun personFoundInDB() {
        // personFinderInstance
        val personFinder = PersonFinder()
        // init database for our personFinder
        val db = PersonDbSimulatorImplementation()
        // Dummy persons
        val person1 = Person(1, "John", 27304159)
        val person2 = Person(2, "Thomas", 42273631)
        val person3 = Person(3, "Arthur", 27489171)
        val person4 = Person(4, "Finn", 20499078)
        val person5 = Person(5, "Michael", 40599078)
        // Add data to the database.
        db.insert(person1)
        db.insert(person2)
        db.insert(person3)
        db.insert(person4)
        db.insert(person5)
        personFinder.db = db

        assertEquals(
            person1,
            personFinder.getPerson(1),
            "Find person returns incorrect record."
        )
        assertEquals(
            person3,
            personFinder.getPerson(3),
            "Find person returns incorrect record."
        )
        assertEquals(
            person2,
            personFinder.getPerson(2),
            "Find person returns incorrect record."
        )
        assertEquals(
            person5,
            personFinder.getPerson(5),
            "Find person returns incorrect record."
        )
        assertEquals(
            person4,
            personFinder.getPerson(4),
            "Find person returns incorrect record."
        )
    }

    @Test
    fun personFoundInIdMap() {
        // personFinderInstance
        val personFinder = PersonFinder()
        // init database for our personFinder
        val db = PersonDbSimulatorImplementation()
        // Dummy persons
        val person1 = Person(1, "John", 27304159)
        val person2 = Person(2, "Thomas", 42273631)
        val person3 = Person(3, "Arthur", 27489171)
        val person4 = Person(4, "Finn", 20499078)
        val person5 = Person(5, "Michael", 40599078)
        // Add data to the database.
        db.insert(person1)
        db.insert(person2)
        db.insert(person3)
        db.insert(person4)
        db.insert(person5)
        personFinder.db = db
        // Assure key is not in the ID map.
        assertFalse(personFinder.identityMap.personMap.containsKey(3))
        // Assure key is in the database.
        assertEquals(person3, personFinder.getPerson(3), "Finder returns incorrect record.")
        // Assure that the record for this key is cached in the Map now.
        assertTrue(personFinder.identityMap.personMap.containsKey(3))
        // Find the record again. This time it will be found in the map.
        assertEquals(person3, personFinder.getPerson(3), "Finder returns incorrect record.")
    }

    @Test
    fun personNotFoundInDB() {
        val personFinder = PersonFinder()
        // init database for our personFinder
        val db = PersonDbSimulatorImplementation()
        personFinder.db = db
        assertThrows(IdNotFoundException::class.java) { personFinder.getPerson(1) }
        // Dummy persons
        val person1 = Person(1, "John", 27304159)
        val person2 = Person(2, "Thomas", 42273631)
        val person3 = Person(3, "Arthur", 27489171)
        val person4 = Person(4, "Finn", 20499078)
        val person5 = Person(5, "Michael", 40599078)
        db.insert(person1)
        db.insert(person2)
        db.insert(person3)
        db.insert(person4)
        db.insert(person5)
        personFinder.db = db
        // Assure that the database has been updated.
        assertEquals(person4, personFinder.getPerson(4), "Find returns incorrect record")
        // Assure key is in DB now.
        assertDoesNotThrow { personFinder.getPerson(1) }
        // Assure key not in DB.
        assertThrows(IdNotFoundException::class.java) { personFinder.getPerson(6) }
    }
}
