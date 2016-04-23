/**
 * The MIT License Copyright (c) 2016 Amit Dixit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.datamapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.iluwatar.datamapper.Student;
import com.iluwatar.datamapper.StudentDataMapper;
import com.iluwatar.datamapper.StudentDataMapperImpl;

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
    final StudentDataMapper mapper = new StudentDataMapperImpl();

    /* Create new student */
    Student student = new Student(1, "Adam", 'A');

    /* Add student in respectibe db */
    mapper.insert(student);

    /* Check if student is added in db */
    assertEquals(student.getStudentId(), mapper.find(student.getStudentId()).get().getStudentId());

    /* Update existing student object */
    student = new Student(student.getStudentId(), "AdamUpdated", 'A');

    /* Update student in respectibe db */
    mapper.update(student);

    /* Check if student is updated in db */
    assertEquals(mapper.find(student.getStudentId()).get().getName(), "AdamUpdated");

    /* Delete student in db */
    mapper.delete(student);

    /* Result should be false */
    assertEquals(false, mapper.find(student.getStudentId()).isPresent());
  }
}
