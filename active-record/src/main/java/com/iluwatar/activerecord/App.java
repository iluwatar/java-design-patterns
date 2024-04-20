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
package com.iluwatar.activerecord;

import static com.iluwatar.activerecord.SchemaConstants.CREATE_SCHEMA_SQL;

import com.iluwatar.activerecord.base.RecordBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

/**
 * The main application for the manual testing purposes.
 */
@Slf4j
public class App {

  private static final String DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1";

  /**
   * Java main method to execute all the logic out there.
   *
   * @param args arguments.
   * @throws Exception Any sort of exception that has to be picked up by the JVM.
   */
  public static void main(final String[] args) throws Exception {
    final DataSource dataSource = createDataSource();
    createSchema(dataSource);
    RecordBase.setDataSource(dataSource);
    executeOperation();
  }

  private static void executeOperation() {
    LOGGER.info("saving the customer data...");
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setCustomerNumber("C123");
    customer.setFirstName("John");
    customer.setLastName("Smith");

    Order order = new Order();
    order.setId(1L);
    order.setOrderNumber("O123");

    // customer.addOrder(order);
    customer.save(Customer.class);

    LOGGER.info("The customer data by ID={}", customer.findById(1L, Customer.class));

    LOGGER.info("find all the customers={}", customer.findAll(Customer.class));
  }

  private static void createSchema(DataSource dataSource) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.execute(CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

}
