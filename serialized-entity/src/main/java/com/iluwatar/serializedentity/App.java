/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
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
 * Serialized Entity Pattern.
 *
 * <p> Serialized Entity Pattern allow us to easily persist Java objects to the database. It uses Serializable interface
 * and DAO pattern. Serialized Entity Pattern will first use Serializable to convert a Java object into a set of bytes,
 * then it will using DAO pattern to store this set of bytes as BLOB to database.</p>
 *
 * <p> In this example, we first initialize two Java objects (Country) "China" and "UnitedArabEmirates", then we
 * initialize "serializedChina" with "China" object and "serializedUnitedArabEmirates" with "UnitedArabEmirates",
 * then we use method "serializedChina.insertCountry()" and "serializedUnitedArabEmirates.insertCountry()" to serialize
 * "China" and "UnitedArabEmirates" and persist them to database.
 * Last, with "serializedChina.selectCountry()" and "serializedUnitedArabEmirates.selectCountry()" we could read "China"
 * and "UnitedArabEmirates" from database as sets of bytes, then deserialize them back to Java object (Country). </p>
 *
 */
@Slf4j
public class App {

  private static final String DB_URL = "jdbc:h2:~/testdb";

  private App() {}

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

    // Initializing Country Object China
    final var China = new Country(
            86,
            "China",
            "Asia",
            "Chinese"
    );

    // Initializing Country Object UnitedArabEmirates
    final var UnitedArabEmirates = new Country(
            971,
            "United Arab Emirates",
            "Asia",
            "Arabic"
    );

    // Initializing CountrySchemaSql Object with parameter "China" and "dataSource"
    final var serializedChina = new CountrySchemaSql(China, dataSource);
    // Initializing CountrySchemaSql Object with parameter "UnitedArabEmirates" and "dataSource"
    final var serializedUnitedArabEmirates = new CountrySchemaSql(UnitedArabEmirates, dataSource);

    /*
    By using CountrySchemaSql.insertCountry() method, the private (Country) type variable  within Object
    CountrySchemaSql will be serialized to a set of bytes and persist to database.
    For more details of CountrySchemaSql.insertCountry() method please refer to CountrySchemaSql.java file
    */
    serializedChina.insertCountry();
    serializedUnitedArabEmirates.insertCountry();

    /*
    By using CountrySchemaSql.selectCountry() method, CountrySchemaSql object will read the sets of bytes from database
    and deserialize it to Country object.
    For more details of CountrySchemaSql.selectCountry() method please refer to CountrySchemaSql.java file
    */
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