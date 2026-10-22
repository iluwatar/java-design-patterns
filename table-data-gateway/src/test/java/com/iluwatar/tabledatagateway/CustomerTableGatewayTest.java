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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTableGatewayTest {

  private static final String DB_URL = "jdbc:h2:mem:tabledatagatewaytest;DB_CLOSE_DELAY=-1";

  private DataSource dataSource;
  private CustomerTableGateway gateway;

  @BeforeEach
  void setUp() throws SQLException {
    dataSource = createDataSource();
    execute(CustomerTableGateway.DELETE_SCHEMA_SQL);
    execute(CustomerTableGateway.CREATE_SCHEMA_SQL);
    gateway = new CustomerTableGateway(dataSource);
  }

  @AfterEach
  void tearDown() throws SQLException {
    execute(CustomerTableGateway.DELETE_SCHEMA_SQL);
  }

  private static DataSource createDataSource() {
    var source = new JdbcDataSource();
    source.setURL(DB_URL);
    return source;
  }

  private static void execute(final String sql) throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL);
        var statement = connection.createStatement()) {
      statement.execute(sql);
    }
  }

  @Test
  void insertAndFindByIdShouldReturnPersistedRow() throws SQLException {
    var customer = new Customer(1, "Alice", "Smith");

    assertEquals(1, gateway.insert(customer));
    assertEquals(customer, gateway.findById(1).orElseThrow());
  }

  @Test
  void findByIdShouldReturnEmptyWhenRowDoesNotExist() throws SQLException {
    assertTrue(gateway.findById(99).isEmpty());
  }

  @Test
  void findAllShouldReturnRowsInIdOrder() throws SQLException {
    gateway.insert(new Customer(2, "Bob", "Jones"));
    gateway.insert(new Customer(1, "Alice", "Smith"));

    assertEquals(
        List.of(new Customer(1, "Alice", "Smith"), new Customer(2, "Bob", "Jones")),
        gateway.findAll());
  }

  @Test
  void updateShouldPersistNewValuesAndReportMissingRows() throws SQLException {
    gateway.insert(new Customer(1, "Alice", "Smith"));

    assertEquals(1, gateway.update(new Customer(1, "Alice", "Jones")));
    assertEquals("Jones", gateway.findById(1).orElseThrow().lastName());
    assertEquals(0, gateway.update(new Customer(99, "No", "Row")));
  }

  @Test
  void deleteShouldRemoveRowAndReportMissingRows() throws SQLException {
    gateway.insert(new Customer(1, "Alice", "Smith"));

    assertEquals(1, gateway.delete(1));
    assertEquals(0, gateway.delete(1));
    assertTrue(gateway.findById(1).isEmpty());
  }
}
