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

package com.iluwatar.serializedentity;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Serialized Entity design pattern.
 * Saves an object by serializing it and storing it in a database.
 *
 * App.
 */
@Slf4j
public final class App {
  /**
   * Local database to test against
   */
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
  public static void main(final String[] args) throws SQLException, IOException, ClassNotFoundException {
    // Create data source and create the person table.
    final var dataSource = createDataSource();

    // Drop table Positions if exists
    deleteSchema(dataSource);

    // Create table Positions if not exists
    createSchema(dataSource);

    final var human = new Taxonomy (
            1,
            "Eukarya",
            "Animalia",
            "Chordata",
            "Mammalia",
            "Primates",
            "Hominidae",
            "Homo",
            "H. sapiens");

    final var fruitFly = new Taxonomy (
            2,
            "Eukarya",
            "Animalia",
            "Arthropoda",
            "Insecta",
            "Diptera",
            "Drosophilidae",
            "Drosophila",
            "D. melanogaster");

    final var pea = new Taxonomy (
            3,
            "Eukarya",
            "Plantae",
            "Magnoliophyta",
            "Magnoliopsida",
            "Fabales",
            "Fabaceae",
            "Pisum",
            "\tP. sativum");


    // Initialize Serializations to perform CRUD operations on these positions.
    final var serializeHuman = new Serialization(human, dataSource);
    final var serializeFly = new Serialization(fruitFly, dataSource);
    final var serializePea = new Serialization(pea, dataSource);

    // Insert serialized entities into database.
    serializeHuman.insert();
    serializeFly.insert();
    serializePea.insert();

    // Read serialized entity from database.
    serializeHuman.read();

    // Update serialized entity to something intentionally wrong
    serializeFly.read();
    serializeFly.update(human);
    serializeFly.read();

    // Delete serialized entity from database.
    serializeHuman.delete();
    serializeFly.delete();
  }

  /**
   * Private helper method for deleting SQL schema.
   *
   */
  private static void deleteSchema(final DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(com.iluwatar.serializedentity.Serialization.DELETE_SCHEMA_SQL);
    }
  }

  /**
   * Private helper method for creating SQL schema.
   *
   */
  private static void createSchema(final DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(com.iluwatar.serializedentity.Serialization.CREATE_SCHEMA_SQL);
    }
  }

  /**
   * Private helper method for creating SQL data source.
   *
   */
  private static DataSource createDataSource() {
    final var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
