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

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.joda.money.CurrencyUnit.USD;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoImplTest {

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

    customer = Customer.builder().name("customer").money(Money.of(CurrencyUnit.USD,100.0)).customerDao(customerDao).build();

    product =
        Product.builder()
            .name("product")
            .price(Money.of(USD, 100.0))
            .expirationDate(LocalDate.parse("2021-06-27"))
            .productDao(new ProductDaoImpl(dataSource))
            .build();
  }

  @AfterEach
  void tearDown() throws SQLException {
    TestUtils.deleteSchema(dataSource);
  }

  @Test
  void shouldFindCustomerByName() throws SQLException {
    var customer = customerDao.findByName("customer");

    assertTrue(customer.isEmpty());

    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);

    customer = customerDao.findByName("customer");

    assertTrue(customer.isPresent());
    assertEquals("customer", customer.get().getName());
    assertEquals(Money.of(USD, 100), customer.get().getMoney());
  }

  @Test
  void shouldSaveCustomer() throws SQLException {
    customerDao.save(customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_CUSTOMERS_SQL)) {

      assertTrue(rs.next());
      assertEquals(customer.getName(), rs.getString("name"));
      assertEquals(customer.getMoney(), Money.of(USD, rs.getBigDecimal("money")));
    }

    assertThrows(SQLException.class, () -> customerDao.save(customer));
  }

  @Test
  void shouldUpdateCustomer() throws SQLException {
    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);

    customer.setMoney(Money.of(CurrencyUnit.USD, 99));

    customerDao.update(customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_CUSTOMERS_SQL)) {

      assertTrue(rs.next());
      assertEquals(customer.getName(), rs.getString("name"));
      assertEquals(customer.getMoney(), Money.of(USD, rs.getBigDecimal("money")));
      assertFalse(rs.next());
    }
  }

  @Test
  void shouldAddProductToPurchases() throws SQLException {
    TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource);
    TestUtils.executeSQL(ProductDaoImplTest.INSERT_PRODUCT_SQL, dataSource);

    customerDao.addProduct(product, customer);

    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_PURCHASES_SQL)) {

      assertTrue(rs.next());
      assertEquals(product.getName(), rs.getString("product_name"));
      assertEquals(customer.getName(), rs.getString("customer_name"));
      assertFalse(rs.next());
    }
  }

  @Test
  void shouldDeleteProductFromPurchases() throws SQLException {
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
