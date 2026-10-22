/*
 * This project is licensed under the MIT license. Module table-data-gateway uses H2 database licensed under EPL (see epl-1.0.txt).
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
package com.iluwatar.tabledatagateway;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Table Data Gateway pattern demonstration. */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final String DB_URL = "jdbc:h2:mem:tabledatagateway;DB_CLOSE_DELAY=-1";

  /**
   * Program entry point.
   *
   * @param args command line args
   * @throws SQLException if a database operation fails
   */
  public static void main(final String[] args) throws SQLException {
    var dataSource = createDataSource();
    execute(dataSource, CustomerTableGateway.CREATE_SCHEMA_SQL);

    var gateway = new CustomerTableGateway(dataSource);
    gateway.insert(new Customer(1, "Alice", "Smith"));

    var found = gateway.findById(1);
    LOGGER.info("Found customer: {}", found.orElse(null));

    gateway.update(new Customer(1, "Alice", "Jones"));
    LOGGER.info("Customers: {}", gateway.findAll());

    gateway.delete(1);
    LOGGER.info("Customers after delete: {}", gateway.findAll());

    execute(dataSource, CustomerTableGateway.DELETE_SCHEMA_SQL);
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  private static void execute(final DataSource dataSource, final String sql) throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(sql);
    }
  }
}
