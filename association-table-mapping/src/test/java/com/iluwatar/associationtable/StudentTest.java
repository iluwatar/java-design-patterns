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

final class StudentTest {

  @Test
  void testAddCourse_valid() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);

    int associationTableLength = AssociationTable.associations.length;
    studentTest.addCourse(courseTest);
    assertEquals(associationTableLength+1, AssociationTable.associations.length);
  }

  @Test
  void testAddCourse_invalidStudent() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");

    Exception e = assertThrows(RuntimeException.class, () ->
      studentTest.addCourse(courseTest));

    assertEquals("Student [Joanne] is not found", e.getMessage());
  }

  @Test
  void testAddCourse_duplicatedCourse() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);

    studentTest.addCourse(courseTest);
    Exception e = assertThrows(RuntimeException.class, () ->
      studentTest.addCourse(courseTest));

    assertEquals("Student [Joanne] already took this course", e.getMessage());
  }

  @Test
  void testListOfCourses_valid() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);
    studentTest.addCourse(courseTest);

    assertDoesNotThrow(studentTest::listOfCourses);
  }

  @Test
  void testListOfCourses_invalidStudent() {
    Student studentTest = new Student("Joanne");

    Exception e = assertThrows(RuntimeException.class, studentTest::listOfCourses);

    assertEquals("Student [Joanne] is not found", e.getMessage());
  }

  @Test
  void testDeleteCourse_valid() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);
    studentTest.addCourse(courseTest);

    int associationTableLength = AssociationTable.associations.length;
    studentTest.deleteCourse(courseTest);

    assertEquals(associationTableLength-1, AssociationTable.associations.length);
  }

  @Test
  void testDeleteCourse_invalidStudent() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");

    Exception e = assertThrows(RuntimeException.class, () ->
      studentTest.deleteCourse(courseTest));

    assertEquals("Student [Joanne] is not found", e.getMessage());
  }

  @Test
  void testDeleteCourse_invalidCourse() {
    Course courseTest = new Course("Biology");
    Student studentTest = new Student("Joanne");
    CourseTableImpl courseTableTest = new CourseTableImpl();
    StudentTableImpl studentTableTest = new StudentTableImpl();
    courseTableTest.insert(courseTest);
    studentTableTest.insert(studentTest);

    Exception e = assertThrows(RuntimeException.class, () ->
      studentTest.deleteCourse(courseTest));

    assertEquals("Joanne didn't take this course.", e.getMessage());
  }

}
