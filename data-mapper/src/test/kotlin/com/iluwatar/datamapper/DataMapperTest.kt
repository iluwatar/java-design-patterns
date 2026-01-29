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

// ABOUTME: Tests the StudentDataMapperImpl with full CRUD operations.
// ABOUTME: Verifies insert, find, update, and delete functionality.
package com.iluwatar.datamapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory objects from the
 * database. Its responsibility is to transfer data between the two and also to isolate them from
 * each other. With Data Mapper the in-memory objects needn't know even that there's a database
 * present; they need no SQL interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a form of Mapper,
 * Data Mapper itself is even unknown to the domain layer.
 */
class DataMapperTest {

    /**
     * This test verify that first data mapper is able to perform all CRUD operations on Student.
     */
    @Test
    fun testFirstDataMapper() {
        /* Create new data mapper of first type */
        val mapper = StudentDataMapperImpl()

        /* Create new student */
        val studentId = 1
        var student = Student(studentId, "Adam", 'A')

        /* Add student in respective db */
        mapper.insert(student)

        /* Check if student is added in db */
        assertEquals(studentId, mapper.find(student.studentId)!!.studentId)

        /* Update existing student object */
        val updatedName = "AdamUpdated"
        student = Student(student.studentId, updatedName, 'A')

        /* Update student in respective db */
        mapper.update(student)

        /* Check if student is updated in db */
        assertEquals(updatedName, mapper.find(student.studentId)!!.name)

        /* Delete student in db */
        mapper.delete(student)

        /* Result should be null */
        assertNull(mapper.find(student.studentId))
    }
}
