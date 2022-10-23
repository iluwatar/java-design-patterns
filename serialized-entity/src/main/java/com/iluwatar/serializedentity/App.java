/*
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;


/**
 * Serialized Country objects and store them into database.
 * Read data from database and deserialize them and reconstruct to Country objects.
 */
@Slf4j
public class App {
  private static final String DB_URL = "jdbc:h2:~/test";

  private App() {

  }

  /**
   * Program entry point.
   * @param args command line args.
   * @throws IOException if any
   * @throws ClassNotFoundException if any
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    final var dataSource = createDataSource();

    deleteSchema(dataSource);
    createSchema(dataSource);

    final var China = new Country(
            86,
            "China",
            "Asia",
            "Chinese"
    );

    final var UnitedArabEmirates = new Country(
            971,
            "United Arab Emirates",
            "Asia",
            "Arabic"
    );

    final var serializedChina = new CountrySchemaSql(China, dataSource);
    final var serializedUnitedArabEmirates = new CountrySchemaSql(UnitedArabEmirates, dataSource);
    serializedChina.insertCountry();
    serializedUnitedArabEmirates.insertCountry();

    serializedChina.selectCountry();
    serializedUnitedArabEmirates.selectCountry();
  }

  private static void deleteSchema(DataSource dataSource) {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(CountrySchemaSql.DELETE_SCHEMA_SQL);
    } catch (SQLException e) {
      LOGGER.info("Exception thrown " + e.getMessage());
    }
  }

  private static void createSchema(DataSource dataSource) {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(CountrySchemaSql.CREATE_SCHEMA_SQL);
    } catch (SQLException e) {
      LOGGER.info("Exception thrown " + e.getMessage());
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
