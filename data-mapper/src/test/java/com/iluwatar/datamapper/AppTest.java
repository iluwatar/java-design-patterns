/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
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
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * The Data Mapper (DM) is a layer of software that separates the in-memory objects from the
 * database. Its responsibility is to transfer data between the two and also to isolate them from
 * each other. With Data Mapper the in-memory objects needn't know even that there's a database
 * present; they need no SQL interface code, and certainly no knowledge of the database schema. (The
 * database schema is always ignorant of the objects that use it.) Since it's a form of Mapper ,
 * Data Mapper itself is even unknown to the domain layer.
 * <p>
 * The below example demonstrates basic CRUD operations: select, add, update, and delete.
 * 
 */
public final class App {

  private static Logger log = Logger.getLogger(App.class);


  private static final String DB_TYPE_ORACLE = "Oracle";
  private static final String DB_TYPE_MYSQL = "MySQL";


  /**
   * Program entry point.
   * 
   * @param args command line args.
   */
  public static final void main(final String... args) {

    if (log.isInfoEnabled() & args.length > 0) {
      log.debug("App.main(), db type: " + args[0]);
    }

    StudentDataMapper mapper = null;

    /* Check the desired db type from runtime arguments */
    if (DB_TYPE_ORACLE.equalsIgnoreCase(args[0])) {

      /* Create new data mapper for mysql */
      mapper = new StudentMySQLDataMapper();

    } else if (DB_TYPE_MYSQL.equalsIgnoreCase(args[0])) {

      /* Create new data mapper for oracle */
      mapper = new StudentMySQLDataMapper();
    } else {

      /* Don't couple any Data Mapper to java.sql.SQLException */
      throw new DataMapperException("Following data source(" + args[0] + ") is not supported");
    }

    /* Create new student */
    Student student = new Student(UUID.randomUUID(), 1, "Adam", 'A');

    /* Add student in respectibe db */
    mapper.insert(student);

    /* Find this student */
    final Optional<Student> studentToBeFound = mapper.find(student.getGuId());

    if (log.isDebugEnabled()) {
      log.debug("App.main(), db find returned : " + studentToBeFound);
    }

    /* Update existing student object */
    student = new Student(student.getGuId(), 1, "AdamUpdated", 'A');

    /* Update student in respectibe db */
    mapper.update(student);

    /* Delete student in db */
    mapper.delete(student);
  }

  private App() {}
}
