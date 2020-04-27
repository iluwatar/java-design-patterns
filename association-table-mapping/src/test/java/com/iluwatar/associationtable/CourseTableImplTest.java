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

final class CourseTableImplTest  {

  @Test
  void testInsert_valid() {
    CourseTableImpl courseTableTest = new CourseTableImpl();
    Course courseTest = new Course("Literature");

    int courseTableSize = courseTableTest.courses.size();
    courseTableTest.insert(courseTest);
    assertEquals(courseTableSize+1, courseTableTest.courses.size());
  }

  @Test
  void testInsert_duplicatedCourse() {
    CourseTableImpl courseTableTest = new CourseTableImpl();
    Course courseTest = new Course("Literature");

    courseTableTest.insert(courseTest);
    Exception e = assertThrows(RuntimeException.class, () ->
      courseTableTest.insert(courseTest));

    assertEquals("Course [Literature] already exist", e.getMessage());
  }

  @Test
  void testDelete_valid() {
    CourseTableImpl courseTableTest = new CourseTableImpl();
    Course courseTest = new Course("Literature");
    courseTableTest.insert(courseTest);

    int courseTableSize = courseTableTest.courses.size();
    courseTableTest.delete(courseTest);
    assertEquals(courseTableSize-1, courseTableTest.courses.size());
  }

  @Test
  void testDelete_invalid() {
    CourseTableImpl courseTableTest = new CourseTableImpl();
    Course courseTest = new Course("Literature");

    Exception e = assertThrows(RuntimeException.class, () ->
      courseTableTest.delete(courseTest));

    assertEquals("Course [Literature] is not found", e.getMessage());
  }

  @Test
  void testUpdate_valid() {
    CourseTableImpl courseTableTest = new CourseTableImpl();
    Course courseTest = new Course("Literature");
    courseTableTest.insert(courseTest);

    courseTableTest.update(courseTest, "Italian literature");
    assertEquals("Italian literature", courseTableTest.courses.get(courseTest.getId()));
  }

  @Test
  void testUpdate_invalidCourse() {
    CourseTableImpl courseTableTest = new CourseTableImpl();
    Course courseTest = new Course("Literature");

    Exception e = assertThrows(RuntimeException.class, () ->
      courseTableTest.update(courseTest, "Italian literature"));

    assertEquals("Course [Literature] is not found", e.getMessage());
  }

}
