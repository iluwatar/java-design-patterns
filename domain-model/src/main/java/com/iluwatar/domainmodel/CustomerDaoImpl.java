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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.joda.money.Money;

/**
 * Implementations for database operations of Customer.
 */
public class CustomerDaoImpl implements CustomerDao {

  private final DataSource dataSource;

  public CustomerDaoImpl(final DataSource userDataSource) {
    this.dataSource = userDataSource;
  }

  @Override
  public Optional<Customer> findByName(String name) throws SQLException {
    var sql = "select * from CUSTOMERS where name = ?;";

    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, name);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        return Optional.of(
            Customer.builder()
                .name(rs.getString("name"))
                .money(Money.of(USD, rs.getBigDecimal("money")))
                .customerDao(this)
                .build());
      } else {
        return Optional.empty();
      }
    }
  }

  @Override
  public void update(Customer customer) throws SQLException {
    var sql = "update CUSTOMERS set money = ? where name = ?;";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setBigDecimal(1, customer.getMoney().getAmount());
      preparedStatement.setString(2, customer.getName());
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void save(Customer customer) throws SQLException {
    var sql = "insert into CUSTOMERS (name, money) values (?, ?)";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, customer.getName());
      preparedStatement.setBigDecimal(2, customer.getMoney().getAmount());
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void addProduct(Product product, Customer customer) throws SQLException {
    var sql = "insert into PURCHASES (product_name, customer_name) values (?,?)";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, product.getName());
      preparedStatement.setString(2, customer.getName());
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void deleteProduct(Product product, Customer customer) throws SQLException {
    var sql = "delete from PURCHASES where product_name = ? and customer_name = ?";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, product.getName());
      preparedStatement.setString(2, customer.getName());
      preparedStatement.executeUpdate();
    }
  }
}
