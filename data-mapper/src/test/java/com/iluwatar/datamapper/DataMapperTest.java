/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.datamapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory objects from the
 * database. Its responsibility is to transfer data between the two and also to isolate them from
 * each other. With Data Mapper the in-memory objects needn't know even that there's a database
 * present; they need no SQL interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a form of Mapper ,
 * Data Mapper itself is even unknown to the domain layer.
 * <p>
 */
public class DataMapperTest {

  /**
   * This test verify that first data mapper is able to perform all CRUD operations on Student
   */
  @Test
  public void testFirstDataMapper() {

    /* Create new data mapper of first type */
    final var mapper = new StudentDataMapperImpl();

    /* Create new student */
    var studentId = 1;
    var student = new Student(studentId, "Adam", 'A');

    /* Add student in respectibe db */
    mapper.insert(student);

    /* Check if student is added in db */
    assertEquals(studentId, mapper.find(student.getStudentId()).get().getStudentId());

    /* Update existing student object */
    var updatedName = "AdamUpdated";
    student = new Student(student.getStudentId(), updatedName, 'A');

    /* Update student in respectibe db */
    mapper.update(student);

    /* Check if student is updated in db */
    assertEquals(updatedName, mapper.find(student.getStudentId()).get().getName());

    /* Delete student in db */
    mapper.delete(student);

    /* Result should be false */
    assertFalse(mapper.find(student.getStudentId()).isPresent());
  }
}
