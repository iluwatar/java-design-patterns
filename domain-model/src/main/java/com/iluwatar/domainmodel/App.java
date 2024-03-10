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
package com.iluwatar.domainmodel;

import static org.joda.money.CurrencyUnit.USD;

import java.sql.SQLException;
import java.time.LocalDate;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.joda.money.Money;


/**
 * Domain Model pattern is a more complex solution for organizing domain logic than Transaction
 * Script and Table Module. It provides an object-oriented way of dealing with complicated logic.
 * Instead of having one procedure that handles all business logic for a user action like
 * Transaction Script there are multiple objects and each of them handles a slice of domain logic
 * that is relevant to it. The significant difference between Domain Model and Table Module pattern
 * is that in Table Module a single class encapsulates all the domain logic for all records stored
 * in table when in Domain Model every single class represents only one record in underlying table.
 *
 * <p>In this example, we will use the Domain Model pattern to implement buying of products
 * by customers in a Shop. The main method will create a customer and a few products.
 * Customer will do a few purchases, try to buy product which are too expensive for him,
 * return product which he bought to return money.</p>
 */
public class App {

  public static final String H2_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

  public static final String CREATE_SCHEMA_SQL =
      "CREATE TABLE CUSTOMERS (name varchar primary key, money decimal);"
          + "CREATE TABLE PRODUCTS (name varchar primary key, price decimal, expiration_date date);"
          + "CREATE TABLE PURCHASES ("
          + "product_name varchar references PRODUCTS(name),"
          + "customer_name varchar references CUSTOMERS(name));";

  public static final String DELETE_SCHEMA_SQL =
      "DROP TABLE PURCHASES IF EXISTS;"
          + "DROP TABLE CUSTOMERS IF EXISTS;"
          + "DROP TABLE PRODUCTS IF EXISTS;";

  /**
   * Program entry point.
   *
   * @param args command line arguments
   * @throws Exception if any error occurs
   */
  public static void main(String[] args) throws Exception {

    // Create data source and create the customers, products and purchases tables
    final var dataSource = createDataSource();
    deleteSchema(dataSource);
    createSchema(dataSource);

    // create customer
    var customerDao = new CustomerDaoImpl(dataSource);

    var tom =
        Customer.builder()
            .name("Tom")
            .money(Money.of(USD, 30))
            .customerDao(customerDao)
            .build();

    tom.save();

    // create products
    var productDao = new ProductDaoImpl(dataSource);

    var eggs =
        Product.builder()
            .name("Eggs")
            .price(Money.of(USD, 10.0))
            .expirationDate(LocalDate.now().plusDays(7))
            .productDao(productDao)
            .build();

    var butter =
        Product.builder()
            .name("Butter")
            .price(Money.of(USD, 20.00))
            .expirationDate(LocalDate.now().plusDays(9))
            .productDao(productDao)
            .build();

    var cheese =
        Product.builder()
            .name("Cheese")
            .price(Money.of(USD, 25.0))
            .expirationDate(LocalDate.now().plusDays(2))
            .productDao(productDao)
            .build();

    eggs.save();
    butter.save();
    cheese.save();

    // show money balance of customer after each purchase
    tom.showBalance();
    tom.showPurchases();

    // buy eggs
    tom.buyProduct(eggs);
    tom.showBalance();

    // buy butter
    tom.buyProduct(butter);
    tom.showBalance();

    // trying to buy cheese, but receive a refusal
    // because he didn't have enough money
    tom.buyProduct(cheese);
    tom.showBalance();

    // return butter and get money back
    tom.returnProduct(butter);
    tom.showBalance();

    // Tom can buy cheese now because he has enough money
    // and there is a discount on cheese because it expires in 2 days
    tom.buyProduct(cheese);

    tom.save();

    // show money balance and purchases after shopping
    tom.showBalance();
    tom.showPurchases();
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setUrl(H2_DB_URL);
    return dataSource;
  }

  private static void deleteSchema(DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(CREATE_SCHEMA_SQL);
    }
  }
}
