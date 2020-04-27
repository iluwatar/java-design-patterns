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

package com.iluwatar.associationtable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class StudentTableImplTest  {

  @Test
  void testInsert_valid() {
    StudentTableImpl studentTableTest = new StudentTableImpl();
    Student studentTest = new Student("Jessica");

    int studentTableSize = studentTableTest.students.size();
    studentTableTest.insert(studentTest);
    assertEquals(studentTableSize+1, studentTableTest.students.size());
  }

  @Test
  void testInsert_duplicatedStudent() {
    StudentTableImpl studentTableTest = new StudentTableImpl();
    Student studentTest = new Student("Jessica");

    studentTableTest.insert(studentTest);
    Exception e = assertThrows(RuntimeException.class, () ->
      studentTableTest.insert(studentTest));

    assertEquals("Student [Jessica] already exist", e.getMessage());
  }

  @Test
  void testDelete_valid() {
    StudentTableImpl studentTableTest = new StudentTableImpl();
    Student studentTest = new Student("Student");
    studentTableTest.insert(studentTest);

    int studentTableSize = studentTableTest.students.size();
    studentTableTest.delete(studentTest);
    assertEquals(studentTableSize-1, studentTableTest.students.size());
  }

  @Test
  void testDelete_invalid() {
    StudentTableImpl studentTableTest = new StudentTableImpl();
    Student studentTest = new Student("Jessica");

    Exception e = assertThrows(RuntimeException.class, () ->
      studentTableTest.delete(studentTest));

    assertEquals("Student [Jessica] is not found", e.getMessage());
  }

  @Test
  void testUpdate_valid() {
    StudentTableImpl studentTableTest = new StudentTableImpl();
    Student studentTest = new Student("Jessica");
    studentTableTest.insert(studentTest);

    studentTableTest.update(studentTest, "Maria");
    assertEquals("Maria", studentTableTest.students.get(studentTest.getId()));
  }

  @Test
  void testUpdate_invalidStudent() {
    StudentTableImpl studentTableTest = new StudentTableImpl();
    Student studentTest = new Student("Jessica");

    Exception e = assertThrows(RuntimeException.class, () ->
      studentTableTest.update(studentTest, "Maria"));

    assertEquals("Student [Jessica] is not found", e.getMessage());
  }

}
