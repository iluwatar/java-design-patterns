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

package com.iluwatar.domainmodel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoImplTest {

  public static final String INSERT_CUSTOMER_SQL = "insert into CUSTOMERS values('customer', 100)";
  public static final String SELECT_CUSTOMERS_SQL = "select name, money from CUSTOMERS";
  public static final String INSERT_PURCHASES_SQL =
      "insert into PURCHASES values('product', 'customer')";
  public static final String SELECT_PURCHASES_SQL =
      "select product_name, customer_name from PURCHASES";

  private DataSource dataSource;
  private Product product;
  private Customer customer;
  private CustomerDao customerDao;

  @BeforeEach
  void setUp() throws SQLException {
    // create db schema
    dataSource = TestUtils.createDataSource();

    TestUtils.deleteSchema(dataSource);
    TestUtils.createSchema(dataSource);

    // setup objects
    customerDao = new CustomerDaoImpl(dataSource);

    customer = Customer.builder().name("customer").money(100.0).customerDao(customerDao).build();

    product =
        Product.builder()
            .name("product")
            .price(100.0)
            .expirationDate(LocalDate.parse("2021-06-27"))
            .productDao(new ProductDaoImpl(dataSource))
            .build();
  }

  @AfterEach
  void tearDown() throws SQLException {
    TestUtils.deleteSchema(dataSource);
  }

  @Test
  void findByNameTest() throws SQLException {
    var customer = customerDao.findByName("customer");

    assertTrue(customer.isEmpty());

    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);

    customer = customerDao.findByName("customer");

    assertTrue(customer.isPresent());
    assertEquals(customer.get().getName(), "customer");
    assertEquals(customer.get().getMoney(), 100);
  }

  @Test
  void saveTest() throws SQLException {
    customerDao.save(customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_CUSTOMERS_SQL)) {

      assertTrue(rs.next());
      assertEquals(rs.getString("name"), customer.getName());
      assertEquals(rs.getInt("money"), customer.getMoney());
    }

    assertThrows(SQLException.class, () -> customerDao.save(customer));
  }

  @Test
  void updateTest() throws SQLException {
    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);

    customer.setMoney(99.0);

    customerDao.update(customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_CUSTOMERS_SQL)) {

      assertTrue(rs.next());
      assertEquals(rs.getString("name"), customer.getName());
      assertEquals(rs.getDouble("money"), customer.getMoney());
      assertFalse(rs.next());
    }
  }

  @Test
  void addProductTest() throws SQLException {
    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);
    TestUtils.executeSQL(ProductDaoImplTest.INSERT_PRODUCT_SQL, dataSource);

    customerDao.addProduct(product, customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_PURCHASES_SQL)) {

      assertTrue(rs.next());
      assertEquals(rs.getString("product_name"), product.getName());
      assertEquals(rs.getString("customer_name"), customer.getName());
      assertFalse(rs.next());
    }
  }

  @Test
  void deleteProductTest() throws SQLException {
    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);
    TestUtils.executeSQL(ProductDaoImplTest.INSERT_PRODUCT_SQL, dataSource);
    TestUtils.executeSQL(INSERT_PURCHASES_SQL, dataSource);

    customerDao.deleteProduct(product, customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_PURCHASES_SQL)) {

      assertFalse(rs.next());
    }
  }
}
