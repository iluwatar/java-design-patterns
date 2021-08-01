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

import static org.joda.money.CurrencyUnit.USD;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.joda.money.Money;


public class ProductDaoImpl implements ProductDao {

  private final DataSource dataSource;

  public ProductDaoImpl(final DataSource userDataSource) {
    this.dataSource = userDataSource;
  }

  @Override
  public Optional<Product> findByName(String name) throws SQLException {
    var sql = "select * from PRODUCTS where name = ?;";

    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, name);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        return Optional.of(
            Product.builder()
                .name(rs.getString("name"))
                .price(Money.of(USD, rs.getBigDecimal("price")))
                .expirationDate(rs.getDate("expiration_date").toLocalDate())
                .productDao(this)
                .build());
      } else {
        return Optional.empty();
      }
    }
  }

  @Override
  public void save(Product product) throws SQLException {
    var sql = "insert into PRODUCTS (name, price, expiration_date) values (?, ?, ?)";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, product.getName());
      preparedStatement.setBigDecimal(2, product.getPrice().getAmount());
      preparedStatement.setDate(3, Date.valueOf(product.getExpirationDate()));
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void update(Product product) throws SQLException {
    var sql = "update PRODUCTS set price = ?, expiration_date = ? where name = ?;";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setBigDecimal(1, product.getPrice().getAmount());
      preparedStatement.setDate(2, Date.valueOf(product.getExpirationDate()));
      preparedStatement.setString(3, product.getName());
      preparedStatement.executeUpdate();
    }
  }
}
