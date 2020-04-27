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

final class CourseTest {

  @Test
  void testAddStudent_valid() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);

    int associationTableLength = AssociationTable.associations.length;
    courseTest.addStudent(studentTest);
    assertEquals(associationTableLength+1, AssociationTable.associations.length);
  }

  @Test
  void testAddStudent_invalidCourse() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");

    Exception e = assertThrows(RuntimeException.class, () ->
      courseTest.addStudent(studentTest));

    assertEquals("Course [Math] is not found", e.getMessage());
  }

  @Test
  void testAddStudent_duplicatedStudent() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);

    courseTest.addStudent(studentTest);
    Exception e = assertThrows(RuntimeException.class, () ->
      courseTest.addStudent(studentTest));

    assertEquals("Course [Math] already has this student", e.getMessage());
  }

  @Test
  void testListOfStudents_valid() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);
    courseTest.addStudent(studentTest);

    assertDoesNotThrow(courseTest::listOfStudents);
  }

  @Test
  void testListOfStudents_invalidCourse() {
    Course courseTest = new Course("Math");

    Exception e = assertThrows(RuntimeException.class, courseTest::listOfStudents);

    assertEquals("Course [Math] is not found", e.getMessage());
  }

  @Test
  void testDeleteStudent_valid() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);
    courseTest.addStudent(studentTest);

    int associationTableLength = AssociationTable.associations.length;
    courseTest.deleteStudent(studentTest);

    assertEquals(associationTableLength-1, AssociationTable.associations.length);
  }

  @Test
  void testDeleteStudent_invalidCourse() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");

    Exception e = assertThrows(RuntimeException.class, () ->
      courseTest.deleteStudent(studentTest));

    assertEquals("Course [Math] is not found", e.getMessage());
  }

  @Test
  void testDeleteStudent_invalidStudent() {
    Course courseTest = new Course("Math");
    Student studentTest = new Student("Carol");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);

    Exception e = assertThrows(RuntimeException.class, () ->
      courseTest.deleteStudent(studentTest));

    assertEquals("Carol didn't take this course.", e.getMessage());
  }

}
