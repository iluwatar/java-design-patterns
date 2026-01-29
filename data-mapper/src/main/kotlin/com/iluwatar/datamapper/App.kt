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

// ABOUTME: Main entry point demonstrating the Data Mapper pattern.
// ABOUTME: Shows basic CRUD operations separating in-memory objects from the database layer.
package com.iluwatar.datamapper

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

private const val STUDENT_STRING = "App.main(), student : "

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory objects from the
 * database. Its responsibility is to transfer data between the two and also to isolate them from
 * each other. With Data Mapper the in-memory objects needn't know even that there's a database
 * present; they need no SQL interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a form of Mapper,
 * Data Mapper itself is even unknown to the domain layer.
 *
 * The below example demonstrates basic CRUD operations: Create, Read, Update, and Delete.
 */
fun main() {
    /* Create new data mapper for type 'first' */
    val mapper = StudentDataMapperImpl()

    /* Create new student */
    var student = Student(1, "Adam", 'A')

    /* Add student in respective store */
    mapper.insert(student)

    logger.debug { "$STUDENT_STRING$student, is inserted" }

    /* Find this student */
    val studentToBeFound = mapper.find(student.studentId)

    logger.debug { "$STUDENT_STRING$studentToBeFound, is searched" }

    /* Update existing student object */
    student = Student(student.studentId, "AdamUpdated", 'A')

    /* Update student in respective db */
    mapper.update(student)

    logger.debug { "$STUDENT_STRING$student, is updated" }
    logger.debug { "$STUDENT_STRING$student, is going to be deleted" }

    /* Delete student in db */
    mapper.delete(student)
}
