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

import java.util.Optional;

import org.apache.log4j.Logger;

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory objects from the
 * database. Its responsibility is to transfer data between the two and also to isolate them from
 * each other. With Data Mapper the in-memory objects needn't know even that there's a database
 * present; they need no SQL interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a form of Mapper ,
 * Data Mapper itself is even unknown to the domain layer.
 * <p>
 * The below example demonstrates basic CRUD operations: Create, Read, Update, and Delete.
 * 
 */
public final class App {

  private static Logger log = Logger.getLogger(App.class);

  /**
   * Program entry point.
   * 
   * @param args command line args.
   */
  public static void main(final String... args) {

    /* Create new data mapper for type 'first' */
    final StudentDataMapper mapper = new StudentDataMapperImpl();

    /* Create new student */
    Student student = new Student(1, "Adam", 'A');

    /* Add student in respectibe store */
    mapper.insert(student);

    log.debug("App.main(), student : " + student + ", is inserted");

    /* Find this student */
    final Optional<Student> studentToBeFound = mapper.find(student.getStudentId());

    log.debug("App.main(), student : " + studentToBeFound + ", is searched");

    /* Update existing student object */
    student = new Student(student.getStudentId(), "AdamUpdated", 'A');

    /* Update student in respectibe db */
    mapper.update(student);

    log.debug("App.main(), student : " + student + ", is updated");
    log.debug("App.main(), student : " + student + ", is going to be deleted");

    /* Delete student in db */
    mapper.delete(student);
  }

  private App() {}
}
