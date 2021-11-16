/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.serialized.lob;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

/**
 *
 */
@Slf4j
public final class App {
  private static final String DB_URL = "jdbc:h2:~/test";

  /**
   * Private Constructor.
   *
   */
  private App() {}

  /**
   * Program entry point.
   *
   * @param args command line args.
   * @throws SQLException if any error occurs.
   */
  public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
    // Create data source and create the person table.
    final var dataSource = createDataSource();

    // Drop table Positions if exists
    deleteSchema(dataSource);

    // Create table Positions if not exists
    createSchema(dataSource);

    // Initialize five departments and access the database using Serialization.
    var devDept = new Department("Developing", null);
    var testDept = new Department("Testing", null);
    var devManageDept = new Department("Development Management", List.of(devDept));
    var testManageDept = new Department("Testing Management", List.of(testDept));
    var engineeringManageDept = new Department("Engineering Management", List.of(devManageDept, testManageDept));

    // Initialize five departments and access the database using Serialization.
    var CTO = new Position(1, "CTO", List.of(engineeringManageDept));
    var director = new Position(2, "Director", List.of(devManageDept, testManageDept));
    var developer = new Position(3, "Developer", List.of(devDept));
    var tester = new Position(5, "Tester", List.of(testDept));

    var serializationCTO = new Serialization(CTO, dataSource);
    var serializationDirector = new Serialization(director, dataSource);
    var serializationDeveloper = new Serialization(developer, dataSource);
    var serializationTester = new Serialization(tester, dataSource);

    serializationCTO.insert();
    serializationDirector.insert();
    serializationDeveloper.insert();
    serializationTester.insert();

    serializationCTO.read();

    serializationDirector.read();
    serializationDirector.update(CTO);
    serializationDirector.read();

    serializationDeveloper.read();
    serializationTester.read();

    serializationDeveloper.delete();
    serializationTester.delete();

    deleteSchema(dataSource);
  }

  @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
  private static void deleteSchema(final DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(Serialization.DELETE_SCHEMA_SQL);
    }
  }

  @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
  private static void createSchema(final DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(Serialization.CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
